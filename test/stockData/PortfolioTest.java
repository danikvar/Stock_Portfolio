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
    myPort.addStock("OK",1);
    System.out.println(myPort.size());
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
    System.out.println(myStock.printDataAt("current"));
  }

}