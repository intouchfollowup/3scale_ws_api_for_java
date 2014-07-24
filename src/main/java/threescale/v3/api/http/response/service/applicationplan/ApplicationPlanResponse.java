package threescale.v3.api.http.response.service.applicationplan;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AbstractResponse;
import threescale.v3.xml.response.ApplicationPlan;

/**
 *
 * @author Moncef
 *
 */
public class ApplicationPlanResponse extends AbstractResponse<ApplicationPlan>{

	public ApplicationPlanResponse(HttpResponse response) throws ServerError {
		super(response, ApplicationPlan.class);
	}

	@Override
	protected boolean isSuccessResponse() {
		int status = getStatus();
		return (status == 201 || status == 200);
	}
}
