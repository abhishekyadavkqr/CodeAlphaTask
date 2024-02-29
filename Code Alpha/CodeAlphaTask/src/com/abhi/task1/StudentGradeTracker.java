package com.abhi.task1;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abhi.database.DatabaseAccess;

public class StudentGradeTracker {
	Scanner sc ;
	List<Integer> li ;
	 int rollNum ;
	String stName ;
	public static void main(String[] args) {
			StudentGradeTracker reference = new StudentGradeTracker();
//			try {
//				Class.forName("DatabaseAccess");
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		
//			
			
		int choice;
		try {
//	 -------------------------------- Declaration--------------------------------------------
				
				reference.sc = new Scanner(System.in);
				for(;;) {
					System.out.println("Press \'1\' for new Student data entry.");
					System.out.println("Press \'2\' for delete the Student data.");
					System.out.println("Press \'3\' for update the Student data entry.");
					System.out.println("Press \'4\' for display the all Student data.");
					System.out.println("Press \'5\' for exit.");
					System.out.println("Enter the choice!");
					choice = reference.sc.nextInt();
					switch (choice) {
					case 1: {
						reference.newStudentData();	
						break;
					}
					case 2:{
						reference.deleteStudentData();
						break;
					}
					case 3:{
						reference.updateStudentData();
						break;
					}
					case 4:{
						reference.listAllStudentData();
						break;
					}
					case 5:{
						System.exit(0);
						break;
					}
					default:
						System.out.println("Invalid choice!");
						break;
					}
				
				}//switch close
		} // infinity for loop close
		
		catch (Exception e) {
			e.printStackTrace();
		}//catch close
		finally {
			try {
			DatabaseAccess.terminator();
			if(reference.sc!=null)
				reference.sc.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		} 
	}// finally close
	}//main close
	 
	 void newStudentData() throws ClassNotFoundException{
		int marks;
		this.li = new ArrayList<Integer>();
//   ------------------------------- Student Details ---------------------------------------------
	System.out.println("Enter Roll No. of Student");
	this.rollNum  = sc.nextInt();
	sc.nextLine();
	System.out.println("Enter the Name of Student");
	this.stName =sc.nextLine();
//---------------------------------------Marks input ------------------------------------
	 System.out.println("Subject 1 :: English\n"
	 		  + "Subject 2 :: Hindi\n"
	 		  + "Subject 3 :: Mathematics\n"
	 		  + "Subject 4 :: Science\n"
	 		  + "Subject 3 :: Social Science\n");
	for(int i =0;i<5; i++) {
		System.out.print("Enter the subject "+(i+1)+ " marks. :: ");
		marks = sc.nextInt();
		if(marks <= 100 && marks >= 0)
			li.add(marks);
		else {
			System.err.println( "Invalid Marks Entered.\nMarks should be in range of 0 to 100 \nPlease try again!");
			i--;
		}
		
	}
//	System.out.println("Do you want store these data in database? \nEnter 'Yes' or 'No' ");
//	String s = sc.next();
	
	
	DatabaseAccess.insertTheData(rollNum, stName, li);
	
	} 
	 void deleteStudentData() {
		 System.out.println("Enter the Student Roll No.");
		 this.rollNum = sc.nextInt();  sc.nextLine();
		 System.out.println("Enter the Student Name.");
		 this.stName = sc.nextLine();
//		 System.out.println(rollNum +" "+stName);
		 if(DatabaseAccess.deleteTheData(rollNum,stName)==0) 
			 System.out.println("Data Not Found!" );
		 else
			 System.out.println("Roll Number "+rollNum+" and Student Name "+stName+" Successfully deleted!");
	 }
	 void updateStudentData(){
		 this.li = new ArrayList<Integer>();
		 int marks;
		 System.out.println("Enter the Student Roll No.");
		 this.rollNum = sc.nextInt();  sc.nextLine();
		 System.out.println("Enter the Student Name.");
		 this.stName = sc.nextLine();
		 System.out.println("Please re-enter the student marks of all subject...");
		
//				first   :: English
//				second  :: Hindi
//				third	:: Mathematics
//				fourth  :: Science
//				fifth	:: Social Science
		 System.out.println("Subject 1 :: English\n"
				 		  + "Subject 2 :: Hindi\n"
				 		  + "Subject 3 :: Mathematics\n"
				 		  + "Subject 4 :: Science\n"
				 		  + "Subject 3 :: Social Science\n");
		 
		 for(int i =0;i<5; i++) {
				System.out.print("Enter the subject "+(i+1)+ " marks. :: ");
				marks = sc.nextInt();
				if(marks <= 100 && marks >= 0)
					li.add(marks);
				else {
					System.err.println( "Invalid Marks Entered.\nMarks should be in range of 0 to 100 \nPlease try again!");
					i--;
				}
				
			}
		 DatabaseAccess.updateTheData(rollNum, stName, li);
		 
		 	
	 }
	 void listAllStudentData() {
		 DatabaseAccess.displayAllTheData();
	 }
	 
}

