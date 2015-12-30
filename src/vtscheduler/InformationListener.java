package vtscheduler;

/**
 * How classes listen to events that the classes that
 * send out information produce. 
 * @author Mark Wiggans
 */
public interface InformationListener {
	
	/**
	 * Lets the application know that information has been recieved
	 * @param event the details from the event
	 */
	public void informationRecieved(InformationEvent event);
	
	/**
	 * Lets the caller know that an error has occurred
	 * @param e the error that happened
	 */
	public void errorOccurred(Exception e);
}
