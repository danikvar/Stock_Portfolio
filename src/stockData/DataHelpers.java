package stockData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
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

  public static HashMap<LocalDate, double[]> getStockData(String ticker) {

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

    HashMap<LocalDate, double[]> stockData = new HashMap<> ();

    try {
      Set<String> stockNames = new HashSet<String>();
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
        double[] dateData = new double[5];
        dateData[0] = Double.parseDouble(tickerInfo[1]);
        dateData[1] = Double.parseDouble(tickerInfo[2]);
        dateData[2] = Double.parseDouble(tickerInfo[3]);
        dateData[3] = Double.parseDouble(tickerInfo[4]);
        dateData[4] = Double.parseDouble(tickerInfo[5]);

        stockData.put(myKey, dateData);
        line = in.readLine();

      }

    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+ticker);
    }






    return stockData;
  }
}