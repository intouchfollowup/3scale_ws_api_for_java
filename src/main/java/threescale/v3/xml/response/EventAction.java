package threescale.v3.xml.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EventAction")
@XmlEnum
public enum EventAction {

	@XmlEnumValue("created")
	APP_CREATED("created"),

	@XmlEnumValue("updated")
	APP_UPDATED("updated"),

	@XmlEnumValue("suspended")
	APP_SUSPENDED("suspended"),

	@XmlEnumValue("key_created")
	KEY_CREATED("key_created"),

	@XmlEnumValue("key_deleted")
	KEY_DELETED("key_deleted");

	private final String value;

	EventAction(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static EventAction fromValue(String value) {
		for (EventAction action : EventAction.values()) {
			if (action.value.equals(value)) {
				return action;
			}
		}

		throw new IllegalArgumentException(value);
	}
}
