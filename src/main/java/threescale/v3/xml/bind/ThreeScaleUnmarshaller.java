package threescale.v3.xml.bind;

import javax.xml.bind.JAXBException;

import threescale.v3.api.http.response.ApplicationResponse;
import threescale.v3.xml.response.Application;


/**
 *
 * @author Andrew Wu
 *
 */
public class ThreeScaleUnmarshaller {

	/**
	 * Convert an {@link ApplicationResponse} to an {@link Application}
	 *
	 * @param response
	 * @return {@link Application}
	 * @throws JAXBException
	 */
	public static Application unmarshall(ApplicationResponse response) throws JAXBException {
		return unmarshall(response.getRootElement().toXML());
	}

	/**
	 * Map an XML string into an {@link Application}
	 *
	 * @param source - an XML string representation of an {@link Application}
	 * @return {@link Application}
	 * @throws JAXBException
	 */
	public static Application unmarshall(String source) throws JAXBException {
		return XMLUnmarshaller.unmarshall(Application.class, source);
	}
}
