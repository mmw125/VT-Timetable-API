package vtscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Timetable {
	private static Timetable instance;
	private static final String URL = "https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest";

	/**
	 * Gets an instance of the Timetable
	 * 
	 * @return the instance of Timetable
	 */
	public static Timetable getInstance() {
		if (instance == null) {
			instance = new Timetable();
		}
		return instance;
	}

	private Options options;

	/**
	 * Creates a new instance of Timetable
	 */
	private Timetable() {
		options = null;
	}

	/**
	 * Gets the options and will return a cached copy if there is one
	 */
	public void getOptions() {
		getOptions(false);
	}

	/**
	 * Gets the available options
	 * 
	 * @param forceUpdate
	 *            if true, will update even if there is a cached copy, if false,
	 *            will return a cached copy if there is one
	 */
	public void getOptions(boolean forceUpdate) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if (forceUpdate || options == null) {
					WebClient webClient = new WebClient();
					HtmlPage page = null;
					try {
						page = webClient.getPage(URL);
					} catch (FailingHttpStatusCodeException | IOException e1) {
						e1.printStackTrace();
					}
					HtmlForm form = page.getFormByName("ttform");

					// Campus
					ArrayList<String> campusesList = new ArrayList<String>();
					HtmlSelect campuses = form.getSelectByName("CAMPUS");
					for (HtmlOption option : campuses.getOptions()) {
						campusesList.add(option.asText());
					}

					// Term
					ArrayList<String> termList = new ArrayList<String>();
					HtmlSelect term = form.getSelectByName("TERMYEAR");
					for (HtmlOption option : term.getOptions()) {
						if (!option.asText().equals("Select Term")) {
							termList.add(option.asText());
						}
					}

					// CLE
					ArrayList<String> cleList = new ArrayList<String>();
					HtmlSelect cles = form.getSelectByName("CORE_CODE");
					for (HtmlOption option : cles.getOptions()) {
						cleList.add(option.asText());
					}

					// Subject
					ArrayList<String> subjectList = new ArrayList<String>();
					HtmlSelect subjects = form.getSelectByName("subj_code");
					for (HtmlOption option : subjects.getOptions()) {
						subjectList.add(option.asText());
					}

					// Section Type
					ArrayList<String> sectTypeList = new ArrayList<String>();
					HtmlSelect sectType = form.getSelectByName("SCHDTYPE");
					for (HtmlOption option : sectType.getOptions()) {
						sectTypeList.add(option.asText());
					}

					// Display
					ArrayList<String> displayList = new ArrayList<String>();
					HtmlSelect display = form.getSelectByName("open_only");
					for (HtmlOption option : display.getOptions()) {
						displayList.add(option.asText());
					}

					webClient.closeAllWindows();
					options = new Options(campusesList, termList, cleList, subjectList, sectTypeList, displayList);
				}
			}
		});
		thread.run();
	}

	/**
	 * Parses the given page and creates class objects
	 * 
	 * @param page
	 *            the page to parse
	 * @param semester
	 *            semester name
	 */
	public ArrayList<Course> parsePage(HtmlPage page, String semester) {
		ArrayList<Course> courses = new ArrayList<Course>();
		if (page.getByXPath("//table[@class='dataentrytable']").isEmpty()) {
			return courses;
		}
		HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='dataentrytable']").get(0);
		Course lastCourse = null;
		CRN lastCRN = null;

		for (HtmlTableRow row : table.getRows()) {
			List<HtmlTableCell> cells = row.getCells();
			if (!cells.get(1).asText().trim().contains("Course")) {
				RowParser p = new RowParser(row);
				if (p.atr()) {
					// If it is an additional time row, just add the times
					// defined in the row
					for (MeetingTime m : MeetingTime.parseStrings(p.days(), p.begin(), p.end(), p.location())) {
						lastCRN.addAdditionalTimes(m);
					}
				} else {
					// If row is a course, check if the course is the same as the last course
					// If it is not, create a new course
					if (lastCourse == null || !lastCourse.getCourseString().equals(p.course())
							|| !lastCourse.getType().equals(p.type())) {
						Course c = new Course(p.course(), p.title(), p.type(), semester);
						courses.add(c);
						lastCourse = c;
					}
					// Create a new crn and add it to the course
					CRN crn = new CRN(p.crn(), p.instructor(), p.location());
					if (!p.online()) {
						for (MeetingTime m : MeetingTime.parseStrings(p.days(), p.begin(), p.end(), p.location())) {
							crn.addAdditionalTimes(m);
						}
						lastCRN = crn;
					}
					lastCourse.addCRN(crn);
				}
			}
		}
		return courses;
	}

	/**
	 * Scrapes the timetable of classes and puts the data into a database at database.db
	 * @param args unused
	 */
	public static void main(String[] args) {
		Timetable.getInstance().cacheTimeTable();
		File f = new File("database.db");
		f.delete();
		CreateDatabase db = CreateDatabase.createNewDatabase("database.db");
		db.createTables();
		for (Department d : Department.getDepartments()) {
			db.insert(d);
		}
	}

	/**
	 * Caches the entire time table to make queries faster
	 */
	public void cacheTimeTable() {
		System.out.println("Started parsing");
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient.getPage(URL);
		} catch (FailingHttpStatusCodeException | IOException e1) {
			e1.printStackTrace();
		}
		HtmlForm form = page.getFormByName("ttform");
		HtmlSubmitInput button = form.getInputByName("BTN_PRESSED");
		HtmlSelect term = form.getSelectByName("TERMYEAR");
		term.setSelectedAttribute(term.getOptions().get(1), true);
		HtmlSelect select = form.getSelectByName("subj_code");
		for (HtmlOption termOption : term.getOptions()) {
			if (termOption.equals(term.getOption(0))) {
				continue;
			}
			term.setSelectedAttribute(termOption, true);
			for (HtmlOption option : select.getOptions()) {
				if (!option.asText().contains("All Subjects")) {
					String[] split = option.asText().split(" - ");
					Department.getDepartment(split[0], split[1]);
					System.out.println(option.asText());
					select.setSelectedAttribute(option, true);
					try {
						parsePage((HtmlPage) button.click(), termOption.asText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		webClient.closeAllWindows();
	}

	/**
	 * Starts a new caching thread
	 */
	public void startCachingThread() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				cacheTimeTable();
			}
		});
		thread.start();
	}

	/**
	 * Writes the database to a JSON file
	 * @param path location for the JSON file
	 */
	@SuppressWarnings("unchecked")
	public void writeToJSON(String path) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		JSONObject obj = new JSONObject();
		for (Department d : Department.getDepartments()) {
			obj.put(d.getAbbreviation(), d.toObject());
		}
		writer.write(obj.toJSONString());
		writer.close();
	}
}
