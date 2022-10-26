package stockData;

import java.util.ArrayList;

public class Portfolio implements StockPortfolio{
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

  @Override
  public int getValueAt(String Date, String Stock) {
    return 0;
  }
}

