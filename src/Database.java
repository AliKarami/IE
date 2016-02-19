import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;


public class Database {
	Vector<Customer> list;

    Database() {
        Customer Admin = new Customer(1,"admin","admin");
        list.add(Admin);
    }
}
