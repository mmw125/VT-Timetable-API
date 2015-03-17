package vtscheduler;

/**
 * Created by Mark on 3/3/2015.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, ONLINE;

    /**
     * This converts a string that starts with a day of the week to a day object. This
     * correlates with Virginia Tech's naming system.
     * M = Monday
     * T = Tuesday
     * W = Wednesday
     * R = Thursday
     * F = Friday
     * @param str the string object
     * @return the day of the week to return
     */
    public static Day stringToDay(String str){
        if(str.startsWith("M")){
            return MONDAY;
        }else if(str.startsWith("T")){
            return TUESDAY;
        }else if(str.startsWith("W")){
            return WEDNESDAY;
        }else if(str.startsWith("R")){
            return THURSDAY;
        }else if(str.startsWith("F")){
            return FRIDAY;
        }
        return ONLINE;
    }
}