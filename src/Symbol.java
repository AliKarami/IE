import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;

public class Symbol {
	
	PriorityQueue<Customer> seller;
	PriorityQueue<Customer> buyer;
	
	public Symbol(){
		
		seller = new PriorityQueue<Customer> (0,Collections.reverseOrder());
		buyer = new PriorityQueue<Customer> ();
	}
	
}
