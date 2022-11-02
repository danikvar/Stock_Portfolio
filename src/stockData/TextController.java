package stockData;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * TextController class that acts as the controller that gets input from the user through the view
 * and passes to the model and presents data to the user.
 */

public class TextController implements Controller {
  private Scanner in;
  private TextInterface view;
  private Portfolio model;

  public TextController(Portfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void controller() throws FileNotFoundException {
    String input;
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      DataHelpers.getUsers();
      String user = in.nextLine();
      if (DataHelpers.getUsers().contains(user)) {
        view.chooseOption();
        String option1 = in.nextLine();
        switch (option1) {
          case "C":
            view.createnew();
            String fileoremptyport = in.nextLine();
            switch (fileoremptyport) {
              case "A":
                view.getpath();
                String path = in.nextLine();
                model = (Portfolio) DataHelpers.loadPortfolio(user,path);
              case "B":
                //create empty port
              case "Finish":
                controller();
            }
          case "D":
            System.out.println(DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name  = in.nextLine();
            //retrieve portfolio
            String date = in.nextLine();
            model.printPortfolioAt(date);
          case "Finish":
            controller();
          default:
            quit = true;
        }
      } else {
        view.create();
        view.createnew();
        String fileoremptyport = in.nextLine();
        switch (fileoremptyport) {
          case "A":
            view.getpath();
            String path = in.nextLine();
          //  model = DataHelpers.createUser(user,path);
          case "B":
            view.getportName();
            String name = in.nextLine();
           // model = DataHelpers.createUser(user,name);
          case "Finish":
            controller();
          default:
            quit = true;
        }
      }
      view.showOptions();
      //accept user input
      String option = in.next();
      switch (option) {
        case "E":
          //ask for stock input.
          view.addStockdetails();
          in.nextLine();
          input = in.nextLine();
          //ask for shares input
          view.addStockdetails2();
          in.nextInt();
          int input2 = in.nextInt();
          //asl for date/data input.
          view.addStockdetails3();
          String input3 = in.next();
          //give to model
          try {
            model.addStock(input, input2, input3);
            view.getportName();
            String portName = in.nextLine();
            model.save(portName,user);
            break;
          } catch (Exception e) {
            System.out.println(e);
          }
        case "Q":
          view.dateInput();
          in.nextLine();
          input = in.nextLine();
          //give to model
          try {
            model.getTotalValues(input);
            break;
          } catch (Exception e) {
            System.out.println(e);
          }
        case "Finish":
          controller();
        default:
          quit = true;
      }
    }
  }

}

