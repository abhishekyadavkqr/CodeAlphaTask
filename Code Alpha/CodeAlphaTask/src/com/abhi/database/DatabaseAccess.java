package com.abhi.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DatabaseAccess {
	static Connection con;
	
	static Statement st ;
	static ResultSet rs;
	static{
		try {
			
//			Class.forName("oracle.jdbc.driver.OracleDriver");
		 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
				"system",
				"root");
		 if(con!=null)
		 st = con.createStatement();
if(con == null)
System.out.println("Connection is not establised");
else
System.out.println("Connection is Establised");
}//try closed
		catch(Exception e) {
	e.printStackTrace();
}
}
	

	public static  void  insertTheData( int rollNum, String stName, List<Integer> li ) throws ClassNotFoundException {
		System.out.println("Roll No.\t\tStudent Name\t\tSubject Marks.");
		System.out.println(rollNum+"\t\t\t"+stName+"\t\t"+li);
		float totalMarks = 0;
		String query;
		String grade = null;
		float percentage=0;
		try {
//			subject ::
//			first   :: English
//			second  :: Hindi
//			third	:: Mathematics
//			fourth  :: Science
//			fifth	:: Social Science
//			40-60 c 60-80 B 80-100 A
			for (Integer integer : li) {
				totalMarks = totalMarks + integer.intValue();
				if(integer.intValue() <33)
					grade="Fail";
			}
			
			percentage = (totalMarks/500)*100;
			if(grade ==null) {
				
			if(percentage<33) 
				grade ="Fail";
			else if(percentage >=33 && percentage <60)
				grade ="C";
			else if(percentage >=60 && percentage <80)
				grade ="B";
			else
				grade ="A";
			}
			
			query = "INSERT INTO student VALUES( "+rollNum+",'"+stName+"',"+li.get(0)+","+li.get(1)+","
												  +li.get(2)+","+li.get(3)+","+li.get(4)+ ","+totalMarks
												  + ","+percentage+","+"'"+grade+"'"+")";
			System.out.println(query);
			int x=st.executeUpdate(query);
			if(x ==0)
				System.out.println("There are some technical error!\nPlease try again.");
			else
				System.out.println("Data saved successfully!");
		}catch (Exception e) {
			System.out.println("There are some technical error!\nPlease try again.");
			System.out.println(e.getMessage());
		}
		
		
	}
	public static int deleteTheData(int rollNum, String stName) {
	String query ="DELETE FROM STUDENT WHERE ROLL_NUMBER = "+rollNum +"AND STUDENT_NAME ="+"'"+stName+"'";
	System.out.println(query);
	try {
	int item = st.executeUpdate(query);
		return item;
	}catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
		return 0;
	}
	
}

	public static void updateTheData(int rollNum, String stName, List<Integer> li) {
		System.out.println("Roll No.\t\tStudent Name\t\tSubject Marks.");
		System.out.println(rollNum+"\t\t\t"+stName+"\t\t"+li);
		float totalMarks = 0;
		String query;
		String grade = null;
		float percentage=0;
		try {
//			subject ::
//			first   :: English
//			second  :: Hindi
//			third	:: Mathematics
//			fourth  :: Science
//			fifth	:: Social Science
//			40-60 c 60-80 B 80-100 A
			for (Integer integer : li) {
				totalMarks = totalMarks + integer.intValue();
				if(integer.intValue() <33)
					grade="Fail";
			}
			
			percentage = (totalMarks/500)*100;
			if(grade ==null) {
				
			if(percentage<33) 
				grade ="Fail";
			else if(percentage >=33 && percentage <60)
				grade ="C";
			else if(percentage >=60 && percentage <80)
				grade ="B";
			else
				grade ="A";
			}
  
			query = "UPDATE STUDENT SET ENGLISH = "+li.get(0)+",HINDI = "+li.get(1)+",MATHEMATICS = "+li.get(2)
													+",SCIENCE = "+li.get(3)+",SOCIAL_SCIENCE = "+li.get(4)+
													",TOTAL_MARKS = "+totalMarks  + ",PERCENT = "+percentage+",GRADE = "+"'"+grade+"' "+
													"WHERE ROLL_NUMBER = "+rollNum+" and STUDENT_NAME = '"+stName+"'";
			System.out.println(query);
			int x=st.executeUpdate(query);
			if(x ==0)
				//System.out.println("There are some technical error!\nPlease try again.");
				System.out.println("Data Not found!\nPlease try again...");
			else
				System.out.println("Data saved successfully!");
		}catch (Exception e) {
			System.out.println("There are some technical error!\nPlease try again.");
			System.out.println(e.getMessage());
		}
	}
	public static void displayAllTheData() {
		String query;
		try {
			query = "SELECT ROLL_NUMBER, STUDENT_NAME, ENGLISH, HINDI,MATHEMATICS, SCIENCE,SOCIAL_SCIENCE, TOTAL_MARKS, PERCENT, GRADE FROM STUDENT";
		rs = st.executeQuery(query);
		boolean flag = false;
//		Displaying Record on the Console
		while(rs.next()) {
			flag = true;
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+" "+rs.getInt(3)+"  "+rs.getString(4)+" "+rs.getInt(5)+"  "+rs.getString(6)+" "+rs.getInt(7)+"  "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10));
		}//while close
		if(flag == false) 
			System.out.println("Record Not Found!");
		
			}//try close
		catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}//catch close
	}
	public static void terminator() {
		if(DatabaseAccess.rs != null) {
			try {
				DatabaseAccess.rs.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			}// if block close
		if(DatabaseAccess.st != null) {
			try {
				DatabaseAccess.st.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			}// if block close
		if(DatabaseAccess.con != null) {
			try {
				DatabaseAccess.con.close();;
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			}// if block close
	}
}