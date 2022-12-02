package stockdataa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import stockdataa.DataHelpers;
import stockdataa.Pair;

import static stockdataa.DataHelpers.getTickers;
import static stockdataa.DataHelpers.padLeft;
import static stockdataa.DataHelpers.padRight;

/**
 * Class that performs add and creation of a portfolio.
 */
public class SmartPortfolio implements StockPortfolio {
  private Map<String, SmartStock> stockList;
  private Set<String> stockTickers;

  // The amount to invest for DCAverage
  // Always 0 on creation of new stock
  private double dolCostVal;

  // Shows the last date DC Averaging was performed and
  // every X days to perform it. A new portfolio will just
  // have creation date and 0 to show no average needed.
  private int investDays;
  private double dcaComm;
  private LocalDate lastInvestDate;

  // Will be initialized to null so that DC averaging continues
  private LocalDate finDate;

  // Will be initialized to null
  private LocalDate startDate;



  /**
   * Portfolio constructor that initializes the portfolio with new list.
   */
  public SmartPortfolio() {
    this.stockList = new HashMap<String, SmartStock>();
    this.stockTickers = getTickers();
    this.dolCostVal = 0.0;
    this.lastInvestDate = null;
    this.investDays = 0;
    this.finDate = null;
    this.startDate = null;
    this.dcaComm = 0.0;
  }



  /**
   * USED FOR TESTING
   * Portfolio constructor that initializes a portfolio
   * with a given stock list.
   */
  public SmartPortfolio(Map<String, SmartStock> myList) {
    this.stockList = myList;
    this.stockTickers = getTickers();
    this.dolCostVal = 0.0;
    this.lastInvestDate = null;
    this.investDays = 0;
    this.finDate = null;
    this.startDate = null;
    this.dcaComm = 0.0;
  }


  /**
   * Sets the last date of DCA to the date given.
   * @param date the date DCA was performed
   * @throws IllegalArgumentException if date cannot be parsed
   */
  public void setLastInvestDate(String date) throws IllegalArgumentException {

    LocalDate investDate;
    try {
      investDate = LocalDate.parse(date);
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not parse last investment date");
    }
    this.lastInvestDate = investDate;
  }

  /**
   *  Setting all Dollar Cost Average variables from user input.
   *  Sets and checks all inputs then passes to the correct averaging function.
   * @param daysStr Every x days to perform DCA
   * @param startDate start date for DCA
   * @param endDate end date for DCA
   * @param amountStr  the amount to invest
   * @param commStr the commission fee for each investment
   * @param propMapStr a map of stock tickers and weights
   * @throws IllegalArgumentException If any input cannot be parsed or invalid values.
   */
  public void setDLCostAverage(String daysStr, String startDate,
                               String endDate, String amountStr,
                               String commStr, String propMapStr) throws IllegalArgumentException {

    double[] numVals = this.preCheckDCAStrings(daysStr, amountStr, commStr);
    int days = (int) numVals[0];
    double amount = numVals[1];
    double cf = numVals[2];
    this.lastInvestDate = null;



    // If days is not 0 then we must have a start date
    LocalDate start;
    try {
      start = LocalDate.parse(startDate);
    } catch (Exception e) {
      throw new IllegalArgumentException("Your start date could not be parsed.");
    }

    // WE are allowed to have new stocks if this is a single DCA
    Map<String,Double> propMap = this.parsePropMap(propMapStr, days == 0);
    if (days == 0) {
      this.dcaonce(amount, propMap, cf, start);
      return;
    }

    LocalDate checkStart = LocalDate.now().minusDays(days + 1);
    if (start.isBefore(checkStart)) {
      throw new IllegalArgumentException("Cannot start DCA more than 1 full timespan in the past");
    }

    if (endDate.isEmpty()) {
      this.setUnlimitedAverage(amount, propMap, cf, days, start);

    } else {
      LocalDate end;
      try {
        end = LocalDate.parse(endDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Your end date could not be parsed.");
      }

      // If this is not loading a file (the user is trying to set a new strategy)
      // then end dates before the current date are not allowed.
      if (end.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("Cannot have buy strategy ending in the past");
      }

      if (end.isBefore(start)) {
        throw new IllegalArgumentException("Cannot have buy strategy ending before start date.");
      }

      this.setLimitedAverage(amount, propMap, cf, days, start, end);

    }

    this.checkDCA();
  }

  /**
   *  Setting all Dollar Cost Average variables from loaded file input.
   *  Sets and checks all inputs then passes to the correct averaging function.
   * @param daysStr Every x days to perform DCA
   * @param startDate start date for DCA
   * @param endDate end date for DCA
   * @param amountStr  the amount to invest
   * @param commStr the commission fee for each investment
   * @param propMap a hashmap of stock tickers and weights
   * @throws IllegalArgumentException  If any input cannot be parsed or invalid values.
   */
  public void setDLCostAverage(String daysStr, String startDate,
                               String endDate, String amountStr,
                               String commStr, Map<String,Double> propMap)
          throws IllegalArgumentException {

    double[] numVals = this.preCheckDCAStrings(daysStr, amountStr, commStr);
    int days = (int) numVals[0];
    double amount = numVals[1];
    double cf = numVals[2];

    // We must always have a start date
    LocalDate start;
    try {
      start = LocalDate.parse(startDate);
    } catch (Exception e) {
      throw new IllegalArgumentException("Your start date could not be parsed.");
    }

    if (days == 0) {
      if ( start.isAfter(LocalDate.now())) {

        throw new IllegalArgumentException("For single time DCA transaction start date cannot"
                + " be in the future.");
      }
      this.dcaonce(amount, propMap, cf, start);
      return;
    }

    //System.out.println(endDate);
    if (endDate.isEmpty()) {
      this.setUnlimitedAverage(amount, propMap, cf, days, start);

    } else {
      LocalDate end;
      try {
        end = LocalDate.parse(endDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Your end date could not be parsed.");
      }

      if (end.isBefore(start)) {
        throw new IllegalArgumentException("Cannot have buy strategy ending before start date.");
      }

      this.setLimitedAverage(amount, propMap, cf, days, start, end);
    }

    this.checkDCA();
  }


  /**
   * Checks all the number strings to avoid duplicate code.
   * @param daysStr The timeframe
   * @param amountStr the amount to invest
   * @param commStr the Comm Fee
   * @return a double array with all 3 values
   */
  private double[] preCheckDCAStrings(String daysStr, String amountStr,
                                  String commStr) throws IllegalArgumentException {
    double days;
    double amount;
    double cf;

    try {
      days = Double.parseDouble(daysStr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Number of days must be a positive integer integer.");
    }

    try {
      amount = Double.parseDouble(amountStr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Amount invested must be a positive double.");
    }

    try {
      cf = Double.parseDouble(commStr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Commission fee must be a double.");
    }

    if (cf < 0) {
      throw new IllegalArgumentException("Commission cannot be negative");
    }

    if (amount < 0 || days < 0) {
      throw new IllegalArgumentException("Cannot have a negative time span or investment "
              + " < $0.");
    }

    return new double[]{days, amount, cf};

  }

  private void checkDCA() {

    // Check that we have a start date and are past it then we look at performing DCA
    LocalDate curDate = LocalDate.now();

    if (!Objects.isNull(this.startDate) && !this.startDate.isAfter(curDate)) {

      // If we have not made an initial investment do it on the start date
      if (Objects.isNull(this.lastInvestDate)) {
        this.doDCA(this.startDate);
        this.lastInvestDate = this.startDate;
      } else {
        // If we have an ending date
        LocalDate nextDate = this.lastInvestDate.plusDays(this.investDays);
        if (!Objects.isNull(this.finDate)) {

          // If the last investment + span is after the end reset all variables
          if (nextDate.isAfter(this.finDate)) {
            this.startDate = null;
            this.lastInvestDate = null;
            this.wipeProp();
          } else if (! LocalDate.now().isBefore(nextDate)) {
            this.doDCA(nextDate);
            this.lastInvestDate = nextDate;
          }
          // To avoid having no prices for the current date we only buy in the past.
        } else if (LocalDate.now().isAfter(nextDate)) {
          this.doDCA(nextDate);
          this.lastInvestDate = nextDate;
        }
      }
    }
  }

  private void wipeProp() {
    for (String key: stockList.keySet()) {
      SmartStock myStock = stockList.get(key);
      myStock.setProp(0.0);
      stockList.put(key, myStock);
    }
  }

  /**
   * Set all DCA variables with no end date.
   *
   * @param amount Amount to invest
   * @param propMap Map of tickers to proportions
   * @param cf the Commission Fee
   * @param start the start date for DCA
   * @throws IllegalArgumentException setting the proportion throws an error
   */
  private void setUnlimitedAverage(double amount, Map<String, Double> propMap,
                                   double cf, int days,
                                   LocalDate start) throws IllegalArgumentException {
    this.dcaComm = cf;
    this.startDate = start;
    this.dolCostVal = amount;
    this.investDays = days;
    this.finDate = null;
    for (String key: propMap.keySet()) {
      SmartStock myStock = this.stockList.get(key);
      myStock.setProp(propMap.get(key));
      this.stockList.put(key, myStock);
    }

  }

  /**
   * Set all DCA variables with an end date.
   *
   * @param amount Amount to invest
   * @param propMap Map of tickers to proportions
   * @param cf the Commission Fee
   * @param days The time span
   * @param start the start date for DCA
   * @param end the last date for DCA
   * @throws IllegalArgumentException setting the proportion throws an error
   */
  private void setLimitedAverage(double amount, Map<String, Double> propMap,
                                 double cf, int days, LocalDate start,
                                 LocalDate end) throws IllegalArgumentException {
    this.dcaComm = cf;
    this.startDate = start;
    this.dolCostVal = amount;
    this.finDate = end;
    this.investDays = days;

    for (String key: propMap.keySet()) {
      SmartStock myStock = this.stockList.get(key);
      myStock.setProp(propMap.get(key));
      this.stockList.put(key, myStock);
    }

  }

  /**
   * Performs DCA once and does not set any values. Can add new stocks to the list.
   * @param amount amount to invest
   * @param propMap map of stocks to weights
   * @param cf comission fee
   */
  private void dcaonce(double amount, Map<String, Double> propMap, double cf, LocalDate date) {

    this.dolCostVal = 0.0;
    this.lastInvestDate = null;
    this.investDays = 0;
    this.finDate = null;
    this.startDate = null;
    this.dcaComm = 0.0;

    for (String key: propMap.keySet()) {

      if (this.stockList.containsKey(key)) {

        this.dcaOnceExisting(amount, propMap, cf, date, key);
      }  else {
        this.dcaOnceNew(amount, propMap, cf, date, key);
      }

    }
  }

  /**
   * Performs DCA once and does not set any values. Add shares to already created stocks.
   * @param amount amount to invest
   * @param propMap map of stocks to weights
   * @param cf comission fee
   */
  private void dcaOnceExisting(double amount, Map<String, Double> propMap,
                               double cf, LocalDate date, String key) {
    SmartStock myStock = this.stockList.get(key);
    BigDecimal price = new BigDecimal(myStock.getPrice(date));
    BigDecimal spent = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(propMap.get(key)));
    double shares = spent.divide(price,2, RoundingMode.HALF_UP).doubleValue();
    shares = DataHelpers.round(shares);
    Pair<Double, Double> newAdd = new Pair<>(shares, cf);
    Map<LocalDate, Pair<Double, Double>> newMap = new HashMap<>();
    newMap.put(date,newAdd);
    SmartStock bought = myStock.addShares(newMap, false);
    this.stockList.put(key, bought);
  }

  /**
   * Performs DCA once and does not set any values. Add stock when not existing.
   * @param amount amount to invest
   * @param propMap map of stocks to weights
   * @param cf comission fee
   */
  private void dcaOnceNew(double amount, Map<String, Double> propMap,
                          double cf, LocalDate date, String key) {

    Map<LocalDate, Double> priceData = DataHelpers.getStockData(key);
    BigDecimal price;
    try {
      price = new BigDecimal(priceData.get(date));
    } catch (Exception e) {
      throw new IllegalArgumentException("PriceData not available for given date. Try again.");
    }


    BigDecimal spent = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(propMap.get(key)));
    double shares = spent.divide(price,2, RoundingMode.HALF_UP).doubleValue();

    shares = DataHelpers.round(shares);
    Pair<Double, Double> newAdd = new Pair<>(shares, cf);
    Map<LocalDate, Pair<Double, Double>> newMap = new HashMap<>();
    newMap.put(date,newAdd);
    SmartStock myStock = new SmartStock(key, "API", newMap, false);
    this.stockList.put(key, myStock);
  }

  /**
   * Perform Dollar Cost Averaging.
   * @param date the date of the transactions.
   */
  private void doDCA(LocalDate date) {

    for (String key: this.stockList.keySet()) {
      SmartStock myStock = this.stockList.get(key);
      double myProp = myStock.getProp();
      if (myProp > 0) {


        BigDecimal price = new BigDecimal(myStock.getPrice(date));
        BigDecimal spent = BigDecimal.valueOf(this.dolCostVal).multiply(BigDecimal.valueOf(myProp));
        double shares = spent.divide(price,2, RoundingMode.HALF_UP).doubleValue();
        shares = DataHelpers.round(shares);
        Pair<Double, Double> newAdd = new Pair<>(shares, this.dcaComm);
        Map<LocalDate, Pair<Double, Double>> newMap = new HashMap<>();
        newMap.put(date,newAdd);
        SmartStock bought = myStock.addShares(newMap, false);
        this.stockList.put(key, bought);
      }
    }
  }

  /**
   * Parses the proportion map provided and throws error for improper input.
   * @param propMapStr String mapping of tickers to proportions
   * @param newStocks If this DCA is allowed to have new stocks.
   * @return Map of stocks to weights
   * @throws IllegalArgumentException if data cannot be parsed
   */

  private Map<String, Double> parsePropMap(String propMapStr, boolean newStocks)
          throws IllegalArgumentException {

    Map<String,Double> propMap = new HashMap<String,Double>();
    double totProp = 0;
    String[] propsArr = propMapStr.split(";");

    for (int i = 0; i < propsArr.length; i++) {

      String prop2 = propsArr[i].replaceAll("[() ]", "");

      String[] propStock = prop2.split(",");

      String stock = propStock[0];

      if (!this.stockList.containsKey(stock) && !newStocks) {
        String error = new StringBuilder().append("Unable to find a stock ticker provided [")
                .append(stock)
                .append("] in your portfolio. Please check input.").toString();
        throw new IllegalArgumentException(error);
      }

      double prop;

      try {
        prop = Double.parseDouble(propStock[1]);
      } catch (Exception e) {
        String error = new StringBuilder().append("Unable to parse double provided [")
                .append(propStock[1])
                .append("] for ticker [")
                .append(stock)
                .append("]. Please check input.").toString();
        throw new IllegalArgumentException(error);
      }

      if (prop < 0.0 || prop > 1.0 ) {
        throw new IllegalArgumentException("Proportion must be >= 0 and <= 1.");
      }

      totProp += prop;
      if (totProp > 1.00) {
        throw new IllegalArgumentException("Sum of proportions invested cannot be > 1");
      }

      propMap.put(stock, prop);

    }

    if (totProp != 1.0) {
      throw new IllegalArgumentException("Total proportion must sum to 1.");
    }

    return propMap;
  }

  /**
   *  Set the weight for a given stock.
   * @param ticker the ticker for the stock
   * @param prop the weight of the stock
   * @throws IllegalArgumentException if the weight is improper.
   */
  public void setProp(String ticker, double prop) throws IllegalArgumentException {

    SmartStock myStock = this.stockList.get(ticker);
    myStock.setProp(prop);
    this.stockList.put(ticker, myStock);
  }

  /**
   * I changed this method signature to be void since it should just change the object.
   * There is no reason to return something every time we add a stock.
   * If the ticker is not already in the portfolio then add the stock to the
   * portfolio, otherwise find the stock in the portfolio and update the number
   * of shares.
   *
   * @param stock  stock data.
   * @param data either 'API' or the price inputs for this stock.
   * @param sharesData string containing each date bought, number of shares, and the
   *                   commission for that date.
   */
  @Override
  public void addStock(String stock, String data,
                       String sharesData, boolean onlyInts) throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!data.equals("API")) {
      data = data.replaceAll("[^0-9-,.); (]", "");
    }

    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;

    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, sharesData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      in_1 = myStock.addShares(sharesData, onlyInts);
    }

    stockList.put(stock, in_1);
  }

  /**
   * Adds stock to portfolio by taking in map arguments, without parsing.
   *
   * @param stock The stock name
   * @param data the price data map
   * @param buyData the buy data map
   * @param soldData the sold data map
   * @param onlyInts whether shares can only be integers
   * @throws IllegalArgumentException fractional shares and onlyInts is true
   */

  public void addStock(String stock, Map<LocalDate, Double> data,
                       Map<LocalDate, Pair<Double, Double>> buyData,
                       Map<LocalDate, Pair<Double, Double>> soldData, boolean onlyInts)
          throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;



    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, buyData, soldData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      in_1 = myStock.addShares(buyData, soldData, onlyInts);
    }

    stockList.put(stock, in_1);

  }



  /**
   * Adds stock to portfolio by taking in map arguments, only parsing the
   * price data given as a string. This is used for API generally.
   *
   * @param stock The stock name
   * @param data the price data map string
   * @param buyData the buy data map
   * @param soldData the sold data map
   * @param onlyInts whether shares can only be integers
   * @throws IllegalArgumentException fractional shares and onlyInts is true
   */

  public void addStock(String stock, String data,
                       Map<LocalDate, Pair<Double, Double>> buyData,
                       Map<LocalDate, Pair<Double, Double>> soldData, boolean onlyInts)
          throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!data.equals("API")) {
      data = data.replaceAll("[^0-9-,.); (]", "");
    }
    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;
    LocalDate curDate = LocalDate.now();

    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, buyData, soldData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      //TODO: Overload addShares method
      in_1 = myStock.addShares(buyData, soldData, onlyInts);
    }

    stockList.put(stock, in_1);

  }

  /**
   * Adds stock to portfolio by taking in map arguments, only parsing the
   * price data given as a string. This is used for API generally.
   *
   * @param stock The stock name
   * @param data the price data map
   * @param buyData the buy data map
   * @param onlyInts whether shares can only be integers
   * @throws IllegalArgumentException fractional shares and onlyInts is true
   */

  public void addStock(String stock, String data,
                       Map<LocalDate, Pair<Double, Double>> buyData, boolean onlyInts)
          throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!data.equals("API")) {
      data = data.replaceAll("[^0-9-,.); (]", "");
    }
    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;
    LocalDate curDate = LocalDate.now();

    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, buyData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      in_1 = myStock.addShares(buyData, onlyInts);
    }

    stockList.put(stock, in_1);

  }

  /**
   * Adds stock to portfolio by taking in map arguments without parsing.
   *
   * @param stock The stock name
   * @param data the price data map
   * @param buyData the buy data map
   * @param onlyInts whether shares can only be integers
   * @throws IllegalArgumentException fractional shares and onlyInts is true
   */
  public void addStock(String stock, Map<LocalDate, Double> data,
                       Map<LocalDate, Pair<Double, Double>> buyData, boolean onlyInts)
          throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;



    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, buyData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      in_1 = myStock.addShares(buyData, onlyInts);
    }

    stockList.put(stock, in_1);

  }

  /**
   * Sell a designated stock.
   * @param ticker the ticker of the stock
   * @param shares amount of shares to sell
   * @param cf the commission fee
   * @param dateStr date to sell
   * @param onlyInts whether we can only sell non-fractional shares
   * @throws IllegalArgumentException when values are improper or cannot be parsed.
   */
  public void sellStock(String ticker, String shares, String cf, String dateStr, boolean onlyInts)
          throws IllegalArgumentException {

    if (!this.stockList.containsKey(ticker)) {
      throw new IllegalArgumentException("Your portfolio does not contain this stock."
              + "Please check your ticker input.");
    }

    double amount;
    double commFee;
    LocalDate date;

    try {
      amount = Double.parseDouble(shares);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot parse amount to sell. "
              + "Please check input and try again.");
    }

    try {
      commFee = Double.parseDouble(cf);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot parse commission fee. "
              + "Please check input and try again.");
    }

    try {
      date = LocalDate.parse(dateStr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot parse date to sell at. "
              + "Please check input and try again.");
    }

    SmartStock soldStock = this.stockList.get(ticker);
    String soldOutput = soldStock.sellStock(date, amount, commFee, onlyInts);
    this.stockList.put(ticker, soldStock);

    System.out.println(soldOutput);
  }





  // Gets the number of different stocks
  @Override
  public int size() {
    return stockList.size();
  }

  /**
   * Gets total value of a portfolio at a date.
   *
   *
   * @param date date for the portfolio to be retrieved.
   * @return the total value data.
   */
  @Override
  public double[] getTotalValues(String date) {
    BigDecimal[] myVal = new BigDecimal[]{BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0)};

    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        SmartStock myStock = stockList.get(myKey);


        Pair<Double, Double> priceShares = myStock.getValue(date);

        double totVal = BigDecimal.valueOf(priceShares.a)
                .multiply(BigDecimal.valueOf(priceShares.b)).doubleValue();

        myVal[0] = myVal[0].add(BigDecimal.valueOf(priceShares.a));
        myVal[1] = myVal[1].add(BigDecimal.valueOf(totVal));

      }
    }
    double finTot = DataHelpers.round(myVal[1].doubleValue());
    double[] finVal = new double[]{myVal[0].doubleValue(), finTot};
    return finVal;
  }

  /**
   * Gets total costBasis of a portfolio at a date.
   *
   * @param date date for the portfolio to be retrieved.
   * @return the total costBasis data.
   */
  public double getCostBasis(String date) {
    double costBasis = 0.0;

    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        //System.out.println("KEY:");
        //System.out.println(myKey);

        SmartStock myStock = stockList.get(myKey);

        costBasis += myStock.getCostBasis(date);

      }
    }

    return DataHelpers.round(costBasis);
  }

  /**
   * Prints total costBasis of a portfolio at a date.
   *
   * @param date date for the portfolio to be retrieved.
   * @return the total costBasis data.
   */
  public String printCostBasis(String date) {
    double costBasis = this.getCostBasis(date);

    StringBuilder output = new StringBuilder();
    if (date.contains("current")) {
      output.append("Your current total cost basis is: ");
    } else {
      output.append("Your total cost basis on ");
      output.append(date);
      output.append(" is: ");
    }


    output.append(String.valueOf(costBasis));
    return output.toString();

  }

  /**
   * Gets the total # of shares in portfolio at current date.
   *
   * @return the number of stocks in portfolio.
   */

  @Override
  public int getNumStocks() {
    int myVal = 0;
    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        SmartStock myStock = stockList.get(myKey);
        Pair<Double, Double> myShares = myStock.getShareCommm("current");

        myVal += myShares.a;
      }
    }
    return myVal;
  }


  /**
   * Gets the total # of shares in portfolio at a given date.
   *
   * @param date YYYY-MM-DD string of date to fetch shares at
   * @return the number of shares in portfolio.
   */
  public double getNumStocks(String date) {
    double myVal = 0;
    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        SmartStock myStock = stockList.get(myKey);
        Pair<Double, Double> myShares = myStock.getShareCommm(date);

        myVal += DataHelpers.round(myShares.a);
      }
    }
    return myVal;
  }


  /**
   * This outputs a graph of portfolio performance over time.
   * @param date1 the performance start date in YYYY-MM-DD format
   * @param date2 the performance end date in YYYY-MM-DD format
   * @throws IllegalArgumentException when date string provided are invalid.
   */

  public String portfolioPerformance(String date1, String date2) {

    LocalDate d1;
    LocalDate d2;
    try {
      d1 = LocalDate.parse(date1);
      d2 = LocalDate.parse(date2);
    } catch (Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed. "
              +  "Please check your inputs and try again");
    }

    Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1, d2);
    char timeType = myPair.a;
    int timeSplit = myPair.b;
    int counter = 0;
    //Map<LocalDate, Double> map = stock.timeIntervalValues
    Map<LocalDate, Double> totVals = new HashMap<>();

    for (String key: this.stockList.keySet()) {
      SmartStock curStock = stockList.get(key);
      Map<LocalDate, Double> stockVals = curStock.timeIntervalValues(date1, date2,timeType);
      stockVals.forEach((k, v) -> totVals.merge(k, v, Double::sum));
      //System.out.println(totVals.size());
    }

    Pair<Double, Double> myAst = this.numAsterisk(totVals);
    Map<LocalDate, Integer> allAst = getAsterisk(totVals, myAst);

    StringBuilder graph = new StringBuilder();

    graph.append("Performance of portfolio from ");
    graph.append(d1 + " to ");
    graph.append(d2 + "\n\n");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    counter = 0;

    Comparator<LocalDate> comparator = LocalDate::compareTo;

    SortedSet<LocalDate> astKeys = new TreeSet<>(comparator);
    astKeys.addAll(allAst.keySet());

    for (LocalDate dKey: astKeys) {
      counter ++;
      if (counter == timeSplit) {
        graph.append(dtf.format(dKey));
        graph.append(": ");
        String asts = new String(new char[allAst.get(dKey)]).replace("\0", "*");
        graph.append(asts);
        graph.append("\n");
        counter = 0;
      }

    }

    graph.append("Scale: * = $");
    graph.append(myAst.a);
    graph.append(" realtive to a starting value of: $");
    graph.append(myAst.b);
    graph.append("\n");

    return graph.toString();

  }

  //Here the pair returns the dividing value for the aserisk at a
  // and the relative value at b

  /**
   * Gets the asterisk value for plotting.
   * @param totVals Map of total values over time
   * @return A pair with the value of each asterisk and the starting y-axis value.
   */
  public Pair<Double, Double> numAsterisk(Map<LocalDate, Double> totVals) {

    double minVal = Collections.min(totVals.values());
    double maxVal = Collections.max(totVals.values());

    System.out.println("Maximum Value: ");
    System.out.println(maxVal);
    System.out.println("Minimum Value: ");
    System.out.println(minVal);
    double range = maxVal - minVal;
    double relVal = 0;

    if (maxVal <= 50) {
      return new Pair<Double,Double>(1.0, 0.0);
    }
    double ast = 1.0;

    System.out.println("Max/Ast Value 1: ");
    System.out.println(maxVal / ast);
    System.out.println("Ast Value 1: ");
    System.out.println(ast);
    System.out.println("Relative Value: ");
    System.out.println(relVal);
    while (maxVal / ast > 50) {
      ast = ast * 10;
    }
    if (ast <= 1) {
      return new Pair<Double, Double>(1.0, relVal);
    }

    if ( (10.0 * ast) > range) {
      relVal = minVal;
      maxVal = maxVal - minVal;
      minVal = 0;
      ast = 1;

      while (maxVal / ast > 48) {

        if (ast == 1) {
          ast = 10;
        } else {
          ast += 10;
        }

      }
      if (ast <= 1) {
        ast = 1;
      }

    }

    relVal = Math.round(relVal * 100.0) / 100.0;

    if (relVal > ast + 1) {
      relVal -= (ast + 1);
    }



    return new Pair<Double, Double>(ast, relVal);
  }

  private Map<LocalDate, Integer> getAsterisk(Map<LocalDate, Double> totVals,
                                              Pair<Double, Double> myAst) {

    Map<LocalDate, Integer> allAst = new HashMap<LocalDate, Integer>();
    for (LocalDate key: totVals.keySet()) {
      double curVal = totVals.get(key);
      curVal = curVal - myAst.b;
      int numAst = ((int) (curVal / myAst.a));
      allAst.put(key, numAst);
    }

    return allAst;
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
  public String printPortfolioAt(String date) {
    //System.out.println("CURRENT DATE STRING = " + date);
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    outBuild.append("|   TICKER   |    DATE    |    TOTAL SHARES    |");
    outBuild.append("    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    for (String stockName : stockList.keySet()) {
      SmartStock myStock = stockList.get(stockName);
      outBuild.append(myStock.printDataAt(date) + "\n");
    }

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(10);

    String[] paddedVals = getPortPadding(
            this.getNumStocks(date),
            df, this.getTotalValues(date),
            this.getCostBasis(date)
    );

    outBuild.append("|    TOTAL   | ");

    if (date.contains("current")) {
      outBuild.append("  Current ");
    } else {
      outBuild.append(date);
    }

    outBuild.append(" | ");
    outBuild.append(paddedVals[0]);
    outBuild.append(" | ");
    outBuild.append(paddedVals[1]);
    outBuild.append(" | ");
    outBuild.append(paddedVals[2]);
    outBuild.append(" | ");
    outBuild.append(paddedVals[3]);
    outBuild.append(" |\n");

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


    return outBuild.toString();
  }


  //TODO: CREATE METHOD FOR WHEN DATE IS BEFORE FIRST SHARE
  private String printEmptyPortfolio(StringBuilder myBuild) {
    return null;
  }

  /**
   * Does padding to the portfolio.
   *
   * @param shares number of shares.
   * @param myDF   prices of stocks.
   * @param myData date and price data.
   * @return a string array of portfolio padded correctly.
   */
  private String[] getPortPadding(double shares, DecimalFormat myDF,
                                  double[] myData, double costBasis) {

    //|    TOTAL SHARES    |    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |);
    int[] padAmount = new int[4];

    padAmount[0] = Math.max(0, 18 - myDF.format(shares).length());
    padAmount[1] = Math.max(0, 16 - myDF.format(costBasis).length());
    padAmount[2] = Math.max(0, 17 - myDF.format(myData[0]).length());
    padAmount[3] = Math.max(0, 17 - myDF.format(myData[1]).length());


    int[] leftPad = new int[4];
    int[] rightPad = new int[4];

    for (int i = 0; i < 4; i++) {
      if (padAmount[i] != 0) {
        leftPad[i] = padAmount[i] / 2;
        rightPad[i] = padAmount[i] - leftPad[i];
      } else {
        leftPad[i] = 0;
        rightPad[i] = 0;
      }

    }

    String[] output = new String[4];

    output[0] = padRight(padLeft(myDF.format(shares), leftPad[0]), rightPad[0]);
    output[1] = padRight(padLeft(myDF.format(costBasis), leftPad[1]), rightPad[1]);
    output[2] = padRight(padLeft(myDF.format(myData[0]), leftPad[2]), rightPad[2]);
    output[3] = padRight(padLeft(myDF.format(myData[1]), leftPad[3]), rightPad[3]);


    return output;
  }

  /**
   * Prints the portfolio in string format.
   *
   * @return the portfolio printed at current date.
   */
  @Override
  public String toString() {
    return this.printPortfolioAt("current");
  }

  /**
   * THIS SAVES DATA MANUALLY ALWAYS BUT ONLY PRINTS THE LAST 50 DAYS.
   *
   * @return the portfolio as a string.
   */

  @Override
  public String portToJSON() {
    StringBuilder outBuild = new StringBuilder().append("{ \n");
    outBuild.append("  \"SmartPortfolio\": {\n");
    outBuild.append("    \"dolCostVal\": \"").append(this.dolCostVal).append("\",\n");
    outBuild.append("    \"investDays\": \"").append(this.investDays).append("\",\n");
    outBuild.append("    \"DCAComm\": \"").append(this.dcaComm).append("\",\n");

    String fDate;
    String sDate;
    String lInvDate;
    if (Objects.isNull(this.finDate)) {
      fDate = "null";
    } else {
      fDate = finDate.toString();
    }
    if (Objects.isNull(this.startDate)) {
      sDate = "null";
    } else {
      sDate = startDate.toString();
    }
    if (Objects.isNull(this.lastInvestDate)) {
      lInvDate = "null";
    } else {
      lInvDate = lastInvestDate.toString();
    }
    outBuild.append("    \"lastInvestDate\": \"").append(lInvDate).append("\",\n");
    outBuild.append("    \"finDate\": \"").append(fDate).append("\",\n");
    outBuild.append("    \"startDate\": \"").append(sDate).append("\",\n");

    Iterator<String> iterator = stockList.keySet().iterator();
    int cnt = 0;
    while (iterator.hasNext()) {

      outBuild.append("    \"Stock" + String.valueOf(cnt)
              + "\": {\n");
      String stockName = iterator.next();
      SmartStock myStock = stockList.get(stockName);
      outBuild.append("      \"ticker\": \"" + stockName + "\",\n");

      double myProp = myStock.getProp();
      outBuild.append("      \"prop\":  \"").append(myProp).append( "\",\n");

      outBuild.append("      \"priceData\": [\n");


      if (myStock.getDataSize() > 100) {
        outBuild.append("          \"API\"\n"
                + "      ],\n");
      } else {
        outBuild.append("       {\n");
        outBuild.append(myStock.sharesToJSON());
        outBuild.append("        }\n"
                + "      ],\n");
      }

      outBuild.append("     \"buyData\": [\n");
      outBuild.append("       {\n");
      outBuild.append(myStock.buySellToJSON(true));

      // If we have sold stock we add a second item to our array which is
      // our sales transactions map

      if (myStock.hasSold()) {
        outBuild.append("       },\n");
        outBuild.append("       {\n");
        outBuild.append(myStock.buySellToJSON(false));
      }

      outBuild.append("       }\n"
              + "     ]\n"
              + "    }");



      if (iterator.hasNext()) {
        outBuild.append(",\n");
      } else {
        outBuild.append("\n");
      }
      cnt++;

    }
    outBuild.append("  }\n"
            + "}");

    return outBuild.toString();
  }

  /**
   * This saves the current portfolio to the users chosen portfolio directory.
   *
   * @param fileName the name of the file to save portfolio as.
   * @param user     the name of the user to save to.
   * @throws IllegalArgumentException when input is not valid.
   */
  @Override
  public void save(String fileName, String user) throws IllegalArgumentException {
    if (!fileName.matches("[A-Za-z0-9]+")) {
      throw new IllegalArgumentException("File names may only contain letters and numbers."
              + "If your filename has an extension please remove it. (It will be saved as .json)");
    }

    StringBuilder fullName = new StringBuilder();
    try {
      fullName.append(DataHelpers.getPortfolioDir(user));
      fullName.append(File.separator);
    } catch (FileNotFoundException e) {
      System.out.println("Your file has encountered an error while finding the user directory."
              + "Please check portfolio directory.");
      throw new RuntimeException(e);
    }

    fullName.append(fileName).append(".json");

    try (PrintWriter out = new PrintWriter(fullName.toString())) {
      out.println(this.portToJSON());
    } catch (FileNotFoundException e) {
      System.out.println("Your file has encountered an error while saving."
              + "Please check portfolio directory.");
      throw new RuntimeException(e);
    }
  }

  /**
   * This saves the current portfolio to the given path.
   *
   * @param path the full path  to save the portfolio
   * @throws RuntimeException when input is not valid.
   */
  public void saveDirect(String path) throws RuntimeException {

    try (PrintWriter out = new PrintWriter(path)) {
      out.println(this.portToJSON());
    } catch (FileNotFoundException e) {
      System.out.println("Your file has encountered an error while saving."
              + "Please check portfolio directory.");
      throw new RuntimeException(e);
    }
  }


  /**
   * This gets the mapping of portfolio performance over a set time frame.
   * @param date1 the performance start date in YYYY-MM-DD format
   * @param date2 the performance end date in YYYY-MM-DD format
   * @throws IllegalArgumentException when date string provided are invalid.
   */

  public Map<LocalDate, Double> performanceMapping(String date1, String date2) {

    LocalDate d1;
    LocalDate d2;
    try {
      d1 = LocalDate.parse(date1);
      d2 = LocalDate.parse(date2);
    } catch (Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed. "
              + "Please check your inputs and try again");
    }

    Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1, d2);
    char timeType = myPair.a;
    int timeSplit = myPair.b;
    //Map<LocalDate, Double> map = stock.timeIntervalValues
    Map<LocalDate, Double> totVals = new HashMap<>();

    for (String key: this.stockList.keySet()) {
      SmartStock curStock = stockList.get(key);
      Map<LocalDate, Double> stockVals = curStock.timeIntervalValues(date1, date2,timeType);
      stockVals.forEach((k, v) -> totVals.merge(k, v, Double::sum));
      //System.out.println(totVals.size());
    }

    if (timeSplit == 1) {
      return totVals;
    } else {

      int counter = 0;

      SortedSet<LocalDate> myKeys = new TreeSet<>(LocalDate::compareTo);
      myKeys.addAll(totVals.keySet());
      Map<LocalDate, Double> finVals = new HashMap<>();

      for (LocalDate dKey: myKeys) {
        counter ++;
        if (counter == timeSplit) {
          finVals.put(dKey, totVals.get(dKey));
          counter = 0;
        }
      }

      return finVals;
    }

  }

}

