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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

  private static String apiKey = "STCCIMO3IO23H86C";

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
        return loadLocalTickers();
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private static Set<String> loadLocalTickers() throws FileNotFoundException {

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
      throw new FileNotFoundException("There is no local Ticker file and the API cannot be reached.");
    }
    return allStocks;
  }

  private static Set<String> getAPITickers() throws RuntimeException {
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
      https:
//www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo
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
      Iterator it = stockNames.iterator();
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

    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curDate = dtf.format(now); //curent date as a string object
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      https:
//www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo
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
        Double dateData = Double.parseDouble(tickerInfo[1]);

        stockData.put(myKey, dateData);
        line = in.readLine();

      }

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    return stockData;
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
   * This function gets all the folders in the Users folder and lists them
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
      throw new FileNotFoundException("There is no User with a matching name." +
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
      throw new FileNotFoundException("There is no Portfolio directory in 'root.txt' specified in the User File");
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
   *
   * @param userName      name of the user.
   * @param PortfolioName name of the portfolio.
   * @return the portfolio for the user.
   * @throws FileNotFoundException when portfolio is not found.
   */
  public static StockPortfolio loadPortfolio(String userName, String PortfolioName) throws FileNotFoundException {
    String portDir = getPortfolioDir(userName);
    File portFolder = new File(portDir);
    File[] portList = portFolder.listFiles();

    String myPort = "";

    // Checking to see if we already have a folder for the user
    // Otherwise we must create a new one

    if (portList.length > 0) {
      for (int i = 0; i < portList.length; i++) {

        //System.out.println(listOfFiles[i].getPath());
        if (PortfolioName.equals(portList[i].getName())) {
          //System.out.println(listOfFiles[i].getPath());
          myPort = portList[i].getPath();
          break;
        }

      }
    }

    if (myPort.isEmpty()) {
      throw new FileNotFoundException("There is no Portfolio with a matching name." +
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
    while (scan.hasNextLine()) {
      line = scan.nextLine();
      if (line.contains("Stock")) {

        String line2 = scan.nextLine();
        String ticker = line2.split("\"")[3];
        line2 = scan.nextLine();
        int shares = Integer.parseInt(line2.split("\"")[3]);
        scan.nextLine();
        line2 = scan.nextLine();

        if (line2.contains("API")) {
          myPortfolio.addStock(ticker, shares, "API");
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
            myPortfolio.addStock(ticker, shares, priceData);
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


}
