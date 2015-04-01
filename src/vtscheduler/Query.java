package vtscheduler;

import java.util.ArrayList;

/**
 * This class holds all of the options for a search
 * @author Mark Wiggans
 */
public class Query {
	private ArrayList<String> options;
	public Query(String campus, String term, String cle, String subject, String sectionType, String display){
		options = new ArrayList<String>();
		options.add(campus);
		options.add(term);
		options.add(cle);
		options.add(subject);
		options.add(sectionType);
		options.add(display);
	}
	
	public ArrayList<String> getOptions(){
		return options;
	}
}
