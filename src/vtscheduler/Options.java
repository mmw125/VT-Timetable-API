package vtscheduler;

import java.util.ArrayList;

public class Options {
	private ArrayList<String> campuses = null, 
			terms = null, 
			cles = null, 
			subject = null, 
			sectionType = null, 
			display = null;
	
	Options(ArrayList<String> campuses, ArrayList<String> terms, ArrayList<String> cles, ArrayList<String> subject, ArrayList<String> sectionType, ArrayList<String> display){
		this.campuses = campuses;
		this.terms = terms;
		this.cles = cles;
		this.subject = subject;
		this.sectionType = sectionType;
		this.display = display;
	}
	
//	public void setCampuses(ArrayList<String> camp){
//		this.campuses = camp;
//	}
//	public void setTerms(ArrayList<String> terms){
//		this.terms = terms;
//	}
//	public void setCLEs(ArrayList<String> camp){
//		this.campuses = camp;
//	}
//	
//	public void setSubject(ArrayList<String> subject){
//		this.subject = subject;
//	}
//	public void setSectionType(ArrayList<String> sectionTypes){
//		this.sectionType = sectionTypes;
//	}
//	public void setDisplay(ArrayList<String> display){
//		this.display = display;
//	}
	
	public ArrayList<String> getCampuses(){
		return campuses;
	}
	
	public ArrayList<String> getTerms(){
		return terms;
	}
	
	public ArrayList<String> getCLEs(){
		return cles;
	}
	
	public ArrayList<String> getSubject(){
		return subject;
	}
	public ArrayList<String> getSectionType(){
		return sectionType;
	}
	
	public ArrayList<String> getDisplay(){
		return display;
	}
}
