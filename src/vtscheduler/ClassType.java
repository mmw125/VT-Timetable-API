package vtscheduler;

/**
 * Represents the different class types
 * This is helpful for classes like physics where you need to request a lab and a lecture
 * @author Mark Wiggans
 */
public enum ClassType {
    LECTURE, LAB, INDEPENDENT_STUDY, RECITATION, RESEARCH, ONLINE;

    /**
     * This converts the type string to a ClassType object
     * This uses the first character of the string as the
     * @param s the type string
     * @return a ClassType object that correlates with the given string
     */
    public static ClassType stringToType(String s){
        if(s.equals("ONLINE COURSE")){
            return ONLINE;
        }
        switch(s.charAt(0)){
            case 'B': return LAB;
            case 'I': return INDEPENDENT_STUDY;
            case 'C': return RECITATION;
            case 'R': return RESEARCH;
            default: return LECTURE;
        }
    }

    /**
     * Returns a nicer-looking version of the string
     * @return a nicer version of the string
     */
    @Override
    public String toString(){
        switch(this){
            case LECTURE: return "Lecture";
            case LAB: return "Lab";
            case INDEPENDENT_STUDY: return "Independent Study";
            case RECITATION: return "Recitation";
            case RESEARCH: return "Research";
            case ONLINE: return "Online";
            default: return "";
        }
    }
}
