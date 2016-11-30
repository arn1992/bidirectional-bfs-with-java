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

public class Route 
{
	//boolean blocked;
	//String blockStart;
	//String blockEnd;
	ArrayList <String> route  = new ArrayList<String>();  // array to keep a single valid route

	public Route(ArrayList <String> r)
	{
		this.route = r;
	}
}
