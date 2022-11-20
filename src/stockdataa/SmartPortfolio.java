package stockdataa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static stockdataa.DataHelpers.getTickers;
import static stockdataa.DataHelpers.padLeft;
import static stockdataa.DataHelpers.padRight;

/**
 * Class that performs add and creation of a portfolio.
 */
public class SmartPortfolio implements StockPortfolio {
  private Map<String, SmartStock> stockList;
  private Set<String> stockTickers;

  /**
   * Portfolio constructor that initializes the portfolio with new list.
   */
  public SmartPortfolio() {
    this.stockList = new HashMap<String, SmartStock>();
    this.stockTickers = getTickers();
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
  public void addStock(String stock, String data, String sharesData, boolean onlyInts) throws IllegalArgumentException {

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

  public void addStock(String stock, Map<LocalDate, Double> data,
                       Map<LocalDate, Pair<Double, Double>> buyData, boolean onlyInts)
          throws IllegalArgumentException {

    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!this.stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    SmartStock in_1;
    LocalDate curDate = LocalDate.now();

    //TODO: Implement OnlyInts without fucking up method
    // Add boolean in constructor??
    // Use Map = myStock.parseShares(sharesData, true)
    //  !Check that all shares values are ints!   ---> myStock.addShares(MAP)
    if (!this.stockList.containsKey(stock)) {
      in_1 = new SmartStock(stock, data, buyData, onlyInts);
    } else {
      SmartStock myStock = this.stockList.get(stock);
      in_1 = myStock.addShares(buyData, onlyInts);
    }

    stockList.put(stock, in_1);

  }



  // Gets the number of different stocks
  @Override
  public int size() {
    return stockList.size();
  }

  //TODO: FIX THIS PART GET TOTAL VALUE CORRECTLY
  // FOLLOW LOGIC FROM THE TIME INTERVAL FUNC
  /**
   * Gets total value of a portfolio at a date.
   *
   *
   * @param date date for the portfolio to be retrieved.
   * @return the total value data.
   */
  @Override
  public double[] getTotalValues(String date) {
    double[] myVal = new double[]{0.0, 0.0};

    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        //System.out.println("KEY:");
        //System.out.println(myKey);

        SmartStock myStock = stockList.get(myKey);

        // TODO IMPLEMENT STOCK LIST GET TOTAL VALUES
        Pair<Double, Double> priceShares = myStock.getValue(date);
        double totVal = priceShares.a * priceShares.b;

        myVal[0] += priceShares.a;
        myVal[1] += totVal;

      }
    }

    return myVal;
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

    return costBasis;
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
    output.append("Your total cost basis on ");
    output.append(date);
    output.append(" is: ");
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
   * This outputs a graph or portfolio performance over time
   * @param date1 the performance start date in YYYY-MM-DD format
   * @param date2 the performance end date in YYYY-MM-DD format
   * @throws IllegalArgumentException when date string provided are invalid.
   */

  public String portfolioPerformance(String date1, String date2) {

    LocalDate d1;
    LocalDate d2;
    try{
      d1 = LocalDate.parse(date1);
      d2 = LocalDate.parse(date2);
    } catch(Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed. " +
              "Please check your inputs and try again");
    }

    Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1, d2);
    char timeType = myPair.a;
    int timeSplit = myPair.b;
    int counter = 0;
    //Map<LocalDate, Double> map = stock.timeIntervalValues
    Map<LocalDate, Double> totVals = new HashMap<>();

    for(String key: this.stockList.keySet()) {
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

    for(LocalDate dKey: astKeys) {
      counter ++;
      if(counter == timeSplit) {
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
  private Pair<Double, Double> numAsterisk(Map<LocalDate, Double> totVals) {

    double minVal = Collections.min(totVals.values());
    double maxVal = Collections.max(totVals.values());

    double range = maxVal - minVal;
    double relVal = 0;

    if(maxVal <= 50) {
      return new Pair<Double,Double>(1.0, 0.0);
    }
    double ast = 1.0;

    while(maxVal/ast > 50) {
      ast = ast * 10;
    }
    if(ast <= 1){
      return new Pair<Double, Double>(1.0, relVal);
    }


    if( (10.0 * ast) > range) {
      relVal = minVal;
      maxVal = maxVal - minVal;

      minVal = 0;

      while(maxVal/ast > 50) {
        ast = ast * 10;
      }
      if(ast <= 1){
        ast = 1;
      }

    }

    return new Pair<Double, Double>(ast, relVal);
  }
  private Map<LocalDate, Integer> getAsterisk(Map<LocalDate, Double> totVals,
                                              Pair<Double, Double> myAst) {

    Map<LocalDate, Integer> allAst = new HashMap<LocalDate, Integer>();
    for(LocalDate key: totVals.keySet()) {
      double curVal = totVals.get(key);
      curVal = curVal - myAst.b;
      int numAst = ((int) (curVal/myAst.a));
      allAst.put(key, numAst);
    }

    return allAst;
  }

  /**
   * Gets the total # of shares in portfolio at a given date.
   *
   * @return the number of shares in portfolio.
   */
  public int getNumStocks(String date) {
    int myVal = 0;
    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        SmartStock myStock = stockList.get(myKey);
        Pair<Double, Double> myShares = myStock.getShareCommm(date);

        myVal += myShares.a;
      }
    }
    return myVal;
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
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    //| TICKER |    DATE    |    TOTAL SHARES     |    COST BASIS    |     CLOSE PRICE     |    TOTAL VALUE    |
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
  private String[] getPortPadding(int shares, DecimalFormat myDF,
                                  double[] myData, double costBasis) {

    //|    TOTAL SHARES    |    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |);
    int[] padAmount = new int[4];

    padAmount[0] = Math.max(0, 18 - String.valueOf(shares).length());
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

    output[0] = padRight(padLeft(String.valueOf(shares), leftPad[0]), rightPad[0]);
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

    Iterator<String> iterator = stockList.keySet().iterator();
    int cnt = 0;
    while (iterator.hasNext()) {

      outBuild.append("    \"Stock" + String.valueOf(cnt)
              + "\": {\n");
      String stockName = iterator.next();
      SmartStock myStock = stockList.get(stockName);
      outBuild.append("      \"ticker\": \"" + stockName + "\",\n");
      outBuild.append("      \"priceData\": [\n");
      if(myStock.getDataSize() > 100) {
        outBuild.append("          \"API\"\n"
                + "      ],\n");
      } else {
        outBuild.append(myStock.sharesToJSON());
        outBuild.append("        }\n" +
                "      ],\n");
      }

      outBuild.append("     \"buyData\": [\n");
      outBuild.append("       {\n");
      outBuild.append(myStock.buyToJSON());

      outBuild.append("       }\n" +
              "     ]\n" +
              "    }");

      if (iterator.hasNext()) {
        outBuild.append(",\n");
      } else {
        outBuild.append("\n");
      }
      cnt++;

    }
    outBuild.append("  }\n" +
            "}");

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



}

