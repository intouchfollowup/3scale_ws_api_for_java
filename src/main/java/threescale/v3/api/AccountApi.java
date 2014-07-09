package threescale.v3.api;

import threescale.v3.api.http.response.AccountResponse;

public interface AccountApi {

    public AccountResponse signup(ParameterMap parameters) throws ServerError;
}
