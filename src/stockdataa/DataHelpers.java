package stockdataa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * The data helpers class that handles the api data to get tickers and other information.
 */

public class DataHelpers {

  // download the Stocks file --> keeps all the tickers
  // list all folders in a directory (Users) --> go into that directory and then fetch a text file
  private static String thisDir = new File(".").getAbsoluteFile().getParent();

  private static String apiKey = "GRABRKHK5FVOYTBF";

  /**
   * Constructor that processes the tickers.
   *
   * @return list of tickers.
   * @throws IllegalArgumentException when file not found.
   */
  public static Set<String> getTickers() throws IllegalArgumentException {
    try {
      return getAPITickers();
    } catch (RuntimeException e) {
      try {
        System.out.println("Error Thrown");
        return loadLocalTickers();
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * It loads the local tickers from the resources file.
   * @return a string set with the tickers.
   * @throws FileNotFoundException when is no local file.
   */
  public static Set<String> loadLocalTickers() throws FileNotFoundException {

    Set<String> allStocks = new HashSet<String>();

    try {
      String tickerPath = "Resources" + File.separator + "Tickers.txt";
      File nameFile = new File(tickerPath);
      Scanner scan = new Scanner(nameFile);
      String line;
      while (scan.hasNextLine()) {
        line = scan.nextLine();
        allStocks.add(line);
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("There is no local Ticker file and the API cannot "
              +
              "be reached.");
    }
    return allStocks;
  }

  /**
   * Gets API tickers from the URL. If it successfully gets a set of
   * tickers then it will save them to the resources folder as tickers.txt.
   * @return a set with tickers in String form.
   * @throws RuntimeException if API could not be contacted.
   */
  public static Set<String> getAPITickers() throws RuntimeException {
    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/


    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */

      url = new URL("https://www.alphavantage"
              + ".co/query?function=LISTING_STATUS"
              + "&state=active"
              + "&apikey=" + apiKey);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    Set<String> stockNames = new HashSet<String>();
    BufferedReader in = null;

    try {
      in = new BufferedReader(new InputStreamReader(url.openStream()));
      //System.out.println("Line 1");
      String line = null;
      try {
        line = in.readLine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      while (!Objects.isNull(line)) {

        if(line.equals("{}")) {
          throw new RuntimeException("API was called too much");
        }

        String[] tickerInfo = line.split(",");



        stockNames.add(tickerInfo[0]);
        try {
          line = in.readLine();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }


        stockNames.add(tickerInfo[0]);

      }
    } catch (IOException e) {
      throw new RuntimeException("API could not be contacted.");
    }

    BufferedWriter out = null;
    try {
      String tickerPath = "Resources" + File.separator + "Tickers.txt";
      out = new BufferedWriter(new FileWriter(tickerPath));
      Iterator<String> it = stockNames.iterator();
      while (it.hasNext()) {
        out.write(String.valueOf(it.next()));
        out.newLine();
      }
      out.close();
    } catch (IOException e) {
      throw new RuntimeException("The API file could not be written to");
    }


    return stockNames;

  }

  /**
   * Method to get the stock data from API.
   *
   * @param ticker the stock.
   * @return the map of date and ticker.
   */

  public static Map<LocalDate, Double> getStockData(String ticker) {

    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    Map<LocalDate, Double> stockData = new HashMap<>();

    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

      String line = null;

      try {
        in.readLine();
        line = in.readLine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      while (!Objects.isNull(line)) {
        String[] tickerInfo = line.split(",");
        //System.out.println(line);
        String keyStr = tickerInfo[0];
        LocalDate myKey = LocalDate.parse(keyStr);
        Double dateData = Double.parseDouble(tickerInfo[4]);

        stockData.put(myKey, dateData);
        line = in.readLine();

      }

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    return stockData;
  }

  /**
   * Method to get the stock data from API.
   *
   * This method also allows you to input date strings to get data between the two dates.
   * It can get this data daily, weekly, or monthly depending on the input for the char type.
   * If type is D it defaults to daily data. If it is W then it returns weekly data.
   * If the type character is M then it is monthly. If it is Y then it is yearly,
   * otherwise it throws an error. If the date starts in the middle of a time frame type
   * it will return the price at the end of the timeframe (closing year/month/week price).
   * If the data is yearly all date keys will be in the format 01-01-YYYY BUT
   * the average value for a year will only include the average of all the months at or after
   * a given start date.
   * If it is not empty, then the function get all dates after the given start date.
   * Otherwise, it returns all available dates. If the end date is not empty then the prices
   * returned will be between this and the start date. Otherwise, it returns all dates with
   * no last date cap. If date1 is empty then date2 must also be empty. Lastly it also includes
   * a variable buyDate. This is the first date the stock was bought at and the method will not
   * return any dates before it, unless it is null.
   * @param date1 This is the date to start our time frame
   * @param date2 This is a second optional date to end gathering stock data after.
   * @param ticker the stock.
   * @param type The time frame type of stock data to output
   * @param buyDate The first date this stock was bought
   * @throws IllegalArgumentException
   * @return the map of date and ticker.
   */


  public static Map<LocalDate, Double> getStockData(
          String ticker, final String date1,
          final String date2, final char type,
          String buyDate) throws IllegalArgumentException {
    //System.out.println("WHAT?");

    // This part is setup for our later parsing

    LocalDate[] startEndDate = new LocalDate[2];
    LocalDate buyD = null;
    boolean bD = false;
    if(!Objects.isNull(buyDate)) {
      try{
        buyD = LocalDate.parse(buyDate);
        bD = true;
      } catch(Exception e) {
        throw new IllegalArgumentException("Your buy-date could not be parsed " +
                "and is not null! Please check the input.");
      }

    }

    try {
      startEndDate = catchInitStockExcpt(date1, date2, type);
    } catch(IllegalArgumentException e) {
      throw e;
    }

    boolean pre = false;
    boolean post = false;
    if(!Objects.isNull(startEndDate[0])) {
      pre = true;
    }
    //System.out.println("WHAT?1");

    if(!Objects.isNull(startEndDate[1])) {
      post = true;
    }
    //
    URL url = null;
    String myType;
    switch (type) {
      case 'D':
        myType = "DAILY";
        break;
      case 'W':
        myType = "WEEKLY";
        break;
      case 'M':
        myType = "MONTHLY";
        break;
      case 'Y':
        myType = "MONTHLY";
      default:
        throw new IllegalArgumentException("Timeframe type specification is incorrect" +
                " Please us D for daily, W for Weekly, and M for monthly");
    }

    //System.out.println("WHAT?2");
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append("https://www.alphavantage.co/query?function=TIME_SERIES_");
    urlBuilder.append(myType);
    urlBuilder.append("&outputsize=full&symbol=");
    urlBuilder.append(ticker);
    urlBuilder.append("&apikey=");
    urlBuilder.append(apiKey);
    urlBuilder.append("&datatype=csv");


    // now we beging parsing
    try {


      //System.out.println("WHAT?3");

      url = new URL(urlBuilder.toString());
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    Map<LocalDate, Double> stockData = new HashMap<>();


    try {
      //System.out.println("WHAT?4");
      //System.out.println(startEndDate[0]);
      //System.out.println(startEndDate[1]);
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

      String line = null;

      in.readLine();
      line = in.readLine();

      //System.out.println(line);
      while (!Objects.isNull(line)) {
        //System.out.println(line);
        String[] tickerInfo = line.split(",");
        //System.out.println(line);
        String keyStr = tickerInfo[0];

        try{
          LocalDate myKey = LocalDate.parse(keyStr);
          Double dateData = Double.parseDouble(tickerInfo[4]);

          if(addToList(pre, post, bD, myKey, startEndDate,buyD)) {
            if(type == 'Y') {
              int myYear = myKey.getYear();
              StringBuilder lastDate = new StringBuilder().append("12-31-");
              lastDate.append(String.valueOf(myYear));
              if (myKey.isEqual(LocalDate.parse(lastDate.toString()))) {
                stockData.put(myKey, dateData);
              }
            } else {
              stockData.put(myKey, dateData);
            }

          }

        } catch(Exception e) {
          throw e;
        }

        line = in.readLine();

      }

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    return stockData;
  }

  public static Pair<Character, Integer> createTimeInterval(LocalDate d1, LocalDate d2) {
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

    Pair<Character, Integer> outPair = new Pair<Character, Integer>(dayWeekMonth, numSplits);
    return outPair;
  }

  private static boolean addToList(boolean pre, boolean post, boolean bD,
                                   LocalDate myKey, LocalDate[] startEndDate,
                                   LocalDate buyD) {
    boolean shouldAdd = false;
    if(pre) {
      if(myKey.compareTo(startEndDate[0]) >= 0) {

        if(post) {
          if(myKey.compareTo(startEndDate[1]) <= 0) {
            if(!bD || myKey.compareTo(buyD) >= 0) {
              shouldAdd = true;
            }
          }


        } else if(!bD || myKey.compareTo(buyD) >= 0) {
          shouldAdd = true;
        }
      }
    } else if(!bD || myKey.compareTo(buyD) >= 0) {
      shouldAdd = true;
    }

    return shouldAdd;
  }
  private static LocalDate[] catchInitStockExcpt(String date1, String date2,
                                                 char type) throws IllegalArgumentException {

    if(date1.isEmpty() && ! date2.isEmpty() ) {
      throw new IllegalArgumentException("The end date must be empty if the start date " +
              "is empty!");
    }
    LocalDate startDate = null;
    LocalDate endDate = null;
    if(!date1.isEmpty() && !date2.isEmpty()) {

      try{
        startDate = LocalDate.parse(date1);
        endDate = LocalDate.parse(date2);
      } catch(Exception e) {
        throw new IllegalArgumentException("Your date strings could not be parsed! " +
                "Please check that your date input strings are in YYYY-MM-DD format.");
      }


      if( startDate.isAfter(endDate) ) {
        throw new IllegalArgumentException("The end date must be after the start date." +
                " Please recheck your date inputs and try again.");
      }

    }

    if(Objects.isNull(type)) {
      throw new IllegalArgumentException("Please specify a timeframe type!");
    }

    if("DWM".indexOf(type) == -1) {
      throw new IllegalArgumentException("Timeframe type specification is incorrect" +
              " Please us D for daily, W for Weekly, and M for monthly");
    }

    return new LocalDate[] {startDate,endDate};
  }



  /**
   * To move the display to correct positions on the right.
   *
   * @param s to be padded correctly.
   * @param n shares.
   * @return the padded output string.
   */

  public static String padRight(String s, int n) {
    for (int i = 0; i < n; i++) {
      s = s + " ";
    }
    return s;
  }

  /**
   * To move the display to correct positions on the left.
   *
   * @param s to be padded correctly.
   * @param n shares.
   * @return the padded output string.
   */

  public static String padLeft(String s, int n) {
    for (int i = 0; i < n; i++) {
      s = " " + s;
    }
    return s;
  }

  /**
   * this function just gets all the files in the Users folder.
   *
   * @return the files.
   */
  private static File[] getUserArray() {
    String userDir = new StringBuilder().append(thisDir).append(File.separator + "Resources"
            + File.separator + "Users").toString();

    File userFolder = new File(userDir);
    return userFolder.listFiles();
  }

  /**
   * This function gets all the folders in the Users folder and lists them.
   *
   * @return the folders.
   */
  public static String getUsers() {

    File[] listOfFiles = getUserArray();

    StringBuilder myUsers = new StringBuilder();

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    if (listOfFiles.length > 0) {
      for (int i = 0; i < listOfFiles.length; i++) {

        //System.out.println(listOfFiles[i].getPath());
        myUsers.append(listOfFiles[i].getName());

        if (i != listOfFiles.length - 1) {
          myUsers.append(", ");
        }

      }
    }


    return myUsers.toString();
  }

  /**
   * The list for the user.
   *
   * @return the list of all the users.
   */
  public static List<String> getUserList() {

    File[] listOfFiles = getUserArray();

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    List<String> myUsers = new ArrayList<String>();
    if (listOfFiles.length > 0) {
      for (int i = 0; i < listOfFiles.length; i++) {

        //System.out.println(listOfFiles[i].getPath());
        myUsers.add(listOfFiles[i].getName());

      }
    }


    return myUsers;
  }


  /**
   * this function gets the portfolio directory by reading the file "root.txt" created.
   *
   * @param userName           name of the user.
   * @param portfolioDirectory directory where the portfolio is present.
   */

  public static void createUser(String userName, String portfolioDirectory) {
    File directory = new File("Resources" + File.separator
            + "Users" + File.separator + userName);
    directory.mkdirs();

    try (PrintWriter out = new PrintWriter(directory.getPath() + File.separator + "root.txt")) {
      out.println(portfolioDirectory);
    } catch (FileNotFoundException e) {
      System.out.println("Your file has encountered an error while saving."
              + "Please check portfolio directory.");
      throw new RuntimeException(e);
    }
  }

  /**
   * get directory of the portfolio.
   *
   * @param userName name of the user.
   * @return the portfolio directory.
   * @throws FileNotFoundException where no file is present.
   */
  public static String getPortfolioDir(String userName) throws FileNotFoundException {

    File[] listOfFiles = getUserArray();

    StringBuilder myUsers = new StringBuilder();
    String myDir = "";

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    if (listOfFiles.length > 0) {
      for (int i = 0; i < listOfFiles.length; i++) {
        if (userName.equals(listOfFiles[i].getName())) {
          //System.out.println(listOfFiles[i].getPath());
          myDir = listOfFiles[i].getPath();
          break;
        }
      }
    }

    if (myDir.isEmpty()) {
      throw new FileNotFoundException("There is no User with a matching name."
              +
              "Please check your input and try again");
    }


    //File dirFile = new File();
    //System.out.println();
    //System.out.println((new File(myDir).listFiles())[0].getPath());
    try {
      File dirFile = new File((myDir + "\\root.txt"));
      Scanner scan = new Scanner(dirFile);
      String portDir = scan.nextLine();
      return portDir;
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("There is no Portfolio directory in 'root.txt' " +
              "specified in the User File");
    }

  }


  // For my test user i created a folder on my desktop with some JSON and txt files
  // If I use my test folder it prints this: port1.json, port2.txt, port3.txt
  // I would like to make sure that the user enters the extension they would like
  // to save their data as.

  /**
   * This lists all the portfolios associated with a given user.
   *
   * @param userName name of user.
   * @return the list of portfolios.
   * @throws FileNotFoundException when file is not found.
   */

  public static String listPortfolios(String userName) throws FileNotFoundException {
    String portDir = getPortfolioDir(userName);
    File portFolder = new File(portDir);
    File[] portList = portFolder.listFiles();

    StringBuilder myPorts = new StringBuilder();

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    if (portList.length > 0) {
      for (int i = 0; i < portList.length; i++) {

        //System.out.println(listOfFiles[i].getPath());
        myPorts.append(portList[i].getName());

        if (i != portList.length - 1) {
          myPorts.append(", ");
        }

      }
    }
    return myPorts.toString();
  }

  /**
   * loads the portfolio.
   * @param userName      name of the user.
   * @param portfolioName name of the portfolio.
   * @return the portfolio for the user.
   * @throws FileNotFoundException when portfolio is not found.
   */
  public static StockPortfolio loadPortfolio(String userName, String portfolioName)
          throws FileNotFoundException {
    String portDir = getPortfolioDir(userName);
    File portFolder = new File(portDir);
    File[] portList = portFolder.listFiles();

    String myPort = "";

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    if (portList.length > 0) {
      for (int i = 0; i < portList.length; i++) {

        //System.out.println(listOfFiles[i].getPath());
        if (portfolioName.equals(portList[i].getName())) {
          //System.out.println(listOfFiles[i].getPath());
          myPort = portList[i].getPath();
          break;
        }

      }
    }

    if (myPort.isEmpty()) {
      throw new FileNotFoundException("There is no Portfolio with a matching name."
              +
              "Please check your input and try again");
    } else {
      return parsePortfolio(myPort);
    }
  }

  /**
   * converts the portfolio file to a string.
   *
   * @param portDir directory where the portfolio is present.
   * @return the string formatted portfolio.
   * @throws FileNotFoundException where portfolio is not found.
   */
  public static StockPortfolio parsePortfolio(String portDir) throws FileNotFoundException {
    File portFile = new File(portDir);
    Scanner scan = new Scanner(portFile);
    //System.out.println(scan.nextLine());
    String line;

    Portfolio myPortfolio = new Portfolio();
    //Pattern btwnQuotes = Pattern.compile("\".*\\\\\\\"(.*)\\\\\\\".*\"");
    // TODO: CHECK THIS
    while (scan.hasNextLine()) {
      line = scan.nextLine();
      if (line.contains("Stock")) {

        String line2 = scan.nextLine();
        String ticker = line2.split("\"")[3];
        line2 = scan.nextLine();
        String shares = line2.split("\"")[3];
        scan.nextLine();
        line2 = scan.nextLine();

        if (line2.contains("API")) {
          myPortfolio.addStock(ticker, shares, "API", true);
        } else {
          Map<LocalDate, Double> priceData = new HashMap<LocalDate, Double>();
          line2 = scan.nextLine();

          while (!line2.contains("}")) {
            String[] datePrice = line2.split("\"");
            LocalDate myKey = LocalDate.parse(datePrice[1]);
            Double price = Double.parseDouble(datePrice[3]);
            priceData.put(myKey, price);
            line2 = scan.nextLine();
          }
          // If there is an error with the ticker or shares this will say
          try {
            myPortfolio.addStock(ticker, Integer.parseInt(shares), priceData);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
          }
        }

      }
    }


    return myPortfolio;
  }

  /**
   * checks if string is alphanumeric.
   *
   * @param s the string passed.
   * @return true or false.
   */
  public static boolean isAlphaNumeric(String s) {
    return s != null && s.matches("^[a-zA-Z0-9_]*$");
  }

  //TODO: Comment add method
  public static<T extends Number> T add(T x, T y){

    if (x == null || y == null) {
      return null;
    }

    if (x instanceof Double) {
      return (T) new Double(x.doubleValue() + y.doubleValue());
    } else if (x instanceof Integer) {
      return (T)new Integer(x.intValue() + y.intValue());
    } else {
      throw new IllegalArgumentException("Type " + x.getClass() + " is not supported by this method");
    }
  }
}
