package com.abhi.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class QuizPlatform {
	private static HashMap<String, List<String>> ques(){
		HashMap<String, List<String>> question = new HashMap<String,List<String>>();
		List<String> list  =  new ArrayList<String>();
		List<String> list1 =  new ArrayList<String>();
		List<String> list2 =  new ArrayList<String>();
		List<String> list3 =  new ArrayList<String>();		
//	option for first question:----	
		list.add("echo(\"Hello World\");");
		list.add("System.out.println(\"Hello World\");");
		list.add("print (\"Hello World\");");
		list.add("Console.WriteLine(\"Hello World\");");
//		option for second question:----	
			list1.add("False");			
			list1.add("True");
//		option for third question:----	
			list2.add("# This is a comment");
			list2.add("/* This is a comment");
			list2.add("// This is a comment");
			list2.add("No One..");
//		option for fourth question:----	
			list3.add("myString");
			list3.add("Txt");
			list3.add("string");				
			list3.add("String");
		question.put("What is a correct syntax to output \"Hello World\" in Java?",list);
		question.put("Java is short for \"JavaScript\".",list1);
		question.put("How do you insert COMMENTS in Java code?",list2);
		question.put("Which data type is used to create a variable that should store text?",list3);
	return question;
	}
	
	private static Map<String,Integer> getResult() {
		Map<String,Integer> res = new HashMap<String, Integer>();
		// adding result.....
		res.put("What is a correct syntax to output \"Hello World\" in Java?",2);
		res.put("Java is short for \"JavaScript\".",1);
		res.put("How do you insert COMMENTS in Java code?",3);
		res.put("Which data type is used to create a variable that should store text?",4);
		return res;
	}
	
	public static int[] getStudentResult(Map<String,Integer> map, Map<String,Integer> studentResult) {
		int marks =0;
		int wrong =0;
		int[] result= new int[2];
			 for (Map.Entry<String, Integer> res : studentResult.entrySet()) {
				String key = res.getKey();
				Integer val = res.getValue();
				Integer i1= map.get(key);
				if(val.equals(i1)) 
				marks++;					
				else 
					wrong++;
		 }
			 result[0]=marks;
			 result[1]=wrong;
			 return result;		
	}
	public static void main(String[] args) {
		// variable declaration
		int option =0;
		Scanner sc =null;
		HashMap<String, List<String>> questions =null;
		HashMap<String, Integer> answer =null;		
		try {
			sc = new Scanner(System.in);
			questions = QuizPlatform.ques();
			answer =  new HashMap<String, Integer>();
			int ansChoice =0;
			int queNo = 0;
			System.out.println("please enter your choice between 1 to 4 \n otherwise your answer is considered as not attempt\n\n");
			for(Map.Entry  que:questions.entrySet()) {
				queNo++;
				 System.err.println(queNo  +" "+que.getKey());
				 System.out.println();
				 System.out.println();
				Object object = que.getValue();
				@SuppressWarnings({"unchecked"})
				List<String> li = (List<String>)object;
				ansChoice=0;
					for (String str : li)
					{
						ansChoice++;
						System.out.println(ansChoice+" "+ str);
					}
					System.out.println();					
					System.out.println();					
					System.out.print("Enter Your Answer::  ");					
					option = sc.nextInt();
					if(option<0 || option >5) 						
						System.out.println("invalid choice");
					else {
						answer.put((String)(que.getKey()), option);
						System.out.println("your choice is succesfully recorded");	
					}
			}		
			Map<String,Integer> map=getResult();
			int[] res = getStudentResult(map, answer);		
			System.out.println("\n\n-------Result---------");
			System.out.println("Total marks ::\t"+ res[0]);
			System.out.println("Attempt Question ::"+ (res[0] + res[1]));
			System.out.println("Wrong Answer ::"+ (res[1]));
		}catch(Exception ex) 
		{
			System.out.println(ex);
		}
	}
}
