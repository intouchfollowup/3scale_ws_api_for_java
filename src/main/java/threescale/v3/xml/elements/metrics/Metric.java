package threescale.v3.xml.elements.metrics;

import javax.xml.bind.annotation.XmlElement;

import threescale.v3.xml.elements.Element;

/**
 *
 * @author Moncef
 *
 */
public class Metric extends Element {

	private String name;
	private String systemName;
	private String friendlyName;
	private String serviceId;
	private String unit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "system_name")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@XmlElement(name = "friendly_name")
	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	@XmlElement(name = "service_id")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}