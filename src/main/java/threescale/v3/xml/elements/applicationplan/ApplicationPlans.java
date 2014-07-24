package threescale.v3.xml.elements.applicationplan;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plans")
public class ApplicationPlans {

	private List<ApplicationPlan> applicationPlans;

	@XmlElement(name = "plan")
	public List<ApplicationPlan> getApplicationPlans() {
		return applicationPlans;
	}

	public void setApplicationPlans(List<ApplicationPlan> applicationPlans) {
		this.applicationPlans = applicationPlans;
	}
}
