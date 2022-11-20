
import java.io.IOException;
import stockdataa.Controller;
import stockdataa.Portfolio;
import stockdataa.TextController;
import stockdataa.TextGUI;
/**
EXAMPLE DEMONSTRATION ON HOW TO RUN THE PROGRAM
--> FOR WHEN YOU RUN IT TRY TO CREATE A NEW USER
------> ENTER THE PATH TO SOME FOLDER ON YOUR PC
--> CREATE A NEW PORTFOLIO AND PLAY AROUND WITH IT FROM THERE
 **/

public class PortfolioApplication {
  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    TextGUI view = new TextGUI(System.out);
    Controller control = new TextController(model, System.in, view);
    control.controller();

  }
}

