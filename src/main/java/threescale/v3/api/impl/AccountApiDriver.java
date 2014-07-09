package threescale.v3.api.impl;

import static threescale.v3.api.ServiceApiConstants.ADMIN_API_SIGNUP_URL;
import threescale.v3.api.AccountApi;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AccountResponse;

/**
 * {@link AccountApi} implementation that requires the providing of a host, i.e. the
 * Account Management API does not have default host like the Service Management API.
 *
 * @author Moncef
 *
 */
public class AccountApiDriver extends ApiDriver implements AccountApi{

	public AccountApiDriver(String providerKey, String host, boolean useHttps) {
		super(providerKey, host, useHttps);
	}

	public AccountApiDriver(String providerKey, String host) {
		super(providerKey, host);
	}

	@Override
	public AccountResponse signup(ParameterMap parameters) throws ServerError {
		return new AccountResponse(post(ADMIN_API_SIGNUP_URL, parameters));
	}
}
