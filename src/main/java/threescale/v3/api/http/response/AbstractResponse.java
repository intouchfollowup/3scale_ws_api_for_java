package threescale.v3.api.http.response;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static threescale.v3.utils.ObjectUtils.isNotNull;

import javax.xml.bind.JAXBException;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.xml.bind.Unmarshaller;
import threescale.v3.xml.response.Error;
import threescale.v3.xml.response.Errors;

/**
 * Abstract 3scale response object wrapping around {@link HttpResponse}
 *
 * @author Moncef
 *
 */
public abstract class AbstractResponse<T> {

	private int status;
	private boolean success;
	private Errors errors;
	private Error error;
	private Unmarshaller unmarshaller = Unmarshaller.getInstance();
	private HttpResponse response;
	private Class<T> marshallToClass;
	private T root;

	/**
     * Create a {@link AbstractResponse} from an HTML POST
     *
     * @param response - {@link HttpResponse}
     * @throws ServerError
     */
    public AbstractResponse(HttpResponse response, Class<T> marshallToClass) throws ServerError {
        this.response = response;
		this.marshallToClass = marshallToClass;
		this.status = response.getStatus();
    	this.success = isSuccessResponse(response);
    	unmarshallOnResponse();
    }

    /**
     * Test whether the {@link HttpResponse#getStatus()} is successful response. By default a 200 is
     * considered a success, to add additional success statuses override this method.
     *
     * @param response - {@link HttpResponse} to test
     * @return true if considered a successful response
     */
    protected boolean isSuccessResponse(HttpResponse response) {
    	return response.getStatus() == 200;
    }

    /**
     * Unmarshall based on the response, i.e. a successful or failed call to the 3scale API will
     * try to unmarshall the response on different expected outputs.
     *
     * @throws ServerError
     */
    protected void unmarshallOnResponse() throws ServerError {
    	if(success) {
    		unmarshall(marshallToClass);
    	} else {
    		unmarshallFailedResponse();
    	}
    }

    @SuppressWarnings("unchecked")
	protected void unmarshall(Class<?> clazz) throws ServerError {
    	String content = response.getBody();
    	try {
			this.root = (T) unmarshaller.unmarshall(clazz, content);
    	} catch (JAXBException e) {
    		throw new ServerError("Error processing the XML : " + e.getMessage());
		}
    }

	/**
     * Initializes this Response to indicate that it's considered a failure response.
     *
     * @throws ServerError if XML was invalid or unable to be read.
     */
    protected void unmarshallFailedResponse() throws ServerError {
    	String content = response.getBody();

    	if(isNotBlank(content)) {
	    	try {
	    		// handle single error like  : <error>Acces Denied</error>
	    		error = unmarshaller.unmarshall(Error.class, content);
	    	} catch(JAXBException e) {
	    		// couldn't get Error, maybe 3scale showed <errors><error>Users is invalid</error></errors>
	    		try {
					errors = unmarshaller.unmarshall(Errors.class, content);
				} catch (JAXBException e1) {
					// it's an xml output from 3scale that has not been implemented
					// thus needs to be implemented, wish we knew all cases.
		    		throw new ServerError("Error processing the XML : " + e1.getMessage());
				}
	    	}
    	}
    }

	public int getStatus() {
		return status;
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean hasError() {
		return isNotNull(error);
	}

	public boolean hasErrors() {
		return isNotNull(errors);
	}

	public Error getError() {
		return error;
	}

	public Errors getErrors() {
		return errors;
	}

	public T getRoot() {
		return root;
	}
}
