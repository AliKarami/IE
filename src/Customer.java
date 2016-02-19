import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;

class Request{
	Symbol sym;
	int operator;//kind of request
}

public class Customer {
	int id;
	String name;
	String family;
	int fund;

    LinkedHashMap<String,Integer> property = new LinkedHashMap<String,Integer>();
	List<Request> done;
	List<Request> refused;
	List<Request> inAct;

	Customer(int id_, String name_, String family_) {
		id = id_;
		name = name_;
		family = family_;
        int fund = 0;
        if (id == 1)
            fund = Integer.MAX_VALUE;
	}
}
