// Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet( name = "final-exam", urlPatterns = {"/final-exam"} )

public class FinalExam extends HttpServlet
{

// Location of servlet.
static String Domain  = "";
static String Path    = "/";
static String Servlet = "final-exam";

// Button labels
static String OperationSubmit = "Submit";

/** *****************************************************
 *  Overrides HttpServlet's doPost().
 *  Converts the values in the form, performs the operation
 *  indicated by the submit button, and sends the results
 *  back to the client.
********************************************************* */
public void printTruthTable(HttpServletRequest request, HttpServletResponse response, int N, int index, int[] truthVals) throws ServletException, IOException {
  response.setContentType("text/html");
  PrintWriter out = response.getWriter();
  if (index == N) {
    out.println("<p>");
     for(int i=0; i<N; i++)
        out.println(truthVals[i] + " ");
     out.println("</p><br><br>");
  } else {
     for (int i=0; i<2; i++) {
        truthVals[index] = i;
        printTruthTable(request, response, N, index + 1, truthVals);
     }
  }
}

public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   String[] values;
   String expression = request.getParameter("EXP");
   String operation = request.getParameter("Operation");

   values = expression.split("OR|AND|&|&&|or|and|\\||\\|\\|");

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   out.println("<body>");
   if(!expression.contains("OR") && !expression.contains("AND") && !expression.contains("or") && !expression.contains("and") && !expression.contains("&") && !expression.contains("&&") && !expression.contains("|") && !expression.contains("||")) {
     out.println("<p align=\"center\">The Expression you submitted was not valid</p>");
   } else {
     out.println("<p align=\"center\">Your Expression has been submitted</p>");
     int[] nums = new int[values.length];
     printTruthTable(request, response, values.length, 0, nums);
   }
   out.println("</body>");
   PrintTail(out);
}  // End doPost

/** *****************************************************
 *  Overrides HttpServlet's doGet().
 *  Prints an HTML page with a blank form.
********************************************************* */
public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out);
   PrintTail(out);
} // End doGet

/** *****************************************************
 *  Prints the <head> of the HTML page, no <body>.
********************************************************* */
private void PrintHead (PrintWriter out) {
   out.println("<html>");
   out.println("<head>");
   out.println("<title>Final Exam</title>");
   out.println("</head>");
   out.println("");
} // End PrintHead

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, String expression) {
   out.println("<body>");
   out.println("<h1 align=\"center\">SWE 432: Final Exam</h1>");
	 out.println("<p align=\"center\">Enter your truth table in the text box below</p>");
	 out.println("<form align=\"center\" method=\"post\">");
	 out.println("<label for=\"EXP\">Expression:</label>");
	 out.println("<input type=\"text\" name=\"EXP\" value = \"" + expression + "\"><br><br>");
	 out.println("<input type=\"submit\" value=\"" + OperationSubmit + "\" name=\"Operation\">");
	 out.println("</form>");
   out.println("</body>");
} // End PrintBody

/** *****************************************************
 *  Overloads PrintBody (out,lhs,rhs,rslt) to print a page
 *  with blanks in the form fields.
********************************************************* */
private void PrintBody (PrintWriter out) {
   PrintBody(out, "");
}

private void PrintTail (PrintWriter out) {
   out.println("");
   out.println("</html>");
} // End PrintTail

}  // End twoButtons
