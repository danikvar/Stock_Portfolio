package stockdataa;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * TextController class that acts as the controller that gets input from the user through the view
 * and passes to the model and presents data to the user.
 */

public class TextController implements Controller {
  private Scanner in;
  private stockdataa.TextInterface view;
  private stockdataa.StockPortfolio model;

  /**
   * TextCOntroller constructor that creates an object for the model,view and controller.
   *
   * @param model where all operations are performed.
   * @param in    where data is received from user.
   * @param view  the interface of the application.
   */
  public TextController(StockPortfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void controller() throws FileNotFoundException {
    int input;
    view.showporttype();
    input = in.nextInt();
    switch (input) {
      case 1:
        Controller c = new SmartController(new SmartPortfolio(), System.in, view);
        c.controller();

      case 2:
        Controller co = new SimpleController(new Portfolio(), System.in, view);
        co.controller();

      default:
        controller();
    }

  }
}

