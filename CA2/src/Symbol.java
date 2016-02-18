import java.io.*;
import java.net.*;
import java.util.*;

public class Symbol {
	
	PriorityQueue<Customer> seller;
	PriorityQueue<Customer> buyer;
	
	public Symbol(){
		
		seller = new PriorityQueue<Customer> (0,Collections.reverseOrder());
		buyer = new PriorityQueue<Customer> ();
	}
	
}
