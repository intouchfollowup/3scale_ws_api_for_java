package threescale.v3.xml.bind.response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.xml.bind.Unmarshaller;
import threescale.v3.xml.response.application.Application;
import threescale.v3.xml.response.application.ApplicationKey;
import threescale.v3.xml.response.application.ApplicationState;


/**
 *
 * @author Andrew Wu
 *
 */
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

	@Test
	public void testApplicationUnmarshal() {
		try {
			Application application = Unmarshaller.getInstance().unmarshall(Application.class, xml);

			// test extra fields
			Map<String, String> fields = application.getExtraFields();
			assertThat(fields.get("field1"), equalTo("value1"));
			assertThat(fields.get("field2"), equalTo("value2"));
			assertThat(fields.get("field3"), equalTo(""));
			assertThat(fields.get("iDoNotExistField"), nullValue());

		} catch (JAXBException e) {
			fail(e.getMessage());
		}
	}

	private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<application>"
			+ "	<id>1111111111111</id>"
			+ "	<created_at>2014-07-15T10:38:49-07:00</created_at>"
			+ "	<updated_at>2014-07-21T07:44:16-07:00</updated_at>"
			+ "	<state>live</state>"
			+ "	<user_account_id>2222222222222</user_account_id>"
			+ "	<end_user_required>false</end_user_required>"
			+ "	<service_id>3333333333333</service_id>"
			+ "	<application_id>ShareYourFit</application_id>"
			+ "	<redirect_url />"
			+ "	<keys>"
			+ "		<key>testkey</key>"
			+ "	</keys>"
			+ "	<plan custom=\"false\" default=\"false\">"
			+ "		<id>4444444444444</id>"
			+ "		<name>Unlimited</name>"
			+ "		<type>application_plan</type>"
			+ "		<state>hidden</state>"
			+ "		<service_id>5555555555555</service_id>"
			+ "		<end_user_required>false</end_user_required>"
			+ "		<setup_fee>0.0</setup_fee>"
			+ "		<cost_per_month>0.0</cost_per_month>"
			+ "		<trial_period_days />"
			+ "		<cancellation_period>0</cancellation_period>"
			+ "	</plan>"
			+ "	<name>Application Test Name</name>"
			+ "	<description>Application Test Description</description>"
			+ "	<extra_fields>"
			+ "		<field1>value1</field1>"
			+ "		<field2>value2</field2>"
			+ "		<field3></field3>"
			+ "	</extra_fields>"
			+ "</application> ";
}
