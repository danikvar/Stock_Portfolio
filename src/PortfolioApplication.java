import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class PortfolioApplication {
  public static void main(String []args) throws IOException {
    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/
    String apiKey = "STCCIMO3IO23H86C";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curDate = dtf.format(now); //curent date as a string object
    String stockSymbol = "GOOG"; //ticker symbol for Google
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
              + ".co/query?function=LISTING_STATUS"
              + "&state=active"
              + "&apikey="+apiKey);
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }

    BufferedReader br = new BufferedReader(new StringReader(String.valueOf(output)));

    String line = br.readLine();

    System.out.println("oONEEEEEE");
    while (line != null) {
      // split on comma(',')
      String[] tickerInfo = line.split(",");


      // add values from csv to car object
      System.out.println("=============================================");
      System.out.println(tickerInfo[0]);
      System.out.println(tickerInfo[1]);
      System.out.println(tickerInfo[2]);
      System.out.println(tickerInfo[3]);
      System.out.println("=============================================");
      line = br.readLine();
    }
    

    System.out.println("TWWWWWWWWWWWWOOOOOOOOOOOOOOOOOOOOO");

    //System.out.println("Return value: ");
    //System.out.println(output.toString());
  }
}
