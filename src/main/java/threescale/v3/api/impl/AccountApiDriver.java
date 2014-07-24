package threescale.v3.api.impl;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notNull;
import static threescale.v3.api.AccountApiConstants.APPLICATIONS_FIND_URL;
import static threescale.v3.api.AccountApiConstants.APPLICATION_PLAN_DELETE_URL;
import static threescale.v3.api.AccountApiConstants.APPLICATION_PLAN_READ_URL;
import static threescale.v3.api.AccountApiConstants.SERVICES_READ_URL;
import static threescale.v3.api.AccountApiConstants.SERVICES_UPDATE_URL;
import static threescale.v3.api.AccountApiConstants.SIGNUP_URL;
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
		return new AccountResponse(post(SIGNUP_URL, parameters));
	}

	@Override
	public ApplicationResponse findApplication(ParameterMap parameters) throws ServerError {
		return new ApplicationResponse(get(APPLICATIONS_FIND_URL, parameters));
	}

	@Override
	public ServiceResponse readService(String serviceId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		return new ServiceResponse(get(format(SERVICES_READ_URL, serviceId)));
	}

	@Override
	public ServiceResponse updateService(String serviceId, ParameterMap parameterMap) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		return new ServiceResponse(put(format(SERVICES_UPDATE_URL, serviceId), parameterMap));
	}

	@Override
	public ApplicationPlanResponse deleteApplicationPlan(String serviceId, String applicationPlanId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);
		notNull(applicationPlanId, APPLICATION_ID_REQUIRED_MESSAGE);

		return new ApplicationPlanResponse(delete(format(APPLICATION_PLAN_DELETE_URL, serviceId, applicationPlanId)));
	}

	@Override
	public ApplicationPlanResponse readApplicationPlan(String serviceId, String applicationPlanId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);
		notNull(applicationPlanId, APPLICATION_ID_REQUIRED_MESSAGE);

		return new ApplicationPlanResponse(get(format(APPLICATION_PLAN_READ_URL, serviceId, applicationPlanId)));
	}
}
