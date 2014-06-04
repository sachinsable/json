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
	public void insertdata()
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
}