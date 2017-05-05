package vtscheduler;

public class DatabaseConstants {
	public static final String CREATE_COURSE_TABLE = "CREATE TABLE course ("
			+ "whole_name text NOT NULL, "
			+ "name text NOT NULL, "
			+ "course_number text NOT NULL, "
			+ "type text NOT NULL, "
			+ "department_id text NOT NULL REFERENCES department (department_abbreviation), "
			+ "semester text NOT NULL,"
			+ "PRIMARY KEY (whole_name, semester, type))";

	public static final String CREATE_CRN_TABLE = "CREATE TABLE crn ("
			+ "crn integer unsigned NOT NULL, "
			+ "crn_txt text NOT NULL, " 
			+ "instructor text NOT NULL, " 
			+ "location text NOT NULL, "
			+ "course_whole_name text NOT NULL, "
			+ "course_semester text NOT NULL, "
			+ "course_type text NOT NULL, "
			+ "primary key (crn, course_semester), "
			+ "foreign key (course_whole_name, course_semester, course_type) references course(whole_name, semester, type))";

	public static final String CREATE_MEETING_TIME_TABLE = "CREATE TABLE meetingtime ("
			+ "id integer NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ "day text NULL, "
			+ "start_time integer NULL, "
			+ "start_time_str text NOT NULL, "
			+ "end_time integer NULL, "
			+ "end_time_str text NOT NULL, "
			+ "crn_crn integer NOT NULL, "
			+ "crn_semester text NOT NULL,"
			+ "foreign key(crn_crn, crn_semester) references crn(crn, course_semester))";

	public static final String CREATE_DEPARTMENT_TABLE = "CREATE TABLE department ("
			+ "department_name text NULL, "
			+ "department_abbreviation text NOT NULL PRIMARY KEY)";

	public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE schedule ("
			+ "name text NOT NULL, "
			+ "schedule_uuid varchar(256) PRIMARY KEY)";

	public static final String CREATE_SCHEDULE_ITEM_TABLE = "CREATE TABLE scheduleitem ("
			+ "id integer NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ "crn_crn integer NOT NULL, "
			+ "crn_semester text NOT NULL, "
			+ "schedule_uuid varchar(256) NOT NULL REFERENCES schedule (schedule_uuid), "
			+ "foreign key (crn_crn, crn_semester) references crn (crn, course_semester))";
}
