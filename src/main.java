import java.util.List;


public class main {

	//<host> <port> <user> <password> <command...>
	static final String host = "codebb.cloudapp.net";
	static final String port = "17429";
	static final String user = "Here_for_Beer";
	static final String password = "johnsmith";
		
	static String exchangeClientCallFormat[] = {host, port, user, password, ""};
	
	static double ourCash = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExchangeClient myClient = new ExchangeClient();
		try {
			myClient = new ExchangeClient(exchangeClientCallFormat);
			Operations.setClient(myClient);
			
			List<Stock> myStocks = Utilities.parse_SECURITIES();
			int sellingStocks[] = new int[myStocks.size()];
			
			while(true) {
				System.out.println("Trapping under progress.\n");
			ourCash = Utilities.parse_MY_CASH();
			
			for(int i = 0; i < myStocks.size(); i++) {
				Operations.call_CLEAR_ASK(myStocks.get(i).getTicker());
			}
			
			for(int i = 0; i < myStocks.size(); i++) {
				Utilities.greedyPurchase(myStocks.get(i).getTicker(), ourCash/10);
			}
			
			for(int i = 0; i < 3; i++) {
				Thread.sleep(500);
				List<Stock> boughtStocks = Utilities.parse_MY_SECURITIES();
				if(boughtStocks.size() > 0) {
					Thread.sleep(1000);
					for(Stock stock : boughtStocks) {
						sellingStocks[stock.getCounter()] = stock.getQuantity();
						Utilities.greedySell(stock.getTicker(), stock.getQuantity());
					}
				}
			}
			
			for(int i = 0; i < myStocks.size(); i++) {
				Operations.call_CLEAR_BID(myStocks.get(i).getTicker());
			}
			}
			
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{ myClient.close(); } catch(Exception e){}
		}
	}

}
