package stockData;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Portfolio implements StockPortfolio{
  HashMap<String, Stock1> StockList;

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

        System.out.println("KEY:");
        System.out.println(myKey);

        Stock1 myStock = StockList.get(myKey);

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
        double[] stockData = valueForStock(myStock.getData(date), myStock.getShares());

        Arrays.setAll(myVal, i -> myVal[i] + stockData[i]);
      }
    }
    return myVal;
  }

  public String printPortfolioAt(String date) {
    StringBuilder outBuild = new StringBuilder().append(
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    outBuild.append("| TICKER |    DATE    |    SHARES     |     OPEN PRICE     |");
    outBuild.append("    HIGH PRICE    |    LOW PRICE    |    CLOSE PRICE    |    VOLUME    |");


    return " ";
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
}

