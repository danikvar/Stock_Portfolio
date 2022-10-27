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
      output = "| " + ticker + " | " + date + " | " + String.valueOf(shares)
              + " | " +  df.format(myData[0])
              + " | " + df.format(myData[1])
              + " | " + df.format(myData[2])
              + " | " + df.format(myData[3])
              + " | " + df.format(myData[4]) + " |";
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

}

//toString();
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
//| TICKER | DATE | Num_Shares | open | high|  low| close| volume|
