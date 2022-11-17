package stockdataa;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

abstract class AbstractController implements Controller {
  private Scanner in;
  private stockdataa.TextInterface view;
  private stockdataa.StockPortfolio model;

  public AbstractController(StockPortfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);
  }

  /**
   * Gets username from the user.
   * @param user name of the user
   * @throws FileNotFoundException when input is invalid
   */

  public void getUser(String user) throws FileNotFoundException {
    //tell view to show options
    while (!stockdataa.DataHelpers.isAlphaNumeric(user)) {
      System.out.println("Please enter a username consisting only of numbers, letters, and '_'.");
      System.out.println("If you would like to quit please type 'quit'");
      user = in.nextLine();
      if (user.equals("quit")) {
        controller();
      }
    }
    if (user.length() == 0 || user.equals("quit")) {
      controller();
    }
  }


  /**
   * Method that creates a new user and file location for them
   *
   * @param user name of the user
   * @throws FileNotFoundException when input is invalid.
   */
  public void createUser(String user) throws FileNotFoundException {
    System.out.println("Please enter the full path to the local directory"
            + "where your files will be stored.");
    String userPath = in.nextLine();
    Path checkUserPath = Paths.get(userPath);

    while (!Files.exists(checkUserPath)) {
      System.out.println("Your directory does not exist or could not be found."
              + "Please check input and try again or type restart.");
      userPath = in.nextLine();
      if (userPath.equals("restart")) {
        controller();
      }
      checkUserPath = Paths.get(userPath);
    }
    DataHelpers.createUser(user, userPath);
    controller();
  }

  public boolean displayPort(StockPortfolio model) throws FileNotFoundException {
    System.out.println("If you would like display your portfolio type P, otherwise"
            + " type Finish to restart or any other input to exit");
    //accept user input
    String option = in.next();
    boolean quit = false;
    switch (option) {
      case "P":
        getDatetoDisplay(model);
      case "Finish":
        controller();
        break;
      default:
        quit = true;
        break;
    }
    return quit;
  }

  public void getDatetoDisplay(StockPortfolio model) {
    view.getDate();
    String input = in.nextLine();
    //give to model
    try {
      if (input.contains("current")) {
        System.out.println(model.toString());
      } else {
        System.out.println(model.printPortfolioAt(input));
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public String addabs(String inp, String portName, String user, StockPortfolio model) {
    boolean onlyInts = true;
    String inputTemp2;
    while (inp.equals("yes")) {
      //ask for stock input.
      view.addStockdetails();

      String input = in.nextLine();
      //TODO TRY CATCH HERE
      if(model instanceof Portfolio) {
        inputTemp2 = addSimple();
      }
      if(model instanceof SmartPortfolio){
       inputTemp2 = addSmart();
      }
      //ask for date/data input.
      view.addStockdetails3();
      String input3 = in.nextLine();
      //give to model
      try {
        model.addStock(input, input3, inputTemp2, onlyInts);
        model.save(portName, user);
        view.addAgain();
        inp = in.nextLine();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    return inp;
  }

  public String addSimple(){
    view.addStockdetails2();
    String inputTemp2 = in.nextLine();
    while (!inputTemp2.matches("[0-9]+")) {
      System.out.println("Please enter an postive integer value for shares.");
      inputTemp2 = in.nextLine();
    }
    return inputTemp2;
  }

  public String addSmart(){
    view.addStockdetailsmart2();
    String str = in.nextLine();
    String rem = str.substring(str.indexOf(",")+1);
    String get = rem.substring(0, rem.indexOf(","));

    while(!get.matches("[0-9]+")) {
      System.out.println("Please enter an postive integer value for shares.");
      view.addStockdetailsmart2();
      str = in.nextLine();
      rem = str.substring(str.indexOf(",")+1);
      get = rem.substring(0, rem.indexOf(","));
    }
    return get;
  }
}