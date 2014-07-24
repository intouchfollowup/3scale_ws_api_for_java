package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import threescale.v3.xml.response.application.ApplicationState;
import threescale.v3.xml.webhook.EventType;


/**
 *
 * @author Andrew Wu
 *
 */
public class ApplicationStateTest {

	@Test
	public void valueForPendingStateShouldComplyWith3scaleSpec() {
		String expect = "pending"; // all lower case as defined in 3scale API doc
		String actual = ApplicationState.PENDING.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForLiveStateShouldComplyWith3scaleSpec() {
		String expect = "live"; // all lower case as defined in 3scale API doc
		String actual = ApplicationState.LIVE.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void valueForSuspendedStateShouldComplyWith3scaleSpec() {
		String expect = "suspended"; // all lower case as defined in 3scale API doc
		String actual = ApplicationState.SUSPENDED.value();

		assertThat(actual, equalTo(expect));
	}

	@Test
	public void canGetApplicationStateByUsingFromValueMethod() {
		ApplicationState expect = ApplicationState.PENDING;
		ApplicationState actual = ApplicationState.fromValue(expect.value());

		assertThat(actual, equalTo(expect));
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromValueThrowsExceptionForInvalidValue() {
		EventType.fromValue("invalid_value");
	}

	@Test
	public void testIsPending() {
		assertThat(ApplicationState.isPending(ApplicationState.PENDING), equalTo(true));
		assertThat(ApplicationState.isPending(ApplicationState.LIVE), equalTo(false));
		assertThat(ApplicationState.isPending(ApplicationState.SUSPENDED), equalTo(false));
	}

	@Test
	public void testIsLive() {
		assertThat(ApplicationState.isLive(ApplicationState.PENDING), equalTo(false));
		assertThat(ApplicationState.isLive(ApplicationState.LIVE), equalTo(true));
		assertThat(ApplicationState.isLive(ApplicationState.SUSPENDED), equalTo(false));
	}

	@Test
	public void testIsSuspended() {
		assertThat(ApplicationState.isSuspended(ApplicationState.PENDING), equalTo(false));
		assertThat(ApplicationState.isSuspended(ApplicationState.LIVE), equalTo(false));
		assertThat(ApplicationState.isSuspended(ApplicationState.SUSPENDED), equalTo(true));
	}
}
