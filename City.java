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


public class City
{
	String name;
	
	String forwardParent;
	String backwardParent;
	boolean forwardVisited;
	boolean backwardVisited;
	
    //int counter;
	boolean visited;
    ArrayList <String> child  = new ArrayList<String>();

	public City(String name)
	{
		this.name = name;
		//this.counter = -1;
	}
	
	/*public String getNextChild()
	{
		counter++;
		return child.get(counter);		
	}*/
	
}
