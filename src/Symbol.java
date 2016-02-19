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

    public String doDealGTC() {
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

    public String add_buyReq(Request req_) {
        buyer.add(req_);

        return "";
    }
}
