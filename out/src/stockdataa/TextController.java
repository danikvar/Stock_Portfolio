package stockdataa;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * TextController class that acts as the controller that gets input from the user through the view
 * and passes to the model and presents data to the user.
 */

public class TextController implements Controller {
  private Scanner in;
  private stockdataa.TextInterface view;
  private stockdataa.Portfolio model;

  /**
   * TextCOntroller constructor that creates an object for the model,view and controller.
   * @param model where all operations arre performed.
   * @param in    where data is received from user.
   * @param view  the interface of the application.
   */
  public TextController(stockdataa.Portfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void controller() throws FileNotFoundException {
    String input;
    String user;
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      System.out.println(stockdataa.DataHelpers.getUsers());
      System.out.println("Please enter a unique username consisting of alphanumeric characters" +
              " and _ only or choose one from the list above to log in.");
      user = in.nextLine();

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

      if (stockdataa.DataHelpers.getUserList().contains(user)) {
        view.chooseOption();
        String option1 = in.nextLine();
        switch (option1) {
          // create a new portfolio
          case "C":
            //view.createnew();
            //String fileoremptyport = in.nextLine();

            model = new stockdataa.Portfolio();
            String inp = "yes";
            view.getportName();
            String portName = in.nextLine();
            while (inp.equals("yes")) {
              //ask for stock input.
              view.addStockdetails();

              input = in.nextLine();
              //ask for shares input
              view.addStockdetails2();

              //TODO TRY CATCH HERE
              String inputTemp2 = in.nextLine();
              while (!inputTemp2.matches("^[0-9]*$")) {
                System.out.println("Please enter an postive integer value for shares.");
                inputTemp2 = in.nextLine();
              }
              int input2 = Integer.parseInt(inputTemp2);


              //view.pressEnter();
              //in.nextLine();
              //asl for date/data input.
              view.addStockdetails3();
              String input3 = in.nextLine();
              //give to model
              try {
                model.addStock(input, input2, input3);
                model.save(portName, user);
                view.addAgain();
                inp = in.nextLine();
              } catch (Exception e) {
                System.out.println(e);
              }
            }
            break;
          case "D":
            System.out.println(stockdataa.DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name = in.nextLine();
            model = (Portfolio) stockdataa.DataHelpers.loadPortfolio(user, name);
            view.getDate();
            //retrieve portfolio
            String date = in.nextLine();
            if (date.contains("current")) {
              System.out.println("Printing data for latest available dates");
              System.out.println(model.toString());
            } else {
              System.out.println(model.printPortfolioAt(date));
            }

            /*
            try{

            } catch(Exception E) {
              System.out.println(date);
              System.out.println(date.contains("current"));
              System.out.println("There was some issue reading your date input" +
                      "Please check the date and try again.");
            }
             */
            break;
          case "Finish":
            controller();
            break;
          default:
            quit = true;
            break;
        }
      } else {
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

      if (quit) {
        break;
      }
      System.out.println("If you would like display your portfolio type P, otherwise"
              + " type Finish to restart or any other input to exit");
      //accept user input
      String option = in.next();
      switch (option) {
        case "P":
          view.getDate();
          in.nextLine();
          input = in.nextLine();
          //give to model
          try {
            if (input.contains("current")) {
              System.out.println(model.toString());
            } else {
              System.out.println(model.printPortfolioAt(input));
            }

            break;
          } catch (Exception e) {
            System.out.println(e);
          }
        case "Finish":
          controller();
          break;
        default:
          quit = true;
          break;
      }
    }
  }

}

