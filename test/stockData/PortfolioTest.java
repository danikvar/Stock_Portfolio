package stockData;

import org.junit.Test;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortfolioTest {

  @org.junit.Test
  public void addStock() {
    Portfolio myPort = new Portfolio();
    myPort.addStock("GOOG",3, "API");
    myPort.addStock("GOOG",2, "API");
    myPort.addStock("NVDA",1, "API");
    /*
    double[] myVals = myPort.getTotalValues("current");
    System.out.println(myPort.size());


    System.out.println("**********************FINAL**********************");
    System.out.println("open");
    System.out.println(myVals[0]);
    System.out.println("Done");
    for(double i:myVals) {
      System.out.println(i);
    }

     */

    System.out.println("_______________________________________________________");
    System.out.println(myPort.printPortfolioAt("current"));

    //System.out.println("_______________________________________________________");
    //System.out.println(myPort.portToJSON());
  }

  @Test
  public void stockDataGetter() {
    Map<LocalDate, Double> allStocks = DataHelpers.getStockData("GOOG");
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
    stockData.Stock1 myStock = new stockData.Stock1("GOOG", 12, "API");

    stockData.Stock1 myStock2 = new stockData.Stock1("NVDA", 23, "API");
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");




    outBuild.append("| TICKER |    DATE    |    SHARES     |");
    outBuild.append("     OPEN PRICE     |    SHARE VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
    System.out.println(outBuild.toString());
    System.out.println(myStock.printDataAt("current"));
    System.out.println(myStock2.printDataAt("current"));
  }

  @Test
  public void ScannerTest() {
    String content = "{\n  \"GOOG\":5,\n  \"NVDA\":1 \n}";
    System.out.println(content);
    Scanner sc = new Scanner(content);
    while (sc.hasNext()) {
      String myLine = sc.next();

      boolean startEnd = myLine.contains(new StringBuilder("{"))
              || myLine.contains(new StringBuilder("}"));
      if(! startEnd) {

        System.out.println("SCANNER 1");
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(myLine);
        m.find();
        System.out.println(m.group(1));

        System.out.println("SCANNER 2342");

        int num = Integer.parseInt(myLine.replaceAll("[^0-9]", ""));
        System.out.println(num);
        System.out.println("\n");
        System.out.printf(myLine);
        System.out.println("\n");
      }

    }
  }

}