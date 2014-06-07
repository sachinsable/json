package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import video_event_data.*;
import json.Database;

public class Edxparser {
    public static void main(String[] args) {
    	
    	/*
    	JsonParser parser = new JsonParser();
    	JsonElement jsonElement = parser.parse(new FileReader("/path/to/myfile"));
    	JsonObject jsonObject = jsonElement.getAsJsonObject();
    	*/
    	
        String jsonStr = "";
        String jsonline[] = null;
    	Database db = new Database();
		try {
			jsonStr = new Scanner(new File("/home/sachin/workspace/json/src/json/tracking2.log")).useDelimiter("\\Z").next();
			jsonline = jsonStr.split("\n");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//system.out.println("file not found");
		}
        try {
            for(int j=0;j<jsonline.length;j++)
            {
            	JSONObject rootObject = new JSONObject(jsonline[j]); // Parse the JSON to a JSONObject
                Log log = new Log();
	            JSONObject context = rootObject.getJSONObject("context");
	            log.setCourse_id(context.get("course_id").toString());											//2nd field
	            log.setOrg_id(context.get("org_id").toString());												//4th field
	            try
	            {
	            	log.setUser_id((Integer)context.get("user_id"));											//5th field
	            }
	            catch(Exception e)
	            {
	            	log.setUser_id(0);
	            }
	            try
	            {
		            JSONObject module = context.getJSONObject("module");
		            log.setModule(module.get("display_name").toString());											//3rd field
	            }
	            catch (Exception e)
	            {
	            	log.setModule("");	
	            }
	            log.setEvent_source(rootObject.get("event_source").toString());									//7th field
	            log.setEvent_type(rootObject.get("event_type").toString());											//6th and 8th field
	            log.setHost(rootObject.get("host").toString());													//9th field
	            log.setIp(rootObject.get("ip").toString());														//10th field
	            log.setPage(rootObject.get("page").toString());													//11th field
	            String time = rootObject.get("time").toString();
	            String time2=time.substring(0, 10);
	            time2=time2.concat(" ");
	            //system.out.println("********"+time2+"***********");
	            time2=time2.concat(time.substring(11,19));
	            log.setTime(time2);																				//12th field
	            log.setUsername(rootObject.get("username").toString());											//13th field
	            log.setEvent(rootObject.get("event").toString());
            	db.insertlogdata(log);
            }
            //db.getdata();
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }
    }
}