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

	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gym = request.getParameter("gym");
		String experience = request.getParameter("rating");
		String final_result = "Thank you for telling us about your " + experience + " experience at " + gym;

		PrintWriter out = response.getWriter();
		out.println("<html>");
		PrintHead(out);
		out.println("<body>");
		out.println("<p class=\"result\">Your review has been submitted</p>");
		out.println("<p class=\"result\">" + final_result + "</p>");
		out.println("<body>");
		out.println("</html>");
	}

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    PrintHead(out);
	    PrintBody(out, "Hello");
		PrintTail(out);
	}

	private void PrintHead (PrintWriter out) {
		out.println("<html>");
		out.println("");
		out.println("<head>");
		out.println("<title>GMU Gym Review</title>");
		out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
		out.println("<script src=\"" + jscript + "\"></script>");
		out.println("</head>");
		out.println("");
	}

	private void PrintBody (PrintWriter out, String Review_input) {
		out.println("<body>");
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
		out.println("<form method=\"post\" id=\"theForm\">");
		out.println("<table class=\"user_input\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td class=\"gym_type\">");
		out.println("<p><b><u>Gym Attended:</u></b></p>");
		out.println("<label for=\"afc\"><input type=\"radio\" name=\"gym\" id=\"afc\" value=\"the AFC\" />A.F.C.</label></br>");
		out.println("<label for=\"rac\"><input type=\"radio\" name=\"gym\" id=\"rac\" value=\"the RAC\" />R.A.C.</label></br>");
		out.println("<label for=\"skyline\"><input type=\"radio\" name=\"gym\" id=\"skyline\" value=\"Skyline\" />Skyline</label>");
		out.println("</td>");
		out.println("<td class=\"exercise_type\">");
		out.println("<p><b><u>Exercise Type (select all that apply):</u></b></p>");
		out.println("<table align=\"center\">");
		out.println("<tr>");
		out.println("<td>Cardio<input type=\"checkbox\" name=\"cardio\" value=\"cardio\"/>");
		out.println("<td>Chest<input type=\"checkbox\" name=\"chest\" value=\"chest\"/>");
		out.println("<td>Back<input type=\"checkbox\" name=\"back\" value=\"back\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Arms<input type=\"checkbox\" name=\"arms\" value=\"arms\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\"legs\" value=\"legs\"/>");
		out.println("<td>Legs<input type=\"checkbox\" name=\"core\" value=\"core\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Basketball<input type=\"checkbox\" name=\"basketball\" value=\"basketball\"/>");
		out.println("<td>Swimming<input type=\"checkbox\" name=\"swimming\" value=\"swimming\"/>");
		out.println("<td>Other<input type=\"checkbox\" name=\"other\" value=\"other\"/></br>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br>");
		out.println("<table class=\"experience\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td bgcolor=\"#FFEF33\"><p align=\"center\"><b><u>How would you rate your experience at the gym?:</u></b></p>");
		out.println("<label for=\"very_bad\"><input type=\"radio\" name=\"rating\" id=\"very_bad\" value=\"very_bad\" />Very bad</label>");
		out.println("<label for=\"bad\"><input type=\"radio\" name=\"rating\" id=\"bad\" value=\"bad\" />Bad</label>");
		out.println("<label for=\"okay\"><input type=\"radio\" name=\"rating\" id=\"okay\" value=\"okay\" /><label for=\"okay\" />Okay</label>");
		out.println("<label for=\"good\"><input type=\"radio\" name=\"rating\" id=\"good\" value=\"good\" />Good</label>");
		out.println("<label for=\"very_good\"><input type=\"radio\" name=\"rating\" id=\"very_good\" value=\"very_good\" />Very good</label>");
		out.println("<tr/>");
		out.println("</table>");
		out.println("<p style=\"text-align:center\"><input type=\"button\" value=\"Submit\" onClick=\"checkOptions(form)\"></p>");
		out.println("</form>");
		out.println("</body>");
	}

	private void PrintTail (PrintWriter out)
	{
	   out.println("");
	   out.println("</html>");
	} // End PrintTail
}
