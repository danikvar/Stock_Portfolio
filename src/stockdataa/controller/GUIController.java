package stockdataa.controller;

import org.w3c.dom.Text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;



import stockdataa.DataHelpers;
import stockdataa.Pair;
import stockdataa.model.OperationsImpl;
import stockdataa.view.ButtonsAndTextsView.BuyStock;
import stockdataa.view.ButtonsAndTextsView.CheckCB;
import stockdataa.view.ButtonsAndTextsView.CheckCBD;
import stockdataa.view.ButtonsAndTextsView.CheckVal;
import stockdataa.view.ButtonsAndTextsView.CheckValByD;
import stockdataa.view.ButtonsAndTextsView.CreateUser;
import stockdataa.view.ButtonsAndTextsView.DollarCostAveraging;
import stockdataa.view.ButtonsAndTextsView.PortPerformance;
import stockdataa.view.ButtonsAndTextsView.PortfolioPerformance;
import stockdataa.view.ButtonsAndTextsView.Reading;
import stockdataa.view.ButtonsAndTextsView.Saving;
import stockdataa.view.ButtonsAndTextsView.SellStock;
import stockdataa.view.ButtonsAndTextsView.ShowAllUsers;
import stockdataa.view.ButtonsAndTextsView.Texts;
import stockdataa.view.ButtonsView.Buttons;
import stockdataa.view.ButtonsView.BuyMain;
import stockdataa.view.ButtonsView.CheckCost;
import stockdataa.view.ButtonsView.CheckValue;
import stockdataa.view.ButtonsView.DisplayAllPorts;
import stockdataa.view.ButtonsView.Home;

/**
 * This class represents the assignment of buttons and the actions performed for GUI.
 */
public class GUIController extends SmartController implements ActionListener {

  private Buttons homeView;
  private Buttons showAllPortsV;
  private Buttons checkCostMain;
  private Buttons checkValueMain;
  private Buttons buyStockSellStock;
  private Texts createUser;
  private Texts buyStock;
  private Texts sellStock;
  private Texts checkCB;
  private Texts checkCBD;
  private Texts checkV;
  private Texts checkVD;

  private Texts dollarCost;

  private Saving saving;
  private Texts reading;

  private Texts showusers;

  private Texts plot;

  private Text plotGraph;
  private String str;
  private Map<String, Runnable> buttonMap;

  /**
   * Constructor for GUI to pass model, view.
   *
   * @param m is the model of smart portfolio.
   * @param v is the view passed to the controller.
   */
  public GUIController(OperationsImpl m, Buttons v) {
    super(m, v);
    this.homeView = v;
    homeView.addActionListener(this);
    str = "";
  }


  private Map<String, Runnable> buttonAssign() {
    Map<String, Runnable> buttonMap = new HashMap<>();


    buttonMap.put("createUser", () -> {
      createUser = new CreateUser("Create User");
      createUser.addActionListener(this);
      ((JFrame) createUser).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("dollarCost", () -> {
      dollarCost = new DollarCostAveraging("Dollar Cost Averaging");
      dollarCost.addActionListener(this);
      ((JFrame) dollarCost).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("plotGraph", () -> {
      plot = new PortfolioPerformance("Portfolio Performance");
      plot.addActionListener(this);
      ((JFrame) plot).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });


    buttonMap.put("displayPorts", () -> {

      List<String> list;
      try {
        list = DataHelpers.listPortfoliosList(this.opModel.getUser());
      } catch (Exception e) {

        list = new ArrayList<>();

      }

      showAllPortsV = new DisplayAllPorts("Show all Portfolios", list);
      showAllPortsV.addActionListener(this);
      ((JFrame) showAllPortsV).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("showAllUsers", () -> {
      List<String> list = DataHelpers.getUserList();
      showusers = new ShowAllUsers("Show all users or Choose a User", list);
      showusers.addActionListener(this);
      ((JFrame) showusers).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });


    buttonMap.put("buyStockSellStock", () -> {
      buyStockSellStock = new BuyMain("Choose To Buy/Sell");
      buyStockSellStock.addActionListener(this);
      ((JFrame) buyStockSellStock).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("buy stocks", () -> {
      buyStock = new BuyStock("Buy");
      buyStock.addActionListener(this);
      ((JFrame) buyStock).setLocation(((JFrame) this.buyStockSellStock).getLocation());
      ((JFrame) this.buyStockSellStock).dispose();
    });

    buttonMap.put("sell stocks", () -> {
      sellStock = new SellStock("Buy");
      sellStock.addActionListener(this);
      ((JFrame) sellStock).setLocation(((JFrame) this.buyStockSellStock).getLocation());
      ((JFrame) this.buyStockSellStock).dispose();
    });


    buttonMap.put("checkCB", () -> {
      checkCostMain = new CheckCost("Check Cost");
      checkCostMain.addActionListener(this);
      ((JFrame) checkCostMain).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("check cost check cost basis", () -> {
      checkCB = new CheckCB("Check Cost Basis");
      checkCB.addActionListener(this);
      ((JFrame) checkCB).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.checkCostMain).dispose();
    });

    buttonMap.put("check cost check cost basis by date", () -> {
      checkCBD = new CheckCBD("Check Cost Basis");
      checkCBD.addActionListener(this);
      ((JFrame) checkCBD).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.checkCostMain).dispose();
    });

    buttonMap.put("checkV", () -> {
      checkValueMain = new CheckValue("Check Value");
      checkValueMain.addActionListener(this);
      ((JFrame) checkValueMain).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("check value check total value", () -> {
      checkV = new CheckVal("Check Total Value");
      checkV.addActionListener(this);
      ((JFrame) checkV).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.checkValueMain).dispose();
    });

    buttonMap.put("check value check total value by date", () -> {
      checkVD = new CheckValByD("Check Total Value");
      checkVD.addActionListener(this);
      ((JFrame) checkVD).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.checkValueMain).dispose();
    });


    buttonMap.put("savePortfolioToFile", () -> {
      String portDir = this.opModel.getPath();
      saving = new Saving("Save Portfolio", portDir);
      saving.addActionListener(this);
      saving.setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("readOrCreate", () -> {
      reading = new Reading("Load or Create a new Portfolio");
      reading.addActionListener(this);
      ((JFrame) reading).setLocation(((JFrame) this.homeView).getLocation());
      ((JFrame) this.homeView).dispose();
    });

    buttonMap.put("quit", () -> {
      System.exit(0);
    });

    buttonMap.put("create", () -> {
      String userPath = createUser.getInput();

      if (userPath.length() == 0) {
        createUser.outMess("Please enter a valid username");
        return;
      }
      String[] userNameAndPath = userPath.split("\n");
      String userName = userNameAndPath[0];
      String filePath = userNameAndPath[1];
      try {
        Path checkUserPath = Paths.get(filePath);
        DataHelpers.createUser(userName, filePath);
        createUser.outMess("User " + userName + " created.");
        createUser.clean();
      } catch (IllegalArgumentException e) {
        createUser.outMess(e.getMessage());
      }
    });

    buttonMap.put("inv proceed", () -> {
      String allInp = dollarCost.getInput();
      String allInpL[] = allInp.split("\n");
      String days = allInpL[0];
      String sdate = allInpL[1];
      String edate = allInpL[2];
      String prop = allInpL[3];
      String comm = allInpL[4];
      String amount = allInpL[5];

      if (!days.matches("^\\d+$")) {
        dollarCost.outMess("Enter valid number of days to proceed");

      }

      if (!sdate.matches("^\\d{4}-\\d{2}-\\d{2}$") || sdate == "") {
        dollarCost.outMess("Enter valid date to proceed");

      }

      if (!edate.matches("^\\d{4}-\\d{2}-\\d{2}$") || edate.isEmpty()) {
        dollarCost.outMess("Enter valid end date to proceed or leave it blank.");

      }

      if (!comm.matches("^\\d+$") || comm == "") {
        dollarCost.outMess("Enter valid commission fee");

      }

      if (!amount.matches("^\\d+$") || amount == "") {
        dollarCost.outMess("Enter valid amount");

      }

      try{

        String message = opModel.setDCA(days, sdate, edate, amount, comm, prop);
        dollarCost.outMess(message);

      } catch(Exception e) {

        dollarCost.outMess(e.getMessage());
      }

    });

    buttonMap.put("plotting", () -> {
      String plotInp = plot.getInput();
      int index = plotInp.indexOf("\n");
      String sDate = plotInp.substring(0, index);


      if (!sDate.matches("^\\d{4}-\\d{2}-\\d{2}$") || sDate.length() == 0) {
        plot.outMess("Enter a valid start Date to proceed");
        return;
      }

      plotInp = plotInp.substring(index + 1);
      index = plotInp.indexOf("\n");
      String eDate = plotInp.substring(0, index);



      if (!eDate.matches("^\\d{4}-\\d{2}-\\d{2}$") || eDate.length() == 0) {
        plot.outMess("Enter a valid end Date to proceed");
        return;
      }
      try
      {
        Map<LocalDate, Double> data = opModel.getPortPerfData(sDate, eDate);
        Pair<Double, Double> myAst = opModel.getMinMax(data);
        PortPerformance graph = new PortPerformance("Portfolio Performance", data,
                myAst.a.intValue(), myAst.b.intValue());
        graph.setAlwaysOnTop(true);
        graph.pack();
        graph.setSize(600, 400);
        graph.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        graph.setVisible(true);

      } catch (Exception e) {
        throw new RuntimeException(e);
      }


    });


    buttonMap.put("select user", () -> {
      String userName = showusers.getInput();


      if (!DataHelpers.getUserList().contains(userName)) {
        showusers.outMess("Can only load existing users. Please check your input.");
        return;
      }

      try {
        String filePath = DataHelpers.getPortfolioDir(userName);
        this.opModel.setUser(userName);
        this.opModel.setPath(filePath);

        showusers.outMess("User " + this.opModel.getUser() + " loaded.");
        showusers.clean();
      } catch (IllegalArgumentException e) {
        showusers.outMess(e.getMessage());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });


    buttonMap.put("buy", () -> {
      String outToUser;
      String string = buyStock.getInput();
      int index = string.indexOf("\n");
      String ticker = string.substring(0, index);
      if (ticker.length() == 0) {
        buyStock.outMess("Enter a stock ticker symbol to proceed");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String volume = string.substring(0, index);
      if (volume.length() == 0) {
        buyStock.outMess("Enter a valid volume of stocks");
        return;
      }
      if (opModel.getPort().isEmpty()) {
        outToUser = "Must select a portfolio before buying stock!";
      } else {

        try {
          outToUser = opModel.buyStock(ticker, volume, true);

        } catch (IllegalArgumentException e) {
          outToUser = e.getMessage();
        }
      }

      buyStock.clean();
      buyStock.outMess(outToUser);
    });

    buttonMap.put("check cb", () -> {
      String outToUser;
      try {
        outToUser = opModel.getCostBasis("current");
        //outToUser = "Total cost basis of " + portfolioName + " by now is $" + cost;
      } catch (IllegalArgumentException e) {
        outToUser = e.getMessage();
      }
      checkCB.outMess(outToUser);
      //checkCB.outMess(outToUser);
    });

    buttonMap.put("check cbd", () -> {

      String dateInp = checkCBD.getInput();
      try {
        LocalDate.parse(dateInp);
      } catch (Exception e) {
        checkCBD.outMess("Please enter a valid date");
        return;
      }
      String outToUser;
      try {
        outToUser = opModel.getCostBasis(dateInp);
      } catch (IllegalArgumentException e) {
        outToUser = e.getMessage();
      }
      checkCBD.clean();
      checkCBD.outMess(outToUser);


    });

    buttonMap.put("check val", () -> {

      String outToUser;
      if (opModel.getPort().isEmpty()) {
        checkVD.outMess("First select a portfolio from the portfolios tab.");
        return;
      }
      String date = checkVD.getInput();
      if (date.length() == 0) {
        checkVD.outMess("Enter valid date in yyyy-mm-dd format or 'current'");
        return;
      }

      try {
        outToUser = opModel.printPortfolioAt(date);

      } catch (IllegalArgumentException e) {
        outToUser = e.getMessage();
      }
      checkVD.clean();
      checkVD.outMess(outToUser);
    });

    buttonMap.put("check valBD", () -> {
      String outToUser;
      if (opModel.getPort().isEmpty()) {
        checkV.outMess("First select a portfolio from the portfolios tab.");
        return;
      }

      try {
        outToUser = opModel.printPortfolioAt("current");

      } catch (IllegalArgumentException e) {
        outToUser = e.getMessage();
      }
      checkV.clean();
      checkV.outMess(outToUser);

    });


    buttonMap.put("save", () -> {
      String input = saving.getInput();
      String outToUser;
      if (input.length() != 0) {
        this.opModel.setPath(input);
      }
      outToUser = this.opModel.saving();
      saving.outMess(outToUser);
    });

    //
    buttonMap.put("read", () -> {
      String fileName = reading.getInput();
      String outToUser;
      if (fileName.length() == 0) {
        outToUser = "Enter valid portfolio name";
        reading.outMess(outToUser);
        return;
      }
      try {
        outToUser = this.opModel.loadPort(fileName);
      } catch (Exception e) {
        outToUser = e.getMessage();
      }

      reading.outMess(outToUser);
    });

    buttonMap.put("buy stock money sell", () -> {
      String string = sellStock.getInput();
      int index = string.indexOf("\n");
      String ticker = string.substring(0, index);
      if (ticker.length() == 0) {
        sellStock.outMess("Enter valid stock symbol");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String shares = string.substring(0, index);
      if (shares.length() == 0 || !shares.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)"
              + "")) {
        sellStock.outMess("Invalid input");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String dateInp = string.substring(0, index);

      String commFee = string.substring(index + 1, string.length() - 1);

      String outToUser = "";
      if (opModel.getPort().isEmpty()) {
        outToUser = "Must select a portfolio before buying stock!";
      } else {

        try {
          opModel.sellStock(ticker, shares, commFee, dateInp, true);
          outToUser = "Sold " + Integer.parseInt(shares) + " shares of " + ticker
                  + " from portfolio "
                  + opModel.getPort() + " on " + dateInp + "\n";


        } catch (IllegalArgumentException e) {
          outToUser = e.getMessage();
        }
      }

      sellStock.clean();
      sellStock.outMess(outToUser);
    });


    buttonMap.put("create portfolio home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.createUser).getLocation());
      ((JFrame) this.createUser).dispose();
    });

    buttonMap.put("show all portfolios home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.showAllPortsV).getLocation());
      ((JFrame) this.showAllPortsV).dispose();
    });

    buttonMap.put("show all users home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.showusers).getLocation());
      ((JFrame) this.showusers).dispose();
    });

    buttonMap.put("buy stock home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.buyStock).getLocation());
      ((JFrame) this.buyStock).dispose();
    });

    buttonMap.put("check cost view home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkCB).getLocation());
      ((JFrame) this.checkCB).dispose();
    });

    buttonMap.put("check cost by date view home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkCBD).getLocation());
      ((JFrame) this.checkCBD).dispose();
    });

    buttonMap.put("show all users home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.showusers).getLocation());
      ((JFrame) this.showusers).dispose();
    });

    buttonMap.put("check val home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkValueMain).getLocation());
      ((JFrame) this.checkValueMain).dispose();
    });

    buttonMap.put("buy main home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.buyStockSellStock).getLocation());
      ((JFrame) this.buyStockSellStock).dispose();
    });

    buttonMap.put("check cost home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkCostMain).getLocation());
      ((JFrame) this.checkCostMain).dispose();
    });

    buttonMap.put("dc home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.dollarCost).getLocation());
      ((JFrame) this.dollarCost).dispose();
    });

    buttonMap.put("pp home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.plot).getLocation());
      ((JFrame) this.plot).dispose();
    });




    buttonMap.put("check value view home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkV).getLocation());
      ((JFrame) this.checkV).dispose();
    });

    buttonMap.put("check value by date view home", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.checkVD).getLocation());
      ((JFrame) this.checkVD).dispose();
    });


    buttonMap.put("save M", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(this.saving.getLocation());
      this.saving.dispose();
    });

    buttonMap.put("read M", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.reading).getLocation());
      ((JFrame) this.reading).dispose();
    });

    buttonMap.put("buy stock Main H", () -> {
      homeView = new Home("Home");
      homeView.addActionListener(this);
      ((JFrame) homeView).setLocation(((JFrame) this.sellStock).getLocation());
      ((JFrame) this.sellStock).dispose();
    });

    return buttonMap;
  }

  /**
   * This method is used to execute the buttons for GUI.
   */
  @Override
  public void execute() {
    buttonMap = buttonAssign();
  }

  /**
   * This is the main run for GUI.
   *
   * @param e the event to be processed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.str = e.getActionCommand();
    buttonMap.get(str).run();
  }
}
