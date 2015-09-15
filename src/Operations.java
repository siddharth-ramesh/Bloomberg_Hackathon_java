import java.io.IOException;


public class Operations {
	
	//<host> <port> <user> <password> <command...>
	static final String host = "codebb.cloudapp.net";
	static final String port = "17429";
	static final String user = "Here_for_Beer";
	static final String password = "johnsmith";
	
	static String exchangeClientCallFormat[] = {host, port, user, password, ""};
	
	static ExchangeClient mySClient;
	
	public static void setClient(ExchangeClient myClient){
		mySClient = myClient;
	}
	
	public static String call_MY_CASH() throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "MY_CASH";
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_MY_SECURITIES() throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "MY_SECURITIES";
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_MY_ORDERS() throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "MY_ORDERS";
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_SECURITIES() throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "SECURITIES";
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_ORDERS( String ticker ) throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "ORDERS " + ticker;
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_BID( String ticker, double price, int shares ) throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "BID " + ticker + " " + price + " " + shares;
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_ASK( String ticker, double price, int shares ) throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "ASK " + ticker + " " + price + " " + shares;
		return mySClient.call(exchangeClientCall);
	}
	
	public static String call_CLEAR_BID( String ticker ) throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "CLEAR_BID " + ticker;
		return mySClient.call(exchangeClientCall);
	}

	public static String call_CLEAR_ASK( String ticker ) throws IOException{
		String exchangeClientCall[] = exchangeClientCallFormat;
		exchangeClientCall[ exchangeClientCall.length - 1 ] = "CLEAR_ASK " + ticker;
		return mySClient.call(exchangeClientCall);
	}
}
