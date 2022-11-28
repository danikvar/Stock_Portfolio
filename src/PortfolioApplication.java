
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import stockdataa.controller.GUIController;
import stockdataa.controller.IController;
import stockdataa.controller.SmartController;
import stockdataa.model.Operations;
import stockdataa.model.OperationsImpl;
import stockdataa.model.Portfolio;
import stockdataa.controller.TextController;
import stockdataa.model.SmartPortfolio;
import stockdataa.view.ButtonOnlyView.ButtonOnly;
import stockdataa.view.ButtonOnlyView.MainView;
import stockdataa.view.TextGUI;
import stockdataa.view.TextInterface;
import stockdataa.controller.IController;
import stockdataa.controller.TextController;
import stockdataa.model.Portfolio;
import stockdataa.view.TextGUI;

/**
EXAMPLE DEMONSTRATION ON HOW TO RUN THE PROGRAM
--> FOR WHEN YOU RUN IT TRY TO CREATE A NEW USER
------> ENTER THE PATH TO SOME FOLDER ON YOUR PC
--> CREATE A NEW PORTFOLIO AND PLAY AROUND WITH IT FROM THERE
 **/

public class PortfolioApplication {
  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    SmartPortfolio smartModel;
    ButtonOnly guiView;
    IController control;
    while (true) {
      System.out.println("Please follow the command and make choice\n");
      System.out.println("1 - User Command Line View");
      System.out.println("2 - User GUI View");
      Scanner sc = new Scanner(System.in);
      String choice = sc.next();
      if (choice.equals("1")) {

        TextInterface view = new TextGUI(System.out);
        control = new TextController(model, System.in, view);
        control.controller();
        break;
      }
      else if (choice.equals("2")) {
        guiView = new MainView("Main");
        OperationsImpl opModel = new OperationsImpl();
//        controller = new GUIController(smartModel, guiView);
        SmartController control1 = new GUIController(opModel, guiView);
//        control1.controller();
        control1.execute();
        
        break;
      }
      else {
        System.out.println("Invalid choice, try again\n");
      }
    }

  }
}

