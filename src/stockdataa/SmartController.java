package stockdataa;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Objects;
import java.util.Scanner;

public class SmartController extends AbstractController {
  public SmartController(SmartPortfolio model, InputStream in, TextInterface view) {
    super(model, in, view);
  }

  public void controller() throws FileNotFoundException {
    String input;
    String user;
    String currentPort = "";
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
            currentPort= portName;
            while(quit != true) {
              try{
                inp = addabs(inp, portName, user, model, "smart");
                break;
              } catch (Exception e) {
                System.out.println("Found this exception adding Stock:");
                System.out.println(e.toString());
                System.out.println("Type add to try again or 'quit' to quit.");
                String response = in.nextLine();
                if(response.equals("quit")){
                  quit = true;
                  break;
                }
              }
            }

            break;
          case "D":
            System.out.println(stockdataa.DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name = in.nextLine();
            currentPort = name;
            try {
              model = (SmartPortfolio) DataHelpers.loadPortfolio(user, name, 2);
            } catch (Exception e) {
              System.out.println(e.toString());
              controller();
            }
            getDatetoDisplay(model);
            break;
          default:
            quit = true;
            break;
        }

        // Once a porfolio is loaded/displayed we go here
        view.showSmartopt();
        String opt = in.nextLine();
        while(!Objects.equals(opt, "Finish")) {
          //System.out.println("Current Option is:");
          //System.out.println(opt);
          switch (opt) {
            case "A":
              //abstract add
              //String portName = in.nextLine();
              currentPort = currentPort.substring(0, currentPort.lastIndexOf('.'));
              inp = addabs(inp, currentPort, user, model, "smart");
              System.out.println("Choose one of the following options" +
                      " or type 'Finish' to finish");
              view.showSmartopt();
              opt = in.nextLine();
              break;
            case "B":
              displayPort(model);
              System.out.println("Choose one of the following options" +
                      " or type 'Finish' to finish");
              view.showSmartopt();
              opt = in.nextLine();
              break;
            case "C":
              try {
                view.fromDate();
                String date1 = in.nextLine();
                view.endDate();
                String date2 = in.nextLine();
                System.out.println(model.portfolioPerformance(date1, date2));

              } catch (Exception e) {
                System.out.println(e);
              }
              System.out.println("Choose one of the following options or type" +
                      " 'Finish' to finish");
              view.showSmartopt();
              opt = in.nextLine();
              break;
            case "D":
              try {
                view.costDate();
                String d = in.nextLine();
                System.out.println(((SmartPortfolio) model).printCostBasis(d));
              } catch (Exception e) {
                System.out.println(e);
              }
              System.out.println("Choose one of the following options" +
                      " or type 'Finish' to finish");
              view.showSmartopt();
              opt = in.nextLine();
              break;
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
