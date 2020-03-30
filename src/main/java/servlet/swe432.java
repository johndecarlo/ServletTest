/*
swe432_Assignment6.java		
*/

// Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet( name = "assignment", urlPatterns = {"/assignment"} )

public class swe432 extends HttpServlet {
	
	static String css_style = "http://mason.gmu.edu/~jdecarl/swe432.css";
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = request.getParameter("Result");
		String time = request.getParameter("time");
		String review_final = "Time:" + time;
		
		//Basic operations post method
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PrintHead(out);
		PrintBody(out, review_final);
	}
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    PrintHead(out);
	    PrintBody(out, "");
	}
	
	private void PrintHead (PrintWriter out) {
		out.println("<html>");
		out.println("");
		out.println("<head>");
		out.println("<title>GMU Gym Review</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + css_style + "\">");
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
		out.println("<table class=\"user_input\" cellspacing=5>");
		out.println("<tr>");
		out.println("<td class=\"gym_type\">");
		out.println("<p><b><u>Gym Attended:</u></b></p>");
		out.println("<label for=\"afc\"><input type=\"radio\" name=\"gym\" id=\"afc\" value=\"afc\" />A.F.C.</label></br>");
		out.println("<label for=\"rac\"><input type=\"radio\" name=\"gym\" id=\"rac\" value=\"rac\" />R.A.C.</label></br>");
		out.println("<label for=\"skyline\"><input type=\"radio\" name=\"gym\" id=\"skyline\" value=\"skyline\" />Skyline</label>");
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
		out.println("<td>Legs<input type=\"checkbox\" name=\"legs\" value=\"legs\"/>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Basketball<input type=\"checkbox\" name=\"basketball\" value=\"basketball\"/>");
		out.println("<td>Swimming<input type=\"checkbox\" name=\"swimming\" value=\"swimming\"/>");
		out.println("<td>Other<input type=\"checkbox\" name=\"other\" value=\"other\"/></br>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("<td class=\"gym_time\">");
		out.println("<p><b><u>Time Attended:</u></b></p>");
		out.println("<select name=\"time\">");
		out.println("<option value=\"6:00AM\" selected=\"selected\">6:00 AM</option>");
		out.println("<option value=\"6:30AM\">6:30 AM</option>");
		out.println("<option value=\"7:00AM\">7:00 AM</option>");
		out.println("<option value=\"7:30AM\">7:30 AM</option>");
		out.println("<option value=\"8:00AM\">8:00 AM</option>");
		out.println("<option value=\"8:30AM\">8:30 AM</option>");
		out.println("<option value=\"9:00AM\">9:00 AM</option>");
		out.println("<option value=\"9:30AM\">9:30 AM</option>");
		out.println("<option value=\"10:00AM\">10:00 AM</option>");
		out.println("<option value=\"10:30AM\">10:30 AM</option>");
		out.println("<option value=\"11:00AM\">11:00 AM</option>");
		out.println("<option value=\"11:30AM\">11:30 AM</option>");
		out.println("<option value=\"12:00PM\">12:00 PM</option>");
		out.println("<option value=\"12:30PM\">12:30 PM</option>");
		out.println("<option value=\"1:00PM\">1:00 PM</option>");
		out.println("<option value=\"1:30PM\">1:30 PM</option>");
		out.println("<option value=\"2:00PM\">2:00 PM</option>");
		out.println("<option value=\"2:30PM\">2:30 PM</option>");
		out.println("<option value=\"3:00PM\">3:00 PM</option>");
		out.println("<option value=\"3:30PM\">3:30 PM</option>");
		out.println("<option value=\"4:00PM\">4:00 PM</option>");
		out.println("<option value=\"4:30PM\">4:30 PM</option>");
		out.println("<option value=\"5:00PM\">5:00 PM</option>");
		out.println("<option value=\"5:30PM\">5:30 PM</option>");
		out.println("<option value=\"6:00PM\">6:00 PM</option>");
		out.println("<option value=\"6:30PM\">6:30 PM</option>");
		out.println("<option value=\"7:00PM\">7:00 PM</option>");
		out.println("<option value=\"7:30PM\">7:30 PM</option>");
		out.println("<option value=\"8:00PM\">8:00 PM</option>");
		out.println("<option value=\"8:30PM\">8:30 PM</option>");
		out.println("<option value=\"9:00PM\">9:00 PM</option>");
		out.println("<option value=\"9:30PM\">9:30 PM</option>");
		out.println("<option value=\"10:00PM\">10:00 PM</option>");
		out.println("<option value=\"10:30PM\">10:30 PM</option>");
		out.println("<option value=\"11:00PM\">11:00 PM</option>");
		out.println("<option value=\"11:30PM\">11:30 PM</option>");
		out.println("</select>");
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
		out.println("<tr>");
		out.println("<td bgcolor=\"#FFEF33\"><p align=\"center\" style=\"font-size:90%;\"><b>Describe your experience at this gym?:");
		out.println("<form align=\"center\"><textarea rows=\"6\" cols=\"45\">Insert text in here</textarea></form>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<p><input type=\"button\" value=\"" + Submit_Review + "\" name=\"Result\" onClick=\"checkOptions(form)\"></p>");
		out.println("<p>" + Review_input + "</p>");
		out.println("</body>");
		out.println("");
		out.println("</html>");
	}
}