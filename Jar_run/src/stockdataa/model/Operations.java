package stockdataa.model;

import java.time.LocalDate;
import java.util.Map;

import stockdataa.Pair;

/**
 * This is the interface which has all the methods used by the GUI.
 */

public interface Operations {

  /**
   * This is the method used to implement thr Dollar Cost Averaging.
   *
   * @param daysStr    is the number of days.
   * @param startDate  is the start date.
   * @param endDate    is the end date.
   * @param amountStr  is the amount to be invested.
   * @param commStr    is the commission fee.
   * @param propMapStr is the map of ticker symbol and weights.
   * @return the success message.
   * @throws IllegalArgumentException throws exception.
   */

  String setDCA(String daysStr, String startDate,
                String endDate, String amountStr,
                String commStr, String propMapStr) throws IllegalArgumentException;

  /**
   * This method is to implement the sell stocks method through GUI.
   *
   * @param ticker   is the stock symbol.
   * @param shares   is the volume of shares.
   * @param cf       is the commission fee.
   * @param dateStr  is the sell date.
   * @param onlyInts is a boolen to check the volume.
   * @throws IllegalArgumentException throws exception.
   */

  void sellStock(String ticker, String shares,
                 String cf, String dateStr, boolean onlyInts)
          throws IllegalArgumentException;

  /**
   * This method is to get user through GUI.
   *
   * @return the username.
   */

  String getUser();

  /**
   * This is to get the name of the portfolio.
   *
   * @return the portfolio name.
   */

  String getPort();

  /**
   * This method is to buy stock through GUI.
   *
   * @param ticker   is the stock symbol.
   * @param shares   is the volume of shares.
   * @param onlyInts is the booelean to check volume.
   * @return the buy message.
   */

  String buyStock(String ticker, String shares, boolean onlyInts);

  /**
   * This method is used to get the cost basis through GUI.
   *
   * @param date is the date on which cost basis needs to be calculated.
   * @return the cost basis.
   */

  String getCostBasis(String date);

  /**
   * This method is used to load the portfolio.
   *
   * @param portName is the name of the portfolio.
   * @return the details of the portfolio.
   */

  String loadPort(String portName);

  /**
   * This method is used to get the data.
   *
   * @param date is the date on which data needs to be fetched.
   * @return the details of the portfolio.
   */

  double[] getData(String date);

  /**
   * This method is used to get the shares from the user.
   *
   * @return the number shares.
   */

  double getShares();

  /**
   * This method is used to get the ticker symbol from the user.
   *
   * @return the ticker symbol obtained from the user.
   */

  String getTicker();

  /**
   * This method is used to save the portfolio.
   *
   * @return the success message of saving.
   */


  String saving();


  String sharesToJSON();

  /**
   * This method is used to get the size.
   *
   * @return the size.
   */

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
   *
   * @param fileName the name of the file to save portfolio as.
   * @param userName the name of the user to save to.
   * @throws IllegalArgumentException when filename is invalid.
   */
  void save(String fileName, String userName) throws IllegalArgumentException;

  /**
   * This outputs a graph or portfolio performance over time.
   *
   * @param date1 the performance start date in MM-DD-YYYY format
   * @param date2 the performance end date in MM-DD-YYYY format
   * @throws IllegalArgumentException when date string provided are invalid.
   */
  String portfolioPerformance(String date1, String date2) throws IllegalArgumentException;

  /**
   * Set the username for the current operations object.
   *
   * @param userName the current user for loading/saving
   */
  void setUser(String userName);

  /**
   * Set the directory for the current user's portfolios in the operation.
   *
   * @param filePath the portfolio directory for the current user.
   */
  void setPath(String filePath);

  /**
   * Gets the current path to save the model stored in the object.
   *
   * @return The save path.
   */
  String getPath();

  /**
   * Returns a map of dates for a performance graph (x-values) and the corresponding
   * value of the portfolio on that date (y-values) to use in plotting performance.
   *
   * @param sDate The start date to show performance
   * @param eDate The end date to show performance
   * @return a map of dates (x) to the corresponding portfolio value (y)
   */
  Map<LocalDate, Double> getPortPerfData(String sDate, String eDate);

  /**
   * Returns a pair of min and max values for plotting.
   *
   * @param data A map of x,y values for the plot
   * @return the range for the y-axis
   */
  Pair<Double, Double> getMinMax(Map<LocalDate, Double> data);
}
