package vtscheduler;

/**
 * This represents a time
 * It takes in 12 hour AM-PM and converts it to military time to make it easier to compute
 */
class Time {
    private int hours = 0, minutes = 0;
    private String startString;

    /**
     * Creates a new time with the given 
     * @param time
     */
    public Time(String time){
        if(time == null){
            time = "00:00AM";
        }
        startString = time;
        int colonPos = time.indexOf(':');
        if(colonPos != -1){
            hours = Integer.parseInt(time.substring(0, colonPos)) + 12;
            minutes = Integer.parseInt(time.substring(colonPos+1, colonPos+3));
            if(time.endsWith("PM")){
                hours = hours + 12;
            }
        }
    }

    @Override
    public String toString(){
        return startString;
    }

    /**
     * Gets the num of hours in the 24 hour format
     * @return hours in 24 hour formay
     */
    public int getHours(){
        return hours;
    }

    /**
     * Gets the number of minutes in the time
     * @return the minutes
     */
    public int getMinutes(){
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
}
