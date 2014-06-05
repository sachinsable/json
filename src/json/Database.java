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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
			preparedStatement = connect.prepareStatement("insert into log values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, log.getCourse_id());
			preparedStatement.setString(2, log.getModule());
			preparedStatement.setString(3, log.getOrg_id());
			preparedStatement.setInt(4, log.getUser_id());
			preparedStatement.setString(5, log.getEvent());
			preparedStatement.setString(6, log.getEvent_source());
			preparedStatement.setString(7, log.getEvent_type());
			preparedStatement.setString(8, log.getHost());
			preparedStatement.setString(9, log.getIp());
			preparedStatement.setString(10, log.getPage());
			preparedStatement.setString(11, log.getTime());
			preparedStatement.setString(12, log.getUsername());
			preparedStatement.executeUpdate();
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
}