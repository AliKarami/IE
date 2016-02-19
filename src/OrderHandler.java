import ir.ramtung.coolserver.*;
import java.io.*;
import java.util.*;

import com.sun.net.httpserver.*;


public abstract class OrderHandler extends ServiceHandler  {
	
	 	protected Map<String, String> params;

		protected Database db;


	    public abstract void execute(PrintWriter out) throws IOException;

	    public void handle(HttpExchange t) throws IOException {
	       
	    }

}
