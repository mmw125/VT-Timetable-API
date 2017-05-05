package vtscheduler;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * This parses a HTMLTableRow and extracts the valid information.
 * This is necessary because sometimes cells do not exist in the table
 * @author Mark Wiggans
 */
class RowParser {
    private boolean addTim = false, online = false;
    private int crn;
    private String course, title, type, credits, seats, capacity, instructor, days, begin, end, location, exam;

    /**
     * Creates a RowParser object with the given row
     * @param row the row to parse
     */
    public RowParser(HtmlTableRow row){
        if(row == null){
            assert false;
            return;
        }
        if(row.getCells().get(4).asText().contains("* Additional Times *")){
            addTim = true;
            for(int i = 0; i < row.getCells().size(); i++){
                HtmlTableCell cell = row.getCells().get(i);
                switch(i){
                    case 5:days=cell.asText(); break;
                    case 6:begin=cell.asText(); break;
                    case 7:end=cell.asText(); break;
                    case 8:location=cell.asText(); break;
                }
            }
        }else{
        	List<HtmlTableCell> cells = row.getCells();
        	try{
        		try{
        			crn = Integer.parseInt(cells.get(0).asText().trim());
        		}catch(NumberFormatException e){
        			System.err.println("NumberFormatException");
        		}
        		course = cells.get(1).asText();
        		title = cells.get(2).asText();
        		if(cells.get(3).asText().equals("ONLINE COURSE")){
        			online = true;
        		}
        		type = cells.get(3).asText();
        		credits = cells.get(4).asText();
        		seats = cells.get(5).asText();
        		instructor = cells.get(6).asText();
        		days = cells.get(7).asText();
        		begin = cells.get(8).asText();
        		if(begin.equals("----- (ARR) -----")) {
        			end = null;
        			location = cells.get(9).asText();
        			exam = cells.get(10).asText();
        		} else {
        			end = cells.get(9).asText();
        			location = cells.get(10).asText();
        			exam = cells.get(11).asText();
        		}
        		online = end == null || type.equals("ONLINE COURSE");
        	} catch (IndexOutOfBoundsException e){ }
        }
    }

    /**
     * Says if the row just contains additional times for a class
     * @return if is is an additional time row
     */
    public boolean atr(){
        return addTim;
    }

    /**
     * Gets the CRN of the class. This does not work for a additionalTimesRow
     * @return the CRN
     */
    public int crn(){
        return crn;
    }

    /**
     * Gets the string representing the course. For example "CS-2114"
     * @return the course string
     */
    public String course(){
        return course;
    }

    /**
     * Gets the Title of the class. For example "World Regions"
     * @return
     */
    public String title(){
        return title;
    }

    /**
     * Gets the type of class
     * @return the type of the class
     */
    public ClassType type(){
        return ClassType.stringToType(type);
    }

    /**
     * Gets the number of credits that this class provides you. This could be cast to an int, but
     * would cause a little confusion for classes with variable credits
     * @return the number of credits
     */
    public String credits(){
        return credits;
    }


    /**
     * Gets the number of available seats in the class
     * @return the number of seats
     */
    public String seats(){
        return seats;
    }

    /**
     * Gets the capacity of the class
     * @return the capacity
     */
    public String capacity(){
        return capacity;
    }

    /**
     * Gets the instructor's name
     * @return the instructor's name
     */
    public String instructor(){
        if(instructor == null){
            return "";
        }
        return instructor;
    }

    /**
     * Gets the days of the week that the class is offered
     * @return the days of the week
     */
    public String days(){
        if(days == null){
            return "";
        }
        return days;
    }

    /**
     * Gets the time that the class begins
     * @return the time
     */
    public String begin(){
        if(begin == null){
            return "";
        }
        return begin;
    }

    /**
     * Gets the time that the class ends
     * @return the time
     */
    public String end(){
        if(end == null){
            return "";
        }
        return end;
    }

    /**
     * Gets where the class is located
     * @return where the class is located
     */
    public String location(){
        if(location == null){
            return "";
        }
        return location;
    }

    /**
     * Gets the string that represents the exam time
     * @return the exam time
     */
    public String exam(){
        if(exam == null){
            return "";
        }
        return exam;
    }

    /**
     * Says if the class is online
     * @return if the class is online
     */
    public boolean online(){
        return online;
    }
}
