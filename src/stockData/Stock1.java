package stockData;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import java.util.Map;
import java.util.Set;

import static stockData.DataHelpers.padLeft;
import static stockData.DataHelpers.padRight;

public class Stock1 {



  // buyDate - The initial date the stock was bought
  //    This should be in the format: YYYY-MM-DD
  private int shares;
  private String ticker;
  // I changed this to a date object from string so we cna compare dates
  // using the Collections.max() function
  // They key will be a LocalDate object in 'YYYY-MM-DD' format
  // and the values in the int[] will be
  // (0:open,1:high,2:low,3:close,4:volume) - length = 5
  private Map<LocalDate, Double> stockData;

  public Stock1(String ticker, int shares, String data) {
    this.shares = shares;
    this.ticker = ticker;

    if( data.equals("API")) {
      this.stockData = DataHelpers.getStockData(ticker);
    } else {
      this.stockData = parseStock(data);
    }


    // This function gets the value at the current date
  }

  // If the date string passed to this == "current" then it will
  // fetch the most current date available in stockData, otherwise it
  // searches stockData for key matching the date inputted.
  // If this date is not found it will print "NO DATA" in each of
  // the columns except the ticker column
  public String printDataAt(String date){

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    String output = "";

    if( date == "current") {
      myKey = Collections.max(keys);
      date = dtf.format(myKey);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

    if(!stockData.containsKey(myKey)){
      output = "| " + ticker + " | " + date + " | " + String.valueOf(shares)
              + " | NO DATA  | NO DATA |";
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



  // This function returns the actual double value of any main
  // numeric stock data point. It takes a date which is in the
  // same format as above (if date == "current" then get the
  // latest value otherwise fetch the value at date). Unlike
  // above this one does not take an index and just returns
  // the whole array of inputs for efficiency.
  // TODO: Update here
  public double getData(String date){

    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    double output = 0.0;

    if( date == "current") {
      myKey = Collections.max(keys);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

    //TODO: Double check this function
    if( stockData.containsKey(myKey)) {
      output = stockData.get(myKey);
    }

    return output;
  }

  // basic getter for # of shares
  public int getShares() {
    return this.shares;
  }

  // basic getter for the ticker
  public String getTicker() {
    return this.ticker;
  }

  // toString() no prints most the current data
  @Override
  public String toString(){
    return this.printDataAt("current");
  }

  // used to change the number of shares if a certain ticker is
  // added to a portfolio multiple times
  public void addShares(int numShares) {
    this.shares += numShares;
  }

  private String[] getPadding( String ticker, int shares, DecimalFormat myDF, double myData) {
    int[] padAmount = new int[4];

    double totVal = myData * ((double) shares);

    padAmount[0] = Math.max(0, 6 - ticker.length());
    padAmount[1] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[2] = Math.max(0, 18 - myDF.format(myData).length());
    padAmount[3] = Math.max(0, 17 - myDF.format(totVal).length());


    int[] leftPad = new int[4];
    int[] rightPad = new int[4];

    for( int i = 0; i < 4; i ++) {
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
  private Map<LocalDate, Double> parseStock(String data) {


    return null;
  }


}




//toString();
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
