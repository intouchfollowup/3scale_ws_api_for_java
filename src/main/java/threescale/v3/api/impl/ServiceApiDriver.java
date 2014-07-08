package threescale.v3.api.impl;

import static threescale.v3.api.ServiceApiConstants.ADMIN_API_SIGNUP_URL;
import static threescale.v3.api.ServiceApiConstants.DEFAULT_HOST;
import static threescale.v3.api.ServiceApiConstants.DEFAULT_HOST_URL;
import static threescale.v3.api.ServiceApiConstants.HITS_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.HTTPS_PROTOCAL;
import static threescale.v3.api.ServiceApiConstants.HTTP_PROTOCAL;
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
import threescale.v3.api.http.response.AccountResponse;

/**
 * Concrete implementation of the ServiceApi.
 *
 * @see ServiceApi
 */
public class ServiceApiDriver implements ServiceApi {

	private String provider_key;
	private ServerAccessor server;
	private String host = DEFAULT_HOST;
	private String hostURL = DEFAULT_HOST_URL;
	private boolean useHttps;

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

		initHostURL();
	}

	public ServiceApiDriver(String provider_key, String host) {
		this(provider_key);
		this.host = host;

		initHostURL();
	}

	public ServiceApiDriver(String provider_key, String host, boolean useHttps) {
		this(provider_key, host);
		this.host = host;
		this.useHttps = useHttps;

		initHostURL();
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

		return get(TRANSACTIONS_AUTHREP_URL, metrics);
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
			trans.add(Integer.toString(index), transaction);
			index++;
		}

		return new ReportResponse(post(TRANSACTIONS_URL, params));
	}

	@Override
	public AuthorizeResponse authorize(ParameterMap parameters) throws ServerError {
		parameters.add(PROVIDER_KEY_PARAMETER, provider_key);
		return get(TRANSACTIONS_AUTHORIZE_URL, parameters);
	}

	@Override
	public AuthorizeResponse oauth_authorize(ParameterMap params) throws ServerError {
		params.add(PROVIDER_KEY_PARAMETER, provider_key);
		return get(TRANSACTIONS_OAUTH_AUTHORIZE_URL, params);
	}

	@Override
	public AccountResponse signup(ParameterMap parameters) throws ServerError {
		parameters.add(PROVIDER_KEY_PARAMETER, provider_key);
		return new AccountResponse(post(ADMIN_API_SIGNUP_URL, parameters));
	}

	private HttpResponse post(String url, ParameterMap parameters) throws ServerError {
		HttpResponse response = server.post(getFullHostUrl(url), encodeAsString(parameters));
		validateResponse(response);
		return response;
	}

	private AuthorizeResponse get(String url, ParameterMap parameters) throws ServerError {
		return get(getFullHostUrl(url, parameters));
	}

	private AuthorizeResponse get(String url) throws ServerError {
		HttpResponse response = server.get(url);
		validateResponse(response);
		return convertXmlToAuthorizeResponse(response);
	}

	private void initHostURL() {
		this.hostURL = useHttps ? (HTTPS_PROTOCAL + host) : (HTTP_PROTOCAL + host);
	}

	private String getFullHostUrl(String url, ParameterMap parameters) {
		return hostURL + url + "?" + encodeAsString(parameters);
	}

	private String getFullHostUrl(String url) {
		return hostURL + url;
	}

	public String encodeAsString(ParameterMap params) {
		ParameterEncoder encoder = new ParameterEncoder();
		return encoder.encode(params);
	}

	private void validateResponse(HttpResponse response) throws ServerError {
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
	}

	private AuthorizeResponse convertXmlToAuthorizeResponse(HttpResponse res) throws ServerError {
		return new AuthorizeResponse(res.getStatus(), res.getBody());
	}

	@Override
	public String getHost() {
		return host;
	}

	public ServiceApiDriver setServer(ServerAccessor server) {
		this.server = server;
		return this;
	}
}
