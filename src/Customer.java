import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;

public class Customer {
	int id;
	String name;
	String family;
	int fund;

    LinkedHashMap<String,Integer> property = new LinkedHashMap<String,Integer>();
	Vector<Request> done;
	Vector<Request> refused;
	Vector<Request> inAct;

	Customer(int id_, String name_, String family_) {
		id = id_;
		name = name_;
		family = family_;
        int fund = 0;
		done = new Vector<Request>();
        refused = new Vector<Request>();
        inAct = new Vector<Request>();
	}
}
