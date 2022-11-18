package stockdataa;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class SimpleController extends AbstractController{
  private Scanner in;
  private stockdataa.TextInterface view;
  private stockdataa.Portfolio model;

  public SimpleController(Portfolio model, InputStream in, TextInterface view) {
    super(model,in,view);

  }

  public void controller() throws FileNotFoundException{
  String input;
  String user;
  boolean quit = false;

    while (!quit) {
    //tell view to show options
    System.out.println(stockdataa.DataHelpers.getUsers());
    view.showOptions();
    user = in.nextLine();

    getUser(user);

    if (stockdataa.DataHelpers.getUserList().contains(user)) {
      view.chooseOption();
      String option1 = in.nextLine();
      String inp = null;
      switch (option1) {
        // create a new portfolio
        case "C":
          model = new stockdataa.Portfolio();
          inp = "yes";
          view.getportName();
          String portName = in.nextLine();
          inp = addabs(inp, portName, user, model);

      if (Objects.equals(inp, "getvalue")) {
        getDatetoDisplay(model);
      }
      break;
      case "D":
        System.out.println(stockdataa.DataHelpers.listPortfolios(user));
        view.showOptions1();
        String name = in.nextLine();
        model = (Portfolio) stockdataa.DataHelpers.loadPortfolio(user, name, 1);
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
