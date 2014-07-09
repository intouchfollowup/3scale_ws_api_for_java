package threescale.v3.api;

import threescale.v3.api.http.response.AccountResponse;
import threescale.v3.api.http.response.ApplicationResponse;

/**
 *
 * Account Management API interface.
 *
 * @author Moncef
 *
 */
public interface AccountApi {

	/**
	 * This request allows to reproduce a sign-up from a buyer in a single API call. It will create
	 * an Account, an admin User for the account and one Application with its keys. If the plan_id
	 * are not passed the default plans will be used instead. You can add additional custom
	 * parameters that you define in Fields Definition on your Admin Portal.
	 *
	 * @param parameters - {@link ParameterMap}
	 * @return {@link AccountResponse}
	 *
	 * @throws ServerError
	 */
    public AccountResponse signup(ParameterMap parameters) throws ServerError;

    /**
     * Finds an application by keys used on the integration of your API and 3scale's Service Management API or by id (no need to know the account_id).
     * @param parameters - {@link ParameterMap}
	 * @return {@link AccountResponse}
	 *
	 * @throws ServerError
	 */
    public ApplicationResponse findApplication(ParameterMap parameters) throws ServerError;
}
