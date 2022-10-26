import java.util.ArrayList;

public class Portfolio implements StockPortfolio{
  String Stock;
  ArrayList<Stock1> StockList;

  public Portfolio(){
    ArrayList<Stock1> StockList= new ArrayList<>();
    this.StockList = StockList;
  }

  @Override
  public ArrayList<Stock1> addStock(String Stock, int shares, String date) {
    Stock1 in_1 = new Stock1(Stock,shares,date);
    StockList.add(in_1);
    return StockList;
  }

}
 class Stock1 {
  private int shares;
  private String ticker;

  private String date;

  public Stock1(String ticker,int shares,String date){
    this.shares = shares;
    this.ticker = ticker;
    this.date  = date;
  }
}

