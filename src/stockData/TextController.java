package stockData;


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
    String user;
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      System.out.println(DataHelpers.getUsers());
      System.out.println("Please enter a unique username consisting of alphanumeric characters" +
              "and _ only or choose one from the list above to log in.");
      user = in.nextLine();

      while(!DataHelpers.isAlphaNumeric(user)) {
        System.out.println("Please enter a username consisting only of numbers, letters, and '_'.");
        user = in.nextLine();
      }

      if (DataHelpers.getUsers().contains(user)) {
        view.chooseOption();
        String option1 = in.nextLine();
        switch (option1) {
          // create a new portfolio
          case "C":
            view.createnew();
            String fileoremptyport = in.nextLine();
            // create using path or create one for you
            switch (fileoremptyport) {
              case "A":
                view.getpath();
                String path = in.nextLine();
                model = (Portfolio) DataHelpers.loadPortfolio(user,path);

              case "B":
                model = new Portfolio();
              case "Finish":
                controller();
            }
          case "D":
            System.out.println(DataHelpers.listPortfolios(user));
            view.showOptions1();
            String name  = in.nextLine();
            model = (Portfolio) DataHelpers.loadPortfolio(user, name);

            System.out.println("Please enter the date you would like for this portfolio"
                    + " in YYYY-MM-DD format or 'current' to get all latest available stock"
                    + " values");

            //retrieve portfolio
            String date = in.nextLine();
            try{
              model.printPortfolioAt(date);
            } catch(Exception E) {
              throw new IllegalArgumentException("There was some issue reading your date input" +
                      "Please check the date and try again.");
            }


          case "Finish":
            controller();
          default:
            quit = true;
        }
      } else {
        System.out.println("Please enter the full path to the local directory"
                + "where your files will be stored.");
        String userPath = in.nextLine();
        Path checkUserPath = Paths.get(userPath);

        while( !Files.exists(checkUserPath)) {
          System.out.println("Your directory does not exist or could not be found."
                  + "Please check input and try again or type restart.");
          userPath = in.nextLine();
          if(userPath.equals( "restart")) {
            controller();
          }
          checkUserPath = Paths.get(userPath);
        }
        DataHelpers.createUser(user, userPath);
        controller();
        }
      view.showOptions();
      //accept user input
      String option = in.next();
      switch (option) {
        case "E":
          String inp = "yes";
          view.getportName();
          String portName = in.nextLine();
          while(inp == "yes") {
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
            view.addAgain();
            inp = in.nextLine();
        
          } catch (Exception e) {
            System.out.println(e);
          }
          }
        case "Q":
          view.dateInput();
          in.nextLine();
          input = in.nextLine();
          //give to model
          try {
            model.printPortfolioAt(input);
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

