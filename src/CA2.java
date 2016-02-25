import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;
import ir.ramtung.coolserver.*;

class ErrorHandler extends ServiceHandler{

    public void execute(PrintWriter out){
        out.println("Unknown command");
    }
    public void handle(HttpExchange t) throws IOException{
        StringWriter sw = new StringWriter();
        execute(new PrintWriter(sw, true));
        String err = sw.toString();
        byte[] result = err.getBytes();
        t.sendResponseHeaders(404, result.length);
        Headers headers = t.getResponseHeaders();
        headers.add("Date", Calendar.getInstance().getTime().toString());
        headers.add("Content-Type", "text/html");
        OutputStream os = t.getResponseBody();
        os.write(result);
        os.close();
    }
}

class Sell extends OrderHandler {
    Sell(Database db_) {
        db = db_;
    }
    public void execute(PrintWriter out) {
        if (!(params.containsKey("id") && params.containsKey("instrument") && params.containsKey("price") && params.containsKey("quantity") && params.containsKey("type"))) {
            //pass 404 error...
            out.println("Mismatched parameters");
        } else {

            if(params.get("id") == null || params.get("instrument") == null || params.get("price") == null ||params.get("quantity") == null){
                out.println("Mismatched parameters");
                return;
            }

            switch (params.get("type")) {
                case "GTC":
                    out.println(new GTC().Sell(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
                    break;
                case "IOC":
                    out.println(new IOC().Sell(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
                    break;
                case "MPO":
                    out.println(new MPO().Sell(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
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
            out.println("Mismatched parameters");
        } else {

            if(params.get("id") == null || params.get("instrument") == null || params.get("price") == null ||params.get("quantity") == null){
                out.println("Mismatched parameters");
                return;
            }

            switch (params.get("type")) {
                case "GTC":
                    out.println(new GTC().Buy(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
                    break;
                case "IOC":
                    out.println(new IOC().Buy(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
                    break;
                case "MPO":
                    out.println(new MPO().Buy(Integer.parseInt(params.get("id")),params.get("instrument"),Integer.parseInt(params.get("price")),Integer.parseInt(params.get("quantity")),db));
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
            out.println("Mismatched parameters");
        }
        else {

            if(params.get("id") == null || params.get("name") == null || params.get("family") == null){
                out.println("Mismatched parameters");
                return;
            }

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
            out.println("Mismatched parameters");
        }
        else {

            if(params.get("id") == null || params.get("amount") == null){
                out.println("Mismatched parameters");
                return;
            }

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
            out.println("Mismatched parameters");
        }
        else {

            if(params.get("id") == null || params.get("amount") == null){
                out.println("Mismatched parameters");
                return;
            }

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
            server.createContext("/config/uploadzip", new PostHandler());
            server.createContext("/",new ErrorHandler());
			server.start();



		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
