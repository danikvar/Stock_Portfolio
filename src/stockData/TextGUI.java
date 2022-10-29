package StockData;
import java.io.PrintStream;


public class TextGUI implements TextInterface{
  private PrintStream out;

  public TextGUI(PrintStream out) {
    this.out = out;
  }

  public void showOptions() {
    //Enter name of the user
    out.println("Enter Name of user:");
  }

  public void showOptions1() {
    //StockData.Portfolio is displayed if present.
    out.println("StockData.Portfolio:");
  }

  public void createnew() {
    //StockData.Portfolio is created if not present already.
    out.println("New portfolio created");
  }

  public void addStock() {
    //Menu to either add stock or determine value on a date.
    out.println("Menu: ");
    out.println("E : Add stock");
    out.println("Q : Determine total value on a date");
  }

  public void showOptionError() {
    //If E or Q is entered by the user, then invalid option is chosen.
    out.print("\nInvalid option. Please try again.");
  }

  public void addStockdetails() {
    //Enter stock ticker.
    out.println("Enter stock");
  }

  public void addStockdetails2(){
    //ask how many shares to add stock.
    out.println("How many shares?");
  }

  public void dateInput() {
    // a particular date requested.
    out.println("Enter date");
  }



}
