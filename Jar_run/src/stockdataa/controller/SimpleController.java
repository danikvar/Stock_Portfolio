package stockdataa.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Objects;

import stockdataa.DataHelpers;
import stockdataa.model.Portfolio;
import stockdataa.view.TextInterface;

/**
 * This represents the class Simple controller which includes methods to
 * perform on simple portfolio.
 */

public class SimpleController extends AbstractController {
  public SimpleController(Portfolio model, InputStream in, TextInterface view) {
    super(model, in, view);

  }

  /**
   * This is the controller for Simple Portfolio.
   *
   * @throws FileNotFoundException throws exception.
   */

  public void controller() throws FileNotFoundException {
    String input;
    String user;
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      System.out.println(stockdataa.DataHelpers.getUsers());
      this.view.showOptions();
      user = in.nextLine();

      getUser(user);

      if (stockdataa.DataHelpers.getUserList().contains(user)) {
        view.chooseOption();
        String option1 = in.nextLine();
        String inp = null;
        switch (option1) {
          // create a new portfolio
          case "C":
            model = new Portfolio();
            inp = "yes";
            view.getportName();
            String portName = in.nextLine();
            inp = addabs(inp, portName, user, model, "reg");

            if (Objects.equals(inp, "getvalue")) {
              getDatetoDisplay(model);
            }
            break;
          case "D":
            System.out.println(stockdataa.DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name = in.nextLine();
            try {
              model = (Portfolio) DataHelpers.loadPortfolio(user, name, 1);
            } catch (ParseException e) {
              throw new RuntimeException(e);
            }
            getDatetoDisplay(model);
            break;
          case "Finish":
            controller();
            break;
          default:
            quit = true;
            break;
        }
      } else {
        //AbstractController
        createUser(user);
      }

      if (quit) {
        break;
      }
      //AbstractController
      quit = displayPort(model);
      if (quit) {
        break;
      }
    }
  }
}
