package stockdataa;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static stockdataa.DataHelpers.padLeft;
import static stockdataa.DataHelpers.padRight;

/**
 * Class that holds all information about a stock.
 */

public class SmartStock implements Stock {


  // buyDate - The initial date the stock was bought
  //    This should be in the format: YYYY-MM-DD
  private Double shares;
  private String ticker;
  // I changed this to a date object from string so we cna compare dates
  // using the Collections.max() function
  // They key will be a LocalDate object in 'YYYY-MM-DD' format
  // and the values in the int[] will be
  // (0:open,1:high,2:low,3:close,4:volume) - length = 5
  private Map<LocalDate, Double> stockData;
  // A map of dates and prices at those dates ^

  private Map<LocalDate, Pair<Double,Double>> BuyDates;
  // ^ A map of Dates where stock was bought, and the shares/comission on those dates
  private Map<LocalDate, Pair<Double,Double>> SoldDates;
  // ^ A map of Dates where stock was sold, how many shares were sold, and
  //   the commission at those dates.
  //   Key = Date Sold.
  //   ValueMap KeySet = Set of dates that the shares sold on the Key Date were bought on
  //   ValuePair = pair of total shares sold[s] and commission fee [b] on the key date.


  // Not needed unless we plan to track
  //private Map<LocalDate, List<LocalDate>> SoldInv;
  // ^ This is basically an inverse of the previous map
  // The key here is a buyDate from which shares were sold
  // The value here is a list of dates which those shares were
  //     sold on. --> used for easier selling and removal

  /**
   * Constructor that gets each stock.
   *
   * @param ticker of the stock.
   * @param data   date and price of the stock.
   * @param myBuyDates A String consisting of dates when the stock was bought,
   *                   the shares bought at that date, and the commission fee paid.
   * @param onlyInts A boolean stating if the shares are fractional. If true then only
   *                 whole positive values are allowed for shares.
   */

  public SmartStock(String ticker, String data,
                    String myBuyDates, boolean onlyInts) throws IllegalArgumentException {

    this.SoldDates = new HashMap<LocalDate, Pair<Double,Double>>();
    //this.SoldInv = new HashMap<LocalDate, List<LocalDate>>();

    double shares = 0;
    this.ticker = ticker;

    Map<LocalDate, Double> myStockData;
    if (data.contains("API")) {
      myStockData = DataHelpers.getStockData(ticker);
    } else {
      myStockData = parseStock(data);
    }


    //TODO: PARSE BUY DATES
    // COMSHARES SHOULD BE IN FORMAT:
    // "(DATESTRING1, SHARES1, COMM1);(DATESTRING2, SHARES2, COMM2); ..."

    Map<LocalDate, Pair<Double,Double>> myBuyDateMap;

    try{
      myBuyDateMap = parseBuyDates(myBuyDates, onlyInts);
    } catch(IllegalArgumentException e) {
      throw e;
    }

    this.checkBuyDates(myStockData, myBuyDateMap);
    this.stockData = myStockData;
    this.BuyDates = myBuyDateMap;

    for(LocalDate key: this.BuyDates.keySet()) {
      shares += this.BuyDates.get(key).a;
    }


    this.shares = shares;

    // This function gets the value at the current date
  }

  /***
   * This function sells stock by updating the buyDates map. If the data given is
   * 'FIFO' then it sells the earliest bought shares. If the date given is 'LIFO'
   * it will sell the latest bought shares until the amount is satisfied. Otherwise,
   * sells shares at specified date. In the case where the number of shares to sell
   * is greater than the total number of shares at a certain date then it will sell
   * all shares and return a string informing that only X amount of shares were sold.
   *
   * @param date The date to shares are sold on. YYYY-MM-DD string or 'LIFO'/'FIFO'.
   * @param amount The total amount of shares to sell.
   * @param commFee The commission fee for this transaction.
   * @param onlyInts boolean to check if only integer values of shares can be sold.
   * @return Returns a string with the amount of shares sold and each date sold at.
   * @throws IllegalArgumentException if onlyInts and the share amount is fractional,
   *        negative shares or negative Fee, or more shares than bought at date.
   */


  public String sellStock(LocalDate date, double amount, double commFee, boolean onlyInts) throws
          IllegalArgumentException {

    if(date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Cannot sell on future dates. " +
              "Please check that the date input is before/on the current date.");
    }

    // Pre-check error conditions and end conditions
    if(amount == 0) {
      return("Sold no shares, since amount to sell was 0. No commission fee was charged.");
    } else if (amount < 0 || commFee < 0) {
      throw new IllegalArgumentException("Cannot sell negative shares or pay negative" +
              " commission. Please check input and try again.");
    }

    if (onlyInts && (amount % 1) != 0) {
      throw new IllegalArgumentException("Cannot sell fractional shares" +
              ". Please check input and try again.");
    }

    LocalDate maxSellDate = Collections.max(this.SoldDates.keySet());
    LocalDate maxBuyDate = Collections.max(this.BuyDates.keySet());

    if(date.isBefore(maxBuyDate) || date.isBefore(maxSellDate)) {
      String pastSell = new StringBuilder()
              .append("Though selling shares on past dates is allowed, selling")
              .append(" on a date before the most recent transaction is not.")
              .append("Please ensure your input does not conflict with past transactions.")
              .toString();
      throw new IllegalArgumentException(pastSell);
    }

    Pair<Double,Double> curShares = this.getShareCommm(date.toString());

    String sold = "No shares sold";

    if(curShares.a < amount) {
      String overSell = new StringBuilder()
              .append("Cannot sell more shares than you currently have!)")
              .append(" You currently only have [")
              .append(curShares.a)
              .append("] shares as of [")
              .append(date)
              .append("].").toString();
      throw new IllegalArgumentException(overSell);
    } else {
      Pair<Double, Double> newSold = new Pair<>(amount, commFee);
      this.SoldDates.merge(date, newSold, Pair::add);
      sold = new StringBuilder()
              .append("Succesfully sold [")
              .append(amount)
              .append("] shares on [")
              .append(date)
              .append("].").toString();
    }


    return sold;
  }

  // TODO: PUT THE BELOW IN THE PORTFOLIO METHOD TO SELL.
  // TODO: Check the date string in portfolio method then call sellStock
  //  sellFIFO, or sellLIFO can be added if track profit, otherwise it does
  //  not matter since costBasis or Total Value do not change based on price of stock
  //  on buyDate for the sold stock.

  // TODO: Update getTotalValue, getCostBasis, and printDataAt() to work with sales
  // TODO: ^ getShareComm and CostBasis was updated. Check that rest of functions
  //         work with this
  // For Value at a certain date, we just need the total shares at X date
  // For CostBasis we need to know Total Shares * Price, commission paid for buys,
  // and the commission paid on sales up until X date

  // We would only need to know what dates bought we sold IF we want to track profit over time,
  //          since we are not --> only need to know # sold on X date and commission


  /*

   * This function sells stock by updating the buyDates map. If the data given is
   * 'FIFO' then it sells the earliest bought shares. If the date given is 'LIFO'
   * it will sell the latest bought shares until the amount is satisfied. Otherwise,
   * sells shares at specified date. In the case where the number of shares to sell
   * is greater than the total number of shares
   *
   * @param d1 The date to sell shares. Either YYYY-MM-DD string or 'LIFO'/'FIFO'.
   * @param amount The total amount of shares to sell as a String.
   * @param CF The commission fee for this transaction.
   * @param onlyInts boolean to check if only integer values of shares can be sold.
   * @return Returns a string with the amount of shares sold and each date sold at.
   * @throws IllegalArgumentException if onlyInts and the share amount is fractional,
   *        trying to sell negative shares or negative Fee, or more shares than currently owned.

  give -> [LocalDate date, double amount, double commFee, boolean onlyInts]

  try{
      amntSell = Double.parseDouble(amount);
    } catch(Exception e) {
      throw new IllegalArgumentException("Cannot parse amount to sell. " +
              "Please check input and try again.");
    }

    try{
      commFee = Double.parseDouble(CF);
    } catch(Exception e) {
      throw new IllegalArgumentException("Cannot parse commission fee. " +
              "Please check input and try again.");
    }

    LocalDate date;
    try{
      date = LocalDate.parse(d1);
    } catch(Exception e) {
      throw new IllegalArgumentException("Cannot parse date to sell at. " +
              "Please check input and try again.");
    }
   */

  public SmartStock(String ticker, Map<LocalDate, Double> data,
                    Map<LocalDate, Pair<Double,Double>> myBuyDates,
                    boolean onlyInts) throws IllegalArgumentException {

    this.SoldDates = new HashMap<LocalDate, Pair<Double,Double>>();
    //this.SoldInv = new HashMap<LocalDate, List<LocalDate>>();

    double shares = 0;
    this.ticker = ticker;

    this.stockData = data;
    this.BuyDates = myBuyDates;


    this.checkBuyDates(data, myBuyDates);
    for(LocalDate key: this.BuyDates.keySet()) {

      double curShares = this.BuyDates.get(key).a;

      if(onlyInts && (curShares % 1) != 0) {
        throw new IllegalArgumentException("Cannot have fractional shares. " +
                "Please try again.");
      }
      shares += curShares;
    }

    this.shares = shares;

    // This function gets the value at the current date
  }

  /**
   *  Ensures that each buy date in the stock is present in the data. Some API dates
   *  are missing due to stock market closures. Returns true if all buyDates have matching price
   *  dates and throws and error if not.
   */
  private boolean checkBuyDates(Map<LocalDate, Double> data,
                                Map<LocalDate, Pair<Double,Double>> myBuyDates) throws
          IllegalArgumentException {
    for(LocalDate key: myBuyDates.keySet()) {
      if(! data.containsKey(key)) {
        StringBuilder errorBuild = new StringBuilder();
        errorBuild.append("It seems like the buy date provided [")
                .append(key.toString())
                .append("] was not contained in the ")
                .append("Price Data. If using the API this could be due to ")
                .append("trading holidays. If this seems to be in error, please provide a custom")
                .append("price input on the missing date.");
        throw new IllegalArgumentException(errorBuild.toString());
      }
    }
    return true;
  }

  private Map<LocalDate, Pair<Double,Double>> parseBuyDates(String myBuyDates, boolean onlyInts)
          throws IllegalArgumentException {

    //System.out.println(myBuyDates);

    if (!myBuyDates.matches("[0-9-,.); (]+")) {
      throw new IllegalArgumentException("Unexpected character was found in stock data. "
              + "Please try again.");
    }

    Map<LocalDate, Pair<Double,Double>> stockDateData = new HashMap<LocalDate, Pair<Double,Double>>();
    String[] dateInfo = myBuyDates.split(";");

    for (int i = 0; i < dateInfo.length; i++) {

      String m2 = dateInfo[i].replaceAll("[() ]", "");

      String[] info2 = m2.split(",");

      //System.out.println(info2[0]);
      // This will throw an error if the date was entered wrong so ok here
      LocalDate myKey;
      try{
        myKey = LocalDate.parse(info2[0]);
      } catch(Exception e) {
        throw new IllegalArgumentException("Error parsing dates for buying stock. " +
                "Please check input and try again.");
      }

      if(myKey.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("You cannot set a buy date for a future date. " +
                "Please check input and try again.");
      }


      if(info2[1].length() == 0) {
        if(info2[2].length() != 0) {
          info2[1] = info2[2];
        } else {
          throw new IllegalArgumentException("Unexpected input in string data.");
        }
      }
      if (!stockDateData.containsKey(myKey)) {
        Pair<Double, Double> myPair;
        double numShares;
        double commFee;
        try{

          numShares = Double.parseDouble(info2[1]);
          commFee = Double.parseDouble(info2[2]);
          myPair = new Pair<Double, Double>(numShares, commFee);
        } catch(Exception e) {
          throw new IllegalArgumentException("Number of shares could not " +
                  " or commission fee could not be parsed." +
                  " Please check input and try again.");
        }

        // Give descriptive message if the fee or shares are <= 0
        // We do allow zero shares because it signifies that a person sold the shares
        // at date X, but we want to keep the fee
        // We cannot have negative shares or negative commission
        if(numShares < 0 || commFee < 0) {
          throw new IllegalArgumentException("Number of shares and commission fee must " +
                  "have positive values. Please check your input and try again.");
        }

        if(onlyInts) {
          if((numShares % 1) != 0) {
            throw new IllegalArgumentException("The number of shares must be a whole " +
                    "positive value. Please check your input and try again.");

          }

        }


        if(numShares > 0) {
          stockDateData.put(myKey, myPair);
        }

      }
      // This will throw an error if the # was entered wrong, so we should be good here.

    }

    return stockDateData;
  }


  /*

    NOT USED AT THE MOMENT KEEPING FOR TESTING
   * Constructor that gets each stock.
   *
   * @param ticker of the stock.
   * @param shares bought in the stock.
   * @param data   maps date and price of the stock.
   * @param buyDate string of the date the stock was bought in yyyy-MM-DD format.
   * @param commission the commission fee for the transaction


  private SmartStock(String ticker, double shares, Map<LocalDate, Double> data,
                     String buyDate, double commission) {
    this.shares = shares;
    this.ticker = ticker;
    this.stockData = data;
    Pair<Double,Double> myPair = new Pair<Double,Double>(shares,commission);
    LocalDate myDate;
    try {
      myDate = LocalDate.parse(buyDate);
    } catch (Exception e) {
      throw new IllegalArgumentException("Buy-date could not be parsed. " +
              "Please check the input string and try again.");
    }
    HashMap<LocalDate, Pair<Double,Double>> myBuyDates;
    myBuyDates = new HashMap<LocalDate, Pair<Double,Double>>();
    myBuyDates.put(myDate,myPair);
    this.BuyDates = myBuyDates;
  }

  */


  /**
   * Constrcutor that gets each stock.
   *
   * @param ticker of the stock.
   * @param data   date and price of the stock.
   * @param BuyDates A map with dates stock was bought as the key and a pair object
   *                 containing the price and number of stocks bought.
   */

  public SmartStock(String ticker, String data,
                    Map<LocalDate, Pair<Double,Double>> BuyDates,
                    boolean onlyInts) {

    this.SoldDates = new HashMap<LocalDate, Pair<Double,Double>>();
    //this.SoldInv = new HashMap<LocalDate, List<LocalDate>>();

    this.ticker = ticker;

    if (data.equals("API")) {
      this.stockData = DataHelpers.getStockData(ticker);
    } else {
      this.stockData = parseStock(data);
    }

    this.BuyDates = BuyDates;

    double myShares = 0.0;
    for(LocalDate key: this.BuyDates.keySet()) {
      double curShares = this.BuyDates.get(key).a;
      if(onlyInts) {
        if((curShares % 1) != 0) {
          throw new IllegalArgumentException("The number of shares must be a whole " +
                  "positive value. Please check your input and try again.");

        }
      }
      myShares += curShares;
    }

    this.shares = myShares;

    // This function gets the value at the current date
  }

  private SmartStock(String ticker, double shares, Map<LocalDate, Double> data,
                     Map<LocalDate, Pair<Double,Double>> myBuyDates) {
    this.shares = shares;
    this.ticker = ticker;
    this.stockData = data;
    this.BuyDates = myBuyDates;
  }

  public Map<LocalDate, Pair<Double,Double>> getBuyDates() {

    return this.BuyDates;
  }

  public Map<LocalDate, Double> getStockData() {

    return this.stockData;
  }

  /*
  IN PORTFOLIO:
  Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1, d2);
    char timeType = myPair.a;
    int timeSplit = myPair.b;

    THIS FUNCTION SHOULD RETURN THE VALUE AT EACH KEY GROUPING OF THE STOCK
    THE PORTFOLIO WILL THEN SPLIT THE VALUES AS NECESSARY

    THEN FOR EACH STOCK DATE WE GET AN OVERALL MAP and do myMap.merge(key, VALUE, Integer::sum).
    This way for each date we put in a value. If key exists in myMap we add VALUE to it, otherwise
    we create the key and map it to VALUE.

    For the first stock we will just use the value outputted, then for each subsequent stock we
    will add the values to it.
    https://stackoverflow.com/questions/40158605/merge-two-maps-with-java-8
   */
  public Map<LocalDate, Double> timeIntervalValues(String date1, String date2, char timeType) throws IllegalArgumentException {
    LocalDate d1;
    LocalDate d2;
    try{
      d1 = LocalDate.parse(date1);
      d2 = LocalDate.parse(date2);
    } catch(Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed. " +
              "Please check your inputs and try again");
    }


    LocalDate minDate = Collections.min(this.BuyDates.keySet());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Map<LocalDate, Double> newPrices;
    if(timeType == 'D') {
      newPrices =  this.getMyStockDate(date1, date2, dtf.format(minDate));
    } else {
      newPrices = DataHelpers.getStockData(this.ticker, date1,
              date2, timeType, dtf.format(minDate));
    }


    Comparator<LocalDate> comparator = LocalDate::compareTo;

    SortedSet<LocalDate> priceKeys = new TreeSet<>(comparator);
    priceKeys.addAll(newPrices.keySet());


    SortedSet<LocalDate> buyKeys = new TreeSet<>(comparator);
    buyKeys.addAll(this.BuyDates.keySet());

    Iterator<LocalDate> buyIt = buyKeys.iterator();
    LocalDate curBuyKey = buyIt.next();

    if(curBuyKey.isAfter(d2)){
      return new HashMap<LocalDate, Double>();
    }


    LocalDate nextBuy;
    if(buyIt.hasNext()) {
      nextBuy = buyIt.next();
    } else {
      nextBuy = null;
    }

    Pair<Double, Double> curBuy = BuyDates.get(curBuyKey);

    double curTotStock = curBuy.a;
    double curTotComiss = curBuy.b;


    //TODO: WE DONT NEED CURTOT COMISS THIS SHOULD ONLY BE IN THE COST BASIS
    // HERE WE ONLY NEED THE VALUE

    // This is the earliest date we check against our buy keys
    // If the next buy Key is still before the current date, sum the
    // total number of stocks and commission for the current state and update
    // otherwise we keep the current state as the start state
    LocalDate firstDate = priceKeys.first();
    boolean finDate = false;

    while(!Objects.isNull(nextBuy) && !finDate) {
      if(!nextBuy.isAfter(firstDate)) {
        curBuyKey = nextBuy;
        if(buyIt.hasNext()) {
          nextBuy = buyIt.next();
        } else {
          nextBuy = null;
        }
        curBuy = BuyDates.get(curBuyKey);
        curTotStock += curBuy.a;
        curTotComiss += curBuy.b;
      } else {
        finDate = true;
      }
    }


    //Pair<Double, Double> curStocks = this.BuyDates.get(curBuyKey);

    // This is the map with the total value at each date that we will return.
    return getIntervalPriceVals(priceKeys, curBuyKey,
            nextBuy, buyIt, curTotStock, curTotComiss, newPrices);

  }

  public Pair<Double, Double> getValue(String date) {
    LocalDate myKey;
    String output = "";
    Set<LocalDate> keys = stockData.keySet();

    if (date.contains("current")) {
      myKey = Collections.max(keys);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

    LocalDate minDate = Collections.min(this.BuyDates.keySet());
    if(myKey.isBefore(minDate)) {
      return new Pair<Double, Double>(0.0,0.0);
    }

    if(myKey.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Cannot retrieve future prices. " +
              "Try again.");
    }

    double myPrice;

    try {
      myPrice = stockData.get(myKey);
    } catch(NullPointerException e) {
      throw new IllegalArgumentException("Price could not be found at that date." +
              " Please try again.");
    }

    Pair<Double, Double> myComm = this.getShareCommm(date);

    // a = price at date and b = # of stocks at date
    return new Pair<Double, Double>(myPrice,myComm.a);

  }

  private Map<LocalDate, Double> getIntervalPriceVals(SortedSet<LocalDate> priceKeys,
                                                      LocalDate curBuyKey,
                                                      LocalDate nextBuy,
                                                      Iterator<LocalDate> buyIt,
                                                      double curTotStock,
                                                      double curTotComiss,
                                                      Map<LocalDate, Double> newPrices
                                                      ) {

    Map<LocalDate, Double> allVals = new HashMap<LocalDate, Double>();
    Pair<Double, Double> curBuy;

    for(LocalDate key: priceKeys) {

      // If our current date is after/equal to the current buy key
      // First check that we are before the next buy Key
      // If not then ignore --> we only want values after/on the first valid buy date
      // If true then check that we are before the next buyDate
      // If false then we update the buyDate, otherwise continue
      if(!key.isBefore(curBuyKey)) {
        // If we have a next key check that we are not on or after it
        // If we are not before then update the shares and comission fee
        if(!Objects.isNull(nextBuy) && !key.isBefore(nextBuy)) {
          curBuyKey = nextBuy;

          if(buyIt.hasNext()) {
            nextBuy = buyIt.next();
          } else {
            nextBuy = null;
          }

          curBuy = BuyDates.get(curBuyKey);
          curTotStock += curBuy.a;
          curTotComiss += curBuy.b;
        }

        // once everything is updated we can continue
        double curPrice = newPrices.get(key);
        double curValue = (curPrice * curTotStock);
        allVals.put(key, curValue);

      }

      // else if(current key is before the current buy key return nothing)

    }
    return allVals;
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

    //outBuild.append("|   TICKER   |    DATE    |    TOTAL SHARES    |");
    //outBuild.append("    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |\n");

    LocalDate minDate = Collections.min(this.BuyDates.keySet());
    boolean preBuyDate = myKey.isBefore(minDate);

    if (!stockData.containsKey(myKey)) {
      output = "| " + padOne(ticker, 6) + " | " + date + " | "
              + padOne(String.valueOf(shares), 18)
              + "     NO DATA      |      NO DATA      |      NO DATA      |";
    } else if (preBuyDate) {
      output = "| " + padOne(ticker, 6) + " | " + date + " | "
              + padOne("0", 18)
              + "       0          |      NO DATA      |        0          |";
    } else {
      // this makes sure we dont have big numbers in scientific notation
      // It will print up to 10 digits
      DecimalFormat df = new DecimalFormat("#");
      df.setMaximumFractionDigits(10);


      //TODO: fix here
      double myData = stockData.get(myKey);
      Pair <Double, Double> shareComm = this.getShareCommm(date);
      double curShares = shareComm.a;
      double costBasis = getCostBasis(date);
      // myData needs to include cost basis, close price, and total value
      String[] padded = getPadding(ticker, curShares, df, myData, costBasis);


      output = "| " + padded[0]
              + " | " + date
              + " | " + padded[1]
              + " | " + padded[2]
              + " | " + padded[3]
              + " | " + padded[4] + " |";

    }


    return output;
  }


  //TODO: Create doc

  public int getDataSize() {
    return this.stockData.size();
  }


  private String[] getPadding(String ticker, double shares, DecimalFormat myDF, double myData, double cBasis) {

    //outBuild.append("|   TICKER   |    DATE    |    TOTAL SHARES    |");
    //outBuild.append("    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |\n");
    int[] padAmount = new int[5];

    double totVal = myData * shares;

    padAmount[0] = Math.max(0, 10 - ticker.length());
    padAmount[1] = Math.max(0, 18 - myDF.format(shares).length());
    padAmount[2] = Math.max(0, 16 - myDF.format(cBasis).length());
    padAmount[3] = Math.max(0, 17 - myDF.format(myData).length());
    padAmount[4] = Math.max(0, 17 - myDF.format(totVal).length());


    int[] leftPad = new int[5];
    int[] rightPad = new int[5];

    for (int i = 0; i < 5; i++) {
      if (padAmount[i] != 0) {
        leftPad[i] = padAmount[i] / 2;
        rightPad[i] = padAmount[i] - leftPad[i];
      } else {
        leftPad[i] = 0;
        rightPad[i] = 0;
      }

    }

    String[] output = new String[5];

    output[0] = padRight(padLeft(ticker, leftPad[0]), rightPad[0]);
    output[1] = padRight(padLeft(myDF.format(shares), leftPad[1]), rightPad[1]);
    output[2] = padRight(padLeft(myDF.format(cBasis), leftPad[2]), rightPad[2]);
    output[3] = padRight(padLeft(myDF.format(myData), leftPad[3]), rightPad[3]);
    output[4] = padRight(padLeft(myDF.format(totVal), leftPad[4]), rightPad[4]);

    return output;
  }


    //TODO: FIX DESCRIPTION
  /**
   * This function returns the actual double value of any main
   * numeric stock data point. It takes a date which is in the
   * same format as above (if date == "current" then get the
   * latest value otherwise fetch the value at date). Unlike
   * above this one does not take an index and just returns
   * the whole array of inputs for efficiency.
   * @param date the date which to get data at.
   * @return the price of the stock on that date
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

  // used to change the number of shares if a certain ticker is
  // added to a portfolio multiple times
  public SmartStock addShares(String BuyData, boolean onlyInts) throws IllegalArgumentException {

    Map<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<>();
    myBuyDates.putAll(this.BuyDates);

    try {
      myBuyDates.putAll(parseBuyDates(BuyData, onlyInts));
    } catch (Exception e) {
      throw e;
    }

    double totShares = 0.0;
    for(LocalDate key: myBuyDates.keySet()) {
      totShares += myBuyDates.get(key).a;
    }


    return new SmartStock(this.ticker, totShares, this.stockData, myBuyDates);
  }

  public SmartStock addShares(Map<LocalDate, Pair<Double, Double>> buyData,
                              boolean onlyInts) throws IllegalArgumentException {

    Map<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<>();
    myBuyDates.putAll(this.BuyDates);

    try {
      myBuyDates.putAll(buyData);
    } catch (Exception e) {
      throw e;
    }

    double totShares = 0.0;
    for(LocalDate key: myBuyDates.keySet()) {
      double newShares = myBuyDates.get(key).a;
      if(onlyInts && (newShares % 1) != 0) {
        throw new IllegalArgumentException("Cannot have fractional shares. " +
                "Please try again.");
      }
      totShares += newShares;
    }


    return new SmartStock(this.ticker, totShares, this.stockData, myBuyDates);
  }

  public Stock addShares(double numShares) throws IllegalArgumentException {
    double totShares = this.shares + numShares;
    LocalDate myDate = LocalDate.now();

    HashMap<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<LocalDate, Pair<Double,Double>>();
    myBuyDates.putAll(this.BuyDates);

    Pair<Double,Double> myPair = new Pair<Double,Double>(numShares,0.0);
    myBuyDates.put(myDate,myPair);


    return new SmartStock(this.ticker, totShares, this.stockData, myBuyDates);
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

      if(info2[1].length() == 0) {
        if(info2[2].length() != 0) {
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

  public String buyToJSON() {

    StringBuilder outBuild = new StringBuilder();
    List<LocalDate> topDates = new ArrayList<LocalDate>(this.BuyDates.keySet());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(10);


    for (int i = 0; i < topDates.size(); i++) {
      LocalDate key = topDates.get(i);
      Pair<Double, Double> curPair = this.BuyDates.get(key);
      outBuild.append("          \"");
      outBuild.append(dtf.format(key)).append("\": [ \"");
      outBuild.append(df.format(curPair.a)).append("\", \"");
      outBuild.append(df.format(curPair.b)).append("\"]");

      if (i == topDates.size() - 1) {
        outBuild.append("\n");
      } else {
        outBuild.append(",\n");
      }

    }
    //l = l.subList(0,10);
    return outBuild.toString();
  }


  /**
   * Method to get the stock data from the stocks price list.
   *
   * This method also allows you to input date strings to get daily data between the two dates.
   * If the date starts in the middle of a time frame type it will return the price at the end
   * of the timeframe.
   * @param date1 This is the date to start our time frame
   * @param date2 This is a second optional date to end gathering stock data after.
   * @param buyDate The first date this stock was bought
   * @throws IllegalArgumentException
   * @return the map of date and ticker.
   */


  private Map<LocalDate, Double> getMyStockDate(String date1,
                                                String date2,
                                                String buyDate) throws IllegalArgumentException {
    //System.out.println("WHAT?");

    // This part is setup for our later parsing

    LocalDate startDate;
    LocalDate endDate;
    try{
      startDate = LocalDate.parse(date1);
      endDate = LocalDate.parse(date2);
    } catch(Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed! " +
              "Please check that your date input strings are in YYYY-MM-DD format.");
    }


    if( startDate.isAfter(endDate) ) {
      throw new IllegalArgumentException("The end date must be after the start date." +
              " Please recheck your date inputs and try again.");
    }

    LocalDate buyD;

    try{
      buyD = LocalDate.parse(buyDate);
    } catch(Exception e) {
      throw new IllegalArgumentException("Your buy-date could not be parsed!" +
              " Please check the input.");
    }


    if(buyD.isAfter(endDate)) {
      return new HashMap<LocalDate, Double>();
    }

    final LocalDate finStart;
    if (buyD.isAfter(startDate)) {
      finStart = buyD;
    } else {
      finStart = startDate;
    }

    HashMap<LocalDate, Double> newData = new HashMap<LocalDate, Double>(this.stockData);

    newData.keySet().removeIf(k -> k.isBefore(finStart) || k.isAfter(endDate));


    return newData;
  }


  //TODO Write descriptor for getting commission paid and amount of shares
  //TODO test proper shareComm functioning in the case of selling stock
  //TODO check other functions that use getShareComm

  // Returns a pair <Double,Double> that holds the # of shares and the commission
  // at a given date --> if shares were sold then the commission is summed and
  // shares subtracted
  public Pair<Double, Double> getShareCommm (String date) {

    Map<LocalDate, Pair<Double, Double>> trimShareDates;
    Map<LocalDate, Pair<Double, Double>> trimSellDates;

    if(date == "current") {
      trimShareDates = this.BuyDates;
      trimSellDates = this.SoldDates;
    } else {
      trimShareDates = trimBuyList(date);
      trimSellDates = trimSellList(date);
    }

   if(!date.equals("current")) {
     LocalDate checkKey;
     try{
       checkKey = LocalDate.parse(date);
     } catch(Exception e) {
       throw new IllegalArgumentException("Cannot parse date string. ");
     }

     LocalDate minDate = Collections.min(this.BuyDates.keySet());

     if(checkKey.isBefore(minDate)) {
       return new Pair<Double, Double>(0.0, 0.0);
     }

     if(checkKey.isAfter(LocalDate.now())) {
       throw new IllegalArgumentException("Cannot retrieve future prices. " +
               "Try again.");
     }
   }



    Pair<Double, Double> totShareComm = new Pair<>(0.0, 0.0);

    for( LocalDate key: trimShareDates.keySet()) {
      totShareComm = totShareComm.add(trimShareDates.get(key));
    }

    if(! trimSellDates.isEmpty()) {
      for(LocalDate sellKey: trimSellDates.keySet()) {
        totShareComm = totShareComm.addBMinusA(trimSellDates.get(sellKey));
      }
    }

    return totShareComm;

  }

  //TODO: Double check cost basis works properly with sales
  public double getCostBasis(String date) throws IllegalStateException {


    Pair<Double, Double> shareComm;

    Map<LocalDate, Pair<Double, Double>> trimShareDates;
    Map<LocalDate, Pair<Double, Double>> trimSellDates;

    if(date == "current") {
      trimShareDates = this.BuyDates;
      trimSellDates = this.SoldDates;
    } else {
      trimShareDates = trimBuyList(date);
      trimSellDates = trimSellList(date);
    }

    if(!date.equals("current")) {
      LocalDate checkKey;
      try{
        checkKey = LocalDate.parse(date);
      } catch(Exception e) {
        throw new IllegalArgumentException("Cannot parse date string. ");
      }

      LocalDate minDate = Collections.min(this.BuyDates.keySet());

      if(checkKey.isBefore(minDate)) {
        return 0.0;
      }

      if(checkKey.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Cannot retrieve future prices. " +
                "Try again.");
      }
    }



    double costBasis = 0;
    for( LocalDate key: trimShareDates.keySet()) {

      //String StockData = this.stockData.keySet().toString();
      if(! this.stockData.containsKey(key)) {
        StringBuilder myError = new StringBuilder();
        myError.append("The price at the buy date given [ ")
                .append(key.toString())
                .append(" ] could not be found in the stock's [")
                .append(ticker).append(" ] list of prices. If you manually inputted data " +
                        "please check that the price inputs include the buy date.");
        throw new IllegalStateException(myError.toString());
      }
      double myPrice = this.stockData.get(key);
      shareComm = trimShareDates.get(key);

      costBasis += (myPrice * shareComm.a) + shareComm.b;
    }

    if(! trimSellDates.isEmpty()) {
      for(LocalDate sellKey: trimSellDates.keySet()) {
        costBasis += trimSellDates.get(sellKey).b;
      }
    }
    return costBasis;

  }

  private HashMap<LocalDate, Pair<Double, Double>> trimBuyList(String date){
    LocalDate start = LocalDate.parse(date);

    HashMap<LocalDate, Pair<Double, Double>> trimShareDates = new
            HashMap<LocalDate,  Pair<Double, Double>>(this.BuyDates);

    trimShareDates.keySet().removeIf(k -> k.isAfter(start));
    return trimShareDates;
  }


  private HashMap<LocalDate, Pair<Double, Double>> trimSellList(String date){
    LocalDate start = LocalDate.parse(date);

    HashMap<LocalDate, Pair<Double, Double>> trimSellDates = new
            HashMap<LocalDate,  Pair<Double, Double>>(this.SoldDates);

    trimSellDates.keySet().removeIf(k -> k.isAfter(start));
    return trimSellDates;
  }

}




//toString();
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
