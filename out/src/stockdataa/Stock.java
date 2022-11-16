package stockdataa;

public interface Stock {
  String printDataAt(String date);

  double getData(String date);

  int getShares();

  Stock1 addShares(int numShares);

  String sharesToJSON();
}
