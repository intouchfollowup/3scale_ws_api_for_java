package threescale.v3.xml.elements.account;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import threescale.v3.xml.elements.Element;
import threescale.v3.xml.elements.application.Application;

/**
 *
 * @author Moncef
 *
 */
@XmlRootElement(name = "account")
public class Account extends Element {

	private String state;

	@XmlElement(name = "org_name")
	private String orgName;

	private List<Application> applications;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@XmlElement(name="application")
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> application) {
		this.applications = application;
	}
}
