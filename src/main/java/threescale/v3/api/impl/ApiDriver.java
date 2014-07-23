package threescale.v3.api.impl;

import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.api.ServiceApiConstants.DEFAULT_HOST;
import static threescale.v3.api.ServiceApiConstants.DEFAULT_HOST_URL;
import static threescale.v3.api.ServiceApiConstants.HTTPS_PROTOCAL;
import static threescale.v3.api.ServiceApiConstants.HTTP_PROTOCAL;
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerAccessor;
import threescale.v3.api.ServerError;

public class ApiDriver {

	private String providerKey;
	private ServerAccessor server;
	private String host = DEFAULT_HOST;
	private String hostURL = DEFAULT_HOST_URL;
	private boolean useHttps;

	public ApiDriver() {
		this.server = new ServerAccessorDriver();
	}

	public ApiDriver(String providerKey) {
		this();
		this.providerKey = providerKey;
	}

	public ApiDriver(String providerKey, boolean useHttps) {
		this(providerKey);
		this.useHttps = useHttps;

		initHostURL();
	}

	public ApiDriver(String providerKey, String host) {
		this(providerKey);
		this.host = host;

		initHostURL();
	}

	public ApiDriver(String providerKey, String host, boolean useHttps) {
		this(providerKey, host);
		this.host = host;
		this.useHttps = useHttps;

		initHostURL();
	}

	protected HttpResponse get(String url, ParameterMap parameterMap) throws ServerError {
		addProviderKey(parameterMap);

		HttpResponse response = server.get(getFullHostUrl(url, parameterMap));
		validateResponse(response);
		return response;
	}

	protected HttpResponse get(String url) throws ServerError {
		return get(url, new ParameterMap());
	}


	protected HttpResponse post(String url, ParameterMap parameterMap) throws ServerError {
		addProviderKey(parameterMap);

		HttpResponse response = server.post(getFullHostUrl(url), encodeAsString(parameterMap));
		validateResponse(response);
		return response;
	}

	protected HttpResponse put(String url, ParameterMap parameterMap) throws ServerError {
		addProviderKey(parameterMap);

		HttpResponse response = server.put(getFullHostUrl(url), encodeAsString(parameterMap));
		validateResponse(response);
		return response;
	}

	protected HttpResponse delete(String url, ParameterMap parameterMap) throws ServerError {
		addProviderKey(parameterMap);

		HttpResponse response = server.delete(getFullHostUrl(url, parameterMap));
		validateResponse(response);
		return response;
	}

	protected HttpResponse delete(String url) throws ServerError {
		return delete(url, new ParameterMap());
	}

	protected String getFullHostUrl(String url, ParameterMap parameterMap) {
		return hostURL + url + "?" + encodeAsString(parameterMap);
	}

	protected String getFullHostUrl(String url) {
		return hostURL + url;
	}

	protected void addProviderKey(ParameterMap parameterMap) {
		parameterMap.add(PROVIDER_KEY_PARAMETER, providerKey);
	}

	private String encodeAsString(ParameterMap parameterMap) {
		ParameterEncoder encoder = new ParameterEncoder();
		return encoder.encode(parameterMap);
	}

	private void validateResponse(HttpResponse response) throws ServerError {
		if (response.getStatus() == 500) {
			throw new ServerError(response.getBody());
		}
	}

	private void initHostURL() {
		this.hostURL = useHttps ? (HTTPS_PROTOCAL + host) : (HTTP_PROTOCAL + host);
	}

	public String getProviderKey() {
		return providerKey;
	}

	public String getHost() {
		return host;
	}

	public String getHostURL() {
		return hostURL;
	}

	public boolean getUseHttps() {
		return useHttps;
	}

	public ApiDriver setServer(ServerAccessor server) {
		this.server = server;
		return this;
	}
}
