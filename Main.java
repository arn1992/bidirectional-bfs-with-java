/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidirection;

/**
 *
 * @author arn
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Thread
{
	String startingCity;      // Starting city name
	String destinationCity;   // Destination city name
	ArrayList <City> cityList  = new ArrayList<City>(); // array to keep all the City Objects
	
	


	
	// Method to find information from file
	
	public  void obtainInfoFromFile(String fileName) throws IOException   
	{
        Scanner sc = null;;
		
        System.out.println("Reading the map ...............\n\nMap Details : \n");
		try
	    {
	      sc = new Scanner(new BufferedReader(new FileReader(fileName)));
	      
	      while(sc.hasNextLine())
	      {
	    	  
	        int NumberOFcities = sc.nextInt();            // No of Cities
	        System.out.println("Number of Cities : "+NumberOFcities);
	       
	        int NumberOfRoads = sc.nextInt();            // No of total roads
	        System.out.println("Number of Roads : "+NumberOfRoads);
	        
	        
	        startingCity  = sc.next();           // Start City
	        System.out.println("\nStrating City  : "+startingCity);
	           
	        destinationCity = sc.next();
		    System.out.println("Destination City  : "+destinationCity);
	        
		    
	       

		 //   System.out.println("\nPrinting  All roads.......... ");

	        for(int i=0;i<NumberOfRoads;i++)
	        {
	        	String city1 = sc.next();
	        	String city2 = sc.next();
	   //     	System.out.println(city1+" - "+city2);
	        	
	        	setObjectIfNecessary(city1, city2);
	        }
	        
	        
	     //  System.out.println("Completed");
	     }
	     // System.out.println("Completed 2");
	     
	 }
		finally{if (sc != null) {sc.close();
	         }
		}System.out.println("\nMap has been read successfully\n");
		
}
	
	
	
	
	
	/** 
	 
	 Method to find whether the  given City name has been created and saved as an object in
	 the cityList array or not. 
	 
	 **/
	 public boolean isAdded(String name)
	{
		boolean ret = false;
		
		for(int i=0;i<cityList.size();i++)
		{
			String n = cityList.get(i).name;
			if(n.equals(name))
			{
				ret = true;
				break;
			}
		}
	return ret;
	}
	
	 
	
	
	/*
	 * Method to create City Object for the given two City names.
	 * If the Object was created earlier then it just add each other as child city   
	 */
	
	public void setObjectIfNecessary(String city1, String city2)
	 {
		 if(isAdded(city1)==false)
     	{
     	  City newCity = new City(city1);
     	  newCity.child.add(city2);
     	  cityList.add(newCity);
     	}
     	else
     	{
     		insertChildIntoThisParent(city1,city2);
     	}
     	
     	
     	if(isAdded(city2)==false)
     	{
     	  City newCity = new City(city2);
     	  newCity.child.add(city1);
     	  cityList.add(newCity);
     	}
     	else
     	{
     		insertChildIntoThisParent(city2,city1);
     	}
     	
		 
	 }
	 
	
	 

		/** Method to insert the child into the given parent city **/
	 
	 public void insertChildIntoThisParent(String parent, String child)
	{
		for(int i=0;i<cityList.size();i++)
		{
			String presentCity = cityList.get(i).name;
			if(parent.equals(presentCity))
			{
				cityList.get(i).child.add(child);
				break;
			}
		}
	}
	 
	 
	 
	 /*
	  * Method to print City Objects after they have been initialized and all child Cities has been inserted
	  * 
	  */
	
	 public void printCity()
	 {
		 System.out.println("\n\nPrinting all city with child.........\n");
		 for(int i=0;i<cityList.size();i++)
		 {
			 City temp = cityList.get(i);
			 System.out.print(temp.name+" : ");
			 for(int j=0;j<temp.child.size();j++)
			 {
				 System.out.print(temp.child.get(j));
				 if(j!=temp.child.size()-1)
				 {
				 System.out.print(",");
				 }
			 }
			 System.out.println();
		 }
		 System.out.println();
	 }
	
	
	 
	 
	
	 
	public static void main(String[]args) throws IOException 
	{
		
		Main newMain = new Main();
		newMain.obtainInfoFromFile("map1.txt");
		newMain.printCity();
		
		BidirectionalBFS newBFS = new BidirectionalBFS(newMain.cityList, newMain.startingCity,newMain.destinationCity);
	
		Thread t1 = new Thread(newBFS,"forward");
		t1.start(); 
	
		System.out.println("Thread 1 Started");
		
		Thread t2 = new Thread(newBFS,"backward");
		t2.start();
		System.out.println("Thread 2 started");
		
		System.out.println("Running bidirectional BFS algorithm to find the shrotest path.....\n\n");
	 
	}
	




	

}
