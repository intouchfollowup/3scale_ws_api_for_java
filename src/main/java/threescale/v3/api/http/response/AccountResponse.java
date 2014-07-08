package threescale.v3.api.http.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.xom.Element;
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;

public class AccountResponse extends AbstractResponse {

	private String accountID;
	private String state;
	private String organisationName;
	private List<ApplicationResponse> applications = new ArrayList<ApplicationResponse>();

	public AccountResponse(HttpResponse response) throws ServerError {
		super(response);
	}

	@Override
	public void initSuccessResponse() throws ServerError {
		Element rootElement = getRootElement();

		accountID = getFirstChildElementValue(rootElement, "id");
		state = getFirstChildElementValue(rootElement, "state");
		organisationName = getFirstChildElementValue(rootElement, "org_name");

		List<Element> elementList = getFirstChildElementList(rootElement, "applications", "application");
		if(elementList.isEmpty()) {
			for (Element element : elementList) {
				applications.add(new ApplicationResponse(element));
			}
		}
	}

	public String getAccountID() {
		return accountID;
	}

	public String getState() {
		return state;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public List<ApplicationResponse> getApplications() {
		return Collections.unmodifiableList(applications);
	}
}
