package vtscheduler;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents a course offered at tech
 * 
 * @author Mark Wiggans
 */
public class Course {
    private String name, course, number, semester;
    private Department department;
    private ArrayList<CRN> crns;
    private ClassType type;

    /**
     * Creates a course with the given course and name
     * @param course the course's number
     * @param name the name of the course
     * @param semester 
     */
    public Course(String course, String name, ClassType type, String semester){
    	this.type = type;
        this.name = name;
        this.course = course;
        this.semester = semester;
        String[] splitCourse = course.split("-");
        department = Department.getDepartment(splitCourse[0]);
        department.addCourse(this);
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
        StringBuilder builder = new StringBuilder(semester + "|" + department.getAbbreviation() + "|" + number + "|" + name + "|" + type);
        for(CRN crn : crns){
            builder.append("~"+crn.toString());
        }
        return builder.toString();
    }

    /**
     * Gets the course string
     * @return the course string
     */
    public String getCourseString() {
        return course;
    }
    
    public ClassType getType() {
    	return type;
    }
    
    @SuppressWarnings("unchecked")
	public JSONObject toObject() {
    	JSONObject obj = new JSONObject();
    	obj.put("type", this.type);
    	obj.put("name", this.name);
    	obj.put("course", this.course);
    	obj.put("semester", this.semester);
    	obj.put("number", this.number);
    	JSONArray crnList = new JSONArray();
    	for(CRN crn : crns) {
    		crnList.add(crn.toObject());
    	}
    	obj.put("crns", crnList);
    	return obj;
    }
}

