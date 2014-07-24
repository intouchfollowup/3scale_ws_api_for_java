package threescale.v3.api;

/**
 * Class for constants for use around the {@link AccountApi}
 *
 * @author Moncef
 */
public class AccountApiConstants {

	public static final String HTTP_PROTOCAL = "http://";
	public static final String HTTPS_PROTOCAL = "https://";

	// api location uri's
	public static final String SIGNUP_URL = "/admin/api/signup.xml";
	public static final String APPLICATIONS_FIND_URL = "/admin/api/applications/find.xml";
	public static final String SERVICES_READ_URL = "/admin/api/services/%s.xml";
	public static final String SERVICES_UPDATE_URL = "/admin/api/services/%s.xml";
	public static final String APPLICATION_PLAN_READ_URL = "/admin/api/services/%s/application_plans/%s.xml";
	public static final String APPLICATION_PLAN_LIST_URL = " /admin/api/services/%s/application_plans.xml";
	public static final String APPLICATION_PLAN_DELETE_URL = "/admin/api/services/%s/application_plans/%s.xml";
}
