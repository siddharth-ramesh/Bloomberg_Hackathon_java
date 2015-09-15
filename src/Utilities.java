import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Utilities {

	public static double parse_MY_CASH() throws Exception {
		String cash = Operations.call_MY_CASH();
		
		String cashSplit[] = cash.split(" ");
		
		if( !cashSplit[0].equals( "MY_CASH_OUT" ) )
			throw new Exception("Incorrect Securities input string format.");
		
		return Double.parseDouble(cashSplit[1]);
	}
	
	public static double parse_MY_SECURITIES_getDividentRatio( String ticker ) throws Exception {
		String securities = Operations.call_MY_SECURITIES();
		
		String securitiesSplit[] = securities.split(" ");
		
		if( !securitiesSplit[0].equals( "MY_SECURITIES_OUT" ) )
			throw new Exception("Incorrect My_Securities input string format.");
		
		int counter = 1;
		
		while(counter < securitiesSplit.length) {
			if( counter+2 >= securitiesSplit.length ) {
				counter += 3;
				break;
			}
			if((securitiesSplit[counter+0]).equals(ticker)){
				if(securitiesSplit[counter+2].split("E").length == 1){
					return Double.parseDouble(securitiesSplit[counter+2]);
				} else {
					return (Double.parseDouble(securitiesSplit[counter+2].split("E")[0])  +  Double.parseDouble(securitiesSplit[counter+2].split("-")[1]));
				}
			}
			
			counter+=3;
		}
		
		return 0.0;
	}
	
	public static int parse_MY_SECURITIES_getNumOfStocks( String ticker ) throws Exception {
		String securities = Operations.call_MY_SECURITIES();
		
		String securitiesSplit[] = securities.split(" ");
		
		if( !securitiesSplit[0].equals( "MY_SECURITIES_OUT" ) )
			throw new Exception("Incorrect My_Securities input string format.");
		
		int counter = 1;
		
		while(counter < securitiesSplit.length) {
			if( counter+2 >= securitiesSplit.length ) {
				counter += 3;
				break;
			}
			if((securitiesSplit[counter+0]).equals(ticker)){
				return Integer.parseInt(securitiesSplit[counter+1]);
			}
			
			counter+=3;
		}
		
		return -1;
	}
	
	public static List<Stock> parse_MY_SECURITIES() throws Exception {
		List<Stock> myStocks = new ArrayList<Stock>();
		String securities = Operations.call_MY_SECURITIES();
		
		String securitiesSplit[] = securities.split(" ");
		
		if( !securitiesSplit[0].equals( "MY_SECURITIES_OUT" ) )
			throw new Exception("Incorrect My_Securities input string format.");
		
		int counter = 1;
		int stockCounter = 0;
		while(counter < securitiesSplit.length) {
			if( counter+2 >= securitiesSplit.length ) {
				counter += 3;
				break;
			}
			if(Integer.parseInt(securitiesSplit[counter+1]) > 0){
				Stock myStock = new Stock();
				myStock.setCounter(stockCounter);
				myStock.setTicker(securitiesSplit[counter+0]);
				myStock.setQuantity(Integer.parseInt(securitiesSplit[counter+1]));
				myStocks.add(myStock);
			}
			
			counter+=3;
			stockCounter++;
		}
		
		return myStocks;
	}
	
	public static List<Stock> parse_SECURITIES() throws Exception {
		String securities = Operations.call_SECURITIES();
		
		List<Stock> myStocks = new ArrayList<Stock>();
		
		String securitiesSplit[] = securities.split(" ");
		
		if( !securitiesSplit[0].equals( "SECURITIES_OUT" ) ){
			throw new Exception("Incorrect Securities input string format.");
		}
		
		int counter = 1;
		
		while(counter < securitiesSplit.length) {
			if( counter+3 >= securitiesSplit.length ) {
				counter += 4;
				break;
			}					
			
			Stock myStock = new Stock();
				
			myStock.setTicker(securitiesSplit[counter+0]);
			myStock.setNetWorth(Double.parseDouble(securitiesSplit[counter+1]));
			myStock.setDividentRatio(Double.parseDouble(securitiesSplit[counter+2]));
			myStock.setVolatility(Double.parseDouble(securitiesSplit[counter+3]));				
			
			myStocks.add(myStock);
			counter += 4;
		}
		
		return myStocks;
	}
	
	public static List<Order> getBidOrders( String ticker ) throws IOException,Exception {
		List<Order> sellOrders = new ArrayList<Order>();
		
		String orders = Operations.call_ORDERS( ticker );
		
		String ordersSplit[] = orders.split(" ");
		
		if( !ordersSplit[0].equals( "SECURITY_ORDERS_OUT" ) )
			throw new Exception("Incorrect Orders input string format.");
		
		int counter = 1;
		
		while(counter < ordersSplit.length) {
			
			if( counter+3 >= ordersSplit.length ) {
				counter += 4;
				break;
			}	
			
			if(ordersSplit[counter+0].equals("BID")){
				Order myOrder = new Order( ordersSplit[counter+1], Double.parseDouble(ordersSplit[counter+2]), Integer.parseInt(ordersSplit[counter+3]) );
				sellOrders.add(myOrder);
			}
			
			counter += 4;
			
		}
		
		return sellOrders;
	}
	
	public static List<Order> getAskOrders( String ticker ) throws IOException,Exception {
		List<Order> askOrders = new ArrayList<Order>();
		
		String orders = Operations.call_ORDERS( ticker );
		
		String ordersSplit[] = orders.split(" ");
		
		if( !ordersSplit[0].equals( "SECURITY_ORDERS_OUT" ) )
			throw new Exception("Incorrect Orders input string format.");
		
		int counter = 1;
		
		while(counter < ordersSplit.length) {
			
			if( counter+3 >= ordersSplit.length ) {
				counter += 4;
				break;
			}	
			
			if(ordersSplit[counter+0].equals("ASK")){
				Order myOrder = new Order( ordersSplit[counter+1], Double.parseDouble(ordersSplit[counter+2]), Integer.parseInt(ordersSplit[counter+3]) );
				askOrders.add(myOrder);
			}
			
			counter += 4;
			
		}
		
		return askOrders;
	}
	
	public static void purchase1Stock( String ticker ) throws IOException,Exception {
		List<Order> askOrders = getAskOrders( ticker );
		
		Collections.sort(askOrders);
		
		Operations.call_BID(ticker, askOrders.get(0).price, 1);
		
	}
	
	public static void greedySell( String ticker, int quantity ) throws IOException,Exception {
		List<Order> askOrders = getAskOrders( ticker );
		
		Collections.sort(askOrders);
		
		double lowestAsk = askOrders.get(0).price;
		double greedyAsk = lowestAsk * 1.001;
		
		Operations.call_ASK(ticker, greedyAsk, quantity);
	}		
	
	public static double greedyPurchase( String ticker, double cashToSpend ) throws IOException,Exception {
		List<Order> bidOrders = getBidOrders( ticker );
		
		Collections.sort(bidOrders);
		Collections.reverse(bidOrders);
		
		double highestBid = bidOrders.get(0).price;
		double greedyBid = highestBid * 0.999;
		
		int numOfStocksToBuy = (int)Math.floor(cashToSpend/greedyBid);
		
		Operations.call_BID(ticker, greedyBid, numOfStocksToBuy);
		
		return greedyBid;
	}
	
	/**
	 * returns average cost of all purchased stocks
	 * 
	 */
	public static double purchaseStock( String ticker, double cashToSpend ) throws IOException,Exception {
		List<Order> askOrders = getAskOrders( ticker );
		
		Collections.sort(askOrders);
		
		double spendingCash = cashToSpend;
		double moneySpent = 0.0;
		int numOfStocksBought = 0;
		
		for( Order order : askOrders ) {
			if( (order.number * order.price) < spendingCash ) { //we can buy all
				Operations.call_BID(order.ticker, order.price, order.number);
				spendingCash = spendingCash - (order.price*order.number);
				moneySpent += (order.price*order.number);
				numOfStocksBought += order.number;
				
			} else { //can only buy some of
				int numToBuy = (int)Math.floor(spendingCash/order.price);
				Operations.call_BID(order.ticker, order.price, numToBuy);
				spendingCash = spendingCash - (order.price*numToBuy);
				moneySpent += (order.price*numToBuy);
				numOfStocksBought += numToBuy;
			}
		}
		
		return (moneySpent/numOfStocksBought);
	}
	
	public static void sellStock( String ticker, int numOfShares ) throws IOException,Exception {
		List<Order> bidOrders = getBidOrders( ticker );
		
		Collections.sort(bidOrders);
		Collections.reverse(bidOrders);
		
		int numOfRemainingShares = numOfShares;
		
		for( Order order : bidOrders ) {
			if( order.number < numOfRemainingShares ) { //we can buy all
				Operations.call_ASK(ticker, order.price, order.number);
				numOfRemainingShares = numOfRemainingShares - order.number;
			} else { //can only buy some of
				Operations.call_ASK(ticker, order.price, numOfRemainingShares);
			}
		}
		
	}
	
	public static void sellStock( String ticker, int numOfShares , double price) throws IOException,Exception {
		Operations.call_ASK(ticker, price, numOfShares);		
	}

}
