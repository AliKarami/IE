/**
 * Created by ali on 2/19/16.
 */
public class Request{
    Customer cstmr;
    Symbol symbl;
    String type;
    boolean forSale;
    int quantity;
    int price;

    Request(Customer cstmr_,Symbol symbl_,String type_,boolean forSale_,int quantity_,int price_) {
        cstmr = cstmr_;
        symbl = symbl_;
        type = type_;
        forSale = forSale_;
        quantity = quantity_;
        price = price_;
    }
}
