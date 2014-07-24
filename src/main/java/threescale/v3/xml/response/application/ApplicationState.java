package threescale.v3.xml.response.application;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ApplicationState")
@XmlEnum
public enum ApplicationState {

	@XmlEnumValue("pending")
	PENDING("pending"),

	@XmlEnumValue("live")
	LIVE("live"),

	@XmlEnumValue("suspended")
	SUSPENDED("suspended");

	private final String value;

	ApplicationState(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static boolean isPending(ApplicationState state) {
		return PENDING.equals(state);
	}

	public static boolean isLive(ApplicationState state) {
		return LIVE.equals(state);
	}

	public static boolean isSuspended(ApplicationState state) {
		return SUSPENDED.equals(state);
	}

	public static ApplicationState fromValue(String value) {
		for (ApplicationState state : ApplicationState.values()) {
			if (state.value.equals(value)) {
				return state;
			}
		}

		throw new IllegalArgumentException(value);
	}
}
