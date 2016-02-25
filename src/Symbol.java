import java.util.*;

public class Symbol {

	String name;

	private PriorityQueue<Request> seller;
	private PriorityQueue<Request> buyer;
	
	public Symbol(String name_){
		name = name_;
		seller = new PriorityQueue<Request> (1,reqSellComparator);
		buyer = new PriorityQueue<Request> (1,reqBuyComparator);
	}

    public  PriorityQueue<Request> getSeller(){return seller;}
    public  PriorityQueue<Request> getBuyer(){return buyer;}

    public Comparator<Request> reqSellComparator = new Comparator<Request>() {
        @Override
        public int compare(Request r1, Request r2) {
            return r1.price - r2.price;
        }
    };

    public Comparator<Request> reqBuyComparator = new Comparator<Request>() {
        @Override
        public int compare(Request r1, Request r2) {
            return r2.price - r1.price;
        }
    };

    public String add_sellReqGTC(Request req_) {
        seller.add(req_);

        if (!(req_.equals(seller.iterator().next())))
            return "Order is queued";
        else {
            StringBuilder sb = new StringBuilder("");
            while (true) {
                String current = doDealGTC();
                if (current.equals("nothing Dealed!"))
                    break;
                else {
                    sb.append(current + "\n");
                }
            }
            String result = sb.toString();
            if (result.equals(""))
                return "Order is queued";
            else
                return result;
        }
    }

    public String add_sellReqIOC(Request req_) {
        seller.add(req_);

        if (!(req_.equals(seller.iterator().next()))) {
            seller.remove(req_);
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
            String result = doDealIOC();
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

    public String add_sellReqMPO(Request req_) {
        req_.price=0;
        seller.add(req_);

        String result = doDealMPO(req_);
        if (result.equals("nothing Dealed!")) {
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
            req_.cstmr.done.add(req_);
            return result;
        }
    }

    public String add_buyReqGTC(Request req_) {
        buyer.add(req_);

        if (!(req_.equals(buyer.iterator().next())))
            return "Order is queued";
        else {
            StringBuilder sb = new StringBuilder("");
            while (true) {
                String current = doDealGTC();
                if (current.equals("nothing Dealed!"))
                    break;
                else {
                    sb.append(current + "\n");
                }
            }
            String result = sb.toString();
            if (result.equals(""))
                return "Order is queued";
            else
                return result;
        }
    }

    public String add_buyReqIOC(Request req_) {
        buyer.add(req_);

        if (!(req_.equals(buyer.iterator().next()))) {
            buyer.remove(req_);
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
            String result = doDealIOC();
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

    public String add_buyReqMPO(Request req_) {
        if (req_.forSale)
            req_.price=0;
        buyer.add(req_);

        String result = doDealMPO(req_);
        if (result.equals("nothing Dealed!")) {
            req_.cstmr.refused.add(req_);
            return "Order is declined";
        }
        else {
            req_.cstmr.done.add(req_);
            return result;
        }
    }

    public int MPO_quantityPrice(int quantity_) {
        int sumQuantity = 0;
        int sumPrice = 0;
        for (Request buyReq : buyer) {
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

    public String doDealMPO(Request req_) {
            if (MPO_quantityPrice(req_.quantity) == -1)
                return "nothing Dealed!";
            else {
                StringBuilder sb = new StringBuilder("");
                while (true) {
                    if (!req_.forSale)
                        req_.price=seller.iterator().next().price;
                    String current = doDealGTC();
                    if (current.equals("nothing Dealed!"))
                        break;
                    else {
                        sb.append(current + "\n");
                    }
                }
                return sb.toString();
            }

    }

    public String doDealGTC() {
        Request firstBuyReq = buyer.iterator().next();
        Request firstSellReq = seller.iterator().next();
        if (firstBuyReq.price >= firstSellReq.price)
        {
            if (firstBuyReq.quantity > firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                firstBuyReq.quantity -= firstSellReq.quantity;
                seller.remove(firstSellReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                firstSellReq.cstmr.inAct.remove(firstSellReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else if (firstBuyReq.quantity == firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                buyer.remove(firstBuyReq);
                seller.remove(firstSellReq);
                firstBuyReq.cstmr.done.add(firstBuyReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                firstSellReq.cstmr.inAct.remove(firstSellReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                firstSellReq.quantity -= firstSellReq.quantity;
                buyer.remove(firstBuyReq);
                firstBuyReq.cstmr.done.add(firstBuyReq);
                firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            }
        } else
        return "nothing Dealed!";
    }

    public String doDealIOC() {
        Request firstBuyReq = buyer.iterator().next();
        Request firstSellReq = seller.iterator().next();
        if (firstBuyReq.price > firstSellReq.price)
        {
            if (firstBuyReq.quantity > firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                firstBuyReq.quantity -= firstSellReq.quantity;
                seller.remove(firstSellReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else if (firstBuyReq.quantity == firstSellReq.quantity) {
                int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                firstBuyReq.cstmr.fund -= moneyExchanged;
                firstSellReq.cstmr.fund += moneyExchanged;
                buyer.remove(firstBuyReq);
                seller.remove(firstSellReq);
                firstBuyReq.cstmr.done.add(firstBuyReq);
                firstSellReq.cstmr.done.add(firstSellReq);
                firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
            } else {
                int sumQuantity = 0;
                for (Request tmpReq : buyer) {
                    if (tmpReq.price > firstSellReq.price)
                        sumQuantity += tmpReq.quantity;
                }
                if (sumQuantity > firstSellReq.quantity) {
                    int moneyExchanged = (firstSellReq.quantity) * (firstBuyReq.price);
                    firstBuyReq.cstmr.fund -= moneyExchanged;
                    firstSellReq.cstmr.fund += moneyExchanged;
                    firstSellReq.quantity -= firstSellReq.quantity;
                    buyer.remove(firstBuyReq);
                    firstBuyReq.cstmr.done.add(firstBuyReq);
                    firstBuyReq.cstmr.inAct.remove(firstBuyReq);
                    return firstSellReq.cstmr.id + " sold " + firstSellReq.quantity + " shares of " + firstSellReq.symbl + " @" + firstBuyReq.price + " to " + firstBuyReq.cstmr.id;
                }
            }
        }
            return "nothing Dealed!";
    }

}
