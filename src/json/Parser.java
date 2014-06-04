package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
    public static void main(String[] args) {
    	
    	/*
    	JsonParser parser = new JsonParser();
    	JsonElement jsonElement = parser.parse(new FileReader("/path/to/myfile"));
    	JsonObject jsonObject = jsonElement.getAsJsonObject();
    	*/
    	
        String jsonStr = "";
		try {
			jsonStr = new Scanner(new File("/home/sachin/workspace/json/src/json/file.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("file not found");
		}
        try {
            JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

            for(int i=0; i < rows.length(); i++) { // Loop over each each row
                JSONObject row = rows.getJSONObject(i); // Get row object
                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
                    JSONObject element =  elements.getJSONObject(j); // Get the element object
                    JSONObject duration = element.getJSONObject("duration"); // Get duration sub object
                    JSONObject distance = element.getJSONObject("distance"); // Get distance sub object

                    System.out.println("Duration: " + duration.getInt("value")); // Print int value
                    System.out.println("Distance: " + distance.getInt("value")); // Print int value
                }
            }
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }
    }
}