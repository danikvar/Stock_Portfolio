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
    out.println("Enter Name of user (New user name or existing name):");
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
   * Gets the date you would like for this portfolio
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
    out.println("Enter stock");
  }

  /**
   * ask how many shares to add stock.
   */

  public void addStockdetails2() {
    //ask how many shares to add stock.
    out.println("How many shares?");
  }

  /**
   * Date when stock was bought.
   */

  public void addStockdetails3() {
    //Date when stock was bought.
    out.println("Enter the price at each date you would like in the stock.");
    out.println(" It must be in the format (YYYY-MM-DD, XX.yy);(...);...");
    out.println(" Do not enter additional characters other than numerics and separators.");
  }

  /**
   * asks if user wants to add again.
   */

  public void addAgain() {
    out.println("Type 'yes' if you want to add stock again");
    out.println("type anything else if want to finish");
  }

}
