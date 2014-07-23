package threescale.v3.api;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_READ;
import static threescale.v3.api.AccountApiConstants.HTTPS_PROTOCAL;
import static threescale.v3.api.AccountApiConstants.HTTP_PROTOCAL;
import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.xml.bind.response.service.ServiceTest.SERVICE_ID;
import static threescale.v3.xml.bind.response.service.ServiceTest.SERVICE_XML;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import threescale.v3.api.http.response.service.ServiceResponse;
import threescale.v3.api.impl.AccountApiDriver;
import threescale.v3.xml.response.service.Service;

public class AccountApiDriverTest {

    @Rule
    private JUnitRuleMockery context = new JUnitRuleMockery();

    private final String provider_key = "1234abcd";
    private final String host = "testurl.3scale.net";

    private ServerAccessor htmlServer;
	private AccountApiDriver accountApi;

    @Before
    public void setup() {
        htmlServer = context.mock(ServerAccessor.class);
        accountApi = (AccountApiDriver) new AccountApiDriver(provider_key, host).setServer(htmlServer);
    }

    @Test
    public void testReadService() throws ServerError {
    	String url  =  buildMockUrl(format(ADMIN_API_SERVICES_READ, SERVICE_ID));
    	mockHtmlServerGet(url, 200, SERVICE_XML);

    	ServiceResponse serviceResponse = accountApi.readService(SERVICE_ID);
		Service service = serviceResponse.getBody();

		assertThat(service.getId(), equalTo(SERVICE_ID));
    }

    private String buildMockUrl(String path) {
    	return new StringBuffer() //
    		.append(accountApi.getUseHttps() ? HTTPS_PROTOCAL : HTTP_PROTOCAL) //
    		.append(host) //
    		.append(path) //
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
}
