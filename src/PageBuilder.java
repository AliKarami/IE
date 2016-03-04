import ir.ramtung.coolserver.Page;

import java.io.PrintWriter;
import java.util.Map;

public class PageBuilder {

    public static void createPage(PrintWriter out,String page,String adr,String title,String msg){
        new Page("./" + page)
                .subst("title",title)
                .subst("adr", "\""  + adr + "\"" )
                .subst("msg", msg )
                .writeTo(out);
    }
}
