import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
@WebServlet("/Location")
public class Location extends HttpServlet {

	public Location() {  
        super();  
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	
		HttpSession session = request.getSession();
    	Integer id = (Integer) session.getAttribute("id");
    	DataBaseConnection db = new DataBaseConnection();
    	List<LocationModel> locations = db.selectLocations(id);
				
    	JSONObject obj = new JSONObject();

    	JSONArray objs = new JSONArray();
    	
    	for (LocationModel locationModel : locations) {
    		try {
    			obj.put("latitude", locationModel.getLatitude());
    			obj.put("longitude", locationModel.getLongitude());
    			objs.put(obj);
    			obj = new JSONObject();
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
        
    	response.setContentType("application/json");
	     // Get the printwriter object from response to write the required json object to the output stream      
	     PrintWriter out = response.getWriter();
	     // Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	     out.print(objs);
	     out.flush();
    }  
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
        throws ServletException, IOException {  
    	String latitude = request.getParameter("latitude");
    	String longitude = request.getParameter("longitude");
    	HttpSession session = request.getSession();
    	Integer id = (Integer) session.getAttribute("id");
    	
    	DataBaseConnection db = new DataBaseConnection();
    	db.insertLocation(id, latitude, longitude);
    }
	
}
