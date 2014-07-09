package threescale.v3.api.impl;

import static threescale.v3.api.ServiceApiConstants.ADMIN_API_SIGNUP_URL;
import threescale.v3.api.AccountApi;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AccountResponse;

/**
 *
 * @author Moncef
 *
 */
public class AcountApiDriver extends ApiDriver implements AccountApi{

	@Override
	public AccountResponse signup(ParameterMap parameters) throws ServerError {
		return new AccountResponse(post(ADMIN_API_SIGNUP_URL, parameters));
	}
}
