package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import threescale.v3.xml.response.EventType;


/**
 *
 * @author Andrew Wu
 *
 */
public class EventTypeTest {

	@Test
	public void valueForApplicationShouldComplyWith3scaleSpec() {
		String expect = "application"; // all lower case as defined in 3scale API doc
		String actual = EventType.APPLICATION.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void canGetEventTypeByUsingFromValueMethod() {
		EventType expect = EventType.APPLICATION;
		EventType actual = EventType.fromValue(expect.value());

		assertThat(actual, equalTo(expect));
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromValueThrowsExceptionForInvalidValue() {
		EventType.fromValue("invalid_value");
	}
}
