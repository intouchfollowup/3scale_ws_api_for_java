package threescale.v3.xml.bind;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLUnmarshaller {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> clazz, String source) throws JAXBException {
		StringReader reader = new StringReader(source);
		Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
		return (T) unmarshaller.unmarshal(reader);
	}
}
