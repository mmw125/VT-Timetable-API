package vtscheduler;

import java.util.ArrayList;

public class InformationEvent {
	private ArrayList<Course> courses;
	private Query query;
	
	/**
	 * 
	 * @param courses
	 * @param initialQuery
	 */
	public InformationEvent(ArrayList<Course> courses, Query initialQuery) {
		this.courses = courses;
		this.query = initialQuery;
	}
	
	/**
	 * Gets the courses that were returned from the query
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public Query getQuery() {
		return query;
	}
}
