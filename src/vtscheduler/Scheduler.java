package vtscheduler;

/**
 * @author Mark Wiggans
 */
import java.io.IOException;
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

public class Scheduler {
    private static final String URL = "https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest";
    private static Scheduler instance;
    
    public static Scheduler getInstance() throws FailingHttpStatusCodeException, IOException{
    	if(instance == null){
    		instance = new Scheduler();
    	}
    	return instance;
    }

    public Scheduler() throws FailingHttpStatusCodeException, IOException {
        System.out.println("Running");
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL);
        HtmlForm form = page.getFormByName("ttform");
        HtmlSubmitInput button = form.getInputByName("BTN_PRESSED");
        HtmlSelect term = form.getSelectByName("TERMYEAR");
        term.setSelectedAttribute(term.getOptions().get(1), true);
        HtmlSelect select = form.getSelectByName("subj_code");
        System.out.println(select.asText());
        int count = 0;
        for (HtmlOption option : select.getOptions()) {
//			String optionText = option.asText();
//			String[] split = optionText.split(" ");
//			// System.out.print(split[0].toUpperCase()+", ");
//			if (option.getValueAttribute().equals("CS")) {
//				cs = option;
//			}
            if(!option.asText().contains("All Subjects")){
                System.out.println(option.asText());
                select.setSelectedAttribute(option, true);
                parsePage((HtmlPage)button.click());
            }
            count++;
            if(count == 20){
                break;
            }
        }

        for(Department d : Department.getDepartments()){
            for(Course c : d.getCourses()){
                System.out.println(c);
            }
        }
        webClient.closeAllWindows();

    }

    public static void main(String[] args) {
        try {
            new Scheduler();
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the given page and creates class objects
     * @param page
     */
    public void parsePage(HtmlPage page){
        if(page.getByXPath("//table[@class='dataentrytable']").isEmpty()){
            return;
        }
        HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='dataentrytable']").get(0);
        Course lastCourse = null;
        CRN lastCRN = null;

        for (HtmlTableRow row : table.getRows()) {
            List<HtmlTableCell> cells = row.getCells();
            if (!cells.get(1).asText().trim().contains("Course")) {
                RowParser p = new RowParser(row);
                if (p.atr()) {
                    //If it is an additional time row, just add the times defined in the row
                    for (MeetingTime m : MeetingTime.parseStrings(p.days(), p.begin(), p.end(), p.location())) {
                        lastCRN.addAdditionalTimes(m);
                    }
                } else {
                    //If row is a course, check if the course is the same as the last course
                    //If it is not, create a new course
                    if (lastCourse == null || !lastCourse.getCourseString().equals(p.course())) {
                        Course c = new Course(p.course(), p.title(), p.type());
                        lastCourse = c;
                    }
                    //Create a new course and add it to the
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
    }
}
