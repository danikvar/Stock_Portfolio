package stockdataa.model;

import java.time.LocalDate;
import java.util.Map;

import stockdataa.Pair;

public interface Operations {

  String setDCA(String daysStr, String startDate,
                String endDate, String amountStr,
                String CommStr, String propMapStr) throws IllegalArgumentException;

  void sellStock(String ticker, String shares,
                 String CF, String dateStr, boolean onlyInts)
          throws IllegalArgumentException;

  String getUser();

  String getPort();

  String buyStock(String ticker, String shares, boolean onlyInts);

  String getCostBasis(String date);

  String loadPort(String portName);

  double[] getData(String date);

  double getShares();

  String getTicker();


  String saving();

  String sharesToJSON();

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

  /**
   * Set the username for the current operations object.
   * @param userName the current user for loading/saving
   */
  void setUser(String userName);

  /**
   * Set the directory for the current user's portfolios in the operation.
   * @param filePath the portfolio directory for the current user.
   */
  void setPath(String filePath);

  /**
   *  Gets the current path to save the model stored in the object.
   * @return The save path.
   */
  String getPath();

  /**
   * Returns a map of dates for a performance graph (x-values) and the corresponding
   * value of the portfolio on that date (y-values) to use in plotting performance.
   * @param sDate The start date to show performance
   * @param eDate The end date to show performance
   * @return a map of dates (x) to the corresponding portfolio value (y)
   */
  Map<LocalDate, Double> getPortPerfData(String sDate, String eDate);

  /**
   * Returns a pair of min and max values for plotting
   * @param data A map of x,y values for the plot
   * @return the range for the y-axis
   */
  Pair<Double, Double> getMinMax(Map<LocalDate, Double> data);
}
