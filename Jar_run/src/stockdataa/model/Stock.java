package stockdataa.model;

/**
 * Interface stock that performs operations of a stock.
 */

public interface Stock {

  /**
   * This method is used to print the data obtained on a given date.
   *
   * @param date is the date on which data needs to be fetched.
   * @return the data fetched on that particular date.
   */
  String printDataAt(String date);

  /**
   * This method is used to get the data.
   *
   * @param date is the date on which data needs to be fetched.
   * @return the details of the portfolio.
   */

  double getData(String date);

  /**
   * This method is used to get the shares from the user.
   *
   * @return the number shares.
   */

  double getShares();

  /**
   * This method is used to get the ticker symbol from the user.
   *
   * @return the ticker symbol obtained from the user.
   */

  String getTicker();

  /**
   * This method is to add the number of shares.
   *
   * @param numShares is the number of shares to be added.
   * @return the sum obtained.
   */

  Stock addShares(double numShares);

  /**
   * This is the JSON parser.
   *
   * @return parsed json file.
   */

  String sharesToJSON();
}
