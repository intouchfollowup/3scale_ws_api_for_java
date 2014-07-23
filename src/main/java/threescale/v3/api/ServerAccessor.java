package threescale.v3.api;

/**
 * Interface to HTML operation between this client and the 3Scale Server
 */
public interface ServerAccessor {

    /**
     * Perform and HTML GET with the provided URL
     *
     * @param url The URL and parameters to be sent
     * @return Status and content as a HtmlResponse
     * @throws ServerError If there are problems connection tp the server.
     */
    public HttpResponse get(String url) throws ServerError;

    /**
     * Perform an HTML POST with the provided URL and form data
     *
     * @param url  The URl to contact
     * @param data The data to be sent
     * @return Status and content as a HtmlResponse
     * @throws ServerError If there are problems connection tp the server.
     */
    public HttpResponse post(String url, String data) throws ServerError;

    /**
     * Perform an HTML PUT with the provided URL and form data
     *
     * @param url  The URl to contact
     * @param data The data to be sent
     * @return Status and content as a HtmlResponse
     * @throws ServerError If there are problems connection tp the server.
     */
	public HttpResponse put(String url, String data) throws ServerError;

    /**
     * Perform an HTML DELETE with the provided URL
     *
     * @param url  The URl to contact
     * @return Status and content as a HtmlResponse
     * @throws ServerError If there are problems connection tp the server.
     */
	public HttpResponse delete(String url) throws ServerError;
}
