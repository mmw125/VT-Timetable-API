package vtscheduler;

import java.util.ArrayList;

/**
 * Provides a clear way to register listeners 
 * to classes that get information
 * @author Mark Wiggans
 */
public abstract class InformationSender {
	private ArrayList<InformationListener> listeners;
	
	protected void notifyListeners(Object obj) {
		if(listeners != null) {
			if(obj instanceof InformationEvent) {
				for(InformationListener listener : listeners) {
					listener.informationRecieved((InformationEvent) obj);
				}
			} else if(obj instanceof Options) {
				for(InformationListener listener : listeners) {
					listener.informationRecieved((InformationEvent) obj);
				}
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
