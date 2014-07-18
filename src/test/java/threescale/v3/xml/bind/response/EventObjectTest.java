package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.xml.response.Application;
import threescale.v3.xml.response.EventObject;


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

	@Test
	public void getApplicationIdShouldReturnNullIfObjectHasNoApplication() {
		eventObject.setApplication(null);

		assertThat(eventObject.getApplicationId(), nullValue());
	}

	@Test
	public void testGetApplicationId() {
		application.setApplicationId("application_id");
		eventObject.setApplication(application);

		assertThat(eventObject.getApplicationId(), equalTo("application_id"));
	}
}
