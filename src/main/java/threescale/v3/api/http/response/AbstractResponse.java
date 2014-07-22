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
import threescale.v3.xml.response.Error;
import threescale.v3.xml.response.Errors;

/**
 * Abstract 3scale response object wrapping around {@link HttpResponse}
 *
 * @author Moncef
 *
 */
public abstract class AbstractResponse<T> {

	private boolean success;
	private Errors errors;
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
    	this.success = isSuccessResponse();
    	unmarshallOnResponse();
    }

    /**
     * Test whether the {@link HttpResponse#getStatus()} is successful response. By default a 200 is
     * considered a success, to add additional success statuses override this method.
     *
     * @return true if considered a successful response
     */
    protected boolean isSuccessResponse() {
    	return getStatus() == 200;
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
	    		// handle single error like  : <error>Access Denied</error>
	    		 Error error = unmarshaller.unmarshall(Error.class, content);

	    		// combine the single error to go into the Errors object, so we only
	    		// have to read one of them out when needed
	    		List<Error> list = new ArrayList<Error>();
	    		list.add(error);

	    		errors = new Errors();
	    		errors.setErrors(list);

	    	} catch(JAXBException e) {
	    		// couldn't get Error, maybe 3scale showed Errors object like so :
	    		// <errors><error>Users is invalid</error></errors>
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
		return response.getStatus();
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean hasErrors() {
		return isNotNull(errors) && errors.hasErrors();
	}

	public List<Error> getErrors() {
		if(hasErrors()) {
			return errors.getErrors();
		}
		// be kind return empty instead of null
		return emptyList();
	}

	public T getRoot() {
		return root;
	}
}
