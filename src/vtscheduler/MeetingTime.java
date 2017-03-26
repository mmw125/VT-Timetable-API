package vtscheduler;

import java.util.ArrayList;

import org.json.simple.JSONObject;

/**
 * This has a day and a start and end time.
 */
public class MeetingTime implements JSONAble {
    private Time start, end;
    private Day day;
    private String location;
    
    /**
     * Creates a new meeting time
     * @param start when the class starts
     * @param end when the class ends
     * @param day what day of the week the meeting is
     * @param location what building the class is in
     */
    public MeetingTime(Time start, Time end, Day day, String location){
        this.start = start;
        this.end = end;
        this.day = day;
        this.location = location;
    }

    /**
     * Gets the time that the class starts
     * @return the start time
     */
    public Time getStartTime(){
        return start;
    }

    /**
     * Gets the location of the meeting time
     * @return the location
     */
    public String getLocation(){
        return location;
    }

    /**
     * Gets the time that the class ends
     * @return the end time
     */
    public Time getEndTime(){
        return end;
    }

    /**
     * Gets the day that the meeting time is on
     * @return the day
     */
    public Day getDay(){
        return day;
    }

    @Override
    public String toString(){
        if(day == Day.ONLINE){
            return Day.ONLINE.toString();
        }
        return day.toString() + "(" + start.toString() + "-" + end.toString()+")";
    }
    
    @Override
    public boolean equals(Object other) {
    	if(other == null) { return false; }
    	if(other instanceof MeetingTime) {
    		MeetingTime oth = (MeetingTime) other;
    		return oth.day.equals(day) && oth.end.equals(end) && oth.location.equals(location) && oth.start.equals(start);
    	}
    	return false;
    }

    /**
     * Generates the meeting times from the data on the schedule
     * @param days the days the class meets
     * @param start what time the class start
     * @param end when the classes end
     * @param location where the classes are being held
     * @return a list of the meeting objects configured with thise meeting times
     */
    public static ArrayList<MeetingTime> parseStrings(String days, String start, String end, String location){
        ArrayList<MeetingTime> output = new ArrayList<MeetingTime>();

        Time startTime = new Time(start);
        Time endTime = new Time(end);

        String[] daySplit = days.split(" ");

        for(String s : daySplit){
            Day d = Day.stringToDay(s);
            output.add(new MeetingTime(startTime, endTime, d, location));
        }

        return output;
    }
    
    @SuppressWarnings("unchecked")
	public JSONObject toObject(){
    	JSONObject obj = new JSONObject();
    	obj.put("isOnline", day == Day.ONLINE);
    	if(day != Day.ONLINE) {
    		obj.put("day", day.toString());
    		obj.put("startTime", this.start.toString());
    		obj.put("endTime", this.end.toString());
    	}
    	return obj;
    }
}
