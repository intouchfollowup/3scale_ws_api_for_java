package threescale.v3.api.http.response;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.xml.response.Application;

/**
 *
 * @author Moncef
 *
 */
public class ApplicationResponse extends AbstractResponse<Application> {

	public ApplicationResponse(HttpResponse response) throws ServerError {
		super(response, Application.class);
	}

	public Application getApplication() {
		return getRoot();
	}
}
