package threescale.v3.xml.response;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application")
public class Application {
	private String id;
	private Date createdAt;
	private Date updatedAt;
	private ApplicationState state;
	private String userAccountId;
	private Boolean endUserRequired;
	private String serviceId;
	private String applicationId;
	private String redirectUrl;
	private List<ApplicationKey> keys;
	private ApplicationPlan plan;
	private String name;
	private String description;
	private Object extraFields;

	public boolean hasId() {
		return isNotBlank(id);
	}

	public boolean isActive() {
		return ApplicationState.isLive(state);
	}

	public String getFirstKey() {
		return ((keys != null) && !keys.isEmpty()) ? keys.get(0).getKey() : null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ApplicationState getState() {
		return state;
	}

	public void setState(ApplicationState state) {
		this.state = state;
	}

	@XmlElement(name = "user_account_id")
	public String getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId;
	}

	@XmlElement(name = "end_user_required")
	public Boolean getEndUserRequired() {
		return endUserRequired;
	}

	public void setEndUserRequired(Boolean endUserRequired) {
		this.endUserRequired = endUserRequired;
	}

	@XmlElement(name = "service_id")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@XmlElement(name = "application_id")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@XmlElement(name = "redirect_url")
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public List<ApplicationKey> getKeys() {
		return keys;
	}

	public void setKeys(List<ApplicationKey> keys) {
		this.keys = keys;
	}

	public ApplicationPlan getPlan() {
		return plan;
	}

	public void setPlan(ApplicationPlan plan) {
		this.plan = plan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "extra_fields")
	public Object getExtraFields() {
		return extraFields;
	}

	public void setExtraFields(Object extraFields) {
		this.extraFields = extraFields;
	}
}
