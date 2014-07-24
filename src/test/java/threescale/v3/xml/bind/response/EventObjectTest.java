package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.xml.elements.application.Application;
import threescale.v3.xml.webhook.EventObject;


/**
 *
 * @author Andrew Wu
 *
 */
public class EventObjectTest {

	private Application application;
	private EventObject eventObject;

	@Before
	public void setup() {
		application = new Application();
		eventObject = new EventObject();
	}

	@Test
	public void testHasApplication() {
		eventObject.setApplication(application);
		assertThat(eventObject.hasApplication(), equalTo(true));

		eventObject.setApplication(null);
		assertThat(eventObject.hasApplication(), equalTo(false));
	}
}
