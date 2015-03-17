package vtscheduler;

/**
 * This class manages the connection to the Time Table of Classes
 */
public class TimeTable {
    private static TimeTable instance;
    public static TimeTable getInstance(){
        if(instance == null){
            instance = new TimeTable();
        }
        return instance;
    }

    private TimeTable(){

    }
}
