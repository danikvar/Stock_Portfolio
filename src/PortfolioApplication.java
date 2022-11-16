
import java.io.IOException;
import stockdataa.Controller;
import stockdataa.Portfolio;
import stockdataa.TextController;
import stockdataa.TextGUI;


public class PortfolioApplication {
  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    TextGUI view = new TextGUI(System.out);
    Controller control = new TextController(model, System.in, view);
    control.controller();

  }
}

