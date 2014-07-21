package threescale.v3.xml.adapters;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.w3c.dom.Element;

/**
 * {@link Element} to {@link Map} adapter that will map fields to a {@link Map} like the so :
 *
 * <pre>
 * {@code
 * <root>
 *   <abc>123</abc>
 *   <xyz>890</xyz>
 * </root>
 * }
 * </pre>
 *
 * The above will result in a Map like shown here :
 *
 * <pre>
 * {@code
 * Map<String, String> map;
 * map.put("abc", "123");
 * map.put("xyz", "890");
 * }
 * </pre>
 *
 * @author Moncef
 *
 */
public class ElementMapAdapter extends XmlAdapter<ElementMapType, Map<String, String>> {

   @Override
   public ElementMapType marshal(Map<String, String> arg0) throws Exception {
	   // not supported yet
	   throw new UnsupportedOperationException();
   }

   @Override
   public Map<String, String> unmarshal(ElementMapType elementMapType) throws Exception {
      HashMap<String, String> hashMap = new HashMap<String, String>();
      for(Element element : elementMapType.getElements()) {
         hashMap.put(element.getNodeName(), element.getTextContent());
      }
      return hashMap;
   }
}