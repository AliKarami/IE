import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;


public class Database {
	private Vector<Customer> list = new Vector<Customer>();
    private Vector<Symbol> symbs = new Vector<Symbol>();


    Database() {
        Customer Admin = new Customer(1,"admin","");
        list.add(Admin);
    }

    public Customer get_user(int id_){
        for (Customer cstmr : list) {
            if (cstmr.id == id_)
                return cstmr;
        }
        return null;
    }

    public boolean add_customer(int id_,String name_,String family_) {
        for (Customer cstmr : list) {
            if (cstmr.id==id_)
                return false;
        }
        Customer newcstmr = new Customer(id_,name_,family_);
        list.add(newcstmr);
        return true;
    }

    public boolean deposit_customer(int id_,int amount){
        for (Customer cstmr : list) {
            if (cstmr.id==id_) {
                cstmr.fund += amount;
                return true;
            }
        }
        return false;
    }
    public int withdraw_customer(int id_,int amount) {
        for (Customer cstmr : list) {
            if (cstmr.id==id_) {
                if (cstmr.fund >= amount) {
                    cstmr.fund -= amount;
                    return 0; // Successful
                } else {
                    return -1; //Not enough money
                }
            }
        }
        return -2; //Unknown user id
    }

    public Symbol get_symbol(String name_) {
        for (Symbol sym : symbs) {
            if (sym.name.equals(name_))
                return sym;
        }
        return null;
    }

    public String sell_gtc(int id_,String name_,int price_,int quantity_) {
        Customer seller = get_user(id_);
        Symbol stock = get_symbol(name_);
        Request req = new Request(seller,stock,"GTC",true,quantity_,price_);
        if (seller == null)
            return "Unknown user id";
        else if (seller.property.get(name_) < quantity_) {
            seller.refused.add(req);
            return "Not enough share";
        }
        else if (stock == null)
            return "Invalid symbol id";
        else {
            seller.inAct.add(req);
            return stock.add_sellReqGTC(req);
        }
    }
}
