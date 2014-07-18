package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.xml.response.Application;
import threescale.v3.xml.response.ApplicationKey;
import threescale.v3.xml.response.ApplicationState;

public class ApplicationTest {

	private Application app;

	@Before
	public void setup() {
		app = new Application();
	}

	@Test
	public void testHasId() {
		assertThat(app.hasId(), equalTo(false));

		app.setId("1234567890");
		assertThat(app.hasId(), equalTo(true));
	}

	@Test
	public void blankIdIsInvalid() {
		app.setId("    ");
		assertThat(app.hasId(), equalTo(false));
	}

	@Test
	public void emptyIdIsInvalid() {
		app.setId("");
		assertThat(app.hasId(), equalTo(false));
	}

	@Test
	public void testIsActive() {
		app.setState(ApplicationState.PENDING);
		assertThat(app.isActive(), equalTo(false));

		app.setState(ApplicationState.LIVE);
		assertThat(app.isActive(), equalTo(true));
	}

	@Test
	public void getFirstKeyShouldReturnNullIfAppDoesntHaveKeys() {
		app.setKeys(null);
		assertThat(app.getFirstKey(), nullValue());

		app.setKeys(Collections.<ApplicationKey> emptyList());
		assertThat(app.getFirstKey(), nullValue());
	}

	@Test
	public void getFirstKeyShouldReturnFirstKey() {
		List<ApplicationKey> keys = new ArrayList<ApplicationKey>();
		keys.add(new ApplicationKey("1st_key"));
		keys.add(new ApplicationKey("2nd_key"));

		app.setKeys(keys);

		assertThat(app.getFirstKey(), equalTo("1st_key"));
	}
}
