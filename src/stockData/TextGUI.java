import static java.lang.System.out;

public class TextGUI implements TextInterface{
  private PrintStream out;

  public TextGUI(PrintStream out) {
    this.out = out;
  }
  @Override
  public void showOptions() {
    //Enter name of the user
    out.println("Enter Name of user:");
  }
  @Override
  public void showOptions1() {
    //Portfolio is displayed if present.
    out.println("Portfolio:");
  }
  @Override
  public void createnew() {
    //Portfolio is created if not present already.
    out.println("New portfolio created");
  }
  @Override
  public void addStock() {
    //Menu to either add stock or determine value on a date.
    out.println("Menu: ");
    out.println("E : Add stock");
    out.println("Q : Determine total value on a date");
  }
  @Override
  public void showOptionError() {
    //If E or Q is entered by the user, then invalid option is chosen.
    out.print("\nInvalid option. Please try again.");
  }
  @Override
  public void addStockdetails() {
    //Data required to add stock.
    out.println("Enter stock");
    out.println("How many shares?");
  }
  @Override
  public void totalValue() {
    //Total value displayed for a particular date.
    out.println("| TICKER | DATE | Num_Shares | open | high |  low | close | volume |");
  }

}
