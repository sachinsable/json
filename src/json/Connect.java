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

public class Connect 
{
  public Connect()
  {
  }
  static public Connection GetConnection(){
	  try
	  {
		  System.out.println("got into myconnect");
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/expt","root","svsj");
		 System.out.println("connected");
		 return(connect);
		   
	  }catch(Exception e)
	  {
		  return null;
	  }

  }
}