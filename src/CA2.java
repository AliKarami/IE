import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;

class Sell extends OrderHandler {
    public void execute(PrintWriter out) {
       
    }
}

class Buy extends OrderHandler {
    public void execute(PrintWriter out) {
       
    }
}

class Add extends OrderHandler {
	public void execute(PrintWriter out) {

	}
}

class Deposit extends OrderHandler {
	public void execute(PrintWriter out) {

	}
}

class Withdraw extends OrderHandler {
	public void execute(PrintWriter out) {

	}
}

public class CA2 {

	
	public static void main(String[] args) {
		try{
			HttpServer server = HttpServer.create(new InetSocketAddress(9091), 0);
	        server.createContext("/order/sell", new Sell());
	        server.createContext("/order/buy", new Buy());
			server.createContext("/order/add", new Add());
			server.createContext("/order/deposit", new Deposit());
			server.createContext("/order/withdraw", new Withdraw());
			server.start();
		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
