package vtscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

// Using http://www.sqlitetutorial.net/sqlite-java/
/**
 * Class that creates the database
 * @author Mark Wiggans
 */
public class CreateDatabase {
	/**
	 * Creates a new database at the given path
	 * @param path the location for the database
	 * @return the CreateDatabase object
	 */
	public static CreateDatabase createNewDatabase(String path) {
		String url = "jdbc:sqlite:" + path;
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				return new CreateDatabase(path);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String url;

	/**
	 * Create a new CreateDatabase object at the given path 
	 * @param path the location for the database
	 */
	private CreateDatabase(String path) {
		url = "jdbc:sqlite:" + path;
	}

	/**
	 * Creates the tables for the database
	 */
	public void createTables() {
		runSQL(DatabaseConstants.CREATE_DEPARTMENT_TABLE);
		runSQL(DatabaseConstants.CREATE_COURSE_TABLE);
		runSQL(DatabaseConstants.CREATE_CRN_TABLE);
		runSQL(DatabaseConstants.CREATE_MEETING_TIME_TABLE);
		runSQL(DatabaseConstants.CREATE_SCHEDULE_TABLE);
		runSQL(DatabaseConstants.CREATE_SCHEDULE_ITEM_TABLE);
	}
	
	/**
	 * Connects to the database
	 * @return a connection object to the database
	 */
	private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

	/**
	 * Runs the given SQL on the database
	 * @param sql the SQL to run on the database
	 */
	public void runSQL(String sql) {
		try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts the department into the database along with all of ites courses
	 * @param department
	 */
	public void insert(Department department) {
		String sql = "INSERT INTO department(department_name,department_abbreviation) VALUES(?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, department.getName());
			pstmt.setString(2, department.getAbbreviation());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(department.toString());
			System.out.println(e.getMessage());
		}
		
		for(Course c : department.getCourses()) {
			insert(c);
		}
	}
	
	/**
	 * Inserts the course into the database along with all of its crns
	 * @param c the course to insert into the database
	 */
	public void insert(Course c) {
		if(c.getCRNs().size() == 0) {
			return;
		}
		String sql = "INSERT INTO course(whole_name, name, course_number, department_id, semester, type) VALUES(?,?,?,?,?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, c.getCourseString());
			pstmt.setString(2, c.getName());
			pstmt.setString(3, c.getNumber());
			pstmt.setString(4, c.getDepartment().getAbbreviation());
			pstmt.setString(5, c.getSemester());
			pstmt.setString(6, c.getType() == null ? "None": c.getType().toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(c.toString());
			System.out.println(e.getMessage());
		}
		
		for(CRN crn : c.getCRNs()) {
			insert(crn);
		}
	}
	
	/**
	 * Inserts the crn into the database along with all of its meeting times
	 * @param crn the crn to insert into the database
	 */
	public void insert(CRN crn) {
		String sql = "INSERT INTO crn(crn_txt, crn, instructor, location, course_whole_name, course_semester, course_type) VALUES(?,?,?,?,?,?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, String.valueOf(crn.getCRN()));
			pstmt.setInt(2, crn.getCRN());
			pstmt.setString(3, crn.getInstructor());
			pstmt.setString(4, crn.getLocation());
			pstmt.setString(5, crn.getCourse().getCourseString());
			pstmt.setString(6, crn.getCourse().getSemester());
			pstmt.setString(7, crn.getCourse().getType() == null ? "null" : crn.getCourse().getType().toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(crn.toString());
			System.out.println(e.getMessage());
		}
		
		for(MeetingTime mt : crn.getMeetingTimes()) {
			insert(mt, crn);
		}
	}
	
	/**
	 * Inserts the meeting time into the database
	 * @param mt the meeting time to insert into the database
	 * @param crn the parent crn for the meeting time
	 */
	public void insert(MeetingTime mt, CRN crn) {
		String sql = "INSERT INTO meetingtime(day, start_time, start_time_str, end_time, end_time_str, crn_crn, crn_semester) VALUES(?,?,?,?,?,?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, mt.getDay().toString());
			Integer start = mt.getStartTime().toInt();
			if(start == null) {
				pstmt.setNull(2, Types.INTEGER);
			} else {
				pstmt.setInt(2, mt.getStartTime().toInt());
			}
			pstmt.setString(3, mt.getStartTime().toString());
			
			Integer end = mt.getEndTime().toInt();
			if(end == null) {
				pstmt.setNull(4, Types.INTEGER);
			} else {
				pstmt.setInt(4, mt.getEndTime().toInt());
			}
			pstmt.setString(5, mt.getEndTime().toString());
			pstmt.setInt(6, crn.getCRN());
			pstmt.setString(7, crn.getCourse().getSemester());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
