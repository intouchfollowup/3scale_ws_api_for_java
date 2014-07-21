package threescale.v3.xml.webhook;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EventType")
@XmlEnum
public enum EventType {

	@XmlEnumValue("application")
	APPLICATION("application");

	private final String value;

	EventType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static boolean isApplication(EventType type) {
		return APPLICATION.equals(type);
	}

	public static EventType fromValue(String value) {
		for (EventType type : EventType.values()) {
			if (type.value.equals(value)) {
				return type;
			}
		}

		throw new IllegalArgumentException(value);
	}
}
