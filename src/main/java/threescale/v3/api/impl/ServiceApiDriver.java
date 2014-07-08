package threescale.v3.api.impl;

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
	private String host = DEFAULT_HOST;
	private boolean useHttps = false;
	private ServerAccessor server;

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
		metrics.add("provider_key", provider_key);

		ParameterMap usage = metrics.getMapValue("usage");

		if (usage == null || usage.getStringValue("hits") == null) {
			if (usage == null) {
				usage = new ParameterMap();
				metrics.add("usage", usage);
			}
			usage.add("hits", "1");
		}
		String urlParams = encodeAsString(metrics);

		final String s = getFullHostUrl() + "/transactions/authrep.xml?" + urlParams;
		// System.out.println("Actual: " + s);

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
		params.add("provider_key", provider_key);
		if (service_id != null) {
			params.add("service_id", service_id);
		}
		ParameterMap trans = new ParameterMap();
		params.add("transactions", transactions);

		int index = 0;
		for (ParameterMap transaction : transactions) {
			trans.add("" + index, transaction);
			index++;
		}

		HttpResponse response = server.post(getFullHostUrl() + "/transactions.xml", encodeAsString(params));
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return new ReportResponse(response);
	}

	@Override
	public AuthorizeResponse authorize(ParameterMap parameters) throws ServerError {
		parameters.add("provider_key", provider_key);
		String urlParams = encodeAsString(parameters);

		final String s = getFullHostUrl() + "/transactions/authorize.xml?" + urlParams;
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
		params.add("provider_key", provider_key);

		String urlParams = encodeAsString(params);

		final String s = getFullHostUrl() + "/transactions/oauth_authorize.xml?" + urlParams;
		// System.out.println("Actual: " + s);

		HttpResponse response = server.get(s);
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
		return convertXmlToAuthorizeResponse(response);
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
