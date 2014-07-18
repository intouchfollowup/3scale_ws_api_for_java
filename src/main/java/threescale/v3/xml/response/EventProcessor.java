package threescale.v3.xml.response;

public interface EventProcessor {

	/**
	 * Determine if the given {@link Event} is expected and has required data to process.
	 * 
	 * @param event - {@link Event} to be tested
	 * @return true if {@link Event} is of type {@link EventType.APPLICATION} and has valid application ID
	 */
	boolean isProcessable(Event event);

	/**
	 * Handle the given {@link Event} according to its type and action.
	 * 
	 * @param event - {@link Event} to be processed
	 */
	void process(Event Event);
}
