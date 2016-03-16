package vtscheduler;

import java.util.ArrayList;

/**
 * Gives the list of the possible options to select when searching
 * @author Mark Wiggans
 */
public class Options {
	
	private ArrayList<String> campuses = null, 
			terms = null, 
			cles = null, 
			subject = null, 
			sectionType = null, 
			display = null;
	
	/**
	 * Creates a new option object with all of the objects set to zero
	 */
	Options() {
		this(null, null, null, null, null, null);
	}
	
	/**
	 * Creates a new option object
	 * @param campuses options for campuses
	 * @param terms options for terms
	 * @param cles options for cles
	 * @param subject options for subjects
	 * @param sectionType options for sectionTypes
	 * @param display options for displays
	 */
	public Options(ArrayList<String> campuses, ArrayList<String> terms, ArrayList<String> cles, ArrayList<String> subject, ArrayList<String> sectionType, ArrayList<String> display){
		this.campuses = campuses;
		this.terms = terms;
		this.cles = cles;
		this.subject = subject;
		this.sectionType = sectionType;
		this.display = display;
	}
	
	/**
	 * Sets the campuses that are available
	 * @param camp the campuses to choose from
	 */
	void setCampuses(ArrayList<String> camp){ this.campuses = camp;	}
	
	void setTerms(ArrayList<String> terms){ this.terms = terms; }
	
	void setCLEs(ArrayList<String> camp){ this.campuses = camp; }
	
	void setSubjects(ArrayList<String> subject){ this.subject = subject; }
	
	void setSectionTypes(ArrayList<String> sectionTypes){ this.sectionType = sectionTypes; }
	
	void setDisplay(ArrayList<String> display){ this.display = display; }
	
	public ArrayList<String> getCampuses(){ return campuses; }
	
	public ArrayList<String> getTerms(){ return terms; }
	
	public ArrayList<String> getCLEs(){ return cles; }
	
	public ArrayList<String> getSubject(){ return subject; }
	
	public ArrayList<String> getSectionType(){ return sectionType; }
	
	public ArrayList<String> getDisplay(){ return display; }
}
