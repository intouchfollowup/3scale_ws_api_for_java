package threescale.v3.xml.bind;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


/**
 *
 * @author Moncef
 *
 */
public class Marshaller {

	/** Singleton instance **/
	private static Marshaller instance;

	/** Used for "caching" {@link JAXBContext} per {@link Class} type **/
	private Map<Class<?>, JAXBContext> jaxbContextMap = new HashMap<Class<?>, JAXBContext>();

	private Marshaller() {
		// Singleton access only
	}

	/**
	 * Singleton access to the {@link Marshaller}
	 *
	 * @return {@link Marshaller}
	 */
	public static Marshaller getInstance() {
		if(instance == null) {
			instance = new Marshaller();
		}
		return instance;
	}

	public String marshall(Object source) throws JAXBException {
		return marshall(source.getClass(), source);
	}

	public String marshall(Class<?> clazz, Object source) throws JAXBException {
		javax.xml.bind.Marshaller marshaller = getJAXBContext(clazz).createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(source, stringWriter);
		return stringWriter.toString();
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
