import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Set;
import stockData.DataHelpers;


public class PortfolioApplication {
  public static void main(String []args) throws IOException {

    // How to use this set:
    // When a ticker is inputted to buy a specific stock or to display
    // in a portfolio it must be in this set. We can check this for some
    // String Stock using allStocks.contains(Stock)
    // Likely uses:
    // addStock() in Portfolio and parsePortfolio() in the application
    Set<String> allStocks = DataHelpers.getTickers();

    //System.out.println(allStocks.toString());
    //System.out.println(allStocks.toString().length());


  }

}

