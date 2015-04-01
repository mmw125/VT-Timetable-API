package vtscheduler;

import java.util.ArrayList;

public interface DataReceiver {
	public void courseArrayResponse(ArrayList<Course> courses);
	public void formOptions(Options opt);
	public void errorThrown(Exception e);
}
