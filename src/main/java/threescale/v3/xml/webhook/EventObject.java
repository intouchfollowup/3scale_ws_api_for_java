package threescale.v3.xml.webhook;

import javax.xml.bind.annotation.XmlRootElement;

import threescale.v3.xml.response.Application;

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
