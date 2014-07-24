package threescale.v3.xml.response.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import threescale.v3.xml.bind.Unmarshaller;
import threescale.v3.xml.response.metrics.Metric;
import threescale.v3.xml.response.metrics.Metrics;
import threescale.v3.xml.response.service.Service;

/**
 *
 * @author Moncef
 *
 */
public class ServiceTest {

	public static String SERVICE_ID = "1111111111111";
	public static String ACCOUNT_ID = "2222222222222";
	public static String NAME = "Test API";
	public static boolean END_USER_REGISTRATION_REQUIRED = true;
	public static String STATE = "incomplete";
	public static String METRIC_ID = "3333333333333";
	public static Object METRIC_NAME = "hits";
	public static Object METRIC_SYSTEM_NAME = "hits";
	public static Object METRIC_FRIENDLY_NAME = "Hits";
	public static Object METRIC_UNIT = "hit";

	@Test
	public void testServiceUnmarshal() {
		try {
			Service service = Unmarshaller.getInstance().unmarshall(Service.class, SERVICE_XML);

			assertThat(service.getId(), equalTo(SERVICE_ID));
			assertThat(service.getAccountId(), equalTo(ACCOUNT_ID));
			assertThat(service.getName(), equalTo(NAME));
			assertThat(service.getState(), equalTo(STATE));
			assertThat(service.isEndUserRegistrationRequired(), equalTo(END_USER_REGISTRATION_REQUIRED));

			Metrics metrics = service.getMetrics();
			List<Metric> list = metrics.getMetrics();
			assertThat(list.size(), equalTo(1));

			Metric metric = list.get(0);
			assertThat(metric.getId(), equalTo(METRIC_ID));
			assertThat(metric.getName(), equalTo(METRIC_NAME));
			assertThat(metric.getSystemName(), equalTo(METRIC_SYSTEM_NAME));
			assertThat(metric.getFriendlyName(), equalTo(METRIC_FRIENDLY_NAME));
			assertThat(metric.getServiceId(), equalTo(SERVICE_ID));
			assertThat(metric.getUnit(), equalTo(METRIC_UNIT));
		} catch (JAXBException e) {
			fail(e.getMessage());
		}
	}

	public static String SERVICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<service>"
			+ " <id>" + SERVICE_ID + "</id>"
			+ " <account_id>" + ACCOUNT_ID + "</account_id>"
			+ " <name>" + NAME + "</name>"
			+ " <state>" + STATE + "</state>"
			+ " <end_user_registration_required>" + END_USER_REGISTRATION_REQUIRED + "</end_user_registration_required>"
			+ " <metrics>"
			+ "   <metric>"
			+ "     <id>" + METRIC_ID + "</id>"
			+ "     <name>" + METRIC_NAME + "</name>"
			+ "     <system_name>" + METRIC_SYSTEM_NAME + "</system_name>"
			+ "     <friendly_name>" + METRIC_FRIENDLY_NAME + "</friendly_name>"
			+ "     <service_id>" + SERVICE_ID + "</service_id>"
			+ "     <unit>" + METRIC_UNIT + "</unit>"
			+ "   </metric>"
			+ " </metrics>"
			+ "</service>";
}
