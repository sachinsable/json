package json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.json.JSONObject;

import video_event_data.Play;

public class Database
{
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	//constructor for this class to get connection

	
	//to get the all the data
	public void getdata()
	{
		try
		{
			connect = Connect.GetConnection();
			statement = connect.createStatement();
			try
			{
				resultSet = statement.executeQuery("select * from log");
				//writeResultSet(resultSet);
				while(resultSet.next())
				{
					System.out.println("id "+resultSet.getInt("id"));
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}
			connect.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public int insertlogdata(Log log)
	{
		int id=0;
		try
		{
			connect = Connect.GetConnection();
			statement = connect.createStatement();
			/*try
			{*/
			preparedStatement = connect.prepareStatement("insert into log values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, log.getCourse_id());
			preparedStatement.setString(2, log.getOrg_id());
			preparedStatement.setInt(3, log.getUser_id());
			preparedStatement.setString(4, log.getEvent_type());
			preparedStatement.setString(5, log.getEvent_source());
			preparedStatement.setString(6, log.getHost());
			preparedStatement.setString(7, log.getIp());
			preparedStatement.setString(8, log.getPage());
			preparedStatement.setString(9, log.getTime());
			preparedStatement.setString(10, log.getUsername());
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("select max(id) from log");
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int log_id=resultSet.getInt(1);
			System.out.println("***********"+log_id);
			if(log.getEvent_type().equals("load_video"))
            {
				JSONObject eventObject = new JSONObject(log.getEvent());
            	Play data = new Play();
            	System.out.println("*****************"+eventObject.get("code"));
            	preparedStatement = connect.prepareStatement("insert into load_video values (?,?)");
            	preparedStatement.setInt(1, log_id);
            	preparedStatement.setString(2, eventObject.getString("code").toString());
            	preparedStatement.executeUpdate();
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("pause_video"))
            {
            	JSONObject eventObject = new JSONObject(log.getEvent());
            	Play data = new Play();
            	System.out.println("*****************"+eventObject.get("code"));
            	preparedStatement = connect.prepareStatement("insert into pause_video values (?,?,?)");
            	preparedStatement.setInt(1, log_id);
            	preparedStatement.setString(2, eventObject.getString("code").toString());
            	preparedStatement.setInt(3, eventObject.getInt("currentTime"));
            	preparedStatement.executeUpdate();
            	System.out.println("pausing video");
            }
            else if(log.getEvent_type().equals("play_video"))
            {
            	JSONObject eventObject = new JSONObject(log.getEvent());
            	Play data = new Play();
            	System.out.println("*****************"+eventObject.get("code"));
            	preparedStatement = connect.prepareStatement("insert into play_video values (?,?,?)");
            	preparedStatement.setInt(1, log_id);
            	preparedStatement.setString(2, eventObject.getString("code").toString());
            	preparedStatement.setInt(3, eventObject.getInt("currentTime"));
            	preparedStatement.executeUpdate();
            	System.out.println("playing video");
            }
            else if(log.getEvent_type().equals("seek_video"))
            {
            	JSONObject eventObject = new JSONObject(log.getEvent());
            	Play data = new Play();
            	System.out.println("*****************"+eventObject.get("code"));
            	preparedStatement = connect.prepareStatement("insert into seek_video values (?,?,?,?)");
            	preparedStatement.setInt(1, log_id);
            	preparedStatement.setString(2, eventObject.getString("code").toString());
            	preparedStatement.setInt(3, eventObject.getInt("old_time"));
            	preparedStatement.setInt(4, eventObject.getInt("new_time"));
            	preparedStatement.executeUpdate();
            	System.out.println("seeking video");
            }
            else if(log.getEvent_type().equals("speed_change_video"))
            {
            	JSONObject eventObject = new JSONObject(log.getEvent());
	        	Play data = new Play();
	        	System.out.println("*****************"+eventObject.get("code"));
	        	preparedStatement = connect.prepareStatement("insert into speed_change_video values (?,?,?,?,?)");
	        	preparedStatement.setInt(1, log_id);
	        	preparedStatement.setString(2, eventObject.getString("code").toString());
	        	preparedStatement.setInt(3, eventObject.getInt("current_time"));
	        	preparedStatement.setFloat(4, (float) eventObject.getDouble("old_speed"));
	        	preparedStatement.setFloat(5, (float)eventObject.getDouble("new_speed"));
	        	preparedStatement.executeUpdate();
            	System.out.println("changing speed of the video");
            }
            else if(log.getEvent_type().equals("problem_graded"))
            {
            	System.out.println("problem is graded");
            }
            else if(log.getEvent_type().equals("problem_check"))
            {
            	if(log.getEvent_source().equals("server"))
            	{
	            	JSONObject eventObject = new JSONObject(log.getEvent());
		            System.out.println("Attempts:");
		            System.out.println("	"+eventObject.get("attempts")+"\n");
		            System.out.println("Grade:");
		            System.out.println("	"+eventObject.get("grade")+"\n");
	
		            System.out.println("Max Grade:");
		            System.out.println("	"+eventObject.get("max_grade")+"\n");
	
		            //printing correct
		            JSONObject correct_map = eventObject.getJSONObject("correct_map");
		            System.out.println("Number of questions");
		            System.out.println("	"+correct_map.length());
		            JSONObject submission = eventObject.getJSONObject("submission");
		            
	            	Iterator keys = correct_map.keys();
	            	while(keys.hasNext())
	            	{
	            		String key = (String)keys.next();
	            		System.out.println("	"+key);
	            		JSONObject correctness = correct_map.getJSONObject(key);
	            		JSONObject submittedproblem = submission.getJSONObject(key);
	            		System.out.println("	"+correctness.get("correctness"));
	            		System.out.println("    "+submittedproblem.get("response_type"));
	            		System.out.println("    "+submittedproblem.get("input_type"));
	            		int hint;
	            		if(correctness.get("hint").toString().equals("null")||(correctness.get("hint")).toString().length()==0)
	            		{
	            			System.out.println("	0");
	            			hint=0;
	            		}
	            		else
	            		{
	            			System.out.println("	"+correctness.get("hint"));
	            			hint=Integer.parseInt(correctness.get("hint").toString());
	            		}
	            		
	            		System.out.println("problem checking");
	                	preparedStatement = connect.prepareStatement("insert into problem_check_server values (?,?,?,?,?,?,?)");
	    	        	preparedStatement.setInt(1, log_id);
	    	        	preparedStatement.setString(2, key);
	    	        	preparedStatement.setInt(3, hint);
	    	        	preparedStatement.setString(4, correctness.get("hintmode").toString());
	    	        	preparedStatement.setString(5, correctness.get("correctness").toString());
	    	        	preparedStatement.setString(6, submittedproblem.get("response_type").toString());
	    	        	preparedStatement.setString(7, submittedproblem.get("input_type").toString());
	    	        	preparedStatement.executeUpdate();
	            	}
            	}
            	else
            	{
            		System.out.println("problem checking");
                	preparedStatement = connect.prepareStatement("insert into problem_check_browser values (?,?)");
    	        	preparedStatement.setInt(1, log_id);
    	        	String problem_id = log.getEvent();
    	        	problem_id=problem_id.replace("\"", "");
    	        	preparedStatement.setString(2, problem_id);
    	        	preparedStatement.executeUpdate();
            	}
            }
            else if(log.getEvent_type().equals("problem_show"))
            {
            	JSONObject eventObject = new JSONObject(log.getEvent());
	        	preparedStatement = connect.prepareStatement("insert into problem_show values (?,?)");
	        	preparedStatement.setInt(1, log_id);
	        	preparedStatement.setString(2, eventObject.getString("problem").toString());
	        	preparedStatement.executeUpdate();
            	System.out.println("problem is showing");
            }
            else if(log.getEvent_type().equals("showanswer"))
            {
            	System.out.println("showing the answer");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	System.out.println("loading video");
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	
            }
            else if(log.getEvent_type().equals("load_video"))
            {
            	
            }
			/*}
			catch(Exception ex)
			{
				System.out.println(ex);
			}*/
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		finally
		{
			try
			{
				connect.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return id;
	}
	public String todatetime(String date)
	{
        String time2=date.substring(0, 10);
        time2=time2.concat(" ");
        time2=time2.concat(date.substring(11,19));
        return time2;	
	}
	public String totime(String date)
	{
        String time2=date.substring(11,19);
        return time2;	
	}
}