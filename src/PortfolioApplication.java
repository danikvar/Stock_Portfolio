import java.io.IOException;
import java.util.Scanner;

import stockdataa.controller.GUIController;
import stockdataa.controller.IController;
import stockdataa.controller.SmartController;
import stockdataa.model.OperationsImpl;
import stockdataa.model.Portfolio;
import stockdataa.controller.TextController;
import stockdataa.view.ButtonsView.Buttons;
import stockdataa.view.ButtonsView.Home;
import stockdataa.view.TextGUI;
import stockdataa.view.TextInterface;

/**
 EXAMPLE DEMONSTRATION ON HOW TO RUN THE PROGRAM
 --> FOR WHEN YOU RUN IT TRY TO CREATE A NEW USER
 ------> ENTER THE PATH TO SOME FOLDER ON YOUR PC
 --> CREATE A NEW PORTFOLIO AND PLAY AROUND WITH IT FROM THERE
 **/

public class PortfolioApplication {
  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    Buttons guiView;
    IController control;
    while (true) {
      System.out.println("Please enter the respective choice to choose the type of user interface\n");
      System.out.println("1 - Text Line Interface");
      System.out.println("2 - Graphical User Interface");
      Scanner sc = new Scanner(System.in);
      String choice = sc.next();
      if (choice.equals("1")) {

        TextInterface view = new TextGUI(System.out);
        control = new TextController(model, System.in, view);
        control.controller();
        break;
      }
      else if (choice.equals("2")) {
        guiView = new Home("Main");
        OperationsImpl opModel = new OperationsImpl();

        SmartController control1 = new GUIController(opModel, guiView);

        control1.execute();

        break;
      }
      else {
        System.out.println("Please enter a valid choice\n");
      }
    }

  }
}

