package vtscheduler;

import java.util.ArrayList;

/**
 * This has a day and a start and end time.  
 */
public class MeetingTime {
    private Time start, end;
    private Day day;
    private String location;
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

    public String toString(){
        if(day == Day.ONLINE){
            return Day.ONLINE.toString();
        }
        return day.toString() + "(" + start.toString() + "-" + end.toString()+")";
    }

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
}
