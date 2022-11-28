package stockdataa.model;

/**
 * Interface stock that performs operations of a stock.
 */

public interface Stock {
  String printDataAt(String date);

  double getData(String date);

  double getShares();

  String getTicker();

  Stock addShares(double numShares);

  String sharesToJSON();
}
