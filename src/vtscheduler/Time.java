package vtscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This represents a time
 * It takes in 12 hour AM-PM and converts it to military time to make it easier to compute
 */
class Time {
	public static final SimpleDateFormat SHORT_HOUR_TIME = new SimpleDateFormat("hh:mma");
    private Integer hours, minutes;
    private String startString;

    /**
     * Creates a new time with the given 
     * @param time
     */
    public Time(String time){
        startString = time;
        Integer[] times = timeToInt(startString);
        hours = times[0];
        minutes = times[1];
    }
    
    /**
     * Converts a time to a Integer array
     * @param time string time in format 12:00AM
     * @return array for number in [hours, minutes] in 24-hour format
     */
    public static Integer[] timeToInt(String time) {
    	if(time != null) {
    		try{
                Date d = SHORT_HOUR_TIME.parse(time);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(d);
                return new Integer[]{calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)};
            } catch (ParseException e) { }
    	}
        return new Integer[]{null, null};
    }

    @Override
    public String toString(){
        return startString;
    }

    /**
     * Gets the number of hours in the 24 hour format
     * @return hours in 24 hour format
     */
    public Integer getHours(){
        return hours;
    }

    /**
     * Gets the number of minutes in the time
     * @return the minutes
     */
    public Integer getMinutes(){
        return minutes;
    }
    
    @Override
    public boolean equals(Object oth) {
    	if(oth == null) { return false; }
    	if(oth instanceof Time) {
    		Time other = (Time) oth;
    		return other.minutes == minutes && other.hours == hours;
    	}
    	return false;
    }
    
    /**
     * Gets the integer representation of the time
     * @return hours * 60 + minutes or null
     */
    public Integer toInt() {
    	if(this.getHours() == null || this.getMinutes() == null) {
    		return null;
    	}
    	return (this.getHours() * 60) + this.getMinutes();
    }
}
