package threescale.v3.api;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static threescale.v3.api.AccountApiConstants.APPLICATION_PLAN_DELETE_URL;
import static threescale.v3.api.AccountApiConstants.APPLICATION_PLAN_READ_URL;
import static threescale.v3.api.AccountApiConstants.HTTPS_PROTOCAL;
import static threescale.v3.api.AccountApiConstants.HTTP_PROTOCAL;
import static threescale.v3.api.AccountApiConstants.SERVICES_READ_URL;
import static threescale.v3.api.AccountApiConstants.SERVICES_UPDATE_URL;
import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.xml.elements.applicationplan.ApplicationPlanTest.APPLCATION_PLAN_XML;
import static threescale.v3.xml.elements.applicationplan.ApplicationPlanTest.APPLICATON_PLAN_ID;
import static threescale.v3.xml.elements.service.ServiceTest.SERVICE_ID;
import static threescale.v3.xml.elements.service.ServiceTest.SERVICE_XML;

import javax.xml.bind.JAXBException;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import threescale.v3.api.http.response.Response;
import threescale.v3.api.impl.AccountApiDriver;
import threescale.v3.api.impl.ParameterEncoder;
import threescale.v3.xml.bind.Marshaller;
import threescale.v3.xml.elements.applicationplan.ApplicationPlan;
import threescale.v3.xml.elements.service.Service;

public class AccountApiDriverTest {

    @Rule
    private JUnitRuleMockery context = new JUnitRuleMockery();

    private final String provider_key = "1234abcd";
    private final String host = "testurl.3scale.net";
    private final String applicationPlanId = "5555555555555";


    private ServerAccessor htmlServer;
	private AccountApiDriver accountApi;
	private Marshaller marshaller = Marshaller.getInstance();


    @Before
    public void setup() {
        htmlServer = context.mock(ServerAccessor.class);
        accountApi = (AccountApiDriver) new AccountApiDriver(provider_key, host).setServer(htmlServer);
    }

    @Test
    public void testReadService() throws ServerError {
    	String url  =  buildMockGetUrl(format(SERVICES_READ_URL, SERVICE_ID));
    	mockHtmlServerGet(url, 200, SERVICE_XML);

    	Response<Service> response = accountApi.readService(SERVICE_ID);
		Service service = response.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));
    }

    @Test
    public void testUpdateService() throws ServerError, JAXBException {
    	ParameterMap parameterMap = new ParameterMap();

    	String url  =  buildMockUrl(format(SERVICES_UPDATE_URL, SERVICE_ID));
    	mockHtmlServerPut(url, parameterMap, 200, SERVICE_XML);

    	Response<Service> response =  accountApi.updateService(SERVICE_ID, parameterMap);
		Service service = response.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));

		service.setName("Test New Api Name");
		parameterMap.add("name", service.getName());
		mockHtmlServerPut(url, parameterMap, 200, marshaller.marshall(service));

		response = accountApi.updateService(SERVICE_ID, parameterMap);
		service = response.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));
		assertThat(service.getName(), equalTo("Test New Api Name"));
    }

    @Test
    public void testReadAppliationPlan() throws ServerError {

    	String url = buildMockGetUrl(format(APPLICATION_PLAN_READ_URL, SERVICE_ID, APPLICATON_PLAN_ID));
    	mockHtmlServerGet(url, 201, APPLCATION_PLAN_XML);

    	Response<ApplicationPlan> response = accountApi.readApplicationPlan(SERVICE_ID, applicationPlanId);
		ApplicationPlan applicationPlan = response.getBody();

		assertThat(applicationPlan.getId(), equalTo(APPLICATON_PLAN_ID));
		assertThat(applicationPlan.getServiceId(), equalTo(SERVICE_ID));
    }

    @Test
    public void testDeleteAppliationPlan() throws ServerError {

    	String url  =  buildMockGetUrl(format(APPLICATION_PLAN_DELETE_URL, SERVICE_ID, applicationPlanId));
    	mockHtmlServerDelete(url, 200, "");

    	Response<ApplicationPlan> response = accountApi.deleteApplicationPlan(SERVICE_ID, applicationPlanId);

    	assertThat(response.isSuccess(), equalTo(true));
    	assertThat(response.hasBody(), equalTo(false));
    }

    private String buildMockUrl(String path) {
    	return new StringBuffer() //
    	.append(accountApi.getUseHttps() ? HTTPS_PROTOCAL : HTTP_PROTOCAL) //
    	.append(host) //
    	.append(path) //
    	.toString();
    }

    private String buildMockGetUrl(String path) {
    	return new StringBuffer() //
    		.append(buildMockUrl(path)) //
    		.append("?") //
    		.append(PROVIDER_KEY_PARAMETER) //
    		.append("=") //
    		.append(provider_key) //
    		.toString();
    }

    private void mockHtmlServerGet(final String url, final int httpSatus, final String httpBody) throws ServerError {
    	context.checking(new Expectations() {{
            oneOf(htmlServer).get(url);
            will(returnValue(new HttpResponse(httpSatus, httpBody)));
        }});
    }

    private void mockHtmlServerDelete(final String url, final int httpSatus, final String httpBody) throws ServerError {
    	context.checking(new Expectations() {{
    		oneOf(htmlServer).delete(url);
    		will(returnValue(new HttpResponse(httpSatus, httpBody)));
    	}});
    }

    private void mockHtmlServerPut(final String url, final ParameterMap parameterMap, final int httpSatus, final String httpBody) throws ServerError {
    	parameterMap.add(PROVIDER_KEY_PARAMETER, provider_key);

    	final ParameterEncoder mockParameterEncoder = new ParameterEncoder();
    	context.checking(new Expectations() {{
    		oneOf(htmlServer).put(url, mockParameterEncoder.encode(parameterMap));
    		will(returnValue(new HttpResponse(httpSatus, httpBody)));
    	}});
    }
}
