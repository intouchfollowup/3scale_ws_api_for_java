package threescale.v3.api.http.response;

import static threescale.v3.utils.ObjectUtils.isNotNull;
import static threescale.v3.utils.ObjectUtils.isNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;

/**
 * Abstract 3scale response object wrapping around {@link HttpResponse}
 *
 * @author Moncef
 *
 */
public abstract class AbstractResponse {

	private int status;
	private boolean success;
	private String errorCode;
	private String errorMessage;
	private List<String> errors = new ArrayList<String>();
	private HttpResponse response;
	private Element rootElement;

	/**
     * Create a {@link AbstractResponse} from an HTML POST
     *
     * @param response - {@link HttpResponse}
     * @throws ServerError
     */
    public AbstractResponse(HttpResponse response) throws ServerError {
        this.response = response;
		this.status = response.getStatus();
    	this.success = isSuccessResponse(response);

        initRootElement();
        initResponse();
    }

    /**
     * Create a {@link AbstractResponse} from an {@link Element}
     *
     * @param rootElement - {@link Element}
     * @throws ServerError
     */
    public AbstractResponse(Element rootElement) throws ServerError {
    	this.rootElement = rootElement;
    	this.success = true;

    	initResponse();
    }

    /**
     * Initializes this Response to indicate that it's considered a success response.
     *
     * @throws ServerError if XML was invalid or unable to be read.
     */
    protected abstract void initSuccessResponse() throws ServerError;

    /**
     * Test whether the {@link HttpResponse#getStatus()} is successful response
     *
     * @param response - {@link HttpResponse} to test
     * @return true if considered a successful response
     */
    protected boolean isSuccessResponse(HttpResponse response) {
    	return response.getStatus() == 200;
    }

	/**
     * Initializes this Response to indicate that it's considered a failure response.
     *
     * @throws ServerError if XML was invalid or unable to be read.
     */
    protected void initFailedResponse() throws ServerError {
    	Attribute codeElement = rootElement.getAttribute("code");
        if(isNotNull(codeElement)) {
        	errorCode = codeElement.getValue();
        	errorMessage = rootElement.getValue();
        }

        Elements childElements = rootElement.getChildElements("error");
        if(isNotNull(childElements)) {
        	for (int i = 0; i < childElements.size(); i++) {
        		errors.add(childElements.get(i).getValue());
    		}
    	}
    }

    private void initRootElement() throws ServerError {
    	String content = response.getBody();

    	try {
            Document document = new Builder().build(content, null);
            this.rootElement = document.getRootElement();
        } catch (ParsingException ex) {
            throw new ServerError("The xml received was invalid: " + content);
        } catch (IOException ex) {
            throw new ServerError("Error processing the XML");
        }
    }

    protected String getFirstChildElementValue(Element parent, String name) {
    	Element element = parent.getFirstChildElement(name);
		return isNull(element) ?  null : element.getValue();
    }

    protected List<Element> getFirstChildElementList(Element parent, String listName, String listChildName) {
    	List<Element> list = new ArrayList<Element>();
    	Element listElement = parent.getFirstChildElement(listName);

    	if (isNotNull(listElement)) {
            Elements childElements = listElement.getChildElements(listChildName);
            if(isNotNull(childElements)) {
            	for (int i = 0; i < childElements.size(); i++) {
            		list.add(childElements.get(i));
            	}
            }
        }

    	return list;
    }

    private void initResponse() throws ServerError {
    	if(success) {
    		initSuccessResponse();
    	} else {
    		initFailedResponse();
    	}
	}

	public int getStatus() {
		return status;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public Element getRootElement() {
		return rootElement;
	}
}
