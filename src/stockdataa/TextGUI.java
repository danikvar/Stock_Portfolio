package stockdataa;

import java.io.PrintStream;

/**
 * This class represents all the operations to be supported by a text based user interface
 * to tell the user what input they must enter through print statements.
 */
public class TextGUI implements TextInterface {
  private PrintStream out;

  /**
   * Constructor that initializes the printstream.
   * @param out that prints data.
   */
  public TextGUI(PrintStream out) {
    this.out = out;
  }

  /**
   * Gets name of user.
   */

  public void showOptions() {
    //Enter name of the user
    System.out.println("Please enter a unique username consisting of alphanumeric characters"
            +
            " and _ only or choose one from the list above to log in.");
  }


  public void showporttype() {
    //Enter name of the user
    out.println("1 : Create a flexible portfolio");
    out.println("2 : Create a inflexible portfolio");
  }

  /**
   * Choose option to create or display portfolio.
   */

  public void chooseOption() {
    //StockData.Portfolio is displayed if present.
    out.println("C : Create a new portfolio");
    out.println("D : Display a portfolio");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than C or D to quit");
  }

  /**
   * Create a portfolio.
   */

  public void create() {
    out.println(" Create a new portfolio");
  }

  /**
   * gets Portfolio to be displayed.
   */

  public void showOptions1() {
    //StockData.Portfolio is displayed if present.
    out.println("Enter Portfolio to be displayed:");
  }

  /**
   * Gets full path to the local directory.
   */

  public void newUser() {
    //StockData.Portfolio is created by two ways.
    System.out.println("Please enter the full path to the local directory where your files " +
            "will be stored.");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than A or B to quit");
  }

  /**
   * Creates a new portfolio.
   */

  public void createnew() {
    //StockData.Portfolio is created by two ways.
    out.println("Menu: ");
    out.println("A : Do you want to upload a file?");
    out.println("B : Do you want a new empty portfolio to be created?");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than A or B to quit");
  }

  /**
   * Gets root directory.
   */

  public void getpath() {
    out.println("Enter root directory:");
  }

  /**
   * Gets name of the portfolio.
   */

  public void getportName() {
    out.println("Enter name of the portfolio to be saved."
            + " This name must only contain alphanumeric characters or _ like the User. "
            + " Do not include a file extension with the Portfolio it will be saved as .json.");
  }

  /**
   * Gets the date you would like for this portfolio.
   */
  public void getDate() {
    System.out.println("Please enter the date you would like for this portfolio"
            + " in YYYY-MM-DD format or 'current' to get all latest available stock"
            + " values");
  }

  /**
   * Menu to either add stock or determine value on a date.
   */

  public void addStock() {
    //Menu to either add stock or determine value on a date.
    out.println("Menu2: ");
    out.println("E : Add stock");
    out.println("Q : Determine total value on a date");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than E or Q to quit");
  }

  /**
   * If E or Q is entered by the user, then invalid option is chosen.
   */

  public void pressEnter() {
    //If E or Q is entered by the user, then invalid option is chosen.
    out.print("Please press enter.");
  }

  /**
   * ticker of stock.
   */
  public void addStockdetails() {
    //Enter stock ticker.
    out.println("Enter stock ticker:");
  }

  /**
   * ask how many shares to add stock.
   */

  public void addStockdetails2() {
    //ask how many shares to add stock.
    out.println("How many shares?");
  }

  /**
   * ask the commission fee
   */

  public void addCommission() {
    //ask how many shares to add stock.
    out.println("Please enter the commission fee.");
  }

  /**
   * ask for the date to perform a transaction
   */

  public void addDate() {
    //ask how many shares to add stock.
    out.println("Please enter the date for this transaction "
            + "in YYYY-MM-DD format.");
  }
  /**
   * Date when stock was bought.
   */

  public void addStockdetails3() {
    //Date when stock was bought.
    out.println("Enter the price at each date you would like in the stock.");
    out.println(" It must be in the format (YYYY-MM-DD, XX.yy);(...);...");
    out.println(" Do not enter additional characters other than numerics and separators.");
    out.println(" If wou would like to use the API to fetch prices just enter 'API'");
  }

  /**
   * asks if user wants to add again.
   */

  public void addAgain() {
    out.println("Type 'yes' if you want to add stock again");
    out.println("type anything else if want to finish");
  }

  public void showSmartopt(String add, String disp, String perf,
                           String CB, String sell){
    System.out.println("Choose one of the following options" +
            " or type 'Finish' to finish");
    if(!add.equals("")) {
      this.addOption(add);
    }

    if(!disp.equals("")) {
      this.displayOption(disp);
    }

    if(!perf.equals("")) {
      this.performanceOption(perf);
    }

    if(!CB.equals("")) {
      this.costBasisOption(CB);
    }

    if(!sell.equals("")) {
      this.sellOption(sell);
    }
  }

  /**
   * Helper function to print the option to add stock
   * at some input Opt.
   * @param Opt The input to add stock
   */
  public void addOption(String Opt) {
    StringBuilder output = new StringBuilder();
    output.append(Opt)
            .append(": Add stock");
    System.out.println(output.toString());
  }


  /**
   * Helper function to print the option to display portfolio
   * at some input Opt.
   * @param Opt The input to add stock
   */
  public void displayOption(String Opt) {
    StringBuilder output = new StringBuilder();
    output.append(Opt)
            .append(": Display portfolio at a certain date");
    System.out.println(output.toString());
  }

  /**
   * Helper function to print the option to display portfolio performance graph
   * at some input Opt.
   * @param Opt The input to add stock
   */
  public void performanceOption(String Opt) {
    StringBuilder output = new StringBuilder();
    output.append(Opt)
            .append(": Display portfolio performance");
    System.out.println(output.toString());
  }

  /**
   * Helper function to print the option to display Cost Basis
   * at some input Opt.
   * @param Opt The input to add stock
   */
  public void costBasisOption(String Opt) {
    StringBuilder output = new StringBuilder();
    output.append(Opt)
            .append(": Get cost basis at a date");
    System.out.println(output.toString());
  }
  /**
   * Helper function to print the option to sell stock
   * at some input Opt.
   * @param Opt The input to add stock
   */
  public void sellOption(String Opt) {
    StringBuilder output = new StringBuilder();
    output.append(Opt)
            .append(": Sell Stock");
    System.out.println(output.toString());
  }
  public void fromDate() {
    System.out.println("Enter the beginning date to determine the performance in 'yyyy-MM-dd'");
  }

  public void endDate() {
    System.out.println("Enter the end date to determine the performance in 'yyyy-MM-dd'");
  }

  public void costDate(){
    System.out.println("Enter date to determine cost basis:");
  }

  @Override
  public void addStockdetailsmart2() {
    System.out.println("Enter buy date, shares and commission fee in format (yyyy-MM-dd, " +
            "SHARES, COMM);(...);(...). Each data point is encased in a parentheses and " +
            "followed by a semicolon for extra dates.");
  }



  @Override
  public void AddStockError(Exception e) {
    System.out.println("Found this exception adding Stock:");
    System.out.println(e.toString());
    System.out.println("Type 'yes' to try again or 'quit' to quit.");
  }

}
