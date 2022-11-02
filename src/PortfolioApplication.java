import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stockData.Controller;
import stockData.DataHelpers;
import stockData.Portfolio;
import stockData.TextController;
import stockData.TextGUI;


public class PortfolioApplication {
  public static void main(String[] args) throws IOException {

    Portfolio model = new Portfolio();
    TextGUI view = new TextGUI(System.out);
    Controller control = new TextController(model, System.in, view);
    control.controller();

  }
}

