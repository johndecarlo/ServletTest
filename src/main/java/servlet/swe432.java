package servlet;
/*
swe432_Assignment6.java
*/
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

// Import Java Libraries
import java.io.*;
import java.util.Date;

@WebServlet( name = "swe432-assignment-6", urlPatterns = {"/swe432-assignment-6"} )

public class swe432 extends HttpServlet {

	static String Style = "swe432.css";
	//static String jscript = "swe432.js";

	static enum Data {NAME, AGE, GYM, EXPERIENCE, WORKOUT, TIME, ENTRY, ENTRIES};

  static String RESOURCE_FILE = "entries.xml";
	static HttpSession session;


  static String Domain  = "";
  static String Path    = "/";
  static String Servlet = "swe432-assignment-6";

  public class Entry {
		String name;
    Integer age;
		String gym;
		String experience;
		String workout;
		Long time;
  }

  List<Entry> entries;

  public class EntryManager {
    private String filePath = null;
    private XMLEventFactory eventFactory = null;
    private XMLEvent LINE_END = null;
    private XMLEvent LINE_TAB = null;
    private XMLEvent ENTRIES_START = null;
    private XMLEvent ENTRIES_END = null;
    private XMLEvent ENTRY_START = null;
    private XMLEvent ENTRY_END = null;


    public EntryManager(){
      eventFactory = XMLEventFactory.newInstance();
      LINE_END = eventFactory.createDTD("\n");
      LINE_TAB = eventFactory.createDTD("\t");

      ENTRIES_START = eventFactory.createStartElement(
        "","", Data.ENTRIES.name());
      ENTRIES_END =eventFactory.createEndElement(
        "", "", Data.ENTRIES.name());
      ENTRY_START = eventFactory.createStartElement(
        "","", Data.ENTRY.name());
      ENTRY_END =eventFactory.createEndElement(
        "", "", Data.ENTRY.name());
    }

    public void setFilePath(String filePath) {
      this.filePath = filePath;
    }

    public List<Entry> save(String name, Integer age, String gym, String experience, String workout, Long time) throws FileNotFoundException, XMLStreamException {
      List<Entry> entries = getAll();
      Entry newEntry = new Entry();
      newEntry.name = name;
      newEntry.age = age;
			newEntry.gym = gym;
			newEntry.experience = experience;
			newEntry.workout = workout;
			newEntry.time = time;
      entries.add(newEntry);

      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
      XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(filePath));

      eventWriter.add(eventFactory.createStartDocument());
      eventWriter.add(LINE_END);

      eventWriter.add(ENTRIES_START);
      eventWriter.add(LINE_END);

      for(Entry entry: entries) {
        addEntry(eventWriter, entry.name, entry.age, entry.gym, entry.experience, entry.workout, entry.time);
      }

      eventWriter.add(ENTRIES_END);
      eventWriter.add(LINE_END);

      eventWriter.add(eventFactory.createEndDocument());
      eventWriter.close();
      return entries;
    }

    private void addEntry(XMLEventWriter eventWriter, String name, Integer age, String gym, String experience, String workout, Long time) throws XMLStreamException {
        eventWriter.add(ENTRY_START);
        eventWriter.add(LINE_END);
        createNode(eventWriter, Data.NAME.name(), name);
        createNode(eventWriter, Data.AGE.name(), String.valueOf(age));
				createNode(eventWriter, Data.GYM.name(), gym);
				createNode(eventWriter, Data.EXPERIENCE.name(), experience);
				createNode(eventWriter, Data.WORKOUT.name(), workout);
				createNode(eventWriter, Data.TIME.name(), String.valueOf(time));
        eventWriter.add(ENTRY_END);
        eventWriter.add(LINE_END);
    }

    private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
      StartElement sElement = eventFactory.createStartElement("", "", name);
      eventWriter.add(LINE_TAB);
      eventWriter.add(sElement);

      Characters characters = eventFactory.createCharacters(value);
      eventWriter.add(characters);

      EndElement eElement = eventFactory.createEndElement("", "", name);
      eventWriter.add(eElement);
      eventWriter.add(LINE_END);
    }

    private List<Entry> getAll() {
      List entries = new ArrayList();
      try {
        File file = new File(filePath);
        if(!file.exists()) {
          return entries;
        }

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(file);
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

        Entry entry = null;
        while (eventReader.hasNext()) {
          // <ENTRIES> not needed for the example
          XMLEvent event = eventReader.nextEvent();

          if (event.isStartElement()) {
              StartElement startElement = event.asStartElement();
              if (startElement.getName().getLocalPart().equals(Data.ENTRY.name())) {
                  entry = new Entry();
              }
              if (event.isStartElement()) {
                  if (event.asStartElement().getName().getLocalPart().equals(Data.NAME.name())) {
                      event = eventReader.nextEvent();
                      entry.name =event.asCharacters().getData();
                      continue;
                  }
              }
              if (event.asStartElement().getName().getLocalPart().equals(Data.AGE.name())) {
                  event = eventReader.nextEvent();
                  entry.age = Integer.parseInt(event.asCharacters().getData());
                  continue;
              }
              if (event.asStartElement().getName().getLocalPart().equals(Data.GYM.name())) {
                  event = eventReader.nextEvent();
                  entry.gym = event.asCharacters().getData();
                  continue;
              }
							if (event.asStartElement().getName().getLocalPart().equals(Data.EXPERIENCE.name())) {
                  event = eventReader.nextEvent();
                  entry.experience =event.asCharacters().getData();
                  continue;
              }
							if (event.asStartElement().getName().getLocalPart().equals(Data.WORKOUT.name())) {
                  event = eventReader.nextEvent();
                  entry.workout = event.asCharacters().getData();
                  continue;
              }
							if (event.asStartElement().getName().getLocalPart().equals(Data.TIME.name())) {
                  event = eventReader.nextEvent();
                  entry.time = Long.parseLong(event.asCharacters().getData());
                  continue;
              }
          }
          if (event.isEndElement()) {
              EndElement endElement = event.asEndElement();
              if (endElement.getName().getLocalPart().equals(Data.ENTRY.name())) {
                  entries.add(entry);
              }
          }

        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (XMLStreamException e) {
        e.printStackTrace();
      } catch(IOException ioException){
        ioException.printStackTrace();
      }
      return entries;
    }

  	public String getAllAsHTMLTable(List<Entry> entries){
    	StringBuilder htmlOut = new StringBuilder("<table style=\"border:1px solid white;text-align:center;color:#FFFFFF;font-size:125%;margin:auto;\">");
    	htmlOut.append("<tr style=\"text-align:left;\"><th style=\"border:1px solid white;padding:10px;\">Time Submitted</th><th style=\"border:1px solid white;padding:10px;\">Name</th><th style=\"border:1px solid white;padding:10px;\">Age</th><th style=\"border:1px solid white;padding:10px;\">Gym</th><th style=\"border:1px solid white;padding:10px;\">Experience</th><th style=\"border:1px solid white;padding:10px;\">Workout</th></tr>");
    	if(entries == null || entries.size() == 0){
      	htmlOut.append("<tr><td>No entries yet.</td></tr>");
    	} else {
      	for(Entry entry: entries){
					Date date = new Date(entry.time);
         	htmlOut.append("<tr style=\"text-align:left;\"><td style=\"border:1px solid white;padding:10px;\">"+ date.toString() +"</td><td style=\"border:1px solid white;padding:10px;\">"+entry.name+"</td><td style=\"border:1px solid white;padding:10px;\">"+entry.age+"</td><td style=\"border:1px solid white;padding:10px;\">"+entry.gym+"</td><td style=\"border:1px solid white;padding:10px;\">"+entry.experience+"</td><td style=\"border:1px solid white;padding:10px;\">"+entry.workout+"</td></tr>");
      	}
    	}
    	htmlOut.append("</table>");
    	return htmlOut.toString();
  	}
	}

	/** *****************************************************
   *  Overrides HttpServlet's doPost().
   *  Converts the values in the form, performs the operation
   *  indicated by the submit button, and sends the results
   *  back to the client.
  ********************************************************* */
  @Override
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String name = request.getParameter(Data.NAME.name());
     String rawAge = request.getParameter(Data.AGE.name());
     Integer age = null;
		 String gym = request.getParameter(Data.GYM.name());
		 String experience = request.getParameter(Data.EXPERIENCE.name());
		 String[] workout = request.getParameterValues(Data.WORKOUT.name());
		 Long time = session.getLastAccessedTime()
		 String result = "";
		 if(workout != null) {
			 if(workout.length == 1)
					 result = workout[0];
			 else if(workout.length == 2)
					 result = workout[0] + " and " + workout[1];
			 else {
					 for(int i = 0; i < workout.length; i++) {
						 if(i == workout.length - 1)
							 result += " and " + workout[i];
						 else
							 result += workout[i] + ", ";
						 }
			 }
		 }
		 String error = "";

		 if(name == null) {
       error= "<li style=\"text-align:center;color:white;\">Name is required</li>";
       name = "";
     }
     if(rawAge == null) {
       error+= "<li style=\"text-align:center;color:white;\">Age is required.<li>";
       rawAge = "";
     }
		 if(gym == null) {
				error= "<li style=\"text-align:center;color:white;\">Gym is required</li>";
			}
		 if(experience == null) {
				error= "<li style=\"text-align:center;color:white;\">Experience is required</li>";
			}
			if(workout == null) {
 				error= "<li style=\"text-align:center;color:white;\">Workout is required</li>";
 			}
		 else {
          try{
            age =new Integer(rawAge);
            if(age<1){
                error+= "<li style=\"text-align:center;color:white;\">Age must be an integer greater than 0.</li>";
                rawAge = "";
            }else{
              if(age>150){
                  error+= "<li style=\"text-align:center;color:white;\">Age must be an integer less than 150.</li>";
                  rawAge = "";
              }
            }
          }catch (Exception e) {
            error+= "<li style=\"text-align:center;color:white;\">Age must be an integer greater than 0.</li>";
            rawAge = "";
          }
     }

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();

     if (error.length() == 0){
       EntryManager entryManager = new EntryManager();
       entryManager.setFilePath(RESOURCE_FILE);
       List<Entry> newEntries= null;
       try{
         newEntries=entryManager.save(name, age, gym, experience, result, time);
       }catch(FileNotFoundException e){
         e.printStackTrace();
          error+= "<li style=\"text-align:center;color:white;\">Could not save entry.</li>";
       }
       catch(XMLStreamException e){
         e.printStackTrace();
          error+= "<li style=\"text-align:center;color:white;\">Could not save entry.</li>";
       }
       PrintHead(out);
       if(newEntries ==  null){
         error+= "<li style=\"text-align:center;color:white;\">Could not save entry.</li>";
         PrintBody(out, name, rawAge, error);
       }else{
         printResponseBody(out, entryManager.getAllAsHTMLTable(newEntries));
       }
       PrintTail(out);
     }else{
       PrintHead(out);
       PrintBody(out, name, rawAge, error);
       PrintTail(out);
     }
  }

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		response.setContentType("text/html");
	  PrintWriter out = response.getWriter();
	  PrintHead(out);
	  PrintBody(out, "", "", "");
		PrintTail(out);
	}

	private void PrintHead (PrintWriter out) {
		out.println("<html>");
		out.println("");
		out.println("<head>");
		out.println("<title>GMU Gym Review</title>");
		out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
		out.println ("<script>");
		out.println ("  function setFocus(){");
		out.println ("    document.persist2file.NAME.focus();");
		out.println ("  }");
		out.println ("</script>");
		out.println("</head>");
	}

	private void PrintBody (PrintWriter out, String name, String age, String error) {
		out.println("<body onLoad=\"setFocus()\">");
		out.println("<h1>George Mason University Gyms Feedback");
		out.println("<p class=\"intro\">Tell us about your experience with Mason Recreation facilities and what we can improve.</p>");
		out.println("</h1>");
		out.println("<br>");
		out.println("<table class=\"gym_images\">");
		out.println("<tr>");
		out.println("<td><img src=\"http://mason.gmu.edu/~jdecarl/images/AFC.jpg\" alt=\"Mason Recreation Athletic Complex\" align=\"center\" style=\"width:300px;height:200px;\">");
		out.println("<td><img src=\"http://mason.gmu.edu/~jdecarl/images/RAC.jpg\" alt=\"Mason Aquatic Fitness Center\" style=\"width:300;height:200px;\">");
		out.println("<td><img src=\"http://mason.gmu.edu/~jdecarl/images/Skyline.jpg\" alt=\"Mason Skyline Fitness Center\" style=\"width:300px;height:200px;\"></br>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><p class=\"gym_name\"><a href=\"https://recreation.gmu.edu/facilities/aquatic-and-fitness-center/\" target=\"_blank\">Recreation Athletic Complex (RAC)</a></p>");
		out.println("<td><p class=\"gym_name\"><a href=\"https://recreation.gmu.edu/facilities/rac/\" target=\"_blank\">Aquatic Fitness Center (AFC)</a></p>");
		out.println("<td><p class=\"gym_name\"><a href=\"https://recreation.gmu.edu/facilities/skyline-fitness/\" target=\"_blank\">Skyline Fitness Center</a></p>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br>");
		out.print("<form name=\"persist2file\" method=\"post\"");
		out.println(" action=\""+Domain+Path+Servlet+"\">");
		out.println("<table class = \"user_input\">");
		out.println("<tr>");
		out.println("<td style=\"color:#000000\">Name:</td>");
		out.println("<td><input type=\"text\" name=\""+Data.NAME.name()+"\" value=\""+name+"\" size=30 required></td>");
		out.println("<td style=\"color:#000000\">Age:</td>");
		out.println("<td><input type=\"text\" name=\""+Data.AGE.name()+"\" oninput=\"this.value=this.value.replace(/[^0-9]/g,'');\" value=\""+age+"\" size=3 required></td>");
		out.println("</table>");
		out.println("<table class=\"user_input\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td class=\"gym_type\">");
		out.println("<p><b><u>Gym Attended:</u></b></p>");
		out.println("<label for=\"afc\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"afc\" value=\"Aquatic Fitness Center\" />A.F.C.</label></br>");
		out.println("<label for=\"rac\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"rac\" value=\"Recreation Athletic Complex\" />R.A.C.</label></br>");
		out.println("<label for=\"skyline\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"skyline\" value=\"Skyline Fitness Center\" />Skyline</label>");
		out.println("</td>");
		out.println("<td class=\"exercise_type\">");
		out.println("<p><b><u>Exercise Type (select all that apply):</u></b></p>");
		out.println("<table align=\"center\">");
		out.println("<tr>");
		out.println("<td>Cardio<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Cardio\"/>");
		out.println("<td>Chest<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Chest\"/>");
		out.println("<td>Back<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Back\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Arms<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Arms\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Legs\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Core\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Basketball<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Basketball\"/>");
		out.println("<td>Swimming<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Swimming\"/>");
		out.println("<td>Other<input type=\"checkbox\" name=\""+Data.WORKOUT.name() + "\" value=\"Other\"/></br>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br>");
		out.println("<table class=\"experience\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td bgcolor=\"#FFEF33\"><p align=\"center\"><b><u>How would you rate your experience at the gym?:</u></b></p>");
		out.println("<label for=\"very_bad\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"very_bad\" value=\"Very Bad\" />Very bad</label>");
		out.println("<label for=\"bad\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"bad\" value=\"Bad\" />Bad</label>");
		out.println("<label for=\"okay\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"okay\" value=\"Okay\" /><label for=\"Okay\" />Okay</label>");
		out.println("<label for=\"good\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"good\" value=\"Good\" />Good</label>");
		out.println("<label for=\"very_good\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"very_good\" value=\"Very Good\" />Very good</label>");
		out.println("<tr/>");
		out.println("</table>");
		if(error != null && error.length() > 0){
			out.println(
			"<p style=\"color:red; text-align:center;\">Please correct the following and resubmit.</p>"
				);
			out.println("<ol style=\"text-align:center;color:white;\">");
			out.println(error);
			out.println("</ol>");
		}
		out.println("<p style=\"text-align:center;\"><input type=\"submit\" value=\"Submit\" name=\"Operation\" onClick=\"checkOptions(form)\"></p>");
		out.println("</form>");
		out.println("</body>");
	}

	/** *****************************************************
	 *  Prints the <BODY> of the HTML page with persisted entries
	********************************************************* */
	private void printResponseBody (PrintWriter out, String tableString){
		out.println("<body>");
		out.println("<p class=\"result\">Your review has been submitted</p>");
		out.println("");
		out.println(tableString);
		out.println("");
		out.println("</body>");
	}

	private void PrintTail (PrintWriter out)
	{
	   out.println("");
	   out.println("</html>");
	} // End PrintTail
}
