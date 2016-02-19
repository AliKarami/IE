import java.io.*;
import java.net.*;
import java.util.*;
import ir.ramtung.coolserver.*;


public class Database {
	Vector<Customer> list = new Vector<Customer>();

    Database() {
        Customer Admin = new Customer(1,"admin","admin");
        list.add(Admin);
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
}
