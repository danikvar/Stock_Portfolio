package stockdataa.controller;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import stockdataa.model.StockPortfolio;
import stockdataa.view.TextInterface;
import stockdataa.view.ButtonsView.Buttons;
import stockdataa.model.SmartPortfolio;

/**
 * TextController class that acts as the controller that gets input from the user through the view
 * and passes to the model and presents data to the user.
 */

public class TextController implements IController {
  private Scanner in;
  private TextInterface view;
  private StockPortfolio model;

  private Buttons buttonview;

  /**
   * TextCOntroller constructor that creates an object for the model,view and controller.
   *
   * @param model where all operations are performed.
   * @param in    where data is received from user.
   * @param view  the interface of the application.
   */
  public TextController(StockPortfolio model, InputStream in, TextInterface view) {
    this.model = model;
    this.view =  view;
    this.in = new Scanner(in);

  }

  public TextController(StockPortfolio model, InputStream in, Buttons view) {
    this.model = model;
    this.buttonview =  view;
    this.in = new Scanner(in);

  }

  @Override
  public void controller() throws FileNotFoundException {
    int input;
    view.showporttype();
    input = in.nextInt();
    switch (input) {
      case 1:
        IController c = new SmartController(new SmartPortfolio(), System.in, view);
        c.controller();

      case 2:
        IController co = new SimpleController(new stockdataa.model.Portfolio(), System.in, view);
        co.controller();

      default:
        controller();
    }

  }
}

