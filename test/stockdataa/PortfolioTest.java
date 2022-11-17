package stockdataa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class that tests if portfolio operations are performed accurately.
 */

public class PortfolioTest {

  private Random int_gen;
  private Random int_gen2;

  private Random int_gen3;

  private Random int_gen4;

  private Random dgen1;

  private Random dgen2;

  @Before
  public void setUp() throws Exception {
    this.int_gen = new Random(100);
    this.int_gen2 = new Random(101);
    this.int_gen3 = new Random(102);
    this.int_gen4 = new Random(103);
    this.dgen1 = new Random(104);
    this.dgen2 = new Random(105);
  }

  @Test
  public void getTickers() {
    Set<String> APIOutput = DataHelpers.getAPITickers();
    for(int i = 0; i < 30; i++) {
      try{
        DataHelpers.getAPITickers();
        if(i > 10){
          fail();
        }
      } catch(RuntimeException e) {
        System.out.println("Test Passed");
      }

    }
    try {
      Set<String> localOutput = DataHelpers.loadLocalTickers();
      Assert.assertTrue(APIOutput.size() == localOutput.size());
      Assert.assertTrue(APIOutput.containsAll(localOutput));
      Assert.assertTrue(localOutput.containsAll(APIOutput));
    } catch (FileNotFoundException e) {
      fail();
    }


  }
  @Test
  public void addBigRegStock() {

    Set<String> localOutput = new HashSet<>();
    try {
      localOutput = DataHelpers.loadLocalTickers();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] tickers = localOutput.stream().toArray(String[]::new);




    String alphabet = "ABCDEFGHIJLKMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz";

    Portfolio myPort = new Portfolio();
    int[] n1 = int_gen.ints(tickers.length, 0, 9).toArray();
    int[] n2 = int_gen2.ints(tickers.length, 0, 9).toArray();
    int[] n3 = int_gen3.ints(tickers.length, 1, 9).toArray();
    int[] n4 = int_gen4.ints(tickers.length, 4, 9).toArray();
    int[] n5 = int_gen4.ints(tickers.length, 0, alphabet.length() - 1).toArray();
    double[] prices = dgen1.doubles(tickers.length,
            0, 500).toArray();

    double totPrice = 0;
    int totShare = 0;
    double totVal = 0;
    int totStock = 0;

    String setDate = "1000-01-01";

    Set<LocalDate> maxDates = new HashSet<>();

    for(int i = 0; i < 100; i++) {

      String myTicker = tickers[i];
      int num1 = n1[i];
      int num2 = n2[i];
      int num3 = n3[i];
      int num4 = n4[i];
      int num5 = n5[i];
      double price1 = prices[i];
      double price2 = price1 * num1;
      double price3 = price1 * num3;

      String m1;
      String m2;

      int m11 = num3 + num2;
      if(m11 > 12) {
        m1 = "12";
      } else if( m11 == 0) {
        m1 = "1";
      } else {
        if(m11 < 10) {
          m1 = "0" + String.valueOf(m11);
        } else {
          m1 = String.valueOf(m11);
        }
      }

      int m12 = num3 + num2;
      if(m12 > 12) {
        m2 = "12";
      } else if( m11 == 0) {
        m2 = "1";
      } else {
        if(m12 < 10) {
          m2 = "0" + String.valueOf(m11);
        } else {
          m2 = String.valueOf(m11);
        }
      }

      String smallDate = "1" + String.valueOf(1) + String.valueOf(num2)
              + String.valueOf(num3) + "-" + m1 + "-" + String.valueOf(1) + String.valueOf(num4);

      String bigDate = "2" + String.valueOf(num4) + String.valueOf(num2)
              + String.valueOf(num1) + "-" + m2 + "-" + String.valueOf(1) + String.valueOf(num3);


      String myData = "(" + smallDate + "," + String.valueOf(price2) + ");("
              + "(" + bigDate + "," + String.valueOf(price3) + ");("
              + "(1000-01-01," + String.valueOf(price1) +")";

      String wrongData1 = "(" + smallDate + String.valueOf(alphabet.charAt(num5))
              + "," + String.valueOf(price2) + ");("
              + "(" + bigDate + "," + String.valueOf(price3) + ");("
              + "(1000-01-01," + String.valueOf(price1) +")";

      String wrongData2 = "(" + smallDate + ")"
              + "," + String.valueOf(price2) + ");("
              + "(" + bigDate + "," + String.valueOf(price3) + ");("
              + "(1000-01-01," + String.valueOf(price1) +")";

      String wrongData3 = "(" + smallDate + ","
              + "," + String.valueOf(price2) + ");("
              + "(" + bigDate + "," + String.valueOf(price3) + ");("
              + "(1000-01-01," + String.valueOf(price1) +")";

      Stock1 wrongStock = new Stock1(myTicker, num2, wrongData2);
      Assert.assertEquals(price2, wrongStock.getData(smallDate), 0.0000001);
      wrongStock = new Stock1(myTicker, num2, wrongData3);
      Assert.assertEquals(price2, wrongStock.getData(smallDate), 0.0000001);

      try{
        wrongStock = new Stock1(myTicker, num2, wrongData1);
        fail();
      } catch(Exception e) {
        // we should remove all incorrect data
        System.out.println("pass");
      }
      myPort.addStock(myTicker,myData, String.valueOf(num2), true);

      totPrice += price1;
      totShare += num2;
      totVal += price1 * num2;
      totStock += 1;

      Assert.assertEquals(totPrice, myPort.getTotalValues(setDate)[0], 0.0000001);
      Assert.assertEquals(totShare, myPort.getNumStocks());
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(setDate)[1], 0.0000001);


      myPort.addStock(myTicker,myData, String.valueOf(num2), true);

      totShare += num2;
      totVal += price1 * num2;

      Assert.assertEquals(totPrice, myPort.getTotalValues(setDate)[0], 0.0000001);
      Assert.assertEquals(totShare, myPort.getNumStocks());
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(setDate)[1], 0.0000001);
    }

  }


  @Test
  public void addBigSmartStock() {

    Set<String> localOutput = new HashSet<>();
    try {
      localOutput = DataHelpers.loadLocalTickers();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] tickers = localOutput.stream().toArray(String[]::new);


    String fetchDate = "2020-05-05";

    String alphabet = "ABCDEFGHIJLKMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz";

    SmartPortfolio myPort = new SmartPortfolio();
    double[] n1 = int_gen.doubles(tickers.length, 0, 100).toArray();
    double[] p3 = int_gen2.doubles(tickers.length, 1, 1000).toArray();
    double[] p1 = int_gen3.doubles(tickers.length, 1, 1000).toArray();
    double[] p2 = int_gen4.doubles(tickers.length, 2, 1000).toArray();
    int[] shareArr = int_gen4.ints(tickers.length, 2, 1000).toArray();
    int[] n5 = int_gen4.ints(tickers.length, 0, alphabet.length() - 1).toArray();
    double[] prices = dgen1.doubles(tickers.length,
            0, 500).toArray();

    double[] p4 = dgen2.doubles(tickers.length,
            0, 10).toArray();

    double totPrice = 0;
    double totShare = 0;
    double totVal = 0;
    int totStock = 0;
    double totCB = 0;
    double totComm = 0.0;

    String setDate = "1000-01-01";

    Set<LocalDate> maxDates = new HashSet<>();

    for(int i = 0; i < 50; i++) {

      String myTicker = tickers[i];
      double num1 = n1[i];
      int num5 = n5[i];
      double price1 = prices[i];
      double price2 = p1[i];
      double price3 = p2[i];
      double price4 = p3[i];
      double rDub = p4[i];
      double share1 = (double) shareArr[i];

      StringBuilder priceData = new StringBuilder();
      priceData.append("(2020-01-15,");
      priceData.append(price1);
      priceData.append(");(2020-05-01,");
      priceData.append(price2);
      priceData.append(");(2021-12-01,");
      priceData.append(price3);
      priceData.append(");(2020-05-05,");
      priceData.append(price4);
      priceData.append(");(2020-05-04,1.0)");


      Pair<Double,Double> myPair = new Pair<Double,Double>(share1, rDub);
      Pair<Double,Double> myPair2 = new Pair<Double,Double>(share1*2,rDub*2);

      Pair<Double,Double> myPair3 = new Pair<Double,Double>(share1*3,num1);

      LocalDate myDate  = LocalDate.parse("2020-01-15");
      LocalDate myDate2  = LocalDate.parse("2020-05-01");
      LocalDate myDate3  = LocalDate.parse("2021-12-01");

      HashMap<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<LocalDate, Pair<Double,Double>>();


      myBuyDates.put(myDate,myPair);
      myBuyDates.put(myDate2,myPair2);
      myBuyDates.put(myDate3,myPair3);

      StringBuilder buyString = new StringBuilder();
      buyString.append("(2020-01-15,");
      buyString.append(share1);
      buyString.append(",");
      buyString.append(rDub);
      buyString.append(");(2020-05-01,");
      buyString.append(share1*2);
      buyString.append(",");
      buyString.append(rDub*2);
      buyString.append(");(2021-12-01,");
      buyString.append(share1*3);
      buyString.append(",");
      buyString.append(num1);
      buyString.append(")");


      StringBuilder wrongData3 = new StringBuilder();
      wrongData3.append("(2020-01-15,,");
      wrongData3.append(price1);
      wrongData3.append(");(2020-05-01,");
      wrongData3.append(price2);
      wrongData3.append(");(2021-12-01,");
      wrongData3.append(price3);
      wrongData3.append(");(2020-05-05,");
      wrongData3.append(price4);
      wrongData3.append(")");

      StringBuilder wrongData1 = new StringBuilder();
      wrongData1.append("(2020-01-15,");
      wrongData1.append(price1);
      wrongData1.append(");(2020-05-01,");
      wrongData1.append(price2);
      wrongData1.append(");((2021-12-01,");
      wrongData1.append(price3);
      wrongData1.append(");(2020-05-05,");
      wrongData1.append(price4);
      wrongData1.append(")");

      StringBuilder wrongData2 = new StringBuilder();
      wrongData2.append("(2020-01-15" +
              String.valueOf(alphabet.charAt(num5)) + ",");
      wrongData2.append(price1);
      wrongData2.append(");(2020-05-01,");
      wrongData2.append(price2);
      wrongData2.append(");(2021-12-01,");
      wrongData2.append(price3);
      wrongData2.append(");(2020-05-05,");
      wrongData2.append(price4);
      wrongData2.append(")");


      SmartStock wrongStock = new SmartStock(myTicker, wrongData1.toString(), myBuyDates, true);
      Assert.assertEquals(price4, wrongStock.getData(fetchDate), 0.0000001);
      wrongStock = new SmartStock(myTicker, wrongData3.toString(), myBuyDates, true);
      Assert.assertEquals(price4, wrongStock.getData(fetchDate), 0.0000001);

      try{
        wrongStock = new SmartStock(myTicker, wrongData2.toString(), myBuyDates, true);
        fail();
      } catch(Exception e) {
        // we should remove all incorrect data
        System.out.println("pass");
      }

      SmartStock newStock = new SmartStock(myTicker,priceData.toString(),
              buyString.toString(), true);
      myPort.addStock(myTicker,priceData.toString(), buyString.toString(), true);

      totCB += newStock.getCostBasis(fetchDate);
      Pair<Double, Double> pVal = newStock.getValue(fetchDate);
      totPrice += pVal.a;

      Pair<Double, Double> cVal = newStock.getShareCommm(fetchDate);
      totShare += cVal.a;
      totVal += pVal.b * pVal.a;
      totStock += 1;

      // checks all shares are ints
      Assert.assertEquals((totShare % 1), 0.0, 0.00000001);

      Assert.assertEquals(totPrice, myPort.getTotalValues(fetchDate)[0], 0.0000001);
      Assert.assertEquals( (int) totShare, myPort.getNumStocks(fetchDate));
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(fetchDate)[1], 0.0000001);
      Assert.assertEquals(totCB, myPort.getCostBasis(fetchDate), 0.0000001);

    }

  }

  @Test
  public void myTest() {

    LocalDate o1 = LocalDate.parse("2022-12-31");
    LocalDate o2 = LocalDate.parse("2022-01-31");
    System.out.println(o1.isBefore(o2));


  }

  @Test
  public void addAPIStock() {

    System.out.println();
    Portfolio myPort = new Portfolio();
    Assert.assertEquals(0, myPort.getNumStocks());
    Assert.assertEquals(0, myPort.size());

    myPort.addStock("GOOG", "API", "3", true);

    Assert.assertEquals(3, myPort.getNumStocks());
    Assert.assertEquals(1, myPort.size());;


    myPort.addStock("GOOG", "API", "2", true);
    Assert.assertEquals(5, myPort.getNumStocks());
    Assert.assertEquals(1, myPort.size());


    myPort.addStock("NVDA", "API", "1", true);
    Assert.assertEquals(6, myPort.getNumStocks());
    Assert.assertEquals(2, myPort.size());


    myPort.addStock("TGR-WS", "API", "2", true);
    Assert.assertEquals(8, myPort.getNumStocks());
    Assert.assertEquals(3, myPort.size());
    System.out.println(myPort.toString());

  }

  @Test
  public void stockDataGetter() {
    //LocalDate d1 = LocalDate.parse("2020-05-01");
    //LocalDate d2 = LocalDate.parse("2020-05-02");
    //System.out.println(d2.isAfter(d1));
    //System.out.println(d1.isAfter(d2));
    //System.out.println(d2.isEqual(d1));

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    System.out.println(dtf.format(LocalDate.parse("2020-12-31")));
    LocalDate d1 = null;
    LocalDate d2 = null;
    LocalDate[] myDate = {d1, d2};
    System.out.println(Objects.isNull(myDate[0]));

    Map<LocalDate, Double> myMap = DataHelpers.getStockData("NVDA", "2020-05-15",  "2022-11-08", 'M', "2019-05-01");


    System.out.println(myMap.size());
    System.out.println(myMap.keySet().toString());

    Comparator<LocalDate> comparator = LocalDate::compareTo;

    SortedSet<LocalDate> keys = new TreeSet<>(comparator);
    keys.addAll(myMap.keySet());

    for(LocalDate key: keys) {
      System.out.println("KEY: " + dtf.format(key)
              + " || PRICE: " + String.valueOf(myMap.get(key)));
    }
  }

  @Test
  public void createTimeInterval() {

    // setup to test our function --> 2 example dates
    LocalDate d1 = LocalDate.parse("2020-11-05");
    LocalDate d2 = LocalDate.parse("2022-12-08");


    // We need to return a value of 0 if the date bought at was later than

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Map<LocalDate, Double> myMap = DataHelpers.getStockData("NVDA", "2020-05-15",  "2022-11-08", 'M', "2019-05-01");

    //########################################################################################################

    //bar chart has at least 5 lines but no more than 30 lines.
    //no more than 50 asterisks on each line
    char dayWeekMonth = 'F';
    int numSplits = 0;

    // get the total size of the time frame
    long days = ChronoUnit.DAYS.between(d1, d2);

    // 6 months = 182.5
    // 1 month ~ 30

    // if days < 5 THROW ERROR
    // if days  < 30 use daily
    // if days >= 30 && days < 90 use daily x 3
    // if days >= 90 && days < 183 use weekly
    // if days >= 183 && days < 420 use weekly x 3
    // if days > 420 && days < 800 use monthly
    // if days >= 800 && days < 1800 use monthly x 3
    // if days >= 1800 && days < 3650 use monthly x 6 (half year)
    // if days >= 3650 && days < 9125 use yearly
    // if days >= 9125 && days < 9125*2 use yearly x 2
    // if days >= 9125*2  use yearly x 3

    if(days < 5) {
      System.out.println("ERROR");

    } else if(days < 30) {
      dayWeekMonth = 'D';
      numSplits = 1;

    } else if(days < 90) {
      dayWeekMonth = 'D';
      numSplits = 3;

    } else if(days < 183) {
      dayWeekMonth = 'W';
      numSplits = 1;

    } else if(days < 420) {
      dayWeekMonth = 'W';
      numSplits = 3;

    } else if(days < 800) {
      dayWeekMonth = 'M';
      numSplits = 1;

    } else if(days < 1800) {
      dayWeekMonth = 'M';
      numSplits = 3;

    } else if(days < 3650) {
      dayWeekMonth = 'M';
      numSplits = 6;

    } else if(days < 9125) {
      dayWeekMonth = 'Y';
      numSplits = 1;

    }else if(days < 18250) {
      dayWeekMonth = 'Y';
      numSplits = 2;

    } else {
      dayWeekMonth = 'Y';
      numSplits = 3;

    }

    //System.out.println(d1.getYear());
    System.out.println("01-01-" + String.valueOf(d1.getYear()));

    System.out.println("DWMY?: " + Character.toString(dayWeekMonth));
    System.out.println("NUM SPLITS: " + String.valueOf(numSplits));

    System.out.println("DAYS BETWEEN DATES: " + String.valueOf(days));

    System.out.println("TIME FRAME FOR 30 BARS: " + String.valueOf(days/29));
    System.out.println("DAYS BETWEEN DATES: " + String.valueOf(days/5));



    //System.out.println(myMap.size());
    //System.out.println(myMap.keySet().toString());

    Comparator<LocalDate> comparator = LocalDate::compareTo;

    SortedSet<LocalDate> keys = new TreeSet<>(comparator);
    keys.addAll(myMap.keySet());

    Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1,d2);
    System.out.println(Character.toString(myPair.a));
    System.out.println(String.valueOf(myPair.b));

  }

  // placed inline for the demonstration, but doesn't have to be a lambda expression

  @Test
  public void tickerTest() {
    //System.out.println(DataHelpers.isAlphaNumeric("4324_dfgfdg_345dsfgs"));
    Set<String> allStocks2 = DataHelpers.getTickers();
    Set<String> allStocks = new HashSet<String>();
    try {
      File nameFile = new File("Tickers.txt");
      Scanner scan = new Scanner(nameFile);
      String line;
      while (scan.hasNextLine()) {
        line = scan.nextLine();
        allStocks.add(line);
      }
    } catch (FileNotFoundException e) {

    }
    System.out.println(allStocks2.equals(allStocks));
    System.out.println(allStocks.toString());
  }

  /**
   * Test that our Pair() class adds another pair of doubles correctly.
   */
  @Test
  public void pairTest() {
    //System.out.println(DataHelpers.isAlphaNumeric("4324_dfgfdg_345dsfgs"));
    Pair<Double, Double> one = new Pair<>(5.0, 10.0);
    Pair<Double, Double> two = new Pair<>(10.0, 40.0);
    Pair<Double, Double> check = one.add(two);
    Assert.assertEquals(15.0, check.a, 0.0000001);
    Assert.assertEquals(50.0, check.b, 0.0000001);
  }

  @Test
  public void ScanFile() throws FileNotFoundException{
    File portFile = new File("C:\\Users\\danik\\OneDrive\\Desktop\\PortTestDir\\port1.json");
    Scanner scan = new Scanner(portFile);
    //System.out.println(scan.nextLine());
    String line;

    Portfolio myPortfolio = new Portfolio();
    //Pattern btwnQuotes = Pattern.compile("\".*\\\\\\\"(.*)\\\\\\\".*\"");
    line = scan.nextLine();

    boolean check = false;
    while(!check && scan.hasNextLine()){
      System.out.println("START LINE:");
      System.out.println(line);
      if (line.contains("Stock")) {
        check = true;
        String line2 = scan.nextLine();
        System.out.println("NEXT LINE:");
        System.out.println(line2);
        String ticker = line2.split("\"")[3];
        line2 = scan.nextLine();
        System.out.println("NEXT NEXT LINE:");
        System.out.println(line2);
        String shares = line2.split("\"")[3];
        System.out.println("SHARES LINE:");
        System.out.println(shares);
        scan.nextLine();
        line2 = scan.nextLine();
      } else {
        line = scan.nextLine();
      }
    }

  }

  @Test
  public void printStock() {
    Pair<Double,Double> myPair = new Pair<Double,Double>(10.0,1.0);
    Pair<Double,Double> myPair2 = new Pair<Double,Double>(20.0,2.0);

    Pair<Double,Double> myPair3 = new Pair<Double,Double>(20.0,7.0);

    LocalDate myDate  = LocalDate.parse("2020-01-15");
    LocalDate myDate2  = LocalDate.parse("2020-05-01");
    LocalDate myDate3  = LocalDate.parse("2021-12-01");

    HashMap<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<LocalDate, Pair<Double,Double>>();


    myBuyDates.put(myDate,myPair);
    myBuyDates.put(myDate2,myPair2);
    myBuyDates.put(myDate3,myPair3);

    String prices = "(2020-01-15,5.0);(2020-05-01,10.0);(2021-12-01,100.0);(2020-05-05,1.0)";

    double mycostBasis = (10.0 * 5.0 + 1.0) + (20.0 * 10.0 + 2.0);
    double myShares = 10.0 + 20.0;
    double myComm = 1.0 + 2.0;
    double myValue = (myShares) * 1.0;

    SmartStock myStock = new SmartStock("GOOG", prices, myBuyDates, true);

    StringBuilder outBuild = new StringBuilder();
    outBuild.append("|   TICKER   |    DATE    |    TOTAL SHARES    |");
    outBuild.append("    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


    String fetchDate = "2020-05-05";
    System.out.println(outBuild.toString());
    System.out.println(myStock.printDataAt(fetchDate));

    Pair<Double, Double> myStockValue = myStock.getValue(fetchDate);
    Assert.assertEquals(myValue, (myStockValue.a * myStockValue.b), 0.00001);

    Pair<Double, Double> curComm = myStock.getShareCommm(fetchDate);

    Assert.assertEquals(myShares, curComm.a, 0.000001);
    Assert.assertEquals(myComm, curComm.b, 0.000001);

    double costB = myStock.getCostBasis(fetchDate);

    Assert.assertEquals(mycostBasis, costB, 0.000001);

    String fetchDate2 = "2015-05-05";

    Pair<Double, Double> myStockValue2 = myStock.getValue(fetchDate2);
    Assert.assertEquals(0.0, (myStockValue2.a * myStockValue2.b), 0.00001);

    Pair<Double, Double> curComm2 = myStock.getShareCommm(fetchDate2);

    Assert.assertEquals(0, curComm2.a, 0.000001);
    Assert.assertEquals(0, curComm2.b, 0.000001);

    double costB2 = myStock.getCostBasis(fetchDate2);

    Assert.assertEquals(0, costB2, 0.000001);
  }

  @Test
  public void parseBuyDates() {
    Pair<Double,Double> myPair = new Pair<Double,Double>(10.0,1.0);
    Pair<Double,Double> myPair2 = new Pair<Double,Double>(20.0,2.0);

    Pair<Double,Double> myPair3 = new Pair<Double,Double>(20.0,7.0);

    LocalDate myDate  = LocalDate.parse("2020-01-15");
    LocalDate myDate2  = LocalDate.parse("2020-05-01");
    LocalDate myDate3  = LocalDate.parse("2021-12-01");

    HashMap<LocalDate, Pair<Double,Double>> myBuyDates = new HashMap<LocalDate, Pair<Double,Double>>();


    myBuyDates.put(myDate,myPair);
    myBuyDates.put(myDate2,myPair2);
    myBuyDates.put(myDate3,myPair3);



    LocalDate start = LocalDate.parse("2020-06-01");


    HashMap<LocalDate, Pair<Double, Double>> trimShareDates = new
            HashMap<LocalDate,  Pair<Double, Double>>(myBuyDates);

    System.out.println(myBuyDates.keySet().size());
    System.out.println(trimShareDates.keySet().size());

    trimShareDates.keySet().removeIf(k -> k.isBefore(start));

    System.out.println(trimShareDates.keySet().toString());





    Pair<Double, Double> totShareComm = new Pair<>(0.0, 0.0);
    for( LocalDate key: myBuyDates.keySet()) {
      totShareComm = totShareComm.add(myBuyDates.get(key));
    }

    Assert.assertEquals(50.0, totShareComm.a, 0.00001);
    Assert.assertEquals(10.0, totShareComm.b, 0.00001);

    String bdString = "(2020-01-15,10.0,1.0);(2020-05-01,20.0,2.0);(2021-12-01,20.0,7.0)";

  }
  @Test
  public void buyIteratorTest() {

    // We need to return a value of 0 if the date bought at was later than

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Map<LocalDate, Double> newPrices = DataHelpers.getStockData("NVDA", "2020-05-15",  "2022-11-08", 'M', "2019-05-01");
    String date1 = "2020-05-15";
    String date2 = "2022-12-08";

    LocalDate d1;
    LocalDate d2;

    try{
      d1 = LocalDate.parse(date1);
      d2 = LocalDate.parse(date2);
    } catch(Exception e) {
      throw new IllegalArgumentException("Your date strings could not be parsed. " +
              "Please check your inputs and try again");
    }


    Pair<Integer,Double> myPair = new Pair<Integer,Double>(10,1.0);
    Pair<Integer,Double> myPair2 = new Pair<Integer,Double>(20,2.0);

    Pair<Integer,Double> myPair3 = new Pair<Integer,Double>(20,7.0);

    LocalDate myDate  = LocalDate.parse("2020-01-15");
    LocalDate myDate2  = LocalDate.parse("2020-05-01");
    LocalDate myDate3  = LocalDate.parse("2021-12-01");

    HashMap<LocalDate, Pair<Integer,Double>> myBuyDates = new HashMap<LocalDate, Pair<Integer,Double>>();


    myBuyDates.put(myDate,myPair);
    myBuyDates.put(myDate2,myPair2);
    myBuyDates.put(myDate3,myPair3);

    HashMap<LocalDate, Pair<Integer,Double>> BuyDates = myBuyDates;
    LocalDate minDate = Collections.min(BuyDates.keySet());


    // SET UP ***********************************************************


    Comparator<LocalDate> comparator = LocalDate::compareTo;

    SortedSet<LocalDate> priceKeys = new TreeSet<>(comparator);
    priceKeys.addAll(newPrices.keySet());
    System.out.println("FIRST PRICE DATE: " + priceKeys.first().toString());
    SortedSet<LocalDate> buyKeys = new TreeSet<>(comparator);
    buyKeys.addAll(BuyDates.keySet());
    System.out.println("FIRST BUY DATE: " + buyKeys.first().toString());
    Iterator<LocalDate> buyIt = buyKeys.iterator();
    LocalDate curBuyKey = buyIt.next();

    System.out.println("ALL BUY KEYS: " +
            buyKeys.toString());

    System.out.println("CURRENT BUY KEY: " +
            curBuyKey.toString());


    LocalDate nextBuy;
    if(buyIt.hasNext()) {
      nextBuy = buyIt.next();
    } else {
      nextBuy = null;
    }

    Pair<Integer, Double> curBuy = BuyDates.get(curBuyKey);
    int curTotStock = curBuy.a;
    double curTotComiss = curBuy.b;
    Double totVal = 0.0;

    LocalDate firstDate = priceKeys.first();

    System.out.println("CURRENT COMMISSION: " +
            String.valueOf(curTotComiss));

    System.out.println("CURRENT SHARES: " +
            String.valueOf(curTotStock));


    System.out.println("FIRST DATE: " + firstDate.toString());
    boolean finDate = false;

    System.out.println("NEXT BUY KEY: " +
            nextBuy.toString());


    System.out.println("Is nextBuy after the earliest date?");
    System.out.println(nextBuy.isAfter(firstDate));

    while(!Objects.isNull(nextBuy) && !finDate) {
      if(!nextBuy.isAfter(firstDate)) {
        curBuyKey = nextBuy;
        nextBuy = buyIt.next();
        curBuy = BuyDates.get(curBuyKey);
        curTotStock += curBuy.a;
        curTotComiss += curBuy.b;
      } else {
        finDate = true;
      }
    }

    System.out.println("CURRENT BUY KEY 2: " +
            curBuyKey.toString());

    System.out.println("NEXT BUY KEY 2: " +
            nextBuy.toString());

    System.out.println("CURRENT COMMISSION: " +
            String.valueOf(curTotComiss));

    System.out.println("CURRENT SHARES: " +
            String.valueOf(curTotStock));

    // This is the map with the total value at each date that we will return.
    Map<LocalDate, Double> allVals = new HashMap<LocalDate, Double>();

    int i = 0;
    for(LocalDate key: priceKeys) {

      // If our current date is after/equal to the current buy key
      // First check that we are before the next buy Key
      // If not before then ignore --> we only want values after/on the first valid buy date
      // If true then check that we are before the next buyDate
      // If false then we update the buyDate, otherwise continue
      if(!key.isBefore(curBuyKey)) {
        // If we have a next key check that we are not on or after it
        // If we are not before then update the shares and commission fee
        if(!Objects.isNull(nextBuy) && !key.isBefore(nextBuy)) {
          curBuyKey = nextBuy;

          if(buyIt.hasNext()) {
            nextBuy = buyIt.next();
          } else {
            nextBuy = null;
          }

          curBuy = BuyDates.get(curBuyKey);
          curTotStock += curBuy.a;
          curTotComiss += curBuy.b;
        }

        // once everything is updated we can continue
        double curPrice = newPrices.get(key);
        double curValue = (curPrice * curTotStock) - curTotComiss;
        allVals.put(key, curValue);

        if(i == 0) {
          i++;
          System.out.println("EXPECTED: " +
                  "30 SHARES & 3.0 DOLLARS" );
          System.out.println("ACTUAL SHARES: " +
                  String.valueOf(curTotStock));
          System.out.println("ACTUAL COMM: " +
                  String.valueOf(curTotComiss));

          System.out.println("THE CURRENT DATE IS: " +
                  key.toString());
          System.out.println("THE PRICE AT THE FIRST DATE IS: "
                  + String.valueOf(newPrices.get(key)));
          System.out.println("2 THE PRICE AT THE FIRST DATE IS: "
                  + String.valueOf(curPrice));

          System.out.println("THE TOTAL VALUE AT THE FIRST DATE IS: "
                  + String.valueOf(curValue));

        }

      }

    }

    SortedSet<LocalDate> valKeys = new TreeSet<>(comparator.reversed());
    valKeys.addAll(allVals.keySet());
    //System.out.println("VAL KEY SIZE: " + String.valueOf(allVals.size()));
    //System.out.println("VAL KEYS: " + valKeys.toString());
    //System.out.println("PRICE KEYS: " + priceKeys.toString());

    LocalDate lastValKey = valKeys.first();
    LocalDate firstValKey = valKeys.last();

    System.out.println("THE FIRST DATE IS: " + firstValKey.toString());
    System.out.println("THE FIRST NUM SHARES  + COMISSION IS: " +
            "30 SHARES & 3.0 DOLLARS" );
    System.out.println("THE PRICE AT THE FIRST DATE IS: "
            + String.valueOf(newPrices.get(firstValKey)));

    System.out.println("THE TOTAL VALUE AT THE FIRST DATE IS: "
            + String.valueOf(allVals.get(firstValKey)));
    // else if(current key is after or before the current buy key return nothing)


    System.out.println("THE LAST DATE IS: " + lastValKey.toString());
    System.out.println("THE LAST NUM SHARES  + COMISSION IS: " +
            "50 SHARES & 10.0 DOLLARS" );
    System.out.println("THE PRICE AT THE LAST DATE IS: "
            + String.valueOf(newPrices.get(lastValKey)));

    System.out.println("THE TOTAL VALUE AT THE LAST DATE IS: "
            + String.valueOf(allVals.get(lastValKey)));

  }

  @Test
  public void stockStringTest() {
    Stock1 myStock = new Stock1("GOOG", 12, "API");

    Stock1 myStock2 = new Stock1("NVDA", 23, "API");
    StringBuilder outBuild = new StringBuilder().append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
    outBuild.append("| TICKER |    DATE    |    SHARES     |");
    outBuild.append("     OPEN PRICE     |    SHARE VALUE    |\n");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    outBuild.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");

    System.out.println(outBuild.toString());
    assertEquals("|  GOOG  | 2022-11-01 |      12       |       95.59        |   " +
            "   1147.08      |",myStock.printDataAt("current"));

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
      if (!startEnd) {

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

  /*@Test
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
  }*/

  /*@Test
  public void dateParsing() {
    String line = "(2022-10-15,55.5);(2022-10-14,44.4);(2022-08-15,33.3);(2022-07-15,22.2)";

    Map<LocalDate, Double> myMap = parseStock(line);

    for (LocalDate key : myMap.keySet()) {
      System.out.println("okay?");
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
      System.out.println("*************************************************");
      System.out.println(myMap.containsKey(key));
    }

    //System.out.println(x.length());
  } */

  @Test
  public void loadPortTest() {

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
            + "|  GOOG  | 2022-10-31 |      10       |       "
            + "95.78        |       957.8       |\n"
            + "|  NVDA  | 2022-07-15 |      100      |"
            + "        20.3        |       2030        |\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n"
            + "| TOTAL  |   Current  |      110      |"
            + "       116.08       |      2987.8       |\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n";
    //assertEquals(expected, myPort.toString());
    System.out.println(myPort.printPortfolioAt("current"));
  }
}