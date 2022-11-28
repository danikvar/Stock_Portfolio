package stockdataa.view;

/**
 * Interface that implemented by TectGUI to get user inputs for the view.
 */
public interface TextInterface {

  void showOptions();

  void showOptions1();

  void getportName();

  void addAgain();

  void addStockdetails();

  void addStockdetails2();

  void addStockdetails3();

  void showporttype();

  void chooseOption();

  void getDate();

  void showSmartopt(String add, String disp, String perf,
                    String CB, String sell);

  void fromDate();

  void endDate();

  void costDate();

  void addStockdetailsmart2();

  /**
   * Prints the exception passed to the view.
   */
  void AddStockError(Exception e);

  /**
   * Get the commission value.
   */
  void addCommission();

  /**
   * ask for the date to perform a transaction
   */
  void addDate();
}
