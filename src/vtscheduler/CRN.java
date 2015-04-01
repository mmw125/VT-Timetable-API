package vtscheduler;

import java.util.ArrayList;

/**
 * Represents an individual CRN
 * @author Mark Wiggans
 */
public class CRN {
    private int crn;
    private String instructor, location;
    private ArrayList<MeetingTime> meetingTimes;
    private Course course;
    public CRN(int crn, String instructor, String location){
        this.crn = crn;
        this.location = location;
        this.instructor = instructor;
        meetingTimes = new ArrayList<MeetingTime>();
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
     * @param dat
     */
    public void addAdditionalTimes(MeetingTime dat){
        meetingTimes.add(dat);
    }
    
    public void setCourse(Course course){
    	this.course = course;
    }
    
    public Course getCourse(){
    	return course;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append(crn + " ");
        b.append(instructor + " ");
        b.append(location + " ");
        for(MeetingTime time : meetingTimes){
            b.append(time.toString() + " ");
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
}
