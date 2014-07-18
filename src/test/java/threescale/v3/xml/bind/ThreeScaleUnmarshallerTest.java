package threescale.v3.xml.bind;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static threescale.v3.xml.bind.ThreeScaleUnmarshaller.unmarshall;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.junit.Before;
import org.junit.Test;

import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.ApplicationResponse;
import threescale.v3.xml.response.Application;

public class ThreeScaleUnmarshallerTest {

	private ApplicationResponse applicationResponse;

	@Before
	public void setup() //
			throws ServerError, JAXBException, ValidityException, ParsingException, IOException {
		applicationResponse = new MockApplicationResponse(APPLICATION_XML);
	}

	@Test
	public void canUnmarshallApplicationResponse() {
		try {
			Application app = unmarshall(applicationResponse);

			// randomly check a few fields
			assertThat(app, notNullValue());
			assertThat(app.getId(), equalTo("1409611198825"));
			assertThat(app.getApplicationId(), equalTo("4fd321b5"));
			assertThat(app.getName(), equalTo("Web Hook"));
		} catch (JAXBException e) {
			fail();
		}
	}

	@Test
	public void canUnmarshallXmlString() {
		try {
			Application app = unmarshall(APPLICATION_XML);

			// randomly check a few fields
			assertThat(app, notNullValue());
			assertThat(app.getId(), equalTo("1409611198825"));
			assertThat(app.getKeys(), notNullValue());
			assertThat(app.getPlan(), notNullValue());
			assertThat(app.getPlan().getId(), equalTo("2357355808684"));
		} catch (JAXBException e) {
			fail();
		}
	}

	private static class MockApplicationResponse extends ApplicationResponse {
		public MockApplicationResponse(String source) //
				throws ServerError, ValidityException, ParsingException, IOException {
			// override to work around constructor limitation
			// Also can't mock Element.toXML() since the method is final so have to use a concrete instance.
			super(new Builder().build(source, null).getRootElement());
		}

		@Override
		protected void initSuccessResponse() throws ServerError {
			// override to do nothing
		}
	}

	private static final String APPLICATION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + //
			"    <application>" + //
			"      <id>1409611198825</id>" + //
			"      <created_at>2014-07-09T20:29:53Z</created_at>" + //
			"      <updated_at>2014-07-09T20:42:40Z</updated_at>" + //
			"      <state>live</state>" + //
			"      <user_account_id>2445581091730</user_account_id>" + //
			"      <end_user_required>false</end_user_required>" + //
			"      <service_id>2555417723561</service_id>" + //
			"      <application_id>4fd321b5</application_id>" + //
			"      <redirect_url/>" + //
			"      <keys>" + //
			"        <key>86ced34ccaaf5098e5ecabbfbf377cc8</key>" + //
			"      </keys>" + //
			"      <plan custom=\"false\" default=\"false\">" + //
			"        <id>2357355808684</id>" + //
			"        <name>Unlimited (Partner)</name>" + //
			"        <type>application_plan</type>" + //
			"        <state>hidden</state>" + //
			"        <service_id>2555417723561</service_id>" + //
			"        <end_user_required>false</end_user_required>" + //
			"        <setup_fee>0.0</setup_fee>" + //
			"        <cost_per_month>0.0</cost_per_month>" + //
			"        <trial_period_days/>" + //
			"        <cancellation_period>0</cancellation_period>" + //
			"      </plan>" + //
			"      <name>Web Hook</name>" + //
			"      <description>Web Hook Test</description>" + //
			"      <extra_fields>" + //
			"      </extra_fields>" + //
			"    </application>";
}
