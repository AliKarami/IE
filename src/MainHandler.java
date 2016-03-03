import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import ir.ramtung.coolserver.Page;
import ir.ramtung.coolserver.ServiceHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

class MainHandler extends ServiceHandler {

    public void execute(PrintWriter out){
        if(params.containsKey("adr"))
            new Page("./index.html")
                .subst("adr", "\""  + params.get("adr") + "\"" )
                .writeTo(out);
        else
            new Page("./index.html")
                    .subst("adr","\"" + "\"")
                    .writeTo(out);
    }
}
