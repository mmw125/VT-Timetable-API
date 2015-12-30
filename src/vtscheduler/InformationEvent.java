package vtscheduler;

import java.util.ArrayList;

public class InformationEvent {
	private ArrayList<Course> courses;
	
	public InformationEvent(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
}
