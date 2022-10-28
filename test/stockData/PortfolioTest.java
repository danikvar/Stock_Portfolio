package stockData;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class PortfolioTest {

  @org.junit.Test
  public void addStock() {
    Portfolio myPort = new Portfolio();
    myPort.addStock("GOOG",3);
    myPort.addStock("GOOG",2);
    myPort.addStock("NVDA",1);
    double[] myVals = myPort.getTotalValues("current");
    System.out.println(myPort.size());


    System.out.println("**********************FINAL**********************");
    System.out.println("open");
    System.out.println(myVals[0]);
    System.out.println("high");
    System.out.println(myVals[1]);
    System.out.println("low");
    System.out.println(myVals[2]);
    System.out.println("close");
    System.out.println(myVals[3]);
    System.out.println("Volume");
    System.out.println(myVals[4]);
    System.out.println("Done");
    for(double i:myVals) {
      System.out.println(i);
    }
  }

  @Test
  public void stockDataGetter() {
    HashMap<LocalDate, double[]> allStocks = DataHelpers.getStockData("GOOG");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    Set<LocalDate> keys = allStocks.keySet();
    for ( LocalDate key : keys ) {

      System.out.println( dtf.format(key));
    }
    System.out.println( "_____________MAX_______________" );

    System.out.println(dtf.format(Collections.max(keys)));
  }

  @Test
  public void tickerTest() {
    Set<String> allStocks = DataHelpers.getTickers();

    System.out.println(allStocks.toString());
  }

  @Test
  public void stockStringTest() {
    stockData.Stock1 myStock = new stockData.Stock1("GOOG", 12);

    stockData.Stock1 myStock2 = new stockData.Stock1("NVDA", 23);
    StringBuilder outBuild = new StringBuilder().append(
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");




    outBuild.append("| TICKER |    DATE    |    SHARES     |     OPEN PRICE     |");
    outBuild.append("    HIGH PRICE    |    LOW PRICE    |    CLOSE PRICE    |    VOLUME    |");
    System.out.println(outBuild.toString());
    System.out.println(myStock.printDataAt("current"));
    System.out.println(myStock2.printDataAt("current"));
  }

}