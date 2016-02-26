import java.io.*;
import java.util.*;
import com.sun.net.httpserver.*;

class PostHandler implements HttpHandler {

    public void execute(InputStream is,PrintWriter out) {

        try {
            OutputStream outputStream = new FileOutputStream(new File("./DynamicClass.zip"));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            out.println("Unsuccessful reconfiguration");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    out.println("Unsuccessful reconfiguration");
                    e.printStackTrace();
                }
            }
        }
        new Unzip("./DynamicClass.zip",".");
        if ((new File ("./DynamicClass.zip")).delete()) {
            out.println("Successful reconfiguration");
        }

    }

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
        InputStream is = t.getRequestBody();
        StringWriter sw = new StringWriter();
        execute(is,new PrintWriter(sw, true));
        String message = sw.toString();
        byte[] result = message.getBytes();
        if(message.equals("Successful reconfiguration"))
            sendResponse(t,result,200);
        else
            sendResponse(t,result,404);
    }
}