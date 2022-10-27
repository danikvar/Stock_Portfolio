package stockData;

import java.util.ArrayList;

public class Portfolio implements StockPortfolio{
  ArrayList<Stock1> StockList;

  public Portfolio(){
    ArrayList<Stock1> StockList= new ArrayList<>();
    this.StockList = StockList;
  }

  /**
   * I changed this method signature to be void since it should just change the object.
   * There is no reason to return something every time we add a stock.
   * @param Stock
   * @param shares
   */
  @Override
  public void addStock(String Stock, int shares) {
    Stock1 in_1 = new Stock1(Stock,shares);
    StockList.add(in_1);
  }

  public int size() {
    return StockList.size();
  }

  @Override
  public int getValueAt(String Date, String Stock) {
    return 0;
  }
}

