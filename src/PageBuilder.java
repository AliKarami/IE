import ir.ramtung.coolserver.Page;

import java.io.PrintWriter;
import java.util.Map;

public class PageBuilder {

    public static void createPage(PrintWriter out,String adr,String title,String msg){
        new Page("./index.html")
                .subst("title",title)
                .subst("adr", "\""  + adr + "\"" )
                .subst("msg", msg )
                .writeTo(out);
    }
}
