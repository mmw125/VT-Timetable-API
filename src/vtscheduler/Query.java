package vtscheduler;

/**
 * This class holds all of the options for a search
 * @author Mark Wiggans
 */
public class Query {
	private String campus, term, cle, subject, sectionType, display;
	private int courseNumber, crn;
	public Query(String campus, String term, String cle, String subject, String sectionType, String display, int courseNumber, int crn){
		this.campus = campus;
		this.term = term;
		this.cle = cle;
		this.subject = subject;
		this.sectionType = sectionType;
		this.display = display;
		this.courseNumber = courseNumber;
		this.crn = crn;
	}
	
	/**
	 * Runs the query and will call the listeners on the timetable
	 */
	public void run() {
		Timetable.getInstance().runQuery(this);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) { return false; }
		if(other == this) { return true; }
		if(other instanceof Query) {
			Query q = (Query) other;
			return strCmp(q.campus, campus) && 
					strCmp(q.term, term) && 
					strCmp(q.cle, cle) && 
					strCmp(q.subject, subject) && 
					strCmp(q.sectionType, sectionType) && 
					strCmp(q.display, display) &&
					q.courseNumber == courseNumber &&
					q.crn == crn;
		}
		return false;
	}
	
	/**
	 * Compares two strings
	 * @param str0 first string
	 * @param str1 second string
	 * @return true if the two strings are the same false if they are not
	 */
	private boolean strCmp(String str0, String str1) {
		if(str0 == str1 || (str0 != null && str0.equals(str1))) {
			return true;
		}
		return false;
	}
}
