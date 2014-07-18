package threescale.v3.xml.bind;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author Andrew Wu, Moncef
 *
 */
public class XMLUnmarshaller {

	/** Singleton instance **/
	private static XMLUnmarshaller instance;

	/** Used for "caching" {@link JAXBContext} per {@link Class} type **/
	private Map<Class<?>, JAXBContext> jaxbContextMap = new HashMap<Class<?>, JAXBContext>();

	private XMLUnmarshaller() {
		// Singleton access only
	}

	/**
	 * Singleton access to the {@link XMLUnmarshaller}
	 *
	 * @return {@link ThreeScaleUnmarshaller}
	 */
	public static XMLUnmarshaller getInstance() {
		if(instance == null) {
			instance = new XMLUnmarshaller();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T unmarshall(Class<T> clazz, String source) throws JAXBException {
		StringReader reader = new StringReader(source);
		Unmarshaller unmarshaller = getJAXBContext(clazz).createUnmarshaller();
		return (T) unmarshaller.unmarshal(reader);
	}

	/**
	 * Retrieves a {@link JAXBContext} from a "cached" Map, this will allow only one creation
	 * of a {@link JAXBContext} per class, which should reduce the overhead of creation.
	 *
	 * @param clazz - any Class instance
	 * @return {@link JAXBException}
	 * @throws JAXBException
	 */
	private JAXBContext getJAXBContext(Class<?> clazz) throws JAXBException {

		JAXBContext jaxbContext = jaxbContextMap.get(clazz);

		if(jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(clazz);
			jaxbContextMap.put(clazz, jaxbContext);
		}

		return jaxbContext;
	}
}
