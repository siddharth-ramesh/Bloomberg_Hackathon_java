import java.util.List;


public class oldmain {

	static double ourCash = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<Stock> myStocks = Utilities.parse_SECURITIES();
		
			int buyCounter = 0;
			int sellCounter = 0;
			
			while( true ) {
				
				///////////////////////////////////buy phase///////////////////
				
				ourCash = Utilities.parse_MY_CASH();
				double halfOurCash = ourCash/2;
				Stock firstStock = myStocks.get(buyCounter);
				firstStock.setAverageBoughtPrice(Utilities.purchaseStock(firstStock.getTicker(), halfOurCash));
				System.out.println(Operations.call_MY_SECURITIES());
				System.out.println(Operations.call_MY_CASH());
				ourCash = Utilities.parse_MY_CASH();
				
				if( ourCash > halfOurCash*1.5 ) {
					Thread.sleep(500);
					double cashToSpend = ourCash - halfOurCash;
					Utilities.purchaseStock(firstStock.getTicker(), cashToSpend);
					System.out.println(Operations.call_MY_SECURITIES());
					ourCash = Utilities.parse_MY_CASH();
				}
				double firstStock_initialDividentRatio = Utilities.parse_MY_SECURITIES_getDividentRatio(firstStock.getTicker());
				
				buyCounter++;
				buyCounter = buyCounter%(myStocks.size());
				Stock secondStock = myStocks.get(buyCounter);
				secondStock.setAverageBoughtPrice(Utilities.purchaseStock(secondStock.getTicker(), halfOurCash));
				System.out.println(Operations.call_MY_SECURITIES());
				System.out.println(Operations.call_MY_CASH());
				ourCash = Utilities.parse_MY_CASH();
				
				if( ourCash > halfOurCash*0.5 ) {
					Thread.sleep(500);
					double cashToSpend = ourCash;
					Utilities.purchaseStock(secondStock.getTicker(), cashToSpend);
					System.out.println(Operations.call_MY_SECURITIES());
				}
				
				double secondStock_initialDividentRatio = Utilities.parse_MY_SECURITIES_getDividentRatio(secondStock.getTicker());
				
				buyCounter++;
				buyCounter = buyCounter%(myStocks.size());
				
				///////////////////////////////////sell phase///////////////////
				System.out.println("IN SELL PHASE");
				int firstStockCounter = 0;
				boolean firstStockExists =  true;
				int secondStockCounter = 0;
				boolean secondStockExists =  true;
				for(int j = 0; j < 10; j++){
					System.out.println("ITERATION of FOR LOOP");
					Thread.sleep(1000);
					if(firstStockExists){
						double firstStock_newDividentRatio = Utilities.parse_MY_SECURITIES_getDividentRatio(firstStock.getTicker());
						if( firstStock_initialDividentRatio > firstStock_newDividentRatio) {
							//on a downswing, increase firstStockCounter
							firstStockCounter++;
						} else {
							firstStockCounter = 0;
						}
						
						if(firstStockCounter >= 5) {
							Utilities.sellStock(firstStock.getTicker(), Utilities.parse_MY_SECURITIES_getNumOfStocks(firstStock.getTicker()));
							firstStockExists = false;
							System.out.println("SOLD FIRST STOCK");
						}
						firstStock_initialDividentRatio = firstStock_newDividentRatio;
					}
					
					if(secondStockExists) {
						double secondStock_newDividentRatio = Utilities.parse_MY_SECURITIES_getDividentRatio(secondStock.getTicker());
						if( secondStock_initialDividentRatio > secondStock_newDividentRatio) {
							//on a downswing, increase firstStockCounter
							secondStockCounter++;
						} else {
							secondStockCounter = 0;
						}
						
						if( secondStockCounter >= 5){
							Utilities.sellStock(secondStock.getTicker(), Utilities.parse_MY_SECURITIES_getNumOfStocks(secondStock.getTicker()));
							secondStockExists = false;
							System.out.println("SOLD SECOND STOCK");
						}
						secondStock_initialDividentRatio = secondStock_newDividentRatio;
					}
				}
				
				if(firstStockExists){
					Utilities.sellStock(firstStock.getTicker(), Utilities.parse_MY_SECURITIES_getNumOfStocks(firstStock.getTicker()));
					firstStockExists = false;
					System.out.println("SOLD FIRST STOCK");
				}
				
				if(secondStockExists){
					Utilities.sellStock(secondStock.getTicker(), Utilities.parse_MY_SECURITIES_getNumOfStocks(secondStock.getTicker()));
					secondStockExists = false;
					System.out.println("SOLD SECOND STOCK");
				}
				
				
				////////////////////////////////////////////////////////////////
				
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
