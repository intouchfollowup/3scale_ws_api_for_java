package threescale.v3.api.http.response;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static threescale.v3.utils.ObjectUtils.isNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.xml.bind.Unmarshaller;
import threescale.v3.xml.elements.error.Error;
import threescale.v3.xml.elements.error.Errors;

/**
 * Abstract 3scale response object wrapping around {@link HttpResponse}
 *
 * @author Moncef
 *
 */
public class Response<T> {

	private boolean success;
	private Errors errors;
	private Unmarshaller unmarshaller = Unmarshaller.getInstance();
	private HttpResponse response;
	private Class<T> marshallToClass;
	private T body;

	/**
     * Create a {@link Response} from an HTML POST
     *
     * @param response - {@link HttpResponse}
     * @throws ServerError
     */
    public Response(HttpResponse response, Class<T> marshallToClass) throws ServerError {
        this.response = response;
		this.marshallToClass = marshallToClass;
    	this.success = isSuccessResponse();
    	initOnResponse();
    }

    /**
     * Test whether the {@link HttpResponse#getStatus()} is successful response. By default any
     * status in between the range of 200 and 299 is considered a success, to add additional
     * success statuses override this method.
     *
     * @return true if considered a successful response
     */
    protected boolean isSuccessResponse() {
    	int status = getStatus();
    	return (status >= 200 && status <= 299);
    }

    /**
     * Unmarshall based on the response, i.e. a successful or failed call to the 3scale API will
     * try to unmarshall the response on different expected outputs.
     *
     * @throws ServerError - if initialization based on Response failed.
     */
    protected void initOnResponse() throws ServerError {
	    try {
	    	if(success) {
	    		unmarshallForSuccess();
	    	} else {
	    		unmarshallForFailure();
	    	}
	    } catch (JAXBException e) {
			throw new ServerError("Error processing the XML : " + e.getMessage());
		}
    }

	protected void unmarshallForSuccess() throws JAXBException {
		String body = response.getBody();
		if(isNotBlank(body)) {
			this.body = unmarshaller.unmarshall(marshallToClass, body);
		}
    }

	/**
     * Initializes this Response to indicate that it's considered a failure response.
     *
     * @throws JAXBException if XML was invalid or unable to be read.
     */
    protected void unmarshallForFailure() throws JAXBException {
    	String content = response.getBody();

    	if(isNotBlank(content)) {
	    	try {
	    		// handle single error like  : <error>Access Denied</error>
	    		errors = createErrors(unmarshaller.unmarshall(Error.class, content));
	    	} catch(JAXBException e) {
	    		// couldn't get Error, maybe 3scale showed Errors object directly like so :
	    		// <errors><error>Users is invalid</error></errors>

	    		// this might throw another JAXBException if it's an xml output from 3scale that has
	    		// not been implemented, wish we knew all cases.
				errors = unmarshaller.unmarshall(Errors.class, content);
	    	}
    	}
    }

    /**
     * Combine a single {@link Error} to go into an {@link Errors} object, so we only have to read
     * the {@link Errors} out when needed to check errors.
     *
     * @param error - {@link Error}
     * @return {@link Errors}
     */
    private Errors createErrors(Error error) {
		List<Error> list = new ArrayList<Error>();
		list.add(error);

		Errors errors = new Errors();
		errors.setErrors(list);
		return errors;
    }

	public int getStatus() {
		return response.getStatus();
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean hasErrors() {
		return isNotNull(errors) && errors.hasErrors();
	}

	public boolean hasBody() {
		return isNotNull(body);
	}

	public List<Error> getErrors() {
		if(hasErrors()) {
			return errors.getErrors();
		}
		// be kind return empty instead of null
		return emptyList();
	}

	/**
	 * Gets the Unmarshalled Java Typed version of the response
	 *
	 * @return T
	 */
	public T getBody() {
		return body;
	}
}
