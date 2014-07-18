package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.xml.response.Application;
import threescale.v3.xml.response.Event;
import threescale.v3.xml.response.EventObject;
import threescale.v3.xml.response.EventType;

public class EventTest {

	private EventObject eventObject;

	private Event event;

	@Before
	public void setup() {
		eventObject = new EventObject();
		event = new Event();
	}

	@Test
	public void nonApplicationTypeIsNotApplicationEvent() {
		event.setType(null);
		assertThat(event.isForApplication(), equalTo(false));
	}

	@Test
	public void eventWithoutApplicationObjectIsNotForApplication() {
		eventObject.setApplication(null);
		assertThat(event.isForApplication(), equalTo(false));
	}

	@Test
	public void eventWithApplicationTypeAndApplicationObjectIsAnApplicationEvent() {
		event.setType(EventType.APPLICATION);
		event.setObject(eventObject);
		eventObject.setApplication(new Application());
		assertThat(event.isForApplication(), equalTo(true));
	}

	@Test
	public void getApplicationIdShouldReturnNullIfEventHasNoApplication() {
		event.setObject(null);

		assertThat(event.getApplicationId(), nullValue());
	}

	@Test
	public void testGetApplicationId() {
		Application application = new Application();
		application.setApplicationId("application_id");
		eventObject.setApplication(application);
		event.setObject(eventObject);

		assertThat(event.getApplicationId(), equalTo("application_id"));
	}
}
