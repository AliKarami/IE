/**
 * Created by ali on 2/25/16.
 */
public interface Type {

    public String Sell(int id_, String name_, int price_, int quantity_,Database db_);

    public String add_sellReq(Request req_);

    public String Buy(int id_,String name_,int price_,int quantity_,Database db_);

    public String add_buyReq(Request req_);

    public String doDeal(Request req_);
}
