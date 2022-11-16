package stockdataa;

/**
 * Stock portfolio interface that is for implementing save and add stock in a portfolio.
 */
public interface StockPortfolio {

  double[] getTotalValues(String date);

  void addStock(String stock, int shares, String data);

  int getNumStocks();

  // Transforms Portfolio to JSON string representation
  // for easy writing
  String portToJSON();

  /**
   * This saves the current portfolio to the users chosen portfolio directory.
   * @param fileName the name of the file to save portfolio as.
   * @param userName the name of the user to save to.
   * @throws IllegalArgumentException when filename is invalid.
   */
  void save(String fileName, String userName) throws IllegalArgumentException;


}
