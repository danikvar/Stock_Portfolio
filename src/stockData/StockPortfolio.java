package stockData;

import java.util.ArrayList;

public interface StockPortfolio {
  int getValueAt(String Date, String Stock);

 ArrayList<Stock1> addStock(String Stock, int shares, String date);


}
