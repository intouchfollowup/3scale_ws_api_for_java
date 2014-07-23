package threescale.v3.xml.response;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * A parent object for most Responses
 *
 * @author Moncef
 *
 */
public class Response {

	private String id;

	public boolean hasId() {
		return isNotBlank(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
