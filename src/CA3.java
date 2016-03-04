import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;
import ir.ramtung.coolserver.*;
import java.lang.reflect.*;

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
            //out.println("Mismatched parameters");
            PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:اشکال در ورودی.");
        } else {

            if(params.get("id") == null || params.get("instrument") == null || params.get("price") == null ||params.get("quantity") == null){
                //out.println("Mismatched parameters");
                PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:تمام فیلد ها را پر کنید.");
                return;
            }
            try {
                String dealType = params.get("type");
                Class <? extends Type> TypeClass = Class.forName(dealType).asSubclass(Type.class);
                Object obj = TypeClass.newInstance();
                Method mtd = TypeClass.getDeclaredMethod("Sell", new Class[]{Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Database.class});
               // out.println(mtd.invoke(obj, Integer.parseInt(params.get("id")), params.get("instrument"), Integer.parseInt(params.get("price")), Integer.parseInt(params.get("quantity")), db));
                String msg = (String)mtd.invoke(obj,Integer.parseInt(params.get("id")), params.get("instrument"), Integer.parseInt(params.get("price")), Integer.parseInt(params.get("quantity")), db);
                PageBuilder.createPage(out,"","بازار بورس",msg);
            }catch(Exception ex){
                //ex.printStackTrace();
                //out.println("Invalid type");
                PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:این نوع از خرید و فروش تعریف نشده است.");
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
            //out.println("Mismatched parameters");
            PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:اشکال در ورودی.");

        } else {

            if(params.get("id") == null || params.get("instrument") == null || params.get("price") == null ||params.get("quantity") == null){
                //out.println("Mismatched parameters");
                PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:تمام فیلد ها را پر کنید.");
                return;
            }

            try {
                String dealType = params.get("type");
                Class <? extends Type> TypeClass = Class.forName(dealType).asSubclass(Type.class);
                Object obj = TypeClass.newInstance();
                Method mtd = TypeClass.getDeclaredMethod("Buy", new Class[]{Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Database.class});
                //out.println(mtd.invoke(obj, Integer.parseInt(params.get("id")), params.get("instrument"), Integer.parseInt(params.get("price")), Integer.parseInt(params.get("quantity")), db));
                String msg = (String)mtd.invoke(obj,Integer.parseInt(params.get("id")), params.get("instrument"), Integer.parseInt(params.get("price")), Integer.parseInt(params.get("quantity")), db);
                PageBuilder.createPage(out,"","بازار بورس",msg);
            }catch(Exception ex){
                //ex.printStackTrace();
                //out.println("Invalid type");
                PageBuilder.createPage(out,"BuySell.html","خرید و فروش سهام","پیغام سیستم:این نوع از خرید و فروش تعریف نشده است.");
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
            //out.println("Mismatched parameters");
            PageBuilder.createPage(out,"SignInUp.html","ورود و عضویت","پیغام سیستم:اشکال در ورودی.");
        }
        else {

            if(params.get("id") == null || params.get("name") == null || params.get("family") == null){
                //out.println("Mismatched parameters");
                PageBuilder.createPage(out,"SignInUp.html","ورود و عضویت","پیغام سیستم:تمام فیلد ها را پر کنید.");
                return;
            }

            if (db.add_customer(Integer.parseInt(params.get("id")),params.get("name"),params.get("family"))) {
               // out.println("New user is added");
                PageBuilder.createPage(out,"","بازار بورس","پیغام سیستم:ثبت نام شما با موفقیت انجام شد.");
            }
            else {
                //out.println("Repeated id");
                PageBuilder.createPage(out,"SignInUp.html","ورود و عضویت","پیغام سیستم:نام کاربری شما قبلا ثبت شده است.");
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
           //out.println("Mismatched parameters");
            PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:اشکال در ورودی.");
        }
        else {

            if(params.get("id") == null || params.get("amount") == null){
               // out.println("Mismatched parameters");
                PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:تمام فیلد ها را پر کنید.");
                return;
            }

            if (db.deposit_customer(Integer.parseInt(params.get("id")),Integer.parseInt(params.get("amount")))) {
                //out.println("Successful");
                PageBuilder.createPage(out,"","بازار بورس","پیغام سیستم:حساب شما با موفقیت شارژ شد.");
            }
            else {
                //out.println("Unknown user id");
               ; PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:نام کاربری اشتباه است.");
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
            //out.println("Mismatched parameters");
            PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:اشکال در ورودی.");
        }
        else {

            if(params.get("id") == null || params.get("amount") == null){
               // out.println("Mismatched parameters");
                PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:تمام فیلدها را پر کنید.");
                return;
            }

            switch (db.withdraw_customer(Integer.parseInt(params.get("id")),Integer.parseInt(params.get("amount")))) {
                case 0:
                   // out.println("Successful");
                    PageBuilder.createPage(out,"","بازار بورس","پیغام سیستم:پول از حساب شما با موفقیت برداشت شد.");
                    break;
                case -1:
                   // out.println("Not enough money");
                    PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:موجودی کافی نیست.");
                    break;
                case -2:
                   // out.println("Unknown user id");
                    PageBuilder.createPage(out,"DepositWithdraw.html","مدیریت اعتبار","پیغام سیستم:نام کاربری اشتباه است.");
                    break;
            }
        }
	}
}


public class CA3 {
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
            server.createContext("/index",new MainHandler());
            server.createContext("/",new FileHandler());
			server.start();



		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
