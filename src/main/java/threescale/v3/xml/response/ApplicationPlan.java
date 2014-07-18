package threescale.v3.xml.response;

import javax.xml.bind.annotation.XmlElement;

public class ApplicationPlan {
	private String id;
	private String name;
	private String type;
	private String state;
	private String serviceId;
	private Boolean endUserRequired;
	private Float setupFee;
	private Float costPerMonth;
	private String trialPeriodDays;
	private String cancellationPeriod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@XmlElement(name = "service_id")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@XmlElement(name = "end_user_required")
	public Boolean getEndUserRequired() {
		return endUserRequired;
	}

	public void setEndUserRequired(Boolean endUserRequired) {
		this.endUserRequired = endUserRequired;
	}

	@XmlElement(name = "setup_fee")
	public Float getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(Float setupFee) {
		this.setupFee = setupFee;
	}

	@XmlElement(name = "cost_per_month")
	public Float getCostPerMonth() {
		return costPerMonth;
	}

	public void setCostPerMonth(Float costPerMonth) {
		this.costPerMonth = costPerMonth;
	}

	@XmlElement(name = "trial_period_days")
	public String getTrialPeriodDays() {
		return trialPeriodDays;
	}

	public void setTrialPeriodDays(String trialPeriodDays) {
		this.trialPeriodDays = trialPeriodDays;
	}

	@XmlElement(name = "cancellation_period")
	public String getCancellationPeriod() {
		return cancellationPeriod;
	}

	public void setCancellationPeriod(String cancellationPeriod) {
		this.cancellationPeriod = cancellationPeriod;
	}
}
