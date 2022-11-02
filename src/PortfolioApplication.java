import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import StockData.DataHelpers;
import StockData.Portfolio;


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

  /**
   * Creates and returns a portfolio object from a JSON string in
   * the format {"STOCK_NAME": STOCKS_OWNED, ...}. The JSON string
   * is fetched by reading in the file at the given directory.
   * If the file is not found it prints an error message and
   * returns and empty portfolio.
   *
   * @param jsonDir directory of JSON file to parse
   * @return A new StockPortfolio object with the stocks
   *     from the passed string.
   */
  public Portfolio parseJSON(String jsonDir) {

    Portfolio output = new Portfolio();

    File file = new File(jsonDir);
    try (Scanner sc =  new Scanner(file)){
      while (sc.hasNextLine()){
        String myLine = sc.next();

        boolean startEnd = myLine.contains(new StringBuilder("{"))
                || myLine.contains(new StringBuilder("}"));
        if(! startEnd) {
          //

          Pattern p = Pattern.compile("\"([^\"]*)\"");
          Matcher m = p.matcher(myLine);

          m.find();
          String ticker = m.group(1);
          int shares = Integer.parseInt(myLine.replaceAll("[^0-9]", ""));
          // TODO: Fix this so it takes a string data not just "API"
          output.addStock(ticker, shares, "API");
        }

      }
    } catch (FileNotFoundException e) {
      System.out.println("Your Portfolio could not be retrieved from the system."
              + "\n Fetching an empty portfolio for you to work on. Check file name and try again.");
      return output;
    }

    return output;

  }

}

