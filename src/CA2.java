import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;
import ir.ramtung.coolserver.*;

class Sell extends OrderHandler {
    Sell(Database db_) {
        db = db_;
    }
    public void execute(PrintWriter out) {
       
    }
}

class Buy extends OrderHandler {
    Buy(Database db_) {
        db = db_;
    }
    public void execute(PrintWriter out) {
       
    }
}

class Add extends CustomerHandler {
    Add(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {
        if (!(params.containsKey("id") && params.containsKey("name") && params.containsKey("family"))) {
            //pass 404 error...
        }
        else {
            if (!(db.add_customer(Integer.parseInt(params.get("id")),params.get("name"),params.get("family")))) {
                out.println("Repeated id");
            }
            else {
                out.println("New user is added");
            }
        }
	}
}

class Deposit extends CustomerHandler {
    Deposit(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {

	}
}

class Withdraw extends CustomerHandler {
    Withdraw(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {

	}
}

public class CA2 {
    static Database db = new Database();
	
	public static void main(String[] args) {
		try{
            HttpServer server = HttpServer.create(new InetSocketAddress(9091), 0);
	        server.createContext("/order/sell", new Sell(db));
	        server.createContext("/order/buy", new Buy(db));
			server.createContext("/order/add", new Add(db));
			server.createContext("/order/deposit", new Deposit(db));
			server.createContext("/order/withdraw", new Withdraw(db));
			server.start();



		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
