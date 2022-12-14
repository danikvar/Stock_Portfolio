package stockdataa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import stockdataa.DataHelpers;
import stockdataa.Pair;

/**
 * This represents the class with all the methods used by the GUI.
 */

public class OperationsImpl implements Operations {

  private String user;
  private String currentPort;
  private SmartPortfolio model;
  private String saveDir;

  /**
   * This is the constructor for Operations Implementation.
   */
  public OperationsImpl() {
    this.user = "";
    this.currentPort = "";
    this.saveDir = "";
    this.model = new SmartPortfolio();
  }

  /**
   * Sets the Dollar Cost Averaging operation on the current portfolio and
   * outputs a message string informing about the transaction status.
   *
   * @param daysStr    The time span for each next operation.
   * @param startDate  The date to start the operation
   * @param endDate    The end date for the operation (can be blank)
   * @param amountStr  The amount ($) to invest each time
   * @param commStr    The commission fee for each transaction
   * @param propMapStr The weights/proportions of each stock investment
   * @return A string notifying about the success of the operation
   * @throws IllegalArgumentException for any incorrectly inputted data that cannot be
   *                                  parsed, improper weight values, or misaligned dates.
   */
  @Override
  public String setDCA(String daysStr, String startDate,
                       String endDate, String amountStr,
                       String commStr, String propMapStr) throws IllegalArgumentException {
    this.model.setDLCostAverage(daysStr, startDate, endDate, amountStr, commStr, propMapStr);

    boolean oneTime = Double.parseDouble(daysStr) == 0.0;
    StringBuilder output = new StringBuilder()
            .append("Successfully performed Dollar Cost Averaging for portfolio [")
            .append(this.currentPort)
            .append("] by User [")
            .append(this.user).append("] ");
    if (oneTime) {
      output.append(" one time");
    } else {
      output.append(" every [")
              .append(daysStr)
              .append("] days");
    }
    output.append(" starting on [")
            .append(startDate);

    if (endDate.isEmpty()) {
      output.append("] with no end date.");
    } else {
      output.append("] and ending on [")
              .append(endDate)
              .append("].");
    }

    return output.toString();
  }

  @Override
  public void sellStock(String ticker, String shares,
                        String cf, String dateStr, boolean onlyInts)
          throws IllegalArgumentException {

    this.model.sellStock(ticker, shares, cf, dateStr, onlyInts);
  }

  /**
   * Gets the current username for the portfolio.
   *
   * @return the portfolio username
   */
  @Override
  public String getUser() {
    if (user.equals("")) {
      throw new IllegalArgumentException("No user set.");
    }
    return user;
  }

  /**
   * Gets the current portfolio.
   *
   * @return the portfolio name
   */
  @Override
  public String getPort() {
    return currentPort;
  }


  @Override
  public String buyStock(String ticker, String shares, boolean onlyInts) {
    try {
      model.addStock(ticker, "API", shares, onlyInts);
      return "Successfully Bought Stock!";
    } catch (Exception e) {
      return e.toString();
    }
  }

  /**
   * Gets a string of the cost basis view of the portfolio.
   *
   * @param date the date to check the cost basis
   * @return a message with the cost basis
   */

  @Override
  public String getCostBasis(String date) {
    try {
      return model.printCostBasis(date);
    } catch (Exception e) {
      return e.toString();
    }
  }

  /**
   * This is to load a portfolio via a filepath.
   *
   * @param portName is the name of the portfolio.
   * @return the loaded portfolio.
   */
  @Override
  public String loadPort(String portName) {
    StringBuilder msg = new StringBuilder();
    try {
      if (portName.contains(File.separator)) {
        this.currentPort = portName.substring(portName.lastIndexOf(File.separator) + 1,
                portName.lastIndexOf('.'));
        this.model = (SmartPortfolio) DataHelpers.parsePortfolio(portName, 2);
        msg.append("Loading portfolio [");
        this.saveDir = portName;

      } else if (portName.contains(".")) {
        this.currentPort = portName.substring(0, portName.lastIndexOf('.'));
        this.model = (SmartPortfolio) DataHelpers.loadPortfolio(this.user, portName, 2);
        this.saveDir = DataHelpers.getPortfolioDir(this.user) + File.separator + portName;
        msg.append("Loading portfolio [");

      } else {
        this.currentPort = portName;
        this.saveDir = DataHelpers.getPortfolioDir(this.user) + File.separator
                + portName + ".json";
        this.model = new SmartPortfolio();
        msg.append("Creating new portfolio [")
        ;
      }

      msg.append(this.currentPort);
      msg.append("] for the User [");
      msg.append(this.user);
      msg.append("].");
    } catch (Exception e) {
      try {
        throw e;
      } catch (FileNotFoundException | ParseException ex) {
        throw new RuntimeException(ex);
      }
    }

    return msg.toString();
  }

  /**
   * This is to get data of the portfolio.
   *
   * @param date is the date on which data needs to be fetched.
   * @return the data fetched.
   */

  @Override
  public double[] getData(String date) {
    try {
      return this.model.getTotalValues(date);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * This is to get the number of shares from the user.
   *
   * @return the volume of shares.
   */

  @Override
  public double getShares() {
    try {
      return this.model.getNumStocks();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * This is to get the stock symbol from the user.
   *
   * @return the symbol.
   */

  @Override
  public String getTicker() {
    return null;
  }

  /**
   * Saves the model to the current set save path.
   *
   * @return String status message of saving.
   */
  @Override
  public String saving() {
    if (this.saveDir.isEmpty()) {
      return "Please create or load a model before saving.";
    }
    try {
      model.saveDirect(this.saveDir);
      return "Successfully saved model to path: " + this.saveDir;
    } catch (RuntimeException e) {
      return e.toString();
    }

  }

  @Override
  public String sharesToJSON() {
    return null;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public double[] getTotalValues(String date) {
    return new double[0];
  }

  @Override
  public void addStock(String stock, String data, String shares, boolean onlyInts) {
    //Empty

  }

  @Override
  public int getNumStocks() {
    return 0;
  }

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
  @Override
  public String printPortfolioAt(String date) throws IllegalArgumentException {
    return model.printPortfolioAt(date);
  }

  @Override
  public String portToJSON() {
    return null;
  }

  /**
   * This saves the current portfolio to the users chosen portfolio directory.
   *
   * @param fileName the name of the file to save portfolio as.
   * @param userName the name of the user to save to.
   * @throws IllegalArgumentException when filename is invalid.
   */
  @Override
  public void save(String fileName, String userName) throws IllegalArgumentException {

    try {
      this.model.save(fileName, userName);
    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * This outputs a graph or portfolio performance over time.
   *
   * @param date1 the performance start date in MM-DD-YYYY format
   * @param date2 the performance end date in MM-DD-YYYY format
   * @throws IllegalArgumentException when date string provided are invalid.
   */
  @Override
  public String portfolioPerformance(String date1, String date2) throws IllegalArgumentException {
    try {
      return this.model.portfolioPerformance(date1, date2);
    } catch (Exception e) {
      return e.toString();
    }
  }

  /**
   * Set the username for the current operations object.
   *
   * @param userName the current user for loading/saving
   */
  @Override
  public void setUser(String userName) {
    this.user = userName;
  }

  /**
   * Set the directory for the current user's portfolios in the operation.
   *
   * @param filePath the portfolio directory for the current user.
   */
  @Override
  public void setPath(String filePath) {
    this.saveDir = filePath;
  }


  /**
   * Gets the current save directory.
   *
   * @return the path to save the file.
   */
  @Override
  public String getPath() {
    return this.saveDir;
  }

  @Override
  public Map<LocalDate, Double> getPortPerfData(String sDate, String eDate) {
    return model.performanceMapping(sDate, eDate);
  }

  @Override
  public Pair<Double, Double> getMinMax(Map<LocalDate, Double> data) {
    Pair<Double, Double> ast1 = model.numAsterisk(data);
    double minVal = DataHelpers.round(ast1.b);
    double maxVal = DataHelpers.round(ast1.a + Collections.max(data.values()));
    return new Pair<Double, Double>(minVal, maxVal);
  }
}
