package threescale.v3.xml.bind.response;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.Response;
import threescale.v3.xml.elements.application.Application;


/**
 * Test class focused on testing the Error xml output form calls to 3scale
 *
 * @author Moncef
 *
 */
public class ErrorsTest {

	@Test
	public void testUnmarshallingOfErrors() throws ServerError {
		// using ApplicationResponse as a test object
		HttpResponse httpResponse  = new HttpResponse(400, xmlWith1Error);
		Response<Application> response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(400));
		assertThat(response.hasErrors(), equalTo(true));
		assertThat(response.getErrors().size(), equalTo(1));
		assertThat(response.getErrors().get(0).getMessage(), equalTo("error1"));

		httpResponse  = new HttpResponse(400, xmlWith2Errors);
		response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(400));
		assertThat(response.hasErrors(), equalTo(true));
		assertThat(response.getErrors().size(), equalTo(2));
		assertThat(response.getErrors().get(0).getMessage(), equalTo("error1"));
		assertThat(response.getErrors().get(1).getMessage(), equalTo("error2"));

		httpResponse  = new HttpResponse(400, xmlWithNoError);
		response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(400));
		assertThat(response.hasErrors(), equalTo(false));
		assertThat(response.getErrors().size(), equalTo(0));

		httpResponse  = new HttpResponse(400, errorXml);
		response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(400));
		assertThat(response.hasErrors(), equalTo(true));
		assertThat(response.getErrors().size(), equalTo(1));
		assertThat(response.getErrors().get(0).getMessage(), equalTo("error1"));

		httpResponse  = new HttpResponse(401, "");
		response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(401));
		assertThat(response.hasErrors(), equalTo(false));
		assertThat(response.getErrors().size(), equalTo(0));

		httpResponse  = new HttpResponse(401, null);
		response = new Response<Application>(httpResponse, Application.class);

		assertThat(response.getStatus(), equalTo(401));
		assertThat(response.hasErrors(), equalTo(false));
		assertThat(response.getErrors().size(), equalTo(0));
	}

	private String xmlWithNoError = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<errors>"
			+ "</errors>";

	private String xmlWith1Error = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<errors>"
			+ "	<error>error1</error>"
			+ "</errors>";

	private String xmlWith2Errors = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<errors>"
			+ "	<error>error1</error>"
			+ "	<error>error2</error>"
			+ "</errors>";

	private String errorXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "	<error>error1</error>";
}
