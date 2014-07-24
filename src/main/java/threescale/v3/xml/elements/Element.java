package threescale.v3.xml.elements;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * A parent object for most elements that are the same thing
 *
 * @author Moncef
 *
 */
public class Element {

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
