package stockdataa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import stockdataa.model.Portfolio;
import stockdataa.model.SmartPortfolio;
import stockdataa.model.SmartStock;
import stockdataa.model.Stock1;

import static org.junit.Assert.fail;

/**
 * Test class that tests if portfolio operations are performed accurately.
 */

public class portTest {

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

  /**
   * Test tickers download.
   */
  @Test
  public void getTickers() {
    Set<String> apiOutput = DataHelpers.getAPITickers();
    for (int i = 0; i < 30; i++) {
      try {
        DataHelpers.getAPITickers();
      } catch (RuntimeException e) {
        System.out.println("Test Passed");
      }

    }
    try {
      Set<String> localOutput = DataHelpers.loadLocalTickers();
      Assert.assertTrue(apiOutput.containsAll(localOutput));
      Assert.assertTrue(localOutput.containsAll(apiOutput));
    } catch (FileNotFoundException e) {
      fail();
    }


  }

  /**
   * Tests dollar cost averaging.
   */
  @Test
  public void DCATest() {

    SmartPortfolio myPort;

    try {
      myPort = (SmartPortfolio) DataHelpers.loadPortfolio("DanUser",
              "DCATest.json", 2);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    Assert.assertEquals(20, myPort.getNumStocks("2022-11-01"), 0.001);
    Assert.assertTrue(myPort.getNumStocks("2022-11-28") > 100);
    System.out.println(myPort.printPortfolioAt("current"));
    System.out.println(myPort.printPortfolioAt("2022-11-01"));

    myPort.setDLCostAverage("3", "2022-11-25", "",
            "138.2", "5",
            "(IBM, 1.0)");
    Assert.assertTrue(myPort.getNumStocks("2022-11-29") > 107);
    System.out.println(myPort.printPortfolioAt("current"));

    // Trying to add invalid date

    try {
      myPort.setDLCostAverage("3", "2022-123-25", "",
              "138.2", "5",
              "(IBM, 1.0)");
      Assert.fail();
    } catch (Exception e) {
      System.out.println("pass");
    }


    try {
      myPort.setDLCostAverage("3", "2022-11-25", "2022-11-24",
              "138.2", "5",
              "(IBM, 1.0)");
      Assert.fail();
    } catch (Exception e) {
      System.out.println("pass");
    }

    try {
      myPort.setDLCostAverage("3", "2022-12-25", "",
              "138.2", "5",
              "(IBM, 1.5)");
      Assert.fail();
    } catch (Exception e) {
      System.out.println("pass");
    }


    try {
      myPort.setDLCostAverage("3", "2022-12-25", "",
              "-2", "5",
              "(IBM, 1.5)");
      Assert.fail();
    } catch (Exception e) {
      System.out.println("pass");
    }

  }


  /**
   * Tests dollar cost averaging by loading new portfolio with no DCA.
   */
  @Test
  public void DCATest2() {

    SmartPortfolio myPort;
    SmartPortfolio myPort2;
    try {
      myPort = (SmartPortfolio) DataHelpers.loadPortfolio("DanUser",
              "DCATest3.json", 2);
      myPort2 = (SmartPortfolio) DataHelpers.loadPortfolio("DanUser",
              "DCATest3.json", 2);
    } catch (FileNotFoundException | ParseException e) {
      throw new RuntimeException(e);
    }

    myPort.setDLCostAverage("0", "2022-11-22", "", "500",
            "250", "(GOOG,0.1);(IBM,0.9)");

    // Cost Basis should go up by 1000 since we spent 500 and commission of 250 twice
    // Value should only go up by 500
    System.out.println(myPort.printPortfolioAt("2022-11-18"));
    System.out.println(myPort.printPortfolioAt("2022-11-22"));
    System.out.println(myPort.printPortfolioAt("2022-11-23"));

    double costBasisPre = myPort.getCostBasis("2022-11-21");
    double costBasisPost = myPort.getCostBasis("2022-11-22");
    Assert.assertEquals(costBasisPre + 1000, costBasisPost, 0.1);

    double valuePre = myPort2.getTotalValues("2022-11-22")[1];
    double valuePost = myPort.getTotalValues("2022-11-22")[1];
    Assert.assertEquals(valuePre + 500, valuePost, 0.1);


  }


  /**
   * Tests dollar cost averaging by creating new portfolio with no DCA.
   */
  @Test
  public void DCATest3() {

    SmartPortfolio myPort = new SmartPortfolio();
    SmartPortfolio myPort2 = new SmartPortfolio();


    try {
      myPort2.setDLCostAverage("0", "2022-10-20", "", "500",
              "250", "(GOOG,0.1);(IBM,0.9)");
    } catch (Exception e) {
      Assert.fail();
    }


    SmartPortfolio myPort3 = new SmartPortfolio();
    myPort3.addStock("AAPL", "API", "(2022-09-22,10,10)", true);

    myPort.addStock("AAPL", "API", "(2022-09-22,10,10)", true);
    myPort.setDLCostAverage("0", "2022-10-20", "", "500",
            "250", "(AAPL,0.5);(IBM,0.5)");


    // Cost Basis should go up by 1000 since we spent 500 and commission of 250 twice
    // Value should only go up by 500
    System.out.println(myPort.printPortfolioAt("2022-10-18"));
    System.out.println(myPort.printPortfolioAt("2022-11-01"));


    double costBasisPre = myPort.getCostBasis("2022-10-18");
    double costBasisPost = myPort.getCostBasis("2022-11-22");
    Assert.assertEquals(costBasisPre + 1000, costBasisPost, 0.5);


    double valuePre = myPort3.getTotalValues("2022-11-22")[1];
    double valuePost = myPort.getTotalValues("2022-11-22")[1];
    Assert.assertEquals(valuePre + 552, valuePost, 0.1);

  }

  /**
   * Tests the inflexible portfolio stock operations.
   */

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

    for (int i = 0; i < 100; i++) {

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
      if (m11 > 12) {
        m1 = "12";
      } else if (m11 == 0) {
        m1 = "1";
      } else {
        if (m11 < 10) {
          m1 = "0" + m11;
        } else {
          m1 = String.valueOf(m11);
        }
      }

      int m12 = num3 + num2;
      if (m12 > 12) {
        m2 = "12";
      } else if (m11 == 0) {
        m2 = "1";
      } else {
        if (m12 < 10) {
          m2 = "0" + m11;
        } else {
          m2 = String.valueOf(m11);
        }
      }

      String smallDate = "1" + 1 + num2
              + num3 + "-" + m1 + "-" + 1 + num4;

      String bigDate = "2" + num4 + num2
              + num1 + "-" + m2 + "-" + 1 + num3;


      String myData = "(" + smallDate + "," + price2 + ");("
              + "(" + bigDate + "," + price3 + ");("
              + "(1000-01-01," + price1 + ")";

      String wrongData1 = "(" + smallDate + alphabet.charAt(num5)
              + "," + price2 + ");("
              + "(" + bigDate + "," + price3 + ");("
              + "(1000-01-01," + price1 + ")";

      String wrongData2 = "(" + smallDate + ")"
              + "," + price2 + ");("
              + "(" + bigDate + "," + price3 + ");("
              + "(1000-01-01," + price1 + ")";

      String wrongData3 = "(" + smallDate + ","
              + "," + price2 + ");("
              + "(" + bigDate + "," + price3 + ");("
              + "(1000-01-01," + price1 + ")";

      Stock1 wrongStock = new Stock1(myTicker, num2, wrongData2);
      Assert.assertEquals(price2, wrongStock.getData(smallDate), 0.0000001);
      wrongStock = new Stock1(myTicker, num2, wrongData3);
      Assert.assertEquals(price2, wrongStock.getData(smallDate), 0.0000001);

      try {
        wrongStock = new Stock1(myTicker, num2, wrongData1);
        fail();
      } catch (Exception e) {
        // we should remove all incorrect data
        System.out.println("pass");
      }
      myPort.addStock(myTicker, myData, String.valueOf(num2), true);

      totPrice += price1;
      totShare += num2;
      totVal += price1 * num2;
      totStock += 1;

      Assert.assertEquals(totPrice, myPort.getTotalValues(setDate)[0], 0.0000001);
      Assert.assertEquals(totShare, myPort.getNumStocks());
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(setDate)[1], 0.0000001);


      myPort.addStock(myTicker, myData, String.valueOf(num2), true);

      totShare += num2;
      totVal += price1 * num2;

      Assert.assertEquals(totPrice, myPort.getTotalValues(setDate)[0], 0.0000001);
      Assert.assertEquals(totShare, myPort.getNumStocks());
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(setDate)[1], 0.0000001);
    }

  }

  /**
   * Tests flexible portfolio stock operations.
   */
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

    for (int i = 0; i < 50; i++) {

      String myTicker = tickers[i];
      double num1 = n1[i];
      int num5 = n5[i];
      double price1 = prices[i];
      double price2 = p1[i];
      double price3 = p2[i];
      double price4 = p3[i];
      double rDub = p4[i];
      double share1 = shareArr[i];

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


      Pair<Double, Double> myPair = new Pair<Double, Double>(share1, rDub);
      Pair<Double, Double> myPair2 = new Pair<Double, Double>(share1 * 2, rDub * 2);

      Pair<Double, Double> myPair3 = new Pair<Double, Double>(share1 * 3, num1);

      LocalDate myDate = LocalDate.parse("2020-01-15");
      LocalDate myDate2 = LocalDate.parse("2020-05-01");
      LocalDate myDate3 = LocalDate.parse("2021-12-01");

      HashMap<LocalDate, Pair<Double, Double>> myBuyDates =
              new HashMap<LocalDate, Pair<Double, Double>>();


      myBuyDates.put(myDate, myPair);
      myBuyDates.put(myDate2, myPair2);
      myBuyDates.put(myDate3, myPair3);

      StringBuilder buyString = new StringBuilder();
      buyString.append("(2020-01-15,");
      buyString.append(share1);
      buyString.append(",");
      buyString.append(rDub);
      buyString.append(");(2020-05-01,");
      buyString.append(share1 * 2);
      buyString.append(",");
      buyString.append(rDub * 2);
      buyString.append(");(2021-12-01,");
      buyString.append(share1 * 3);
      buyString.append(",");
      buyString.append(num1);
      buyString.append(")");


      String wrongData3 = "(2020-01-15,," +
              price1 +
              ");(2020-05-01," +
              price2 +
              ");(2021-12-01," +
              price3 +
              ");(2020-05-05," +
              price4 +
              ")";

      String wrongData1 = "(2020-01-15," +
              price1 +
              ");(2020-05-01," +
              price2 +
              ");((2021-12-01," +
              price3 +
              ");(2020-05-05," +
              price4 +
              ")";

      StringBuilder wrongData2 = new StringBuilder();
      wrongData2.append("(2020-01-15"
              + alphabet.charAt(num5) + ",");
      wrongData2.append(price1);
      wrongData2.append(");(2020-05-01,");
      wrongData2.append(price2);
      wrongData2.append(");(2021-12-01,");
      wrongData2.append(price3);
      wrongData2.append(");(2020-05-05,");
      wrongData2.append(price4);
      wrongData2.append(")");


      SmartStock wrongStock = new SmartStock(myTicker, wrongData1, myBuyDates, true);
      Assert.assertEquals(price4, wrongStock.getData(fetchDate), 0.0000001);
      wrongStock = new SmartStock(myTicker, wrongData3, myBuyDates, true);
      Assert.assertEquals(price4, wrongStock.getData(fetchDate), 0.0000001);

      try {
        wrongStock = new SmartStock(myTicker, wrongData2.toString(), myBuyDates, true);
        fail();
      } catch (Exception e) {
        // we should remove all incorrect data
        System.out.println("pass");
      }

      SmartStock newStock = new SmartStock(myTicker, priceData.toString(),
              buyString.toString(), true);
      myPort.addStock(myTicker, priceData.toString(), buyString.toString(), true);

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
      Assert.assertEquals((int) totShare, myPort.getNumStocks(fetchDate));
      Assert.assertEquals(totStock, myPort.size());
      Assert.assertEquals(totVal, myPort.getTotalValues(fetchDate)[1], 0.0000001);
      Assert.assertEquals(totCB, myPort.getCostBasis(fetchDate), 0.0000001);

    }

  }


  /**
   * Tests adding API stock.
   */
  @Test
  public void addAPIStock() {

    System.out.println();
    Portfolio myPort = new Portfolio();
    Assert.assertEquals(0, myPort.getNumStocks());
    Assert.assertEquals(0, myPort.size());

    myPort.addStock("GOOG", "API", "3", true);

    Assert.assertEquals(3, myPort.getNumStocks());
    Assert.assertEquals(1, myPort.size());


    myPort.addStock("GOOG", "API", "2", true);
    Assert.assertEquals(5, myPort.getNumStocks());
    Assert.assertEquals(1, myPort.size());


    myPort.addStock("NVDA", "API", "1", true);
    Assert.assertEquals(6, myPort.getNumStocks());
    Assert.assertEquals(2, myPort.size());


    myPort.addStock("TGR-WS", "API", "2", true);
    Assert.assertEquals(8, myPort.getNumStocks());
    Assert.assertEquals(3, myPort.size());
    System.out.println(myPort);

  }


  /**
   * Tests flexible portfolio print output.
   */
  @Test
  public void printTests() {

    System.out.println();
    SmartPortfolio myPort = new SmartPortfolio();
    Assert.assertEquals(0, myPort.getNumStocks());
    Assert.assertEquals(0, myPort.size());

    StringBuilder buyString = new StringBuilder();
    buyString.append("(2020-01-15,");
    buyString.append(10.0);
    buyString.append(",");
    buyString.append(1.0);
    buyString.append(");(2020-05-01,");
    buyString.append(10.0);
    buyString.append(",");
    buyString.append(1.0);
    buyString.append(");(2021-12-01,");
    buyString.append(10.0);
    buyString.append(",");
    buyString.append(1.0);
    buyString.append(")");

    String buyString2 = "(2020-05-15," +
            10.0 +
            "," +
            1.0 +
            ")";

    myPort.addStock("GOOG", "API", buyString.toString(), true);

    Assert.assertEquals(30, myPort.getNumStocks("current"));
    Assert.assertEquals(1, myPort.size());

    myPort.addStock("GOOG", "API", buyString2, true);
    Assert.assertEquals(40, myPort.getNumStocks());
    Assert.assertEquals(1, myPort.size());


    myPort.addStock("NVDA", "API", buyString.toString(), true);
    Assert.assertEquals(70, myPort.getNumStocks());
    Assert.assertEquals(2, myPort.size());


    myPort.addStock("AAPL", "API", buyString.toString(), true);
    Assert.assertEquals(100, myPort.getNumStocks());
    Assert.assertEquals(3, myPort.size());

    try {
      System.out.println(myPort.toString());
    } catch (Exception e) {
      Assert.fail();
    }


    try {
      System.out.println(myPort.portfolioPerformance("2020-01-15", "2020-05-15"));
    } catch (Exception e) {
      Assert.fail();
    }

    System.out.println(myPort.portToJSON());


  }


  /**
   * Tests time interval creation for performance graph.
   */
  @Test
  public void createTimeInterval() {

    // setup to test our function --> 2 example dates
    LocalDate d1 = LocalDate.parse("2020-11-05");
    LocalDate d2 = LocalDate.parse("2022-12-08");


    // We need to return a value of 0 if the date bought at was later than

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Map<LocalDate, Double> myMap = DataHelpers.getStockData("NVDA", "2020-05-15",
            "2022-11-08", 'M', "2019-05-01");


    //bar chart has at least 5 lines but no more than 30 lines.
    //no more than 50 asterisks on each line
    char dayWeekMonth = 'F';
    int numSplits = 0;

    // get the total size of the time frame
    long days = ChronoUnit.DAYS.between(d1, d2);


    if (days < 5) {
      System.out.println("ERROR");

    } else if (days < 30) {
      dayWeekMonth = 'D';
      numSplits = 1;

    } else if (days < 90) {
      dayWeekMonth = 'D';
      numSplits = 3;

    } else if (days < 183) {
      dayWeekMonth = 'W';
      numSplits = 1;

    } else if (days < 420) {
      dayWeekMonth = 'W';
      numSplits = 3;

    } else if (days < 800) {
      dayWeekMonth = 'M';
      numSplits = 1;

    } else if (days < 1800) {
      dayWeekMonth = 'M';
      numSplits = 3;

    } else if (days < 3650) {
      dayWeekMonth = 'M';
      numSplits = 6;

    } else if (days < 9125) {
      dayWeekMonth = 'Y';
      numSplits = 1;

    } else if (days < 18250) {
      dayWeekMonth = 'Y';
      numSplits = 2;

    } else {
      dayWeekMonth = 'Y';
      numSplits = 3;

    }

    //System.out.println(d1.getYear());
    System.out.println("01-01-" + d1.getYear());

    System.out.println("DWMY?: " + dayWeekMonth);
    System.out.println("NUM SPLITS: " + numSplits);

    System.out.println("DAYS BETWEEN DATES: " + days);

    System.out.println("TIME FRAME FOR 30 BARS: " + days / 29);
    System.out.println("DAYS BETWEEN DATES: " + days / 5);


    Pair<Character, Integer> myPair = DataHelpers.createTimeInterval(d1, d2);
    Assert.assertEquals("M", Character.toString(myPair.a));
    Assert.assertEquals(1, myPair.b.intValue());

  }

  // placed inline for the demonstration, but doesn't have to be a lambda expression

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
  public void printStock() {
    Pair<Double, Double> myPair = new Pair<Double, Double>(10.0, 1.0);
    Pair<Double, Double> myPair2 = new Pair<Double, Double>(20.0, 2.0);

    Pair<Double, Double> myPair3 = new Pair<Double, Double>(20.0, 7.0);

    LocalDate myDate = LocalDate.parse("2020-01-15");
    LocalDate myDate2 = LocalDate.parse("2020-05-01");
    LocalDate myDate3 = LocalDate.parse("2021-12-01");

    HashMap<LocalDate, Pair<Double, Double>> myBuyDates =
            new HashMap<LocalDate, Pair<Double, Double>>();


    myBuyDates.put(myDate, myPair);
    myBuyDates.put(myDate2, myPair2);
    myBuyDates.put(myDate3, myPair3);

    String prices = "(2020-01-15,5.0);(2020-05-01,10.0);(2021-12-01,100.0);(2020-05-05,1.0)";

    double mycostBasis = (10.0 * 5.0 + 1.0) + (20.0 * 10.0 + 2.0);
    double myShares = 10.0 + 20.0;
    double myComm = 1.0 + 2.0;
    double myValue = (myShares);

    SmartStock myStock = new SmartStock("GOOG", prices, myBuyDates, true);

    String outBuild = "|   TICKER   |    DATE    |    TOTAL SHARES    |" +
            "    COST BASIS    |    CLOSE PRICE    |    TOTAL VALUE    |\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n";


    String fetchDate = "2020-05-05";
    System.out.println(outBuild);
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

  /**
   * Tests parsing of buy date strings.
   */
  @Test
  public void parseBuyDates() {
    Pair<Double, Double> myPair = new Pair<Double, Double>(10.0, 1.0);
    Pair<Double, Double> myPair2 = new Pair<Double, Double>(20.0, 2.0);

    Pair<Double, Double> myPair3 = new Pair<Double, Double>(20.0, 7.0);

    LocalDate myDate = LocalDate.parse("2020-01-15");
    LocalDate myDate2 = LocalDate.parse("2020-05-01");
    LocalDate myDate3 = LocalDate.parse("2021-12-01");

    HashMap<LocalDate, Pair<Double, Double>> myBuyDates =
            new HashMap<LocalDate, Pair<Double, Double>>();


    myBuyDates.put(myDate, myPair);
    myBuyDates.put(myDate2, myPair2);
    myBuyDates.put(myDate3, myPair3);


    LocalDate start = LocalDate.parse("2020-06-01");


    HashMap<LocalDate, Pair<Double, Double>> trimShareDates = new
            HashMap<LocalDate, Pair<Double, Double>>(myBuyDates);

    System.out.println(myBuyDates.keySet().size());
    System.out.println(trimShareDates.keySet().size());

    trimShareDates.keySet().removeIf(k -> k.isBefore(start));

    System.out.println(trimShareDates.keySet());


    Pair<Double, Double> totShareComm = new Pair<>(0.0, 0.0);
    for (LocalDate key : myBuyDates.keySet()) {
      totShareComm = totShareComm.add(myBuyDates.get(key));
    }

    Assert.assertEquals(50.0, totShareComm.a, 0.00001);
    Assert.assertEquals(10.0, totShareComm.b, 0.00001);

  }


  /**
   * Tests the selling of stocks in a portfolio.
   *
   * @throws FileNotFoundException If we could not find the file to load.
   * @throws ParseException        If methods cannot parse the string values.
   */
  @Test
  public void PortfolioSaleTest() throws FileNotFoundException, ParseException {
    //TODO: Implement selling in the portfolio not in the stock
    // TODO: Add selling to controller.
    // TODO: Test performance graph with sold stock
    String buy = "(2022-01-15,2,1);(2022-01-18,2,1)";
    String price = "(2022-01-15,1); "
            + "(2022-01-16,1);"
            + "(2022-01-17,1);"
            + "(2022-01-18,1);"
            + "(2022-01-19,1);"
            + "(2022-01-20,1);"
            + "(2022-01-21,1);"
            + "(2022-01-22,1);"
            + "(2022-01-23,1);"
            + "(2022-01-24,1);"
            + "(2022-01-25,1);"
            + "(2022-01-26,1);"
            + "(2022-01-27,1);";
    SmartStock myStock = new SmartStock("NVDA", price, buy, true);
    SmartStock myStock2 = new SmartStock("IBM", price, buy, true);
    Map<String, SmartStock> myList = new HashMap<String, SmartStock>();
    myList.put("NVDA", myStock);
    myList.put("IBM", myStock2);

    SmartPortfolio myPort = new SmartPortfolio(myList);

    System.out.println(myPort.printPortfolioAt("2022-01-16"));

    double[] valueAt16 = myPort.getTotalValues("2022-01-16");
    Assert.assertEquals(4, valueAt16[1], 0.000001);
    Assert.assertEquals(6, myPort.getCostBasis("2022-01-16"), 0.000001);

    System.out.println(myPort.printPortfolioAt("2022-01-19"));

    double[] valueAt19 = myPort.getTotalValues("2022-01-19");
    Assert.assertEquals(8, valueAt19[1], 0.000001);
    Assert.assertEquals(12, myPort.getCostBasis("2022-01-19"), 0.000001);


    String saleDate = "2022-01-20";
    myPort.sellStock("IBM", "3", "3", saleDate, true);

    System.out.println(myPort.printPortfolioAt("2022-01-22"));

    System.out.println(myPort.portfolioPerformance("2022-01-16", "2022-01-22"));

    double[] valueAt22 = myPort.getTotalValues("2022-01-22");


    // loading and saving test
    myPort.save("SellPortTest", "DanUser");
    SmartPortfolio newPort = (SmartPortfolio) DataHelpers.loadPortfolio(
            "DanUser", "SellPortTest.json", 2);

    double[] valueAt22New = newPort.getTotalValues("2022-01-22");
    System.out.println(newPort.printPortfolioAt("2022-01-22"));

    Assert.assertEquals(valueAt22[1], valueAt22New[1], 0.000001);
    Assert.assertEquals(myPort.getCostBasis("2022-01-22"),
            newPort.getCostBasis("2022-01-22"), 0.000001);

    String buy2 = "(2022-03-16,2,1);(2022-03-18,2,1)";
    SmartStock myStock3 = new SmartStock("NVDA", "API", buy2, true);
    SmartStock myStock4 = new SmartStock("IBM", "API", buy2, true);
    Map<String, SmartStock> myList2 = new HashMap<String, SmartStock>();
    myList2.put("NVDA", myStock3);
    myList2.put("IBM", myStock4);

    SmartPortfolio myPortAPI = new SmartPortfolio(myList2);

    try {
      myPortAPI.sellStock("IBM", "3", "3", saleDate, true);
      Assert.fail();
    } catch (Exception e) {
      System.out.println("pass");
      System.out.println(e.getMessage());
    }


    myPortAPI.sellStock("IBM", "3", "3", "2022-03-20", true);

    System.out.println(myPortAPI.printPortfolioAt("2022-03-18"));
    double[] valueAt22API = myPortAPI.getTotalValues("2022-03-22");


    System.out.println(myPortAPI.printPortfolioAt("2022-03-22"));


    // loading and saving test
    myPortAPI.save("SellPortTestAPI", "DanUser");
    SmartPortfolio newPortAPI = (SmartPortfolio) DataHelpers.loadPortfolio(
            "DanUser", "SellPortTestAPI.json", 2);

    double[] valueAt22NewAPI = newPortAPI.getTotalValues("2022-03-22");
    System.out.println(newPortAPI.printPortfolioAt("2022-03-22"));

    Assert.assertEquals(valueAt22API[1], valueAt22NewAPI[1], 0.000001);
    Assert.assertEquals(myPortAPI.getCostBasis("2022-03-19"),
            newPortAPI.getCostBasis("2022-03-19"), 0.000001);
    Assert.assertEquals(myPortAPI.getCostBasis("2022-03-22"),
            newPortAPI.getCostBasis("2022-03-22"), 0.000001);
    System.out.println(newPortAPI.printPortfolioAt("current"));


  }


}