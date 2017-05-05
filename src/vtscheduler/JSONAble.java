package vtscheduler;

import org.json.simple.JSONObject;

/**
 * Interface that says that the object can be converted to JSON
 * @author Mark Wiggans
 */
public interface JSONAble {
	/**
	 * Converts the object to JSON
	 * @return a JSON representation of the object
	 */
	public JSONObject toObject();
}
