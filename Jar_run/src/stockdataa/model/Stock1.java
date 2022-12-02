package stockdataa.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stockdataa.DataHelpers;

import static java.lang.Math.min;
import static stockdataa.DataHelpers.padLeft;
import static stockdataa.DataHelpers.padRight;

/**
 * Class that holds all information about a stock.
 */

public class Stock1 implements Stock {


  private final double shares;
  private final String ticker;

  private final Map<LocalDate, Double> stockData;

  /**
   * Constructor that gets each stock.
   *
   * @param ticker of the stock.
   * @param shares bought in the stock.
   * @param data   date and price of the stock.
   */

  public Stock1(String ticker, double shares, String data) throws IllegalArgumentException {
    if (shares < 0) {
      throw new IllegalArgumentException("You cannot buy a negative amount of shares.");
    }
    this.shares = shares;
    this.ticker = ticker;

    if (data.equals("API")) {
      this.stockData = DataHelpers.getStockData(ticker);
    } else {
      this.stockData = parseStock(data);
    }


  }

  /**
   * Constructor that gets each stock.
   *
   * @param ticker of the stock.
   * @param shares bought in the stock.
   * @param data   maps date and price of the stock.
   */

  public Stock1(String ticker, double shares,
                Map<LocalDate, Double> data) throws IllegalArgumentException {
    if (shares < 0) {
      throw new IllegalArgumentException("You cannot buy a negative amount of shares.");
    }
    this.shares = shares;
    this.ticker = ticker;
    this.stockData = data;
  }

  /**
   * If the date string passed to this == "current" then it will.
   * fetch the most current date available in stockData, otherwise it.
   * searches stockData for key matching the date inputted.
   * If this date is not found it will print "NO DATA" in each of.
   * the columns except the ticker column.
   */
  @Override
  public String printDataAt(String date) {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    String output = "";

    if (date.contains("current")) {
      myKey = Collections.max(keys);
      date = dtf.format(myKey);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }


    if (!stockData.containsKey(myKey)) {
      output = "| " + padOne(ticker, 6) + " | " + date + " | "
              + padOne(String.valueOf(shares), 13)
              + " |       NO DATA      |       NO DATA     |";
    } else {
      // this makes sure we dont have big numbers in scientific notation
      // It will print up to 10 digits
      DecimalFormat df = new DecimalFormat("#");
      df.setMaximumFractionDigits(10);


      //TODO: fix here
      double myData = stockData.get(myKey);

      String[] padded = getPadding(ticker, shares, df, myData);


      output = "| " + padded[0]
              + " | " + date
              + " | " + padded[1]
              + " | " + padded[2]
              + " | " + padded[3] + " |";

    }


    return output;
  }


  /**
   * This function returns the actual double value of any main
   * numeric stock data point. It takes a date which is in the
   * same format as above (if date == "current" then get the
   * latest value otherwise fetch the value at date). Unlike
   * above this one does not take an index and just returns
   * the whole array of inputs for efficiency.
   */
  @Override
  public double getData(String date) {

    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    double output = 0.0;

    if (date.contains("current")) {
      myKey = Collections.max(keys);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

    //TODO: Double check this function
    if (stockData.containsKey(myKey)) {
      output = stockData.get(myKey);
    }

    return output;
  }

  /**
   * basic getter for # of shares.
   *
   * @return the number of shares.
   */
  @Override
  public double getShares() {
    return this.shares;
  }

  /**
   * basic getter for the ticker.
   */
  @Override
  public String getTicker() {
    return this.ticker;
  }

  /**
   * toString() no prints most the current data.
   */

  @Override
  public String toString() {
    return this.printDataAt("current");
  }

  /**
   * This is used to change the numbe rof shares.
   * @param numShares is the number of shares to be added.
   * @return the added sum of shares.
   * @throws IllegalArgumentException throws exception.
   */
  public Stock1 addShares(double numShares) throws IllegalArgumentException {
    if (numShares < 0) {
      throw new IllegalArgumentException("You cannot buy a negative amount of shares.");
    }
    double totShares = this.shares + numShares;
    return new Stock1(this.ticker, totShares, this.stockData);
  }

  private String padOne(String shares, int size) {

    int padAmount = Math.max(0, size - shares.length());
    int leftPad = 0;
    int rightPad = 0;
    if (padAmount != 0) {
      leftPad = padAmount / 2;
      rightPad = padAmount - leftPad;
    }


    String output = padRight(padLeft(shares, leftPad), rightPad);

    return output;
  }

  private String[] getPadding(String ticker, double shares, DecimalFormat myDF, double myData) {
    int[] padAmount = new int[4];

    double totVal = myData * shares;

    padAmount[0] = Math.max(0, 6 - ticker.length());
    padAmount[1] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[2] = Math.max(0, 18 - myDF.format(myData).length());
    padAmount[3] = Math.max(0, 17 - myDF.format(totVal).length());


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

    String[] output = new String[7];

    output[0] = padRight(padLeft(ticker, leftPad[0]), rightPad[0]);
    output[1] = padRight(padLeft(String.valueOf(shares), leftPad[1]), rightPad[1]);
    output[2] = padRight(padLeft(myDF.format(myData), leftPad[2]), rightPad[2]);
    output[3] = padRight(padLeft(myDF.format(totVal), leftPad[3]), rightPad[3]);

    return output;
  }

  // Example String:
  // "(2020-10-05,32.4),(2022-09-31,46.7),...
  // IF A DATE IS ENTERED MORE THAN ONCE THEN ONLY THE
  // FIRST TIME IT WAS ENTERED WILL BE USED
  private Map<LocalDate, Double> parseStock(String data) throws IllegalArgumentException {

    if (!data.matches("[0-9-,.); (]+")) {
      throw new IllegalArgumentException("Unexpected character was found in stock data. "
              + "Please try again.");
    }

    Map<LocalDate, Double> stockDateData = new HashMap<LocalDate, Double>();
    String[] dateInfo = data.split(";");

    for (int i = 0; i < dateInfo.length; i++) {

      String m2 = dateInfo[i].replaceAll("[() ]", "");

      String[] info2 = m2.split(",");

      // This will throw an error if the date was entered wrong so ok here
      LocalDate myKey = LocalDate.parse(info2[0]);

      if (info2[1].length() == 0) {
        if (info2[2].length() != 0) {
          info2[1] = info2[2];
        } else {
          throw new IllegalArgumentException("Unexpected input in string data.");
        }
      }
      if (!stockDateData.containsKey(myKey)) {
        stockDateData.put(myKey, Double.parseDouble(info2[1]));
      }
      // This will throw an error if the # was entered wrong, so we should be good here.

    }

    return stockDateData;
  }


  /**
   * Prints only the top 50 dates.
   */

  public String sharesToJSON() {

    StringBuilder outBuild = new StringBuilder();
    List<LocalDate> topDates = new ArrayList<LocalDate>(stockData.keySet());
    Collections.sort(topDates, Collections.reverseOrder());
    topDates = topDates.subList(0, min(50, topDates.size()));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("Stock1-MM-dd");
    for (int i = 0; i < topDates.size(); i++) {
      LocalDate key = topDates.get(i);
      String curPrice = String.valueOf(stockData.get(key));
      outBuild.append("          \"");
      outBuild.append(dtf.format(key)).append("\": \"");
      outBuild.append(curPrice).append("\"");

      if (i == topDates.size() - 1) {
        outBuild.append("\n");
      } else {
        outBuild.append(",\n");
      }

    }
    //l = l.subList(0,10);
    return outBuild.toString();
  }
}


//toString();
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
