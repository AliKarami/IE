import java.io.*;
import java.net.*;
import java.util.*;

class Request{
	Symbol sym;
	int operator;//kind of request
}

public class Customer {
	int id;
	String name;
	String family;
	int fund;
	List<Request> done;
	List<Request> refused;
	List<Request> inAct;

	Customer(int id_, String name_, String family_) {
		id=id_;
		name=name_;
		family=family_;
	}
}
