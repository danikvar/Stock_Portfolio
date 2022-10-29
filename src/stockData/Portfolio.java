package stockData;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import static stockData.DataHelpers.padLeft;
import static stockData.DataHelpers.padRight;

public class Portfolio implements StockPortfolio{
  Map<String, Stock1> StockList;

  public Portfolio(){
    this.StockList = new HashMap<String, Stock1>();
  }

  /**
   * I changed this method signature to be void since it should just change the object.
   * There is no reason to return something every time we add a stock.
   * If the ticker is not already in the portfolio then add the stock to the
   * portfolio, otherwise find the stock in the portfolio and update the number
   * of shares.
   * @param Stock
   * @param shares
   */
  @Override
  public void addStock(String Stock, int shares) {
    if(!StockList.containsKey(Stock)) {
      Stock1 in_1 = new Stock1(Stock,shares);
      StockList.put(Stock, in_1);
    } else {
      Stock1 myStock = StockList.get(Stock);
      myStock.addShares(shares);
    }

  }

  public int size() {
    return StockList.size();
  }

  public String printDataAt(String date) {
    return "";
  }
  @Override
  public double[] getTotalValues(String date) {
    double[] myVal = new double[] {0,0,0,0,0};
    if( StockList.size() > 0) {

      for(String myKey: StockList.keySet()) {

        //System.out.println("KEY:");
        //System.out.println(myKey);

        Stock1 myStock = StockList.get(myKey);

        /*
        System.out.println("NumShares:");
        System.out.println(myStock.getShares());
        System.out.println("open");
        System.out.println(myStock.getData(date)[0]);
        System.out.println("high");
        System.out.println(myStock.getData(date)[1]);
        System.out.println("low");
        System.out.println(myStock.getData(date)[2]);
        System.out.println("close");
        System.out.println(myStock.getData(date)[3]);
        System.out.println("Volume");
        System.out.println(myStock.getData(date)[4]);
        System.out.println("Done");
        */

        double[] stockData = valueForStock(myStock.getData(date), myStock.getShares());

        Arrays.setAll(myVal, i -> myVal[i] + stockData[i]);
      }
    }
    return myVal;
  }

  @Override
  public int getNumStocks() {
    int myVal = 0;
    if( StockList.size() > 0) {

      for(String myKey: StockList.keySet()) {

        Stock1 myStock = StockList.get(myKey);

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
   * current then it fetches the data for the last available date for the stock.
   *
   * For each stock the PRICE columns reflect the price for a single stock in the
   * portfolio while for the bottom row those columns reflect a total value by the
   * formula [SUM (Shares_i * Price_i)].
   * @param date the date to find the value at
   * @return a table formatted string displaying the portfolio
   */
  public String printPortfolioAt(String date) {
    StringBuilder outBuild = new StringBuilder().append(
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    outBuild.append("| TICKER |    DATE    |    SHARES     |     OPEN PRICE     |");
    outBuild.append("    HIGH PRICE    |    LOW PRICE    |    CLOSE PRICE    |    VOLUME    | \n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    for (String stockName : StockList.keySet()) {
      Stock1 myStock = StockList.get(stockName);
      outBuild.append(myStock.printDataAt(date) + "\n");
    }

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(10);

    String[] paddedVals = getPortPadding(
            String.valueOf(this.getNumStocks()),
            df, this.getTotalValues(date)
    );


    outBuild.append("| TOTAL  | ");

    if( date.equals("current") ) {
      outBuild.append("  current ");
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
    outBuild.append(" | ");
    outBuild.append(paddedVals[4]);
    outBuild.append(" | ");
    outBuild.append(paddedVals[5]);
    outBuild.append(" | \n");

    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


    return outBuild.toString();
  }

  // adds to arrays in an element wise fashion
  private double[] applyOn2Arrays(BinaryOperator<Double> operator, double[] a, double b[]) {
    return IntStream.range(0, a.length)
            .mapToDouble(index -> operator.apply(a[index], b[index]))
            .toArray();
  }

  private static double[] valueForStock(double[] array, int val) {
    return Arrays.stream(array).map(i -> i * val).toArray();
  }


  private String[] getPortPadding(String shares, DecimalFormat myDF, double[] myData) {
    int[] padAmount = new int[6];

    padAmount[0] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[1] = Math.max(0, 18 - myDF.format(myData[0]).length());
    padAmount[2] = Math.max(0, 16 - myDF.format(myData[1]).length());
    padAmount[3] = Math.max(0, 15 - myDF.format(myData[2]).length());
    padAmount[4] = Math.max(0, 17 - myDF.format(myData[3]).length());
    padAmount[5] = Math.max(0, 12 - myDF.format(myData[4]).length());


    int[] leftPad = new int[6];
    int[] rightPad = new int[6];

    for( int i = 0; i < 6; i ++) {
      if (padAmount[i] != 0) {
        leftPad[i] = padAmount[i] / 2;
        rightPad[i] = padAmount[i] - leftPad[i];
      } else {
        leftPad[i] = 0;
        rightPad[i] = 0;
      }

    }

    String[] output = new String[6];

    output[0] = padRight(padLeft(shares, leftPad[0]), rightPad[0]);
    for (int i = 1; i < 6; i++) {
      output[i] = padRight(padLeft(myDF.format(myData[i-1]), leftPad[i]), rightPad[i]);
    }

    return output;
  }

  @Override
  public String toString() {
    return this.printPortfolioAt("current");
  }

  @Override
  public String portToJSON() {
    StringBuilder outBuild = new StringBuilder().append("{ \n");

    Iterator<String> iterator = StockList.keySet().iterator();
    while (iterator.hasNext()) {

      String stockName = iterator.next();
      Stock1 myStock = StockList.get(stockName);
      outBuild.append("  \"");
      outBuild.append(stockName);
      outBuild.append("\":");
      outBuild.append(String.valueOf(myStock.getShares()));
      if(iterator.hasNext()) {
        outBuild.append(",\n");
      }else{
        outBuild.append("\n}");
      }

    }


    return outBuild.toString();
  }


}

