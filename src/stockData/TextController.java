package stockData;

import java.io.InputStream;
import java.util.Scanner;

public class TextController implements Controller{
  private Scanner in;
  private TextInterface view;
  private Portfolio model;

  public TextController(Portfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }
  @Override
  public void go() {
    String input;
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      view.showOptions();
      //accept user input
      String option = in.next();
      switch (option) {
        case "E":
          view.addStockdetails();
          in.nextLine();
          input = in.nextLine();
          //ask for Integer input
          view.addStockdetails2();
          in.nextInt();
          int input2= in.nextInt();
          //give to model
          //TODO FIX THIS WITH THIRD INPUT
          // HERE WE NEED TO CHECK INPUT 3 TO MAKE SURE THAT
          // IF THEY ARE INPUTTING STRING THEY DO SO CORRECTLY
          // WRAP THIS IN A TRY CATCH BLOCK AND IF IT THROWS AN
          // ERROR ASK THEM TO TRY AGAIN
          // Also I was thinking we should let them quit an restart if they
          // enter some string so they dont get stuck. (Like if input == "q!", restart the application)
          // I was thinking similar to something like this:

          /*
          while ( true ) {
            in.nextLine();
            input = in.nextLine();
            //ask for Integer input
            view.addStockdetails2();
            in.nextInt();
            int input2 = in.nextInt();
            in.nextLine();
            String input3 = in.nextLine();
            try {
              model.addStock(input, input2, input3);
              break;
            } catch (Exception E) {
              System.out.println("Please make sure you are printing a valid sequence and try again!");
              in.next()
            } //end catch
          }
           */

          //model.addStock(input,input2);
          break;
        case "Q":
          view.dateInput();
          in.nextLine();
          input = in.nextLine();
          //give to model
          model.getTotalValues(input);
          break;
        default:
          view.showOptionError();
          quit = true;
      }
    }
  }

}

