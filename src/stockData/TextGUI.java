package stockData;
import java.io.PrintStream;


public class TextGUI implements TextInterface{
  private PrintStream out;

  public TextGUI(PrintStream out) {
    this.out = out;
  }

  public void showOptions() {
    //Enter name of the user
    out.println("Enter Name of user (New user name or existing name):");
  }
  public void chooseOption() {
    //StockData.Portfolio is displayed if present.
    out.println(" C : Create a new portfolio");
    out.println("D : Display a portfolio");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than C or D to quit");
  }

  public void create(){
    out.println(" Create a new portfolio");
  }

  public void showOptions1() {
    //StockData.Portfolio is displayed if present.
    out.println("Enter Portfolio to be displayed:");
  }

  public void createnew() {
    //StockData.Portfolio is created by two ways.
    out.println("Menu: ");
    out.println("A : Do you want to upload a file?");
    out.println("B : Do you want a new empty portfolio to be created?");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than A or B to quit");
  }

  public void getpath(){
    out.println("Enter file path:");
  }

  public void addStock() {
    //Menu to either add stock or determine value on a date.
    out.println("Menu2: ");
    out.println("E : Add stock");
    out.println("Q : Determine total value on a date");
    out.println("Finish : To finish and save the operation and go to the start.");
    out.println("Enter a character other than E or Q to quit");
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
  public void addStockdetails3(){
    //Date when stock was bought.
    out.println("Enter date bought:");
  }

  public void dateInput() {
    // a particular date requested.
    out.println("Enter date");
  }



}
