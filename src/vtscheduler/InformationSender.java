package vtscheduler;

import java.util.ArrayList;

/**
 * Provides a clear way to register listeners 
 * to classes that get information
 * @author Mark Wiggans
 */
public abstract class InformationSender {
	private ArrayList<InformationListener> listeners;
	
	protected void notifyListeners(InformationEvent event) {
		if(listeners != null) {
			for(InformationListener listener : listeners) {
				listener.informationRecieved(event);
			}
		}
	}
	
	/**
	 * Registers the given listener to this sender. The listeners
	 * are called in the order that they are added
	 * @param listener the listerner to register
	 */
	public void registerListener(InformationListener listener) {
		if(listeners == null) {
			listeners = new ArrayList<InformationListener>();
		}
		listeners.add(listener);
	}
}
