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

	// api location uri's
	public static final String TRANSACTIONS_URL = "/transactions.xml";
	public static final String TRANSACTIONS_AUTHORIZE_URL = "/transactions/authorize.xml";
	public static final String TRANSACTIONS_OAUTH_AUTHORIZE_URL = "/transactions/oauth_authorize.xml";
	public static final String TRANSACTIONS_AUTHREP_URL = "/transactions/authrep.xml";
}
