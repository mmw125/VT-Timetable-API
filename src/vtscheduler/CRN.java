package vtscheduler;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents an individual CRN
 * @author Mark Wiggans
 */
public class CRN implements JSONAble {
    private int crn;
    private String instructor, location;
    private ArrayList<MeetingTime> meetingTimes;
    private Course course;
    
    /**
     * Creates a new CRN
     * @param crn the crn's number
     * @param instructor who teaches the class
     * @param location where the building is located
     */
    public CRN(int crn, String instructor, String location){
        this.crn = crn;
        this.location = location;
        this.instructor = instructor;
        meetingTimes = new ArrayList<MeetingTime>();
    }
    
    public String getLocation() {
    	return location;
    }

    /**
     * Gets the number
     * @return the crn
     */
    public int getCRN(){
        return crn;
    }

    /**
     * Gets the instructor of the class
     * @return the instructor of the class
     */
    public String getInstructor(){
        return instructor;
    }

    /**
     * Gets a list of the meeting times
     * @return a list of the meeting times
     */
    public ArrayList<MeetingTime> getMeetingTimes(){
        return meetingTimes;
    }

    /**
     * Adds the given meeting time to
     * @param dat the additional meeting time
     */
    public void addAdditionalTimes(MeetingTime dat){
        meetingTimes.add(dat);
    }
    
    /**
     * Sets which course that this is 
     * @param course the course
     */
    public void setCourse(Course course){
    	this.course = course;
    }
    
    /**
     * Gets the course that this is
     * @return the course
     */
    public Course getCourse(){
    	return course;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append(crn + "|");
        b.append(instructor + "|");
        b.append(location + "|");
        for(MeetingTime time : meetingTimes){
            b.append(time.toString() + "|");
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other){
        if(other == null){ return false;}
        if(other instanceof CRN){
            return crn == ((CRN)other).crn;
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
	public JSONObject toObject() {
    	JSONObject obj = new JSONObject();
    	obj.put("crn", this.crn);
    	obj.put("instructor", this.instructor);
    	obj.put("location", this.location);
    	JSONArray meetingTimeList = new JSONArray();
    	for(MeetingTime mt : this.getMeetingTimes()) {
    		meetingTimeList.add(mt.toObject());
    	}
    	obj.put("meetingTimes", meetingTimeList);
    	return obj;
    }
}

