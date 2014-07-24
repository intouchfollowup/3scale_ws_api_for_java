package threescale.v3.api.http.response;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.xml.elements.account.Account;

public class AccountResponse extends AbstractResponse<Account> {

	public AccountResponse(HttpResponse response) throws ServerError {
		super(response, Account.class);
	}

	@Override
	protected boolean isSuccessResponse() {
		return getStatus() == 201;
	}
}
