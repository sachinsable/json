package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			jsonStr = new Scanner(new File("/home/sachin/workspace/json/src/json/tracking.log")).useDelimiter("\\Z").next();
			jsonline = jsonStr.split("\n");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("file not found");
		}
        try {
            for(int j=0;j<jsonline.length;j++)
            {
            	JSONObject rootObject = new JSONObject(jsonline[j]); // Parse the JSON to a JSONObject
                Log log = new Log();
            	
            	System.out.println("*****Value of the j is "+j+"\n");
	            //Printing agent
	            System.out.println("Description of browser user was using:");
	            System.out.println("	"+rootObject.get("agent")+"\n");
	            
	            //printing context
	            System.out.println("Context was:");
	            JSONObject context = rootObject.getJSONObject("context");
	            System.out.println("	course_id	:"+context.get("course_id"));
	            log.setCourse_id(context.get("course_id").toString());											//2nd field
	            System.out.println("	org_id		:"+context.get("org_id"));
	            log.setOrg_id(context.get("org_id").toString());												//4th field
	            System.out.println("	user_id		:"+context.get("user_id"));
	            log.setUser_id((Integer)context.get("user_id"));												//5th field
	
	            System.out.println("	Module was:");
	            JSONObject module = context.getJSONObject("module");
	            System.out.println("		Display name	:"+module.get("display_name")+"\n");
	            log.setModule(module.get("display_name").toString());											//3rd field
	
	            //printing event_source
	            System.out.println("Event Source:");
	            System.out.println("	"+rootObject.get("event_source")+"\n");
	            log.setEvent_source(rootObject.get("event_source").toString());									//7th field
	
	            //printing event_type
	            System.out.println("Event Type:");
	            System.out.println("	"+rootObject.get("event_type")+"\n");
	            log.setEvent(rootObject.get("event_type").toString());											//6th and 8th field
	
	            //printing host
	            System.out.println("Host:");
	            System.out.println("	"+rootObject.get("host")+"\n");
	            log.setHost(rootObject.get("host").toString());													//9th field
	
	            //printing ip address
	            System.out.println("IP:");
	            System.out.println("	"+rootObject.get("ip")+"\n");
	            log.setIp(rootObject.get("ip").toString());														//10th field
	
	            //printing page
	            System.out.println("Page:");
	            System.out.println("	"+rootObject.get("page")+"\n");
	            log.setTime(rootObject.get("page").toString());													//11th field
	
	            //printing time
	            System.out.println("Time:");
	            System.out.println("	"+rootObject.get("time")+"\n");
	            String time = rootObject.get("time").toString();
	            
	            //converting into date format
	            String time2=time.substring(0, 10);
	            System.out.println("********"+time2+"***********");
	            time2.concat(" ");
	            System.out.println("********"+time2+"***********");
	            time2.concat(time.substring(11,18));
	            System.out.println("********"+time2+"***********");
	            log.setTime(time2);																				//12th field
	            
	            
	            
	            //printing username
	            System.out.println("username:");
	            System.out.println("	"+rootObject.get("username")+"\n");
	            log.setUsername(rootObject.get("username").toString());											//13th field
	            
	            //printing event
	            JSONObject event = rootObject.getJSONObject("event");
	            System.out.println("Attempts:");
	            System.out.println("	"+event.get("attempts")+"\n");
	            System.out.println("Grade:");
	            System.out.println("	"+event.get("grade")+"\n");
	            System.out.println("Max Grade:");
	            System.out.println("	"+event.get("max_grade")+"\n");
	            
	            //printing correct
	            JSONObject correct_map = event.getJSONObject("correct_map");
	            System.out.println("Number of questions");
	            System.out.println("	"+correct_map.length());
            	Iterator keys = correct_map.keys();
            	while(keys.hasNext())
            	{
            		String key = (String)keys.next();
            		System.out.println("	"+key);
            		JSONObject correctness = correct_map.getJSONObject(key);
            		System.out.println("	"+correctness.get("correctness"));
            		if(correctness.get("hint").toString().equals("null")||(correctness.get("hint")).toString().length()==0)
            		{
            			System.out.println("	0");
            		}
            		else
            		{
            			System.out.println("	"+correctness.get("hint"));
            		}
            	}
            

            	//printing success
            	System.out.println("Success:");
            	System.out.println("	"+event.get("success"));
            	//db.insertlogdata(log);
            }
            db.getdata();
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }
    }
}