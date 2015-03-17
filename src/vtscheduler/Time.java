package vtscheduler;

/**
 * This represents a time
 * It takes in 12 hour AM-PM and converts it to military time to make it easier to compute
 */
public class Time {
    private int hours = 0, minutes = 0;
    private String startString;

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

    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }
}
