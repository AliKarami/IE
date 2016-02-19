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
        if (!(params.containsKey("id") && params.containsKey("instrument") && params.containsKey("price") && params.containsKey("quantity") && params.containsKey("type"))) {
            //pass 404 error...
            out.println("404 Error!");
        } else {
            switch (params.get("type")) {
                case "GTC":
                    out.println(db.sell_gtc(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                case "IOC":
                    out.println(db.sell_ioc(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                case "MPO":
                    out.println(db.sell_mpo(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                default:
                    out.println("Invalid type");
            }
        }
    }
}

class Buy extends OrderHandler {
    Buy(Database db_) {
        db = db_;
    }
    public void execute(PrintWriter out) {
        if (!(params.containsKey("id") && params.containsKey("instrument") && params.containsKey("price") && params.containsKey("quantity") && params.containsKey("type"))) {
            //pass 404 error...
            out.println("404 Error!");
        } else {
            switch (params.get("type")) {
                case "GTC":
                    out.println(db.buy_gtc(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                case "IOC":
                    out.println(db.buy_ioc(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                case "MPO":
                    out.println(db.buy_mpo(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity"))));
                    break;
                default:
                    out.println("Invalid type");
            }
        }
    }
}

class Add extends CustomerHandler {
    Add(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {
        if ( !(params.containsKey("id") && params.containsKey("name") && params.containsKey("family"))) {
            //pass 404 error...
            out.println("404 Error!");
        }
        else {
            if (db.add_customer(Integer.parseInt(params.get("id")),params.get("name"),params.get("family"))) {
                out.println("New user is added");
            }
            else {
                out.println("Repeated id");
            }
        }
	}
}



class Deposit extends CustomerHandler {
    Deposit(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {
        if (!(params.containsKey("id") && params.containsKey("amount"))) {
            //pass 404 error...
            out.println("404 Error!");
        }
        else {
            if (db.deposit_customer(Integer.parseInt(params.get("id")),Integer.parseInt(params.get("amount")))) {
                out.println("Successful");
            }
            else {
                out.println("Unknown user id");
            }
        }
	}
}

class Withdraw extends CustomerHandler {
    Withdraw(Database db_) {
        db = db_;
    }
	public void execute(PrintWriter out) {
        if (!(params.containsKey("id") && params.containsKey("amount"))) {
            //pass 404 error...
            out.println("404 Error!");
        }
        else {
            switch (db.withdraw_customer(Integer.parseInt(params.get("id")),Integer.parseInt(params.get("amount")))) {
                case 0:
                    out.println("Successful");
                    break;
                case -1:
                    out.println("Not enough money");
                    break;
                case -2:
                    out.println("Unknown user id");
                    break;
            }
        }
	}
}

public class CA2 {
    static Database db = new Database();

	public static void main(String[] args) {
		try{

            HttpServer server = HttpServer.create(new InetSocketAddress(9091), 0);
	        server.createContext("/order/sell", new Sell(db));
	        server.createContext("/order/buy", new Buy(db));
			server.createContext("/customer/add", new Add(db));
			server.createContext("/customer/deposit", new Deposit(db));
			server.createContext("/customer/withdraw", new Withdraw(db));
			server.start();



		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
