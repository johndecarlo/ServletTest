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

@WebServlet( name = "swe432-assignment-6", urlPatterns = {"/swe432-assignment-6"} )

public class swe432 extends HttpServlet {

	static String Style = "swe432.css";
	static String jscript = "swe432.js";

	static enum Data {NAME, AGE, GYM, EXPERIENCE, WORKOUT, ENTRY, ENTRIES};

  static String RESOURCE_FILE = "entries.xml";

  static String Domain  = "";
  static String Path    = "/";
  static String Servlet = "xml";

  public class Entry {
    String name;
    Integer age;
		String gym;
		String experience;
		List<String> workout;
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

    public List<Entry> save(String name, Integer age, String gym) throws FileNotFoundException, XMLStreamException {
      List<Entry> entries = getAll();
      Entry newEntry = new Entry();
      newEntry.name = name;
      newEntry.age = age;
			newEntry.gym = gym;
      entries.add(newEntry);

      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
      XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(filePath));

      eventWriter.add(eventFactory.createStartDocument());
      eventWriter.add(LINE_END);

      eventWriter.add(ENTRIES_START);
      eventWriter.add(LINE_END);

      for(Entry entry: entries) {
        addEntry(eventWriter, entry.name, entry.age, entry.gym, entry.experience);
      }

      eventWriter.add(ENTRIES_END);
      eventWriter.add(LINE_END);

      eventWriter.add(eventFactory.createEndDocument());
      eventWriter.close();
      return entries;
    }

    private void addEntry(XMLEventWriter eventWriter, String name, Integer age, String gym, String experience) throws XMLStreamException {
        eventWriter.add(ENTRY_START);
        eventWriter.add(LINE_END);
        createNode(eventWriter, Data.NAME.name(), name);
        createNode(eventWriter, Data.AGE.name(), String.valueOf(age));
				createNode(eventWriter, Data.GYM.name(), gym);
				createNode(eventWriter, Data.GYM.name(), experience);
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
    	StringBuilder htmlOut = new StringBuilder("<table>");
    	htmlOut.append("<tr><th>Name</th><th>Age</th><th>Gym</th><th>Experience</th><th>Workout</th></tr>");
    	if(entries == null || entries.size() == 0){
      	htmlOut.append("<tr><td>No entries yet.</td></tr>");
    	} else {
      	for(Entry entry: entries){
         	htmlOut.append("<tr><td>"+entry.name+"</td><td>"+entry.age+"</td><td>"+entry.gym+"</td><td>"+entry.experience+"</td></tr>");
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
		 String error = "";

		 if(name == null){
       error= "<li>Name is required</li>";
       name = "";
     }
     if(rawAge == null){
       error+= "<li>Age is required.<li>";
       rawAge = "";
     }else{
          try{
            age =new Integer(rawAge);
            if(age<1){
                error+= "<li>Age must be an integer greater than 0.</li>";
                rawAge = "";
            }else{
              if(age>150){
                  error+= "<li>Age must be an integer less than 150.</li>";
                  rawAge = "";
              }
            }
          }catch (Exception e) {
            error+= "<li>Age must be an integer greater than 0.</li>";
            rawAge = "";
          }
     }
		 if(gym == null){
       error= "<li>Gym is required</li>";
       name = "";
     }
		 if(experience == null){
       error= "<li>Experience is required</li>";
       name = "";
     }

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();

     if (error.length() == 0){
       EntryManager entryManager = new EntryManager();
       entryManager.setFilePath(RESOURCE_FILE);
       List<Entry> newEntries= null;
       try{
         newEntries=entryManager.save(name, age);
       }catch(FileNotFoundException e){
         e.printStackTrace();
          error+= "<li>Could not save entry.</li>";
       }
       catch(XMLStreamException e){
         e.printStackTrace();
          error+= "<li>Could not save entry.</li>";
       }
       printHead(out);
       if(newEntries ==  null){
         error+= "<li>Could not save entry.</li>";
         printBody(out, name, rawAge, error);
       }else{
         printResponseBody(out, entryManager.getAllAsHTMLTable(newEntries));
       }
       printTail(out);
     }else{
       printHead(out);
       printBody(out, name, rawAge, error);
       printTail(out);
     }
  }

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		out.println ("<script src=\"" + jscript + "\">");
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
		if(error != null && error.length() > 0){
			out.println(
			"<p style=\"color:red;\">Please correct the following and resubmit.</p>"
				);
			out.println("<ol>");
			out.println(error);
			out.println("</ol>");
		}
		out.println("<table class = \"user_input\">");
		out.println("<tr>");
		out.println("<td>Name:</td>");
		out.println("<td><input type=\"text\" name=\""+Data.NAME.name()+"\" value=\""+name+"\" size=30 required></td>");
		out.println("<td>Age:</td>");
		out.println("<td><input type=\"text\" name=\""+Data.AGE.name()+"\" oninput=\"this.value=this.value.replace(/[^0-9]/g,'');\" value=\""+age+"\" size=3 required></td>");
		out.println("</table>");
		out.println("<table class=\"user_input\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td class=\"gym_type\">");
		out.println("<p><b><u>Gym Attended:</u></b></p>");
		out.println("<label for=\"afc\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"afc\" value=\"Aquatic Fitness Center\" />A.F.C.</label></br>");
		out.println("<label for=\"rac\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"rac\" value=\"Recreation Athletic Complex\" />R.A.C.</label></br>");
		out.println("<label for=\"skyline\"><input type=\"radio\" name=\""+Data.GYM.name() + "\" id=\"skyline\" value=\"Skyline\" />Skyline</label>");
		out.println("</td>");
		out.println("<td class=\"exercise_type\">");
		out.println("<p><b><u>Exercise Type (select all that apply):</u></b></p>");
		out.println("<table align=\"center\">");
		out.println("<tr>");
		out.println("<td>Cardio<input type=\"checkbox\" name=\"workout\" value=\"cardio\"/>");
		out.println("<td>Chest<input type=\"checkbox\" name=\"workout\" value=\"chest\"/>");
		out.println("<td>Back<input type=\"checkbox\" name=\"workout\" value=\"back\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Arms<input type=\"checkbox\" name=\"workout\" value=\"arms\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\"workout\" value=\"legs\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\"workout\" value=\"core\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Basketball<input type=\"checkbox\" name=\"workout\" value=\"basketball\"/>");
		out.println("<td>Swimming<input type=\"checkbox\" name=\"workout\" value=\"swimming\"/>");
		out.println("<td>Other<input type=\"checkbox\" name=\"workout\" value=\"other\"/></br>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br>");
		out.println("<table class=\"experience\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td bgcolor=\"#FFEF33\"><p align=\"center\"><b><u>How would you rate your experience at the gym?:</u></b></p>");
		out.println("<label for=\"very_bad\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"very_bad\" value=\"very_bad\" />Very bad</label>");
		out.println("<label for=\"bad\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"bad\" value=\"bad\" />Bad</label>");
		out.println("<label for=\"okay\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"okay\" value=\"okay\" /><label for=\"okay\" />Okay</label>");
		out.println("<label for=\"good\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"good\" value=\"good\" />Good</label>");
		out.println("<label for=\"very_good\"><input type=\"radio\" name=\"" + Data.EXPERIENCE.name() + "\" id=\"very_good\" value=\"very_good\" />Very good</label>");
		out.println("<tr/>");
		out.println("</table>");
		out.println("<p style=\"text-align:center\"><input type=\"submit\" value=\"Submit\" name=\"Operation\" onClick=\"checkOptions(form)\"></p>");
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
