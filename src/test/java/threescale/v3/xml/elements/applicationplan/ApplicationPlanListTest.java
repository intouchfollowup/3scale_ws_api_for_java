package threescale.v3.xml.elements.applicationplan;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static threescale.v3.xml.elements.applicationplan.ApplicationPlanTest.APPLICATON_PLAN_ID;
import static threescale.v3.xml.elements.applicationplan.ApplicationPlanTest.APPLICATON_PLAN_STATE;
import static threescale.v3.xml.elements.applicationplan.ApplicationPlanTest.APPLICATION_PLAN_NAME;
import static threescale.v3.xml.elements.service.ServiceTest.SERVICE_ID;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import threescale.v3.xml.bind.Unmarshaller;

public class ApplicationPlanListTest {

	@Test
	public void testApplicationPlansUnmarshal() {
		try {
			ApplicationPlans applicationPlans = Unmarshaller.getInstance().unmarshall(ApplicationPlans.class, APPLCATION_PLANS_XML);
			assertThat(applicationPlans, not(nullValue()));

			List<ApplicationPlan> list = applicationPlans.getApplicationPlans();
			ApplicationPlan applicationPlan = list.get(0);

			assertThat(applicationPlan.getId(), equalTo(APPLICATON_PLAN_ID));
			assertThat(applicationPlan.getServiceId(), equalTo(SERVICE_ID));
			assertThat(applicationPlan.getName(), equalTo(APPLICATION_PLAN_NAME));
			assertThat(applicationPlan.getState(), equalTo(APPLICATON_PLAN_STATE));
			assertThat(applicationPlan.getEndUserRequired(), equalTo(false));
		} catch (JAXBException e) {
			fail(e.getMessage());
		}
	}

	public static String APPLCATION_PLANS_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<plans>"
				+ "<plan custom=\"false\" default=\"false\">"
				+ "  <id>" + APPLICATON_PLAN_ID +"</id>"
				+ "  <name>" + APPLICATION_PLAN_NAME + "</name>"
				+ "  <type>application_plan</type>"
				+ "  <state>" + APPLICATON_PLAN_STATE + "</state>"
				+ "  <service_id>" + SERVICE_ID +"</service_id>"
				+ "  <end_user_required>false</end_user_required>"
				+ "  <setup_fee>0.0</setup_fee>"
				+ "  <cost_per_month>0.0</cost_per_month>"
				+ "  <trial_period_days>0</trial_period_days>"
				+ "  <cancellation_period>0</cancellation_period>"
				+ "</plan>"
				+ "</plans>";
}
