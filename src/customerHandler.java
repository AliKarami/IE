/**
 * Created by ali on 2/18/16.
 */

import ir.ramtung.coolserver.*;
import java.io.*;
import java.util.*;
import com.sun.net.httpserver.*;

public abstract class customerHandler extends ServiceHandler {
        protected Map<String, String> params;
        protected Database db;


        public abstract void execute(PrintWriter out) throws IOException;

        public void handle(HttpExchange t) throws IOException {

        }

}
