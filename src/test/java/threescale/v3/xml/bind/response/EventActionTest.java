package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import threescale.v3.xml.response.EventAction;
import threescale.v3.xml.response.EventType;


/**
 *
 * @author Andrew Wu
 *
 */
public class EventActionTest {

	@Test
	public void valueForAppCreatedShouldComplyWith3scaleSpec() {
		String expect = "created"; // all lower case as defined in 3scale API doc
		String actual = EventAction.APP_CREATED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForAppUpdatedShouldComplyWith3scaleSpec() {
		String expect = "updated"; // all lower case as defined in 3scale API doc
		String actual = EventAction.APP_UPDATED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForAppSuspendedShouldComplyWith3scaleSpec() {
		String expect = "suspended"; // all lower case as defined in 3scale API doc
		String actual = EventAction.APP_SUSPENDED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForKeyCreatedShouldComplyWith3scaleSpec() {
		String expect = "key_created"; // all lower case as defined in 3scale API doc
		String actual = EventAction.KEY_CREATED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForKeyDeletedShouldComplyWith3scaleSpec() {
		String expect = "key_deleted"; // all lower case as defined in 3scale API doc
		String actual = EventAction.KEY_DELETED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void canGetEventActionByUsingFromValueMethod() {
		EventAction expect = EventAction.APP_CREATED;
		EventAction actual = EventAction.fromValue(expect.value());

		assertThat(actual, equalTo(expect));
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromValueThrowsExceptionForInvalidValue() {
		EventType.fromValue("invalid_value");
	}
}
