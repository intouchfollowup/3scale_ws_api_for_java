package threescale.v3.xml.elements.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import threescale.v3.xml.elements.Element;
import threescale.v3.xml.elements.metrics.Metrics;

@XmlRootElement(name = "service")
public class Service extends Element {

	private String accountId;
	private String name;
	private boolean endUserRegistrationRequired;
	// TODO - Should be mapped to enum but all different states are unknown
	private String state;
	private Metrics metrics;

	@XmlElement(name = "account_id")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "end_user_registration_required")
	public boolean isEndUserRegistrationRequired() {
		return endUserRegistrationRequired;
	}

	public void setEndUserRegistrationRequired(boolean endUserRegistrationRequired) {
		this.endUserRegistrationRequired = endUserRegistrationRequired;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}
}
