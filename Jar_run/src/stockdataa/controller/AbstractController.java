package stockdataa.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import stockdataa.DataHelpers;
import stockdataa.model.Operations;
import stockdataa.model.StockPortfolio;
import stockdataa.view.TextInterface;
import stockdataa.view.buttons.Buttons;

/**
 * This represents the class which includes the abstract controller used for GUI, Smart and Normal
 * portfolio.
 */

abstract class AbstractController implements IController {
  protected Scanner in;
  protected Buttons buttonview;
  protected stockdataa.model.StockPortfolio model;
  protected TextInterface view;
  protected Operations opModel;

  /**
   * Abstract Controller for Smart, Simple Portfolio and GUI.
   *
   * @param model is the model object of StockPortfolio.
   * @param in    is the input stream.
   * @param view  is the button view.
   */

  public AbstractController(StockPortfolio model, InputStream in, Buttons view) {
    this.model = model;
    this.buttonview = view;
    this.in = new Scanner(in);
  }

  /**
   * Abstract Controller for Smart, Simple Portfolio and GUI.
   *
   * @param model is the model object of StockPortfolio.
   * @param in    is the input stream.
   * @param view  is the TextInterface view.
   */

  public AbstractController(StockPortfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);
  }

  /**
   * Abstract Controller for GUI.
   *
   * @param opModel is the model object passed to the GUI controller.
   * @param view    is the view passed to GUI Controller.
   */

  public AbstractController(Operations opModel, Buttons view) {
    this.opModel = opModel;
    this.buttonview = view;

  }

  /**
   * Gets username from the user.
   *
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
   * Method that creates a new user and file location for them.
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

  /**
   * This method is to display all the portfolios present.
   *
   * @param model is StockPortfolio object.
   * @return the list of portfolios.
   * @throws FileNotFoundException throws exception.
   */

  public boolean displayPort(StockPortfolio model) throws FileNotFoundException {
    System.out.println("If you would like display your portfolio type P, otherwise"
            + " type Finish to restart or any other input to exit");
    //accept user input
    String option = in.next();
    boolean quit = false;
    switch (option) {
      case "P":
        getDatetoDisplay(model);
        break;
      case "Finish":
        controller();
        break;
      default:
        quit = true;
        break;
    }
    return quit;
  }

  /**
   * This is the moethod to get the date.
   *
   * @param model is object of Stockportfolio.
   */
  public void getDatetoDisplay(StockPortfolio model) {
    view.getDate();
    String input = in.nextLine();
    if (input.equals("")) {
      input = in.nextLine();
    }
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

  /**
   * This method is to add stocks to portfolio.
   *
   * @param inp      is the input from user to add more stocks or not.
   * @param portName is the name of the portfolio.
   * @param user     is the username.
   * @param model    is the StockPortfolio object.
   * @param type     is the type of portfolio.
   * @return values.
   */

  public String addabs(String inp, String portName, String user,
                       StockPortfolio model, String type) {
    boolean onlyInts = true;
    String inputTemp2;
    while (inp.equals("yes")) {
      //ask for stock input.
      view.addStockdetails();

      String input = in.nextLine();
      //boolean check = model instanceof SmartPortfolio;
      //TODO TRY CATCH HERE
      if (Objects.equals(type, "reg")) {
        inputTemp2 = addSimple();
      } else if (Objects.equals(type, "smart")) {
        inputTemp2 = addSmart();
      } else {
        throw new IllegalStateException("Model is not allowed.");
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

  /**
   * This method is to add stocks to a simple portfolio.
   *
   * @return the success message.
   */

  public String addSimple() {
    view.addStockdetails2();
    String inputTemp2 = in.nextLine();
    while (!inputTemp2.matches("[0-9]+")) {
      System.out.println("Please enter an postive integer value for shares.");
      inputTemp2 = in.nextLine();
    }
    return inputTemp2;
  }

  /**
   * This method is to add stocks to smart portfolio.
   *
   * @return the success message.
   */
  public String addSmart() {
    view.addStockdetailsmart2();
    String str = in.nextLine();
    return str;
  }
}