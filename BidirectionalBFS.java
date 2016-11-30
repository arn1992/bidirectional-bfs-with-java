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
import java.util.ArrayList;





public class BidirectionalBFS  implements Runnable 
{
	
	ArrayList<City> cityList;
	String startingCity;
	String destinationCity;
	int forwardDepth;
	int backwardDepth;
	String intersectedCity;   // intersected city
	String findingDirection;  // Latest direction in which the path was found
	volatile boolean terminate = false;
	int forwardDistance = 0;
	int backwardDistance = 0;
	boolean print = true;
	ArrayList <String> forwardQueue  = new ArrayList<String>();  // array to keep a forward BFS route
	ArrayList <String> backwardQueue  = new ArrayList<String>();  // array to keep a backward BFS route

	
	ArrayList <String> finalRoute  = new ArrayList<String>();  // array to keep a forward BFS route
	ArrayList <String> forwardRoute  = new ArrayList<String>();  // array to keep a forward BFS route
	ArrayList <String> backwardRoute  = new ArrayList<String>();  // array to keep a backward BFS route

	
	public BidirectionalBFS( ArrayList<City> cityList, String start,String end)
	{
		this.cityList = cityList;
		this.startingCity = start;
		this.destinationCity = end;
	}

	 
	public void setVisited(String name, boolean v, String direction)
	 {
	     	int index = getIndex(name);
	//     	System.out.println(name+" was set as "+direction+" visited as "+v);
		    City presentCity = cityList.get(index);
		
		
				 if(direction.equals("forward"))
				 {
					 presentCity.forwardVisited = v;
						 
				 }
				 else
				 {
					 presentCity.backwardVisited = v;
				 }
			
	}
	
public boolean isVisitedEarlier(String CityName, String visiteBy)
{
	//System.out.println("Visit check for "+CityName+" to check "+visiteBy+" direction");
	boolean ret = false;
	int index = getIndex(CityName);


	switch(visiteBy)
	{
	case "forward" : 
		ret = cityList.get(index).forwardVisited;
	    break;
	case "backward" :
		ret = cityList.get(index).backwardVisited;
		break;
		default : break;
	}			
	
	return ret;
}
	

public void setParent(String Parent, String child, String direction)
{
	int childIndex = getIndex(child);
	
	switch(direction)
	{
	case "forward" : cityList.get(childIndex).forwardParent = Parent;
	break;
	case "backward" : cityList.get(childIndex).backwardParent = Parent;
	break;
	default : break;
	}
	
}


public int getIndex(String name)
{
	int ret = -1;
	for(int i=0;i< cityList.size();i++)
	{
		String n = cityList.get(i).name;
		if(name.equals(n))
		{
			ret = i;
			break;
		}
	}
	return ret;
}

	
public void BFSForward(String CityName) 
	 
{
	//System.out.println("Entered into City "+CityName + "(Forward)");
	
	 setVisited(CityName, true, "forward");
		 boolean visitedEarlier = isVisitedEarlier(CityName,"backward");
		 
		 if(visitedEarlier==true)
		 {
			 System.out.println("Intersected point : "+CityName);
			 System.out.println("Direction : Forward ");

			 intersectedCity = CityName;
			 findingDirection = "f";
		     terminate = true;
			// forwardQueue.clear();
			 //backwardQueue.clear();
			 return;
		 }
		 else
		 {
			 
			 int index = getIndex(CityName);
			 int noOfChild = cityList.get(index).child.size();
			 
			 for(int i=0;i<noOfChild;i++)
			 {
				 
				String childName = cityList.get(index).child.get(i);
				
			     boolean forwardVisitedEarlier = isVisitedEarlier(childName,"forward");
				
			     if(forwardVisitedEarlier==false)
				 {
			    		 setParent(CityName,childName,"forward");
				         forwardQueue.add(childName);
				//         System.out.println("Inserting child "+childName+" of parent "+CityName);		
		             
				 }
			 }
			 
			 forwardQueue.remove(0);
			
			 if(forwardQueue.size()==0)
			 {
				//System.out.println("The Forward Queue is empty but could not found any intersected City"); 
			 }
			 else
			 {
			//	 System.out.println("Going to Run Forward BFS for "+forwardQueue.get(0));
				 try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 if(terminate==false)
				 {
					
				 BFSForward(forwardQueue.get(0));
				 }
				 
					else
					{
						try {
							Thread.currentThread().join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			 }
		  }
	
}
	 
	 
	 public void BFSBackward(String CityName) 
	 {
		
		//	System.out.println("Entered into City "+CityName + "(Backward)");

		       setVisited(CityName, true, "backward");
				 
				 boolean visitedEarlier = isVisitedEarlier(CityName,"forward");
				 
				 if(visitedEarlier==true)
				 {
					 System.out.println("Interscted Point "+CityName);
					 System.out.println("Direction : Backward ");

					 terminate = true;
					 intersectedCity = CityName;
					 findingDirection = "b";
					
				//	 forwardQueue.clear();
				//	 backwardQueue.clear();
					 return ;
				 }
				 
				 else
				 {
					
					 int index = getIndex(CityName);
					 int noOfChild = cityList.get(index).child.size();
					 
					 for(int i=0;i<noOfChild;i++)
					 {
						String childName = cityList.get(index).child.get(i);
						boolean backwardVisitedEarlier = isVisitedEarlier(childName,"backward");
						
						if(backwardVisitedEarlier==false)
						{
							
						 	  setParent(CityName,childName,"backward");
								   backwardQueue.add(childName);
			//					   System.out.println("Inserting child "+childName+" of parent "+CityName);		
								   backwardDepth++;
						  
						   
						  
						   }
					 }
					 
					
					 backwardQueue.remove(0);
					
					 if(backwardQueue.size()==0)
					 {
				//		System.out.println("The Backward Queue is empty but could not found any intersected City"); 
					 }
					 
					 else
					 {
					//	 System.out.println("Going to Run Backward BFS for "+backwardQueue.get(0));
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(terminate==false)
						{
						//	System.out.println(Thread.currentThread().isAlive());
						    BFSBackward(backwardQueue.get(0));
						   
						}
						else
						{
							try {
								Thread.currentThread().join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 }
			 
		         }
		 
		 }
	 
	 
	 public void findForwardRoute(String city)
	 {
		 
		
		 forwardRoute.add(city);
		// System.out.println("adding forward parent "+city);
		
		 while(!city.equals(startingCity))
		 {
			 int index = getIndex(city);
			 City parent = cityList.get(index);
			 city = parent.forwardParent;
		//	 System.out.println("adding forward parent "+city);
			 forwardRoute.add(city);
		     //findForwardRoute(city);	 
		 }
		 
		//	 System.out.println("Forward route size : "+forwardRoute.size());

			 return;
	
	 }
	 
	 public void findBackwardRoute(String city)
	 {
		 
		
		// backwardRoute.add(city);
		
		 while(!city.equals(destinationCity))
		 {
			 int index = getIndex(city);
			 City parent = cityList.get(index);
			 city = parent.backwardParent;
	//		 System.out.println("adding backward parent "+city);
			 backwardRoute.add(city);
		     //findBackwardRoute(city);	 
		 }
		 
		 
		//	 System.out.println("Backward route size : "+backwardRoute.size());
			 return;
		 
	 }
	 
	 public void findRoute()
	 {
		
		 
		
		 findForwardRoute(intersectedCity);
		 findBackwardRoute(intersectedCity);
		
		 
		 for(int i=forwardRoute.size()-1;i>=0;i--)
		 {
			 finalRoute.add(forwardRoute.get(i));
		 }
		
		 
		 for(int i=0;i<backwardRoute.size();i++)
		 {
			 finalRoute.add(backwardRoute.get(i));
		 }
	//	 System.out.println("Final route size : "+finalRoute.size());
		 
	 }
	 
	 
	 public void printRoute()
	 {
		
			System.out.print("Final Route : ");
		 for(int i=0;i<finalRoute.size();i++)
		 {
			 System.out.print(finalRoute.get(i));
			 
			 if(i<finalRoute.size()-1)
			 {
				 System.out.print("-->");
			 }
			 else
			 {
				 System.out.println();	 
			 }
		 }
	
		 
		  if(findingDirection.equals("f"))
		 {
			 System.out.println("Distance upto intersection : "+(forwardRoute.size()));
		 }
		 else
		 {
			 System.out.println("Distance upto intersection : "+(backwardRoute.size()));
			 
		 }
		 
	
		
	 }
	 @Override
		public void run() 
	     {
		 
		    String direction = Thread.currentThread().getName();
			
		    if(direction.equals("forward"))
			{
		    	//System.out.println("forward is running");
				forwardQueue.add(startingCity);
		        BFSForward(startingCity);		
		        //System.out.println("forward completed");
		      
	         }
			
			else if (direction.equals("backward"))
			{
			//	System.out.println("backward is running");
				backwardQueue.add(destinationCity);
			    BFSBackward(destinationCity);
			 //   System.out.println("backward is completed");
			  
			  	       		  
		    }
		    
		    
		    findRoute();
		    printRoute();
            
        	System.out.println("\n........................................................");
            System.out.println("........................................................");
            System.exit(0);	
		    
		
			 //   System.out.println();
			    
			
			   //	System.exit(0);	
				
		
		    
		    
          
	
		


	     }	
		
	     
}
