package threescale.v3.api;

import threescale.v3.api.http.response.Response;
import threescale.v3.xml.elements.account.Account;
import threescale.v3.xml.elements.application.Application;
import threescale.v3.xml.elements.applicationplan.ApplicationPlan;
import threescale.v3.xml.elements.applicationplan.ApplicationPlans;
import threescale.v3.xml.elements.service.Service;

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
     * @return {@link Response}
	 *
	 * @throws ServerError
	 */
    public Response<Account>  signup(ParameterMap parameters) throws ServerError;

    /**
     * Finds an application by keys used on the integration of your API and
     * 3scale's Service Management API or by id (no need to know the account_id).
     * @param parameters - {@link ParameterMap}
     * @return {@link Response}
	 *
	 * @throws ServerError
	 */
    public Response<Application>  findApplication(ParameterMap parameters) throws ServerError;

    /**
     * Reads a {@link Service} in to the {@link Response} by service id
     *
     * @param serviceId - required id of the service
     * @return {@link Response}
     * @throws ServerError
     */
    public Response<Service> readService(String serviceId) throws ServerError;

    /**
     * Updates an existing {@link Service} and returns it in {@link Response} by service id
     *
     * @param serviceId - required id of the service
     * @return {@link Response}
     * @throws ServerError
     */
	public Response<Service> updateService(String serviceId, ParameterMap parameterMap) throws ServerError;

	/**
     * Creates a {@link ApplicationPlan} and returns the created on in to the {@link Response}
     *
     * @param applicationPlan - required {@link ApplicationPlan}
     * @return {@link Response}
     * @throws ServerError
     */
	public Response<ApplicationPlan> createApplicationPlan(ApplicationPlan applicationPlan) throws ServerError;

    /**
     * Reads a {@link ApplicationPlan} in to the {@link Response}
     * by service id and application plan id
     *
     * @param serviceId - required id of the service
     * @param applicationPlanId - required id of the application plan
     * @return {@link Response}
     * @throws ServerError
     */
	public Response<ApplicationPlan> readApplicationPlan(String serviceId, String applicationPlanId) throws ServerError;

	/**
     * Retrieves {@link ApplicationPlans} in to the {@link Response}
     * by service id
     *
     * @param serviceId - required id of the service
     * @return {@link Response}
     * @throws ServerError
     */
	public Response<ApplicationPlans> listApplicatonPlans(String serviceId) throws ServerError;

	/**
     * Delete an existing {@link ApplicationPlan} and returns it in {@link Response}
     * by service id and application plan id
     *
     * @param serviceId - required id of the service
     * @param applicationPlanId - required id of the application plan
     * @return {@link Response}
     * @throws ServerError
     */
	public Response<ApplicationPlan> deleteApplicationPlan(String serviceId, String applicationPlanId) throws ServerError;


}
