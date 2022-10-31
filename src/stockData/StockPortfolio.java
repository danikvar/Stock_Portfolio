package stockData;

import java.util.ArrayList;

public interface StockPortfolio {

  double[] getTotalValues(String Date);
  void addStock(String Stock, int shares, String data);
  int getNumStocks();

  // Transforms Portfolio to JSON string representation
  // for easy writing
  String portToJSON();



}
