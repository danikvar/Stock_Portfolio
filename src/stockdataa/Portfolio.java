package stockdataa;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static stockdataa.DataHelpers.getTickers;
import static stockdataa.DataHelpers.padLeft;
import static stockdataa.DataHelpers.padRight;

/**
 * Class that performs add and creation of a portfolio.
 */
public class Portfolio implements StockPortfolio {
  private final Map<String, Stock1> stockList;
  private final Set<String> stockTickers;

  /**
   * Portfolio constructor that initializes the portfolio with new list.
   */
  public Portfolio() {
    this.stockList = new HashMap<String, stockdataa.Stock1>();
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
   * @param shares number of shares.
   */
  @Override
  public void addStock(String stock, String data, String sharesData, boolean onlyInts)
          throws IllegalArgumentException, NullPointerException {

    double shares;

    try{
      shares = Double.parseDouble(sharesData);
    } catch(NullPointerException | NumberFormatException ex) {
      System.out.println("Either the shares input passed was null or the string" +
              "could not be parsed to a double. Please check your input format and " +
              "try again!");
      throw(ex);
    }



    stock = stock.replaceAll("[^A-Za-z0-9-]", "");
    if (!data.equals("API")) {
      data = data.replaceAll("[^0-9-,.); (]", "");
    }
    if (!stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    stockdataa.Stock1 in_1;
    if (!stockList.containsKey(stock)) {
      in_1 = new stockdataa.Stock1(stock, shares, data);
    } else {
      stockdataa.Stock1 myStock = stockList.get(stock);
      in_1 = myStock.addShares(shares);
    }

    stockList.put(stock, in_1);

  }


  /**
   * Adds stock to the portfolio.
   *
   * @param stock     ticker symbol.
   * @param shares    number of shares in stock.
   * @param priceData date and price of the stock.
   * @throws IllegalArgumentException when invalid input is entered.
   */

  public void addStock(String stock, int shares, Map<LocalDate, Double> priceData) throws
          IllegalArgumentException {

    if (!stockTickers.contains(stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    stockdataa.Stock1 in_1;
    if (!stockList.containsKey(stock)) {
      in_1 = new stockdataa.Stock1(stock, shares, priceData);
    } else {
      stockdataa.Stock1 myStock = stockList.get(stock);
      in_1 = myStock.addShares(shares);
    }

    stockList.put(stock, in_1);

  }

  @Override
  public int size() {
    return stockList.size();
  }

  /**
   * Gets total value of a portfolio at a date.
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

        stockdataa.Stock1 myStock = stockList.get(myKey);

        double price = myStock.getData(date);
        double totVal = price * myStock.getShares();

        myVal[0] += price;
        myVal[1] += totVal;

      }
    }
    return myVal;
  }

  /**
   * Gets number of stocks in portfolio.
   *
   * @return the number of stocks in portfolio.
   */

  @Override
  public int getNumStocks() {
    int myVal = 0;
    if (stockList.size() > 0) {

      for (String myKey : stockList.keySet()) {

        stockdataa.Stock1 myStock = stockList.get(myKey);

        myVal += myStock.getShares();
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
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    outBuild.append("| TICKER |    DATE    |    SHARES     |");
    outBuild.append("     OPEN PRICE     |    SHARE VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    for (String stockName : stockList.keySet()) {
      stockdataa.Stock1 myStock = stockList.get(stockName);
      outBuild.append(myStock.printDataAt(date) + "\n");
    }

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(10);

    String[] paddedVals = getPortPadding(
            this.getNumStocks(),
            df, this.getTotalValues(date)
    );


    outBuild.append("| TOTAL  | ");

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
    outBuild.append(" |\n");

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


    return outBuild.toString();
  }



  /**
   * Does padding to the portfolio.
   *
   * @param shares number of shares.
   * @param myDF   prices of stocks.
   * @param myData date and price data.
   * @return a string array of portfolio padded correctly.
   */
  private String[] getPortPadding(int shares, DecimalFormat myDF, double[] myData) {
    int[] padAmount = new int[3];

    padAmount[0] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[1] = Math.max(0, 18 - myDF.format(myData[0]).length());
    padAmount[2] = Math.max(0, 17 - myDF.format(myData[1]).length());


    int[] leftPad = new int[3];
    int[] rightPad = new int[3];

    for (int i = 0; i < 3; i++) {
      if (padAmount[i] != 0) {
        leftPad[i] = padAmount[i] / 2;
        rightPad[i] = padAmount[i] - leftPad[i];
      } else {
        leftPad[i] = 0;
        rightPad[i] = 0;
      }

    }

    String[] output = new String[3];

    output[0] = padRight(padLeft(String.valueOf(shares), leftPad[0]), rightPad[0]);
    output[1] = padRight(padLeft(myDF.format(myData[0]), leftPad[1]), rightPad[1]);
    output[2] = padRight(padLeft(myDF.format(myData[1]), leftPad[2]), rightPad[2]);


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
    outBuild.append("  \"Portfolio\": {\n");

    Iterator<String> iterator = stockList.keySet().iterator();
    int cnt = 0;
    while (iterator.hasNext()) {

      outBuild.append("    \"Stock" + cnt
              + "\": {\n");
      String stockName = iterator.next();
      Stock1 myStock = stockList.get(stockName);
      outBuild.append("      \"ticker\": \"" + stockName + "\",\n");
      outBuild.append("      \"shares\": \""
              + myStock.getShares()
              + "\",\n");
      outBuild.append("      \"priceData\": [\n"
              + "        {\n");
      outBuild.append(myStock.sharesToJSON());
      outBuild.append("        }\n" +
              "      ]\n" +
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
      fullName.append("\\");
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
   * This outputs a graph or portfolio performance over time
   * @param date1 the performance start date in YYYY-MM-DD format
   * @param date2 the performance end date in YYYY-MM-DD format
   * @throws IllegalArgumentException when date string provided are invalid.
   */
  public String portfolioPerformance(String date1, String date2) throws IllegalArgumentException {
    return null;
  }

}

