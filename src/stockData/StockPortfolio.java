package stockData;

import java.util.ArrayList;

public interface StockPortfolio {
  int getValueAt(String Date, String Stock);

 void addStock(String Stock, int shares);



}
