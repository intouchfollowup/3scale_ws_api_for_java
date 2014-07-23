package threescale.v3.api;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_READ;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_UPDATE;
import static threescale.v3.api.AccountApiConstants.HTTPS_PROTOCAL;
import static threescale.v3.api.AccountApiConstants.HTTP_PROTOCAL;
import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.xml.bind.response.service.ServiceTest.SERVICE_ID;
import static threescale.v3.xml.bind.response.service.ServiceTest.SERVICE_XML;

import javax.xml.bind.JAXBException;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import threescale.v3.api.http.response.service.ServiceResponse;
import threescale.v3.api.impl.AccountApiDriver;
import threescale.v3.api.impl.ParameterEncoder;
import threescale.v3.xml.bind.Marshaller;
import threescale.v3.xml.response.service.Service;

public class AccountApiDriverTest {

    @Rule
    private JUnitRuleMockery context = new JUnitRuleMockery();

    private final String provider_key = "1234abcd";
    private final String host = "testurl.3scale.net";

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
    	String url  =  buildMockGetUrl(format(ADMIN_API_SERVICES_READ, SERVICE_ID));
    	mockHtmlServerGet(url, 200, SERVICE_XML);

    	ServiceResponse serviceResponse = accountApi.readService(SERVICE_ID);
		Service service = serviceResponse.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));
    }

    @Test
    public void testUpdateService() throws ServerError, JAXBException {
    	ParameterMap parameterMap = new ParameterMap();

    	String url  =  buildMockUrl(format(ADMIN_API_SERVICES_UPDATE, SERVICE_ID));
    	mockHtmlServerPut(url, parameterMap, 200, SERVICE_XML);

    	ServiceResponse serviceResponse = accountApi.updateService(SERVICE_ID, parameterMap);
		Service service = serviceResponse.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));

		service.setName("Test New Api Name");
		parameterMap.add("name", service.getName());
		mockHtmlServerPut(url, parameterMap, 200, marshaller.marshall(service));

		serviceResponse = accountApi.updateService(SERVICE_ID, parameterMap);
		service = serviceResponse.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));
		assertThat(service.getName(), equalTo("Test New Api Name"));
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

    private void mockHtmlServerPut(final String url, final ParameterMap parameterMap, final int httpSatus, final String httpBody) throws ServerError {
    	parameterMap.add(PROVIDER_KEY_PARAMETER, provider_key);

    	final ParameterEncoder mockParameterEncoder = new ParameterEncoder();
    	context.checking(new Expectations() {{
    		oneOf(htmlServer).put(url, mockParameterEncoder.encode(parameterMap));
    		will(returnValue(new HttpResponse(httpSatus, httpBody)));
    	}});
    }
}
