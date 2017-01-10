package vtscheduler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

public class Timetable extends InformationSender {
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

	private ArrayList<InformationEvent> cachedEvents;
	private Options options;

	/**
	 * Creates a new instance of Timetable
	 */
	private Timetable() {
		options = null;
		cachedEvents = new ArrayList<InformationEvent>();
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

				notifyListeners(options);
			}
		});
		thread.run();
	}

	void runQuery(Query query, boolean forceUpdate) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (!forceUpdate) {
					for (InformationEvent event : cachedEvents) {
						if (event.getQuery().equals(query)) {
							notifyListeners(event);
							return;
						}
					}
				}

				WebClient webClient = new WebClient();
				HtmlPage page = null;
				try {
					page = webClient.getPage(URL);
				} catch (FailingHttpStatusCodeException | IOException e1) {
					e1.printStackTrace();
				}
				HtmlForm form = page.getFormByName("ttform");

				// Campus
				if (query.getCampus() != null) {
					HtmlSelect campuses = form.getSelectByName("CAMPUS");
					for (HtmlOption option : campuses.getOptions()) {
						if (option.asText().equals(query.getCampus())) {
							option.setSelected(true);
						}
					}
				}

				if (query.getTerm() != null) {
					// Term
					HtmlSelect term = form.getSelectByName("TERMYEAR");
					for (HtmlOption option : term.getOptions()) {
						if (option.asText().equals(query.getTerm())) {
							option.setSelected(true);
						}
					}
				}

				if (query.getCLE() != null) {
					// CLE
					HtmlSelect cles = form.getSelectByName("CORE_CODE");
					cles.setTextContent(query.getCLE());
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
			}
		});
		thread.start();
	}

	/**
	 * Parses the given page and creates class objects
	 * 
	 * @param page
	 *            the page to parse
	 * @param semester 
	 * 			  semester name
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
					// If row is a course, check if the course is the same as
					// the last course
					// If it is not, create a new course
					if (lastCourse == null || !lastCourse.getCourseString().equals(p.course())) {
						Course c = new Course(p.course(), p.title(), p.type(), semester);
						courses.add(c);
						lastCourse = c;
					}
					// Create a new course and add it to the
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
	
	public static void main(String[] args){
		Timetable.getInstance().cacheTimeTable();
	}

	/**
	 * Caches the entire time table to make queries faster
	 */
	public void cacheTimeTable() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
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
				System.out.println(select.asText());
				try {
					PrintWriter writer = new PrintWriter("database");
					for (HtmlOption termOption : term.getOptions()) {
						if(termOption.equals(term.getOption(0))) {
							continue;
						}
						term.setSelectedAttribute(termOption, true);
						for (HtmlOption option : select.getOptions()) {
							if (!option.asText().contains("All Subjects")) {
								writer.print(option.asText() + '|');
								System.out.println(option.asText());
								select.setSelectedAttribute(option, true);
								try {
									parsePage((HtmlPage) button.click(), termOption.asText());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					webClient.closeAllWindows();
					writer.println();
					for (Department d : Department.getDepartments()) {
						for (Course c : d.getCourses()){
							writer.println(c.toString().replaceAll("\n", "\t"));
						}
					}
					writer.close();
				} catch (IOException e) {
					System.err.println("Could not open database file");
				}
			}
		});
		thread.start();
	}
}
