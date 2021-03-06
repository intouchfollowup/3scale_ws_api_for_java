package threescale.v3.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerAccessor;
import threescale.v3.api.ServerError;

/**
 * Performs GET's and POST's against the live 3Scale Server
 */
public class ServerAccessorDriver implements ServerAccessor {

    public ServerAccessorDriver() {
    }

    /**
     * @param urlParams url + parameter string
     * @return Http Response
     * @throws ServerError
     * @see ServerAccessor
     */
    @Override
	public HttpResponse get(String urlParams) throws ServerError {
        return invokeUrl(urlParams, "GET");
    }

    private String getBody(InputStream content) throws IOException {
        StringBuilder sb = new StringBuilder();

        if(content != null) {
	        BufferedReader rd = new BufferedReader(new InputStreamReader(content));

	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line + '\n');
	        }
        }

        return sb.toString();
    }

    /**
     * @param urlParams url to access
     * @param data      The data to be sent
     * @return Response from the server
     * @throws ServerError
     * @see ServerAccessor
     */
    @Override
	public HttpResponse post(final String urlParams,final String data) throws ServerError {
        return invokeUrl(urlParams, data, "POST");
    }

    /**
     * @param urlParams url to access
     * @param data      The data to be sent
     * @return Response from the server
     * @throws ServerError
     * @see ServerAccessor
     */
    @Override
    public HttpResponse put(final String urlParams,final String data) throws ServerError {
    	return invokeUrl(urlParams, data, "PUT");
    }

    @Override
    public HttpResponse delete(String urlParams) throws ServerError {
    	return invokeUrl(urlParams, "DELETE");
    }

	private HttpResponse invokeUrl(final String urlParams,final String data, String requestMethod) throws ServerError {
        HttpURLConnection connection = null;
        OutputStreamWriter wr;
        URL url;

        try {
            url = new URL(urlParams);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            connection.connect();
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();

            return new HttpResponse(connection.getResponseCode(), getBody(connection.getInputStream()));
        } catch (IOException ex) {
            try {
                return new HttpResponse(connection.getResponseCode(),
                        (connection.getErrorStream() == null) ? getBody(connection.getInputStream()) : getBody(connection.getErrorStream()));
            } catch (IOException e) {
                throw new ServerError(e.getMessage());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

	private HttpResponse invokeUrl(final String urlParams, String requestMethod) throws ServerError {
		HttpURLConnection connection = null;
        URL url;

        try {
            url = new URL(urlParams);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            connection.connect();


            return new HttpResponse(connection.getResponseCode(), getBody(connection.getInputStream()));

        } catch (IOException ex) {
            try {
                return new HttpResponse(connection.getResponseCode(), getBody(connection.getErrorStream()));
            } catch (IOException e) {
                throw new ServerError(e.getMessage());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
	}
}
