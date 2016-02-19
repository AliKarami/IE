import ir.ramtung.coolserver.*;
import java.io.*;
import java.util.*;

import com.sun.net.httpserver.*;


public abstract class OrderHandler extends ServiceHandler  {
	

		protected Database db;

	    public abstract void execute(PrintWriter out) throws IOException;

	public void sendResponse(HttpExchange t,byte[] result,int status) throws IOException{
		t.sendResponseHeaders(status, result.length);
		Headers headers = t.getResponseHeaders();
		headers.add("Date", Calendar.getInstance().getTime().toString());
		headers.add("Content-Type", "text/html");
		OutputStream os = t.getResponseBody();
		os.write(result);
		os.close();
	}

	public void handle(HttpExchange t) throws IOException {
		extractParams(t.getRequestURI().getQuery());
		StringWriter sw = new StringWriter();
		execute(new PrintWriter(sw, true));
		String message = sw.toString();
		byte[] result = message.getBytes();
		if(message.equals("Mismatched parameters"))
			sendResponse(t,result,404);

		else
			sendResponse(t,result,200);

	}

}
