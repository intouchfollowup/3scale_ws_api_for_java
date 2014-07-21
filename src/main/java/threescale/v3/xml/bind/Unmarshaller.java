package threescale.v3.xml.bind;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


/**
 *
 * @author Andrew Wu, Moncef
 *
 */
public class Unmarshaller {

	/** Singleton instance **/
	private static Unmarshaller instance;

	/** Used for "caching" {@link JAXBContext} per {@link Class} type **/
	private Map<Class<?>, JAXBContext> jaxbContextMap = new HashMap<Class<?>, JAXBContext>();

	private Unmarshaller() {
		// Singleton access only
	}

	/**
	 * Singleton access to the {@link Unmarshaller}
	 *
	 * @return {@link ThreeScaleUnmarshaller}
	 */
	public static Unmarshaller getInstance() {
		if(instance == null) {
			instance = new Unmarshaller();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T unmarshall(Class<T> clazz, String source) throws JAXBException {
		StringReader reader = new StringReader(source);
		javax.xml.bind.Unmarshaller unmarshaller = getJAXBContext(clazz).createUnmarshaller();
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
