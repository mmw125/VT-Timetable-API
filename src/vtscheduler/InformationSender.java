package vtscheduler;

import java.util.ArrayList;

/**
 * Provides a clear way to register listeners 
 * to classes that get information
 * @author Mark Wiggans
 */
public abstract class InformationSender {
	private ArrayList<InformationListener> listeners;
	
	/**
	 * Notifies all of the listeners with the given object
	 * @param obj the object to give to the listeners
	 */
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
			} else {
				System.err.println("Cannot notify listeners with object of type " + obj.getClass());
			}
		} else {
			System.err.println("Tried to notify listeners of a null object");
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
		if(listener == null) {
			throw new NullPointerException("Tried to register a null listener");
		}
		listeners.add(listener);
	}
}
