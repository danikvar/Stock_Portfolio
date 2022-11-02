package stockData;

import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    Set<String> allStocks2 = DataHelpers.getTickers();
    Set<String> allStocks = new HashSet<String>();
    try{
      File nameFile = new File("Tickers.txt");
      Scanner scan = new Scanner(nameFile);
      String line;
      while(scan.hasNextLine()) {
        line = scan.nextLine();
        allStocks.add(line);

      }
    } catch(FileNotFoundException e) {

    }
    System.out.println(allStocks2.equals(allStocks));
    System.out.println(allStocks.toString());
  }

  @Test
  public void stockStringTest() {
    stockData.Stock1 myStock = new stockData.Stock1("GOOG", 12, "API");
    System.out.println(myStock.sharesToJSON());

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

  public Map<LocalDate, Double> parseStock(String data) throws IllegalArgumentException {

    if (!data.matches("[0-9-,.); (]+")) {
      throw new IllegalArgumentException("Unexpected character was found in stock data. "
              + "Please try again.");
    }

    Map<LocalDate, Double> stockDateData = new HashMap<LocalDate, Double>();
    String[] dateInfo = data.split(";");

    for (int i = 0; i < dateInfo.length; i++) {

      System.out.println(dateInfo[i]);
      String m2 = dateInfo[i].replaceAll("[() ]", "");

      String[] info2 = m2.split(",");

      // This will throw an error if the date was entered wrong so ok here
      LocalDate myKey = LocalDate.parse(info2[0]);
      // This will throw an error if the # was entered wrong so we should be good here.
      stockDateData.put(myKey, Double.parseDouble(info2[1]));
    }



    return stockDateData;
  }

  @Test
  public void dateParsing() {
    String line = "(2022-10-15,55.5);(2022-10-14,44.4);(2022-08-15,33.3);(2022-07-15,22.2)";

    Map<LocalDate, Double> myMap = parseStock(line);

    for(LocalDate key: myMap.keySet()) {
      System.out.println("okay?");
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
      System.out.println("*************************************************");
      System.out.println(myMap.containsKey(key));
    }

    List<LocalDate> topDates = new ArrayList<LocalDate>(myMap.keySet());
    Collections.sort(topDates,Collections.reverseOrder());
    topDates = topDates.subList(0,2);

    for(int i = 0; i < 2; i++) {
      System.out.println(topDates.get(i));
    }
    //System.out.println(x.length());
  }

  @Test
  public void loadPortTest(){

    String userName = "Test_User";
    String PortfolioName = "port1.json";
    Portfolio myPort;
    try {
      myPort = (Portfolio) DataHelpers.loadPortfolio(userName, PortfolioName);
      System.out.println(myPort.toString());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    String expected = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n"
            + "| TICKER |    DATE    |    SHARES     |     OPEN PRICE     |    SHARE VALUE    |\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n"
            + "|  GOOG  | 2022-11-01 |      10       |       "
            + "95.78        |       957.8       |\n"
            + "|  NVDA  | 2022-07-15 |      100      |"
            + "        20.3        |       2030        |\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n"
            + "| TOTAL  |   Current  |      110      |"
            + "       116.08       |      2987.8       |\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n";
    //Assert.assertEquals(expected, myPort.toString());

    System.out.println(myPort.portToJSON());

    myPort.save("newTest1", userName);


  }
}
