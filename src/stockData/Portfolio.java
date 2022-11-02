package stockData;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static stockData.DataHelpers.getTickers;
import static stockData.DataHelpers.padLeft;
import static stockData.DataHelpers.padRight;

public class Portfolio implements StockPortfolio{
  private Map<String, Stock1> StockList;
  private Set<String> stockTickers;

  public Portfolio(){
    this.StockList = new HashMap<String, Stock1>();
    this.stockTickers = getTickers();
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
  public void addStock(String Stock, int shares, String data) throws IllegalArgumentException {

    if(!stockTickers.contains(Stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    Stock1 in_1;
    if(!StockList.containsKey(Stock)) {
      in_1 = new Stock1(Stock,shares, data);
    } else {
      Stock1 myStock = StockList.get(Stock);
      in_1 = myStock.addShares(shares);
    }

    StockList.put(Stock, in_1);

  }

  public void addStock(String Stock, int shares, Map<LocalDate, Double> priceData) throws IllegalArgumentException {

    if(!stockTickers.contains(Stock)) {
      throw new IllegalArgumentException("The ticker provided is not a valid ticker."
              + "Please check the ticker and try again.");
    }

    Stock1 in_1;
    if(!StockList.containsKey(Stock)) {
      in_1 = new Stock1(Stock,shares, priceData);
    } else {
      Stock1 myStock = StockList.get(Stock);
      in_1 = myStock.addShares(shares);
    }

    StockList.put(Stock, in_1);

  }

  public int size() {
    return StockList.size();
  }

  public String printDataAt(String date) {
    return "";
  }
  @Override
  public double[] getTotalValues(String date) {
    double[] myVal = new double[] {0.0, 0.0};
    if( StockList.size() > 0) {

      for(String myKey: StockList.keySet()) {

        //System.out.println("KEY:");
        //System.out.println(myKey);

        Stock1 myStock = StockList.get(myKey);

        double price = myStock.getData(date);
        double totVal = price * ((double) myStock.getShares());
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

        // TODO: Update Here
        myVal[0] += price;
        myVal[1] += totVal;

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
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    outBuild.append("| TICKER |    DATE    |    SHARES     |");
    outBuild.append("     OPEN PRICE     |    SHARE VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    for (String stockName : StockList.keySet()) {
      Stock1 myStock = StockList.get(stockName);
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

    if( date.equals("current") ) {
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

  /*
  // adds to arrays in an element wise fashion
  private double[] applyOn2Arrays(BinaryOperator<Double> operator, double[] a, double b[]) {
    return IntStream.range(0, a.length)
            .mapToDouble(index -> operator.apply(a[index], b[index]))
            .toArray();
  }

  private static double[] valueForStock(double[] array, int val) {
    return Arrays.stream(array).map(i -> i * val).toArray();
  }

   */


  private String[] getPortPadding(int shares, DecimalFormat myDF, double[] myData) {
    int[] padAmount = new int[3];

    padAmount[0] = Math.max(0, 13 - String.valueOf(shares).length());
    padAmount[1] = Math.max(0, 18 - myDF.format(myData[0]).length());
    padAmount[2] = Math.max(0, 17 - myDF.format(myData[1]).length());


    int[] leftPad = new int[3];
    int[] rightPad = new int[3];

    for( int i = 0; i < 3; i ++) {
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

  @Override
  public String toString() {
    return this.printPortfolioAt("current");
  }

  /**
   * THIS SAVES DATA MANUALLY ALWAYS BUT ONLY PRINTS THE LAST 50 DAYS
   * @return
   */
  @Override
  public String portToJSON() {
    StringBuilder outBuild = new StringBuilder().append("{ \n");
    outBuild.append("  \"Portfolio\": {\n");

    Iterator<String> iterator = StockList.keySet().iterator();
    int cnt = 0;
    while (iterator.hasNext()) {

      outBuild.append("    \"Stock"+ String.valueOf(cnt)
              + "\": {\n");
      String stockName = iterator.next();
      Stock1 myStock = StockList.get(stockName);
      outBuild.append("      \"ticker\": \"" + stockName + "\",\n");
      outBuild.append("      \"shares\": \""
              + String.valueOf(myStock.getShares())
              + "\",\n");
      outBuild.append("      \"priceData\": [\n"
              + "        {\n");
      outBuild.append(myStock.sharesToJSON());
      outBuild.append("        }\n" +
              "      ]\n" +
              "    }");
      if(iterator.hasNext()) {
        outBuild.append(",\n");
      }else{
        outBuild.append("\n");
      }
      cnt++;

    }
    outBuild.append("  }\n" +
            "}");

    return outBuild.toString();
  }

  /**
   *
   * This saves the current portfolio to the users chosen portfolio directory
   * @param fileName the name of the file to save portfolio as
   * @param User the name of the user to save to
   * @throws IllegalArgumentException
   */
  @Override
  public void save(String fileName, String User) throws IllegalArgumentException{
    if(!fileName.matches("[A-Za-z0-9]+")) {
      throw new IllegalArgumentException("File names may only contain letters and numbers."
              + "If your filename has an extension please remove it. (It will be saved as .json)");
    }

    StringBuilder fullName = new StringBuilder();
    try {
      fullName.append(DataHelpers.getPortfolioDir(User));
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


}

