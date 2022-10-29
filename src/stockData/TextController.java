package StockData;

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
          model.addStock(input,input2);
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

