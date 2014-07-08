package threescale.v3.api.impl;

import static threescale.v3.api.ServiceApiConstants.DEFAULT_HOST;
import static threescale.v3.api.ServiceApiConstants.HITS_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.SERVICE_ID_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_AUTHORIZE_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_AUTHREP_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_ID_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_OAUTH_AUTHORIZE_URL;
import static threescale.v3.api.ServiceApiConstants.TRANSACTIONS_URL;
import static threescale.v3.api.ServiceApiConstants.USAGE_PARAMETER;
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
public class ServiceApiDriver implements ServiceApi {

	private String provider_key;
	private boolean useHttps = false;
	private ServerAccessor server;
	private String host = DEFAULT_HOST;

	public ServiceApiDriver() {
		this.server = new ServerAccessorDriver();
	}

	public ServiceApiDriver(String provider_key) {
		this();
		this.provider_key = provider_key;
	}

	public ServiceApiDriver(String provider_key, boolean useHttps) {
		this(provider_key);
		this.useHttps = useHttps;
	}

	public ServiceApiDriver(String provider_key, String host) {
		this(provider_key);
		this.host = host;
	}

	public ServiceApiDriver(String provider_key, String host, boolean useHttps) {
		this(provider_key, host);
		this.useHttps = useHttps;
	}

	@Override
	public AuthorizeResponse authrep(ParameterMap metrics) throws ServerError {
		metrics.add(PROVIDER_KEY_PARAMETER, provider_key);

		ParameterMap usage = metrics.getMapValue(USAGE_PARAMETER);

		if (isNull(usage)) {
			usage = new ParameterMap();
			metrics.add(USAGE_PARAMETER, usage);
		}

		if (isNull(usage.getStringValue(HITS_PARAMETER))) {
			usage.add(HITS_PARAMETER, "1");
		}

		final String s = getFullHostUrl(TRANSACTIONS_AUTHREP_URL, metrics);

		HttpResponse response = server.get(s);
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return convertXmlToAuthorizeResponse(response);
	}

	@Override
	public ReportResponse report(String service_id, ParameterMap... transactions) throws ServerError {
		if (transactions == null || transactions.length == 0) {
			throw new IllegalArgumentException("No transactions provided");
		}

		ParameterMap params = new ParameterMap();
		params.add(PROVIDER_KEY_PARAMETER, provider_key);

		if (isNotNull(service_id)) {
			params.add(SERVICE_ID_PARAMETER, service_id);
		}

		ParameterMap trans = new ParameterMap();
		params.add(TRANSACTIONS_ID_PARAMETER, transactions);

		int index = 0;
		for (ParameterMap transaction : transactions) {
			trans.add("" + index, transaction);
			index++;
		}

		HttpResponse response = server.post(getFullHostUrl(TRANSACTIONS_URL), encodeAsString(params));
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return new ReportResponse(response);
	}

	@Override
	public AuthorizeResponse authorize(ParameterMap parameters) throws ServerError {
		parameters.add(PROVIDER_KEY_PARAMETER, provider_key);

		final String s = getFullHostUrl(TRANSACTIONS_AUTHORIZE_URL, parameters);
		HttpResponse response = server.get(s);
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return convertXmlToAuthorizeResponse(response);
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public AuthorizeResponse oauth_authorize(ParameterMap params) throws ServerError {
		params.add(PROVIDER_KEY_PARAMETER, provider_key);
		final String s = getFullHostUrl(TRANSACTIONS_OAUTH_AUTHORIZE_URL, params);
		HttpResponse response = server.get(s);
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return convertXmlToAuthorizeResponse(response);
	}

	private String getFullHostUrl(String url, ParameterMap parameters) {
		return getFullHostUrl() + url + "?" + encodeAsString(parameters);
	}

	private String getFullHostUrl(String url) {
		return getFullHostUrl() + url;
	}

	private String getFullHostUrl() {
		return useHttps ? "https://" + getHost() : "http://" + getHost();
	}

	public String encodeAsString(ParameterMap params) {
		ParameterEncoder encoder = new ParameterEncoder();
		return encoder.encode(params);
	}

	private AuthorizeResponse convertXmlToAuthorizeResponse(HttpResponse res) throws ServerError {
		return new AuthorizeResponse(res.getStatus(), res.getBody());
	}

	public ServiceApiDriver setServer(ServerAccessor server) {
		this.server = server;
		return this;
	}
}
