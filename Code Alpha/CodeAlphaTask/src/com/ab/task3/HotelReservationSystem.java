package com.ab.task3;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class HotelReservationSystem {
	private static final String CREATE_NEW_CUSTOMER_REGISTRATION_QUERY = "INSERT  INTO CUSTOMER(CUST_NAME,CUST_MOBILE,PSWD) VALUES(?,?,?)  ";
	private static final String CREATE_NEW_CUSTOMER_QUERY = "SELECT  CUST_ID FROM CUSTOMER WHERE CUST_MOBILE =?  ";
	private static final String LOG_IN_CUSTOMER_QUERY = "SELECT COUNT(*) FROM CUSTOMER WHERE CUST_ID = ? AND PSWD = ? ";
	private static final String ROOM_SEARCHING_QUERY = " SELECT ROOM_NO, DESCRIPTION, RENT, STATUS FROM HOTEL WHERE STATUS = ? ";
	private static final String ROOM_BOOKING_SEARCH_QUERY = " SELECT ROOM_NO, RENT ,STATUS  FROM HOTEL WHERE ROOM_NO = ?  ";
	private static final String ROOM_BOOKING_QUERY = "UPDATE HOTEL SET CUST_ID = ?, STATUS = ?, CHECK_IN = ?, PAYMENT =?  WHERE ROOM_NO =?";
	private static final String PAYMENT_QUERY = "INSERT INTO PAYMENT_LIST(TRANSACTION, CUST_ID, PAYMENT) VALUES(?,?,?)  ";
	private static int customer = 0;
	
	static Scanner sc = new Scanner(System.in);
//		static class DatabaseAccess{
		static Connection con = null;
		static ResultSet rs= null;
		static PreparedStatement ps =null;
		static {			
			try {
				
//				Class.forName("oracle.jdbc.driver.OracleDriver");
			 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"CODEALPHA",
					"root");
	if(con == null)
	System.out.println("Connection is not establised");
	else
	System.out.println("Connection is Establised");
	}//try closed
			catch(Exception e) {
		e.printStackTrace();
	}// catch close
		}//static close
//	} // DatabaseAccess close
//	
		
		public static void main(String[] args) {
				
//			registerNewCustomer();
//			logIn();
//			searchRoom();
//			bookRoom();
			
			
			boolean logInPassKey =false;
			
			try {
				
				System.out.println("Press! 1, for login to our portal.");
				System.out.println("Press! 2, for register to our portal.");
				System.out.print("enter your choice :: ");				
				int choice = sc.nextInt();
				if (choice ==1) {
					logInPassKey =logIn();
				}
				else if (choice ==2) {
					registerNewCustomer();
					logInPassKey =logIn();
				} else {
						System.err.println("\n invalid! choice");
				}
			}//try closed
			catch(Exception e) {
			System.out.println(e.getMessage());
			}//catch closed			
			
			try {
				//if(logInPassKey == true) {
				
				System.out.println("Press! 1, for search available room in our hotel...");
				System.out.println("Press! 2, for booking room in our hotel...");	
				System.out.print("Enter your choice :: ");
				int choice = sc.nextInt();
				if(choice ==1) {
					searchRoom();
					System.out.println("Do you want book your room? \nPress! \'Y\' or \'N\'");
					String option = sc.next();
					if(option.equalsIgnoreCase("y")) {
						bookRoom();
					}
					
				}// if close
				else if(choice ==2) {
					bookRoom();
				}//else if close
				else 
					System.err.println("invalid! choice");
				
				
			//	}// login parser closed (if)
			}//try closed
			catch (Exception e) {
				System.err.println(e.getMessage());
			 } //catch closed 
		
		finally {
			try {
				 if(rs!=null)
					 rs.close();
			}//close
			catch(Exception e) {
				e.printStackTrace();
			} //catch closed
			try {
				 if(ps!=null)
					 ps.close();
			}//close
			catch(Exception e) {
				e.printStackTrace();
			} //catch closed
			try {
				if(con!=null)
					con.close();
			}//close
			catch(Exception e) {
				e.printStackTrace();
			} //catch closed
			try {
				if(sc!=null)
					sc.close();
			}//close
			catch(Exception e) {
				e.printStackTrace();
			} //catch closed
			
		} // finally closed
//		
	} // main closed
	public static void searchRoom() {
		try {
			ps = con.prepareStatement(ROOM_SEARCHING_QUERY);
			if(ps!=null) {
			ps.setString(1, "AVAILABLE");
			rs = ps.executeQuery();
			boolean flag = false;
			if(rs!=null) {
				System.out.println("ROOM NUMBER" +" \t"+ "DESCRIPTION"+"\t\t"+ "RENT"+"\t"+"STATUS");
				while(rs.next()== true) {
					flag = true;
					System.out.println(rs.getString(1) +" \t"+ rs.getString(2)+"\t\t\t\t"+ rs.getString(3)+"\t"+rs.getString(4));
				}// while closed
				if(flag == false)
					System.err.println("Sorry! no any rooms are available in this hotel");
			}//if closed
			}// if closed
		}// try closed
		catch (Exception e) {
			e.printStackTrace();
		}//catch closed
	}//searchRoom closed
	
	public static void bookRoom() {
		searchRoom();
		System.out.println("Enter the room number which do you want to reserve.");
		int roomNumber = sc.nextInt();
		if(con!=null) {
			try {
				ps = con.prepareStatement(ROOM_BOOKING_SEARCH_QUERY);
				ps.setInt(1, roomNumber);
				rs = ps.executeQuery();
				
				if(rs!=null) {
					rs.next();
					int rNo = rs.getInt(1);
					int rent = rs.getInt(2);
					String status = rs.getString(3);
					if(status.equalsIgnoreCase("available")) {
					System.out.println("ROOM NUMBER    RENT\t STATUS");
					System.out.println("---------------------------------");
					System.out.println(rNo+"  \t\t"+rent+" \t"+status);
					 	ps=con.prepareStatement(ROOM_BOOKING_QUERY);
					 	if(ps!=null) {
					 		Date date = new Date();
					 		java.sql.Date bookingDate = new java.sql.Date(date.getTime());
					 		ps.setInt(1, customer);
					 		ps.setString(2,"BOOKED");
					 		ps.setDate(3,bookingDate);	
					 		ps.setString(4, "PROCESS");
					 		ps.setInt(5, rNo);
					 		
					 		int res = ps.executeUpdate();
					 		if(res ==1) {
					 			System.out.println("Room number "+rNo+ " is booked. \nNow process the payment....");
					 			System.out.println("please pay through the upi on this number +917488722138 (Abhishek Yadav)\nand fill the details...");
					 			System.out.println("\n\n\n please! enter your transaction number");
					 			String trnsc = sc.next();
					 			ps = con.prepareStatement(PAYMENT_QUERY);
					 			if(ps!=null) {
					 				
							 		java.sql.Date paymentDate = new java.sql.Date(date.getTime());
					 				ps.setString(1, trnsc);
					 				ps.setInt(2, customer);
					 				ps.setDate(3, paymentDate);
					 				
					 				
					 			}
					 		}
					 		else
					 			System.out.println("Room number "+rNo+ "is not booked.");
					 		
					 			
					 		
				     	}//if closed
					}//if closed
					else 
						System.out.println(rNo +" is not available \nplease! try another room.");
				}//if closed
				
				
				
			}//try closed
			catch (SQLException se) {
				se.printStackTrace();
			}
			
		}// if closed 
			
		
	}//bookRoom closed
	
	private static boolean logIn() {
		boolean flag = false;
		System.out.println("Enter your Username");
		int username = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter your Password");
		String password = sc.next();
		try {
			ps= con.prepareStatement(LOG_IN_CUSTOMER_QUERY);
			if(ps!=null) {
				System.out.println("setting the value to query");
				ps.setInt(1, username);ps.setString(2, password);
				rs = ps.executeQuery();
		}// if closed
		if(rs.next()) {
				int x= rs.getInt(1);
		if(x==1) {
				System.out.println("login success!");
				customer = username;
				flag = true;
		}
		else 
				System.out.println("invalid! credential");
		
		}//if closed
		}// try closed
		catch(SQLException se) {
			se.printStackTrace();
		}// catch closed
		return flag;
		
	}//logIn closed
	private static void registerNewCustomer() {
		if(con!=null) {
			try {
			ps = con.prepareStatement(CREATE_NEW_CUSTOMER_REGISTRATION_QUERY);
			if(ps!=null) {
				System.out.print("Enter Your Name :: ");
				String custName = sc.nextLine();
				System.out.print("\nEnter your password :: ");
				String custPswd = sc.next();
				System.out.print("\nEnter your mobile number :: ");
				String custMObile = sc.next();
				
				ps.setString(1, custName);
				ps.setString(2, custMObile);
				ps.setString(3, custPswd);
				int res = ps.executeUpdate();
				if(res==1) {
					System.out.println("your registration is successful.");
					ps=con.prepareStatement(CREATE_NEW_CUSTOMER_QUERY);
					if (ps!=null) {
						ps.setString(1, custMObile);
						rs=ps.executeQuery();					
						if(rs!=null) {
							rs.next();
							System.out.println("This is your customer id "+rs.getString(1)+"\nNow you can login in our portal....");
						}
						}//if closed
					
				}
				
			}//if closed
			} // try closed
			catch (Exception e) {
				e.printStackTrace();
			}//catch closed
		}// if closed 
		
			
	}// registerNewCustomer closed

}
