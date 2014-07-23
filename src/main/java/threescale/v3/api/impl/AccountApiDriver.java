package threescale.v3.api.impl;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notNull;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_APPLICATIONS_FIND_URL;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_APPLICATION_DELETE_URL;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_READ;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SERVICES_UPDATE;
import static threescale.v3.api.AccountApiConstants.ADMIN_API_SIGNUP_URL;
import threescale.v3.api.AccountApi;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AccountResponse;
import threescale.v3.api.http.response.ApplicationResponse;
import threescale.v3.api.http.response.service.ServiceResponse;
import threescale.v3.api.http.response.service.applicationplan.ApplicationPlanResponse;

/**
 * {@link AccountApi} implementation that requires the providing of a host, i.e. the
 * Account Management API does not have default host like the Service Management API.
 *
 * @author Moncef
 *
 */
public class AccountApiDriver extends ApiDriver implements AccountApi{

	private final String SERVICE_ID_REQUIRED_MESSAGE = "Service Id is required";
	private final String APPLICATION_ID_REQUIRED_MESSAGE = "Application Id is required";

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

	@Override
	public ApplicationResponse findApplication(ParameterMap parameters) throws ServerError {
		return new ApplicationResponse(get(ADMIN_API_APPLICATIONS_FIND_URL, parameters));
	}

	@Override
	public ServiceResponse readService(String serviceId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		return new ServiceResponse(get(format(ADMIN_API_SERVICES_READ, serviceId)));
	}

	@Override
	public ServiceResponse updateService(String serviceId, ParameterMap parameterMap) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		return new ServiceResponse(put(format(ADMIN_API_SERVICES_UPDATE, serviceId), parameterMap));
	}

	@Override
	public ApplicationPlanResponse deleteApplicationPlan(String serviceId, String applicationPlanId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);
		notNull(applicationPlanId, APPLICATION_ID_REQUIRED_MESSAGE);

		return new ApplicationPlanResponse(delete(format(ADMIN_API_SERVICES_APPLICATION_DELETE_URL, serviceId, applicationPlanId)));
	}
}
