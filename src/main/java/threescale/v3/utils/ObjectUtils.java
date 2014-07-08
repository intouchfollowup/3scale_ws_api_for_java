package threescale.v3.utils;


/**
 * Simple {@link Object} utility class
 *
 * @author Moncef
 */
public class ObjectUtils {

	/**
	 * @return True if the give {@link Object} is null; false otherwise.
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * @return True if the give {@link Object} is <b>not</b> null; false otherwise.
	 */
	public static boolean isNotNull(Object object) {
		return object != null;
	}
}
