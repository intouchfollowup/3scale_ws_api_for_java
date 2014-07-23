package threescale.v3.api.impl;

import static threescale.v3.api.ParameterConstants.HITS_PARAMETER;
import static threescale.v3.api.ParameterConstants.SERVICE_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.TRANSACTIONS_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.USAGE_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_AUTHORIZE_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_AUTHREP_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_OAUTH_AUTHORIZE_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_URL;
import static threescale.v3.utils.ObjectUtils.isNotNull;
import static threescale.v3.utils.ObjectUtils.isNull;
import threescale.v3.api.AuthorizeResponse;
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ReportResponse;
import threescale.v3.api.ServerAccessor;
import threescale.v3.api.ServerError;
import threescale.v3.api.ServiceApi;

/**
 * Concrete implementation of the ServiceApi.
 *
 * @see ServiceApi
 */
public class ServiceApiDriver extends ApiDriver implements ServiceApi {

	public ServiceApiDriver() {
	}

	public ServiceApiDriver(String providerKey, boolean useHttps) {
		super(providerKey, useHttps);
	}

	public ServiceApiDriver(String providerKey, String host, boolean useHttps) {
		super(providerKey, host, useHttps);
	}

	public ServiceApiDriver(String providerKey, String host) {
		super(providerKey, host);
	}

	public ServiceApiDriver(String providerKey) {
		super(providerKey);
	}

	@Override
	public AuthorizeResponse authrep(ParameterMap metrics) throws ServerError {
		ParameterMap usage = metrics.getMapValue(USAGE_PARAMETER);

		if (isNull(usage)) {
			usage = new ParameterMap();
			metrics.add(USAGE_PARAMETER, usage);
		}

		if (isNull(usage.getStringValue(HITS_PARAMETER))) {
			usage.add(HITS_PARAMETER, "1");
		}

		return getAuthorize(TRANSACTIONS_AUTHREP_URL, metrics);
	}

	@Override
	public ReportResponse report(String service_id, ParameterMap... transactions) throws ServerError {
		if (transactions == null || transactions.length == 0) {
			throw new IllegalArgumentException("No transactions provided");
		}

		ParameterMap parameterMap = new ParameterMap();
		if (isNotNull(service_id)) {
			parameterMap.add(SERVICE_ID_PARAMETER, service_id);
		}

		parameterMap.add(TRANSACTIONS_ID_PARAMETER, transactions);

		return new ReportResponse(post(TRANSACTIONS_URL, parameterMap));
	}

	@Override
	public AuthorizeResponse authorize(ParameterMap parameters) throws ServerError {
		return getAuthorize(TRANSACTIONS_AUTHORIZE_URL, parameters);
	}

	@Override
	public AuthorizeResponse oauth_authorize(ParameterMap params) throws ServerError {
		return getAuthorize(TRANSACTIONS_OAUTH_AUTHORIZE_URL, params);
	}

	private AuthorizeResponse getAuthorize(String url, ParameterMap parameterMap) throws ServerError {
		return convertXmlToAuthorizeResponse(get(url, parameterMap));
	}

	private AuthorizeResponse convertXmlToAuthorizeResponse(HttpResponse res) throws ServerError {
		return new AuthorizeResponse(res.getStatus(), res.getBody());
	}

	@Override
	public ServiceApiDriver setServer(ServerAccessor server) {
		return (ServiceApiDriver) super.setServer(server);
	}
}
