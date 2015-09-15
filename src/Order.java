
class Order implements Comparable<Order> {
	
	String ticker;
    double price;
    int number;

    public Order( String ticker, double price, int number ) {
        this.ticker = ticker;
        this.price = price;
        this.number = number;
    }

    @Override
    public int compareTo(Order o) {
        return price < o.price ? -1 : price > o.price ? 1 : 0;
    }
}