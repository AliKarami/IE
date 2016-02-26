/**
 * Created by ali on 2/25/16.
 */
public class IOC implements Type {
    @Override
    public String Sell(int id_, String name_, int price_, int quantity_,Database db_) {
        Customer seller = db_.get_user(id_);
        Symbol stock = db_.get_symbol(name_);
        if (id_ == 1) { //handle admins POWER!!!
            if (stock == null) {
                db_.add_symbol(name_);
                stock = db_.get_symbol(name_);
            }
            seller.property.put(name_,quantity_);
        }
        Request req = new Request(seller,stock,"IOC",true,quantity_,price_);
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
        req_.symbl.getSeller().add(req_);

        if (!(req_.equals(req_.symbl.getSeller().iterator().next()))) {
            req_.symbl.getSeller().remove(req_);
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
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
    }


    @Override
    public String Buy(int id_, String name_, int price_, int quantity_,Database db_) {
        Customer buyer = db_.get_user(id_);
        Symbol stock = db_.get_symbol(name_);
        Request req = new Request(buyer,stock,"IOC",false,quantity_,price_);

        if (buyer == null)
            return "Unknown user id";
        else if (buyer.fund < (price_)*(quantity_)) {
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
        req_.symbl.getBuyer().add(req_);

        if (!(req_.equals(req_.symbl.getBuyer().iterator().next()))) {
            req_.symbl.getBuyer().remove(req_);
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
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
    }

    @Override
    public String doDeal(Request req_) {
        if (!req_.symbl.getBuyer().iterator().hasNext() || !req_.symbl.getSeller().iterator().hasNext())
            return "nothing Dealed!";
        Request firstBuyReq = req_.symbl.getBuyer().iterator().next();
        Request firstSellReq = req_.symbl.getSeller().iterator().next();
        if (firstBuyReq.price > firstSellReq.price)
        {
            if (firstBuyReq.quantity > firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                firstBuyReq.quantity -= firstSellReq.quantity;
                req_.symbl.getSeller().remove(firstSellReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else if (firstBuyReq.quantity == firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                req_.symbl.getBuyer().remove(firstBuyReq);
                req_.symbl.getSeller().remove(firstSellReq);
                firstBuyReq.cstmr.done.add(firstBuyReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else {
                int sumQuantity = 0;
                for (Request tmpReq : req_.symbl.getBuyer()) {
                    if (tmpReq.price > firstSellReq.price)
                        sumQuantity += tmpReq.quantity;
                }
                if (sumQuantity > firstSellReq.quantity) {
                    int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                    firstBuyReq.cstmr.fund -= moneyExchanged;
                    firstSellReq.cstmr.fund += moneyExchanged;
                    firstSellReq.quantity -= firstSellReq.quantity;
                    req_.symbl.getBuyer().remove(firstBuyReq);
                    firstBuyReq.cstmr.done.add(firstBuyReq);
                    firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                    return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
                }
            }
        }
        return "nothing Dealed!";
    }
}
