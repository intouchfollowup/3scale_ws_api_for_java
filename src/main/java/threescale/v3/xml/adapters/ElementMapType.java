package threescale.v3.xml.adapters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;

import org.w3c.dom.Element;

/**
 * Class for typing Elements in the {@link Map} for the {@link ElementMapAdapter}
 *
 * @author Moncef
 *
 */
public class ElementMapType {

	private List<Element> elements = new ArrayList<Element>();

	@XmlAnyElement
	public List<Element> getElements() {
		return elements;
	}
}