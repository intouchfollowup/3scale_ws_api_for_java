package threescale.v3.api;

/**
 * Class for constants for use around the {@link ServiceApi}
 *
 * @author Moncef
 */
public class ServiceApiConstants {

	public static final String HTTP_PROTOCAL = "http://";
	public static final String HTTPS_PROTOCAL = "https://";
	public static final String DEFAULT_HOST = "su1.3scale.net";
	public static final String DEFAULT_HOST_URL= HTTP_PROTOCAL + DEFAULT_HOST;

	// request parameters
	public static final String PROVIDER_KEY_PARAMETER = "provider_key";
	public static final String APPLICATION_ID_PARAMETER = "app_id";
	public static final String SERVICE_ID_PARAMETER = "service_id";
	public static final String TRANSACTIONS_ID_PARAMETER = "transactions";
	public static final String USAGE_PARAMETER = "usage";
	public static final String HITS_PARAMETER = "hits";

	// api location uri's
	public static final String TRANSACTIONS_URL = "/transactions.xml";
	public static final String TRANSACTIONS_AUTHORIZE_URL = "/transactions/authorize.xml";
	public static final String TRANSACTIONS_OAUTH_AUTHORIZE_URL = "/transactions/oauth_authorize.xml";
	public static final String TRANSACTIONS_AUTHREP_URL = "/transactions/authrep.xml";
	public static final String ADMIN_API_SIGNUP_URL = "/admin/api/signup.xml";

}
