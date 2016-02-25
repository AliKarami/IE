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

}
