package stockdataa.model;

/**
 * Stock portfolio interface that is for implementing save and add stock in a portfolio.
 */
public interface StockPortfolio {

  //TODO: Define
  int size();

  //TODO: Define
  double[] getTotalValues(String date);

  //TODO: Define
  void addStock(String stock, String data, String shares, boolean onlyInts);


  int getNumStocks();

  /**
   * This prints the portfolio display for a given date. The date can
   * be "current" for the latest available date or in the format
   * YYYY-MM-DD. If a date is given and data for an individual stock
   * cannot be found (due to it not yet existing) then the output says
   * NO DATA for each column in the respective row. If the date value is
   * current then it fetches the data for the last available date for the stock
   * For each stock the PRICE columns reflect the price for a single stock in the
   * portfolio while for the bottom row those columns reflect a total value by the
   * formula [SUM (Shares_i * Price_i)].
   *
   * @param date the date to find the value at.
   * @return a table formatted string displaying the portfolio.
   */
  String printPortfolioAt(String date);

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

  /**
   * This outputs a graph or portfolio performance over time
   * @param date1 the performance start date in MM-DD-YYYY format
   * @param date2 the performance end date in MM-DD-YYYY format
   * @throws IllegalArgumentException when date string provided are invalid.
   */
  String portfolioPerformance(String date1, String date2) throws IllegalArgumentException;

}
