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
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.Response;
import threescale.v3.xml.elements.account.Account;
import threescale.v3.xml.elements.application.Application;
import threescale.v3.xml.elements.applicationplan.ApplicationPlan;
import threescale.v3.xml.elements.service.Service;

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
	public Response<Account> signup(ParameterMap parameters) throws ServerError {
		return createResponse(post(SIGNUP_URL, parameters), Account.class);
	}

	@Override
	public Response<Application> findApplication(ParameterMap parameters) throws ServerError {
		return createResponse(get(APPLICATIONS_FIND_URL, parameters), Application.class);
	}

	@Override
	public Response<Service>  readService(String serviceId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		HttpResponse httpResponse = get(format(SERVICES_READ_URL, serviceId));
		return createResponse(httpResponse, Service.class);
	}

	@Override
	public Response<Service> updateService(String serviceId, ParameterMap parameterMap) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);

		HttpResponse httpResponse = put(format(SERVICES_UPDATE_URL, serviceId), parameterMap);
		return createResponse(httpResponse, Service.class);
	}

	@Override
	public Response<ApplicationPlan> deleteApplicationPlan(String serviceId, String applicationPlanId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);
		notNull(applicationPlanId, APPLICATION_ID_REQUIRED_MESSAGE);

		HttpResponse httpResponse = delete(format(APPLICATION_PLAN_DELETE_URL, serviceId, applicationPlanId));
		return createResponse(httpResponse, ApplicationPlan.class);
	}

	@Override
	public Response<ApplicationPlan> readApplicationPlan(String serviceId, String applicationPlanId) throws ServerError {
		notNull(serviceId, SERVICE_ID_REQUIRED_MESSAGE);
		notNull(applicationPlanId, APPLICATION_ID_REQUIRED_MESSAGE);

		HttpResponse httpResponse = get(format(APPLICATION_PLAN_READ_URL, serviceId, applicationPlanId));
		return createResponse(httpResponse, ApplicationPlan.class);
	}

	private <T> Response<T> createResponse(HttpResponse httpResponse, Class<T> clazz) throws ServerError {
		return new Response<T>(httpResponse, clazz);
	}
}
