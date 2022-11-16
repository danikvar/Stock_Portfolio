
import java.io.IOException;

import stockdataa.Controller;
import stockdataa.Portfolio;
import stockdataa.TextController;
import stockdataa.TextGUI;

/**
 * The portfolio application that acts as the controller to the entire application
 * to create and store portfolios.
 */

public class PortfolioApplication {

  /**
   * Main method that implements the application.
   *
   * @param args string array.
   * @throws IOException when an invalid input is entered.
   */

  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    TextGUI view = new TextGUI(System.out);
    Controller control = new TextController(model, System.in, view);
    control.controller();

  }
}

