package stockData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DataHelpers {

  private static String apiKey = "STCCIMO3IO23H86C";
  public static Set<String> getTickers() throws IllegalArgumentException{
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

      while ( !Objects.isNull(line)) {
        String[] tickerInfo = line.split(",");
        stockNames.add(tickerInfo[0]);
        try {
          line = in.readLine();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }


        stockNames.add(tickerInfo[0]);

      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }

    return stockNames;

  }

  // WE ARE USING THE OPEN PRICE AS OUR PRICE FOR THE TARGET

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
       */https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    Map<LocalDate, Double> stockData = new HashMap<> ();

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

    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+ticker);
    }
    return stockData;
  }

  public static String padRight(String s, int n) {
    for(int i = 0; i < n; i++) {
      s = s + " ";
    }
    return s;
  }

  public static String padLeft(String s, int n) {
    for(int i = 0; i < n; i++) {
      s = " " + s;
    }
    return s;
  }


}
