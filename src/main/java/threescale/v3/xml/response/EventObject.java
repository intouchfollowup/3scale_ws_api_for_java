package threescale.v3.xml.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "object")
public class EventObject {

	private Application application;

	public boolean hasApplication() {
		return application != null;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}
