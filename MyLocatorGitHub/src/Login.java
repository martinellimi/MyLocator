import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")  
public class Login extends HttpServlet {  
    //@Inject private Login login;  
         
    public Login() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
        throws ServletException, IOException {  
    	//response.getOutputStream().println("<html><h1>" + hello.getGreeting() + "</h1></html>");
    	String name = request.getParameter("name");
    	//response.getOutputStream().println(name);
    }  
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
        throws ServletException, IOException {  
    	String name = request.getParameter("name");
    	String id = request.getParameter("id");
    	
    	DataBaseConnection db = new DataBaseConnection();
    	UserModel user = db.selectUser(id);
    	if(user == null) {
    		db.insertUser(id, name);
    	}
    	HttpSession session = request.getSession();
    	session.setAttribute("id", user.getId());
    	session.setAttribute("userName", user.getName());
    } 
}