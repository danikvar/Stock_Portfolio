package stockdataa;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class SmartController extends AbstractController {
  private Scanner in;
  private stockdataa.TextInterface view;
  private SmartPortfolio model;

  public SmartController(SmartPortfolio model, InputStream in, TextInterface view) {
    super(model, in, view);

  }

  public void controller() throws FileNotFoundException {
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
        String inp = "yes";
        switch (option1) {
          case "C":
            model = new stockdataa.SmartPortfolio();
            inp = "yes";
            view.getportName();
            String portName = in.nextLine();
            inp = addabs(inp, portName, user, model);
            break;
          case "D":
            System.out.println(stockdataa.DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name = in.nextLine();
            model = (SmartPortfolio) stockdataa.DataHelpers.loadPortfolio(user, name);
            getDatetoDisplay(model);
            break;
          default:
            quit = true;
            break;
        }
        view.showSmartopt();
        String opt = in.nextLine();
        while(!Objects.equals(opt, "Finish")) {
          switch (opt) {
            case "A":
              //abstract add
              String portName = in.nextLine();
              inp = addabs(inp, portName, user, model);
            case "B":
              quit = displayPort(model);
              if (quit) {
                break;
              }
            case "C":
              try {
                view.fromDate();
                String date1 = in.nextLine();
                view.endDate();
                String date2 = in.nextLine();
                model.portfolioPerformance(date1, date2);
              } catch (Exception e) {
                System.out.println(e);
              }
            case "D":
              try {
                view.costDate();
                String d = in.nextLine();
                model.printCostBasis(d);
              } catch (Exception e) {
                System.out.println(e);
              }
              view.showSmartopt();
              opt = in.nextLine();
            default:
              quit = true;
          }
        }
      } else {
        //Abstract controller method
        createUser(user);
      }
      //AbstractController
      quit = displayPort(model);
      if (quit) {
        break;
      }
    }
  }
}
