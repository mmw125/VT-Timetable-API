package vtscheduler;

import java.util.ArrayList;


/**
 * This represents a department at the university
 * @author Mark Wiggans
 */
public class Department {
    private static ArrayList<Department> departments = new ArrayList<Department>();
    private String abbrev, name = "";
    private ArrayList<Course> courses;

    /**
     * This will either get the department or create a new department with the given abbreviation
     * @param abbrev the abbrev of the department you are trying to get
     * @return the Department with the given abbrev
     */
    public static Department getDepartment(String abbrev){
        for(Department department : departments){
            if(department.abbrev.equals(abbrev)){
                return department;
            }
        }

        Department d = new Department(abbrev);
        departments.add(d);
        return d;
    }

    /**
     * Gets a list of all of the departments that have been created
     * @return a list of the created departments
     */
    public static ArrayList<Department> getDepartments(){
        return departments;
    }

    /**
     * Creates a department with the given abbreviation
     * @param abbreviation the abbreviation to give it
     */
    private Department(String abbreviation){
        this.abbrev = abbreviation;
        courses = new ArrayList<Course>();
    }

    /**
     * Adds a course
     * @param course course to add
     */
    public void addCourse(Course course){
        courses.add(course);
    }

    /**
     * Sets the name of the department
     * @param name the name to give it
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the name of the department. If the name does not exist, the abbreviation is returned
     * instead.
     * @return the name of the department
     */
    public String getName(){
        if(name.equals("")){
            return abbrev;
        }else{
            return name;
        }
    }

    /**
     * Gets the courses taught
     * @return the courses taught
     */
    public ArrayList<Course> getCourses(){
        return courses;
    }

    /**
     * This gets the abbreviation
     * @return the abbreviation
     */
    public String getAbbreviation(){
        return abbrev;
    }
}