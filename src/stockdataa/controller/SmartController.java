package stockdataa.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;

import stockdataa.DataHelpers;
import stockdataa.model.Operations;
import stockdataa.model.OperationsImpl;
import stockdataa.model.SmartPortfolio;
import stockdataa.view.ButtonOnlyView.ButtonOnly;
import stockdataa.view.TextInterface;

public class SmartController extends AbstractController {

  public SmartController(SmartPortfolio model, InputStream in, ButtonOnly view) {
    super(model, in, view);
  }

  public SmartController(SmartPortfolio model, InputStream in, TextInterface view) {
    super(model, in, view);
  }

  /*
  public SmartController(SmartPortfolio model, ButtonOnly b){
    super(model,b);
  }
   */
  public SmartController(Operations m, ButtonOnly v) {
    super(m, v);
  }

  public void controller() throws FileNotFoundException {
    //String input;
    String user;
    String currentPort = "";
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      System.out.println(DataHelpers.getUsers());
      view.showOptions();
      user = in.nextLine();

      getUser(user);

      if (DataHelpers.getUserList().contains(user)) {
        view.chooseOption();
        String option1 = in.nextLine();
        String inp = "yes";
        switch (option1) {
          case "C":
            model = new SmartPortfolio();
            inp = "yes";
            view.getportName();
            String portName = in.nextLine();
            currentPort= portName;
            while(inp.equals("yes")) {
              try{
                inp = addabs(inp, portName, user, model, "smart");
              } catch (Exception e) {
                view.AddStockError(e);
                inp = in.nextLine();
                if(inp.equals("quit")){
                  quit = true;
                  break;
                }
              }
            }

            break;
          case "D":
            System.out.println(DataHelpers.listPortfolios(user));
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
        view.showSmartopt("A", "B", "C", "D", "E");
        String opt = in.nextLine();
        while(!opt.equals( "Finish")) {
          //System.out.println("Current Option is:");
          //System.out.println(opt);
          switch (opt) {
            case "A":
              //abstract add
              //String portName = in.nextLine();
              if(currentPort.contains(".")){
                currentPort = currentPort.substring(0, currentPort.lastIndexOf('.'));
              }
              inp = "yes";
              while(inp == "yes") {
                try{
                  inp = addabs(inp, currentPort, user, model, "smart");
                } catch (Exception e) {
                  view.AddStockError(e);
                  inp = in.nextLine();
                  if(inp.equals("quit")){
                    break;
                  }
                }
              }

              view.showSmartopt("A", "B", "C", "D", "E");
              opt = in.nextLine();
              break;
            case "B":
              displayPort(model);
              view.showSmartopt("A", "B", "C", "D", "E");
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
              view.showSmartopt("A", "B", "C", "D", "E");
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
              view.showSmartopt("A", "B", "C", "D", "E");
              opt = in.nextLine();
              break;

            case "E":
              try {
                this.sellStock();
                System.out.println("Not yet implemented in controller");
                // TODO finish sales implementation in controller
                // TODO create parser and saver for sold stocks!
                // here read in ticker, date to sell, commission, and shares.
              } catch (Exception e) {
                System.out.println(e);
              }
              view.showSmartopt("A", "B", "C", "D", "E");
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

  /**
   * Gathers all necessary details to sell stock then
   * performs the transaction.
   */
  private void sellStock() {
    view.addStockdetails();
    String ticker = in.nextLine();
    view.addStockdetails2();
    String shares = in.nextLine();
    view.addCommission();
    String comm = in.nextLine();
    view.addDate();
    String date = in.nextLine();
    while(true) {
      try {
        ((SmartPortfolio) model).sellStock(ticker, shares, comm, date, true);
        break;
      } catch(Exception e) {
        System.out.println("Error selling Stock!");
        System.out.println(e);
        System.out.println("If you would like to try again, please type 'Y', "
                + "otherwise enter anything else.");
        String again = in.nextLine();
        if(!again.equals("Y")) {
          break;
        }
      }
    }

  }

  /**
   * Method that use to run the program.
   */
  public void execute() {
    // TODO Auto-generated method stub
    System.out.println();
  }

}
