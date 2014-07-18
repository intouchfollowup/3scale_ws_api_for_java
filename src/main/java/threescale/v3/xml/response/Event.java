package threescale.v3.xml.response;



import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data sent from 3scale on a web hook event.
 *
 * @see <a href="https://support.3scale.net/howtos/api-operations#webhooks">HowTo use Webhooks</a>
 */
@XmlRootElement(name = "event")
public class Event {

	/**
	 * The subject of the event. i.e. 'application', 'account', etc.
	 */
	private EventType type;

	/**
	 * What has been done. i.e. 'updated', 'created', 'deleted', etc.
	 */
	private EventAction action;

	/**
	 * The XML object itself in the same format that is returned by the Account Management API.
	 *
	 * @see <a href="https://support.3scale.net/reference/active-docs#/account_management_api">Active Docs</a>
	 */
	private EventObject object;

	public boolean hasEventObject() {
		return object != null;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public EventAction getAction() {
		return action;
	}

	public void setAction(EventAction action) {
		this.action = action;
	}

	public EventObject getObject() {
		return object;
	}

	public void setObject(EventObject object) {
		this.object = object;
	}
}
