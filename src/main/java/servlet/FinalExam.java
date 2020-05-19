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
* A recursive algorithm to print a truth table of 1s and 0s.
* N is the number of clauses, or columns, in the truth table.
* index should be zero on the first call
* truthVals starts as an empty array of integers of size N
printTruthTable(integer N, integer index, integer array truthVals) {
   if (index == N) {
      for (i=0; i<N; i++)
         print(truthVals[i] + " ");
      print(newline);
   } else {
      for (i=0; i<2; i++) {
         truthVals[index] = i;
         printTruthTable(N, index + 1, truthVals);
      }
   }
}

********************************************************* */
public void printTruthTable(int N, int index, int[] truthVals) {
  if (index == N) {
    out.println("<p>");
     for(i=0; i<N; i++)
        out.print(truthVals[i] + " ");
     out.println("</p><br><br>");
  } else {
     for (i=0; i<2; i++) {
        truthVals[index] = i;
        printTruthTable(N, index + 1, truthVals);
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
     int[] values = new int[values.length];
     printTruthTable(values.length, 0, );
   }
   out.println("</body>");
   PrintTail(out);
}  // End doPost

public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out);
   PrintTail(out);
}

private void PrintHead (PrintWriter out) {
   out.println("<html>");
   out.println("<head>");
   out.println("<title>Final Exam</title>");
   out.println("</head>");
   out.println("");
}

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
}

private void PrintBody (PrintWriter out) {
   PrintBody(out, "");
}

private void PrintTail (PrintWriter out) {
   out.println("");
   out.println("</html>");
} // End PrintTail

}  // End twoButtons
