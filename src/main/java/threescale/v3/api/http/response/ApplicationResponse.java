package threescale.v3.api.http.response;

import static java.lang.Boolean.parseBoolean;
import nu.xom.Element;
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;

public class ApplicationResponse extends AbstractResponse{

	private String applicationId;
	private String name;
	private String description;
	private String state;
	private String userAccountId;
	private boolean endUserRequired;
	private String serviceId;

	public ApplicationResponse(HttpResponse response) throws ServerError {
		super(response);
	}

	public ApplicationResponse(Element element) throws ServerError {
		super(element);
	}

	@Override
	protected void initSuccessResponse() throws ServerError {
		Element rootElement = getRootElement();

		applicationId = getFirstChildElementValue(rootElement, "application_id");
		name = getFirstChildElementValue(rootElement, "name");
		description = getFirstChildElementValue(rootElement, "description");
		state = getFirstChildElementValue(rootElement, "state");
		userAccountId = getFirstChildElementValue(rootElement, "user_account_id");
		endUserRequired = parseBoolean(getFirstChildElementValue(rootElement, "end_user_required"));
		serviceId = getFirstChildElementValue(rootElement, "service_id");
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getState() {
		return state;
	}

	public String getUserAccountId() {
		return userAccountId;
	}

	public boolean isEndUserRequired() {
		return endUserRequired;
	}

	public String getServiceId() {
		return serviceId;
	}
}
