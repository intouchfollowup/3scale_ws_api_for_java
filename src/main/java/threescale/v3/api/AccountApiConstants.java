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
	public static final String ADMIN_API_SIGNUP_URL = "/admin/api/signup.xml";
	public static final String ADMIN_API_APPLICATIONS_FIND_URL = "/admin/api/applications/find.xml";
	public static final String ADMIN_API_SERVICES_READ = "/admin/api/services/%s.xml";
	public static final String ADMIN_API_SERVICES_UPDATE = "/admin/api/services/%s.xml";
	public static final String ADMIN_API_SERVICES_APPLICATION_READ_URL = "/admin/api/services/%s/application_plans/%s.xml";
	public static final String ADMIN_API_SERVICES_APPLICATION_DELETE_URL = "/admin/api/services/%s/application_plans/%s.xml";
}
