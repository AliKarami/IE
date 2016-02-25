/**
 * Created by ali on 2/25/16.
 */
public class MPO implements Type{
    @Override
    public String Sell(int id_, String name_, int price_, int quantity_,Database db_) {
        Customer seller = db_.get_user(id_);
        Symbol stock = db_.get_symbol(name_);
        Request req = new Request(seller,stock,"MPO",true,quantity_,price_);
        if (id_ == 1) { //handle admins POWER!!!
            if (stock == null) {
                db_.add_symbol(name_);
                stock = db_.get_symbol(name_);
            }
            seller.property.put(name_,quantity_);
        }
        if (seller == null)
            return "Unknown user id";
        else if (seller.property.get(name_) < quantity_) {
            seller.refused.add(req);
            return "Not enough share";
        }
        else if (stock == null)
            return "Invalid symbol id";
        else {
            return add_sellReq(req);
        }
    }

    @Override
    public String add_sellReq(Request req_) {
        req_.price=0;
        req_.symbl.getSeller().add(req_);

        String result = doDeal(req_);
        if (result.equals("nothing Dealed!")) {
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
            req_.cstmr.done.add(req_);
            return result;
        }
    }


    @Override
    public String Buy(int id_, String name_, int price_, int quantity_,Database db_) {
        Customer buyer = db_.get_user(id_);
        Symbol stock = db_.get_symbol(name_);
        Request req = new Request(buyer,stock,"MPO",false,quantity_,price_);
        if (buyer == null)
            return "Unknown user id";
        else if (MPO_quantityPrice(quantity_,req) > buyer.fund) {
            buyer.refused.add(req);
            return "Not enough money";
        }
        else if (stock == null)
            return "Invalid symbol id";
        else {
            return add_buyReq(req);
        }
    }

    @Override
    public String add_buyReq(Request req_) {
        return null;
    }

    @Override
    public String doDeal(Request req_) {
        if (MPO_quantityPrice(req_.quantity,req_) == -1)
            return "nothing Dealed!";
        else {
            StringBuilder sb = new StringBuilder("");
            while (true) {
                if (!req_.forSale)
                    req_.price=req_.symbl.getSeller().iterator().next().price;
                String current = new GTC().doDeal(req_);
                if (current.equals("nothing Dealed!"))
                    break;
                else {
                    sb.append(current + "\n");
                }
            }
            return sb.toString();
        }
    }

    public int MPO_quantityPrice(int quantity_,Request req_) {
        int sumQuantity = 0;
        int sumPrice = 0;
        for (Request buyReq : req_.symbl.getBuyer()) {
            if (sumQuantity < (quantity_ + buyReq.quantity)) {
                sumQuantity += buyReq.quantity;
                sumPrice += buyReq.price;
            } else if (sumQuantity < quantity_) {
                sumPrice += (sumQuantity-quantity_)*buyReq.price;
                sumQuantity = quantity_;
            } else if (sumQuantity == quantity_)
                return sumPrice;
            else
                return -1;
        }
        return -1;
    }
}
