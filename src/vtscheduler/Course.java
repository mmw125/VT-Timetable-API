package vtscheduler;

import java.util.ArrayList;

/**
 * Represents a course offered at tech
 * 
 * @author Mark Wiggans
 */
public class Course {
    private String department, name, course, number;
    private ArrayList<CRN> crns;
    private ClassType type;

    /**
     * Creates a course with the given course and name
     * @param course the course's number
     * @param name the name of the course
     */
    public Course(String course, String name, ClassType type){
        this.name = name;
        this.course = course;
        String[] splitCourse = course.split("-");
        department = splitCourse[0];
        crns = new ArrayList<CRN>();
        number = splitCourse[1];
    }

    /**
     * Checks if the given crn is already a part of the course. If it is, do nothing
     * @param crn the crn to add
     */
    public void addCRN(CRN crn){
        for(CRN c : crns){
            if(c.equals(crn)){
                return;
            }
        }
        crns.add(crn);
        crn.setCourse(this);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder(department + " " + number + " " + name + '\n');
        for(CRN crn : crns){
            builder.append("	"+crn.toString()+'\n');
        }
        return builder.toString();
    }

    /**
     * Gets the course string
     * @return the course string
     */
    public String getCourseString() {
        return course + "-" + type;
    }
}

