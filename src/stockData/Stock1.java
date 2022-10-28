package stockData;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

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
  private HashMap<LocalDate, double[]> stockData;

  public Stock1(String ticker, int shares) {
    this.shares = shares;
    this.ticker = ticker;

    this.stockData = DataHelpers.getStockData(ticker);

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
              + " | NO DATA  | NO DATA | NO DATA | NO DATA | NO DATA |";
    } else {
      // this makes sure we dont have big numbers in scientific notation
      // It will print up to 10 digits
      DecimalFormat df = new DecimalFormat("#");
      df.setMaximumFractionDigits(10);



      double[] myData = stockData.get(myKey);

      String[] padded = getPadding(ticker, String.valueOf(shares), df, myData);


      output = "| " + padded[0]
              + " | " + date
              + " | " + padded[1]
              + " | " + padded[2]
              + " | " + padded[3]
              + " | " + padded[4]
              + " | " + padded[5]
              + " | " + padded[6] + " |";
    }


    return output;
  }

  // This function returns the actual double value of any main
  // numeric stock data point. It takes a date which is in the
  // same format as above (if date == "current" then get the
  // latest value otherwise fetch the value at date). It also
  // takes an index which designates which data point to return
  // (0:open,1:high,2:low,3:close,4:volume)

  public double getData(String date, int index){

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    double output = 0.0;

    if( date == "current") {
      myKey = Collections.max(keys);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

    if( stockData.containsKey(myKey)) {
      output = stockData.get(myKey)[index];
    }

    return output;
  }

  // This function returns the actual double value of any main
  // numeric stock data point. It takes a date which is in the
  // same format as above (if date == "current" then get the
  // latest value otherwise fetch the value at date). Unlike
  // above this one does not take an index and just returns
  // the whole array of inputs for efficiency.
  public double[] getData(String date){

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    Set<LocalDate> keys = stockData.keySet();

    LocalDate myKey = null;
    double[] output = null;

    if( date == "current") {
      myKey = Collections.max(keys);
    } else {
      // create a date object from our string date
      myKey = LocalDate.parse(date);
    }

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

  private String[] getPadding( String ticker, String shares, DecimalFormat myDF, double[] myData) {
    int[] padAmount = new int[7];

    padAmount[0] = Math.max(0, 6 - ticker.length());
    padAmount[1] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[2] = Math.max(0, 18 - myDF.format(myData[0]).length());
    padAmount[3] = Math.max(0, 16 - myDF.format(myData[1]).length());
    padAmount[4] = Math.max(0, 15 - myDF.format(myData[2]).length());
    padAmount[5] = Math.max(0, 17 - myDF.format(myData[3]).length());
    padAmount[6] = Math.max(0, 12 - myDF.format(myData[4]).length());


    int[] leftPad = new int[7];
    int[] rightPad = new int[7];

    for( int i = 0; i < 7; i ++) {
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

    output[1] = padRight(padLeft(shares, leftPad[1]), rightPad[1]);
    for (int i = 2; i < 7; i++) {
      output[i] = padRight(padLeft(myDF.format(myData[i-2]), leftPad[i]), rightPad[i]);
    }

    return output;
  }

  public String padRight(String s, int n) {
    for(int i = 0; i < n; i++) {
      s = s + " ";
    }
    return s;
  }

  public String padLeft(String s, int n) {
    for(int i = 0; i < n; i++) {
      s = " " + s;
    }
    return s;
  }
}




//toString();
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
