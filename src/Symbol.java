import java.util.*;

public class Symbol {

	String name;

	private PriorityQueue<Request> seller;
	private PriorityQueue<Request> buyer;
	
	public Symbol(String name_){
		name = name_;
		seller = new PriorityQueue<Request> (0,reqSellComparator);
		buyer = new PriorityQueue<Request> (0,reqBuyComparator);
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

    public String add_sellReq(Request req_) {
        seller.add(req_);

        //if (req_.equals(seller.iterator().next()))

        if (buyer.iterator().next().price < seller.iterator().next().price) {

        }
        return "";
    }

    public String add_buyReq(Request req_) {
        buyer.add(req_);

        return "";
    }
}
