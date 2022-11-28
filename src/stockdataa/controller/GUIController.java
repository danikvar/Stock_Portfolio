package stockdataa.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import stockdataa.DataHelpers;

//import stockdataa.model.getprice.GetPrice;
//import stockdataa.model.getprice.MockGetPrice;
//import stockdataa.model.stock.IStock;
//import stockdataa.model.stock.Stock;
//import stockdataa.model.tradeoperation.TradeOperation;

//import stockdataa.model.SmartPortfolio;
import stockdataa.model.OperationsImpl;
import stockdataa.model.SmartPortfolio;
import stockdataa.view.ButtonOnlyView.ButtonOnly;
import stockdataa.view.ButtonOnlyView.CheckCost;
import stockdataa.view.ButtonOnlyView.CheckValue;
import stockdataa.view.ButtonOnlyView.ChooseAWayToBuyStock;
import stockdataa.view.ButtonOnlyView.GetPortfolio;
import stockdataa.view.ButtonOnlyView.MainView;
import stockdataa.view.ButtonOnlyView.ShowAllPortfolios;
import stockdataa.view.TextFieldView.sellStock;
import stockdataa.view.TextFieldView.BuyStockView;
import stockdataa.view.TextFieldView.CheckCostByDateView;
import stockdataa.view.TextFieldView.CheckCostView;
import stockdataa.view.TextFieldView.CheckValueByDateView;
import stockdataa.view.TextFieldView.CheckValueView;
import stockdataa.view.TextFieldView.createUser;

import stockdataa.view.TextFieldView.ReadPortfolio;
import stockdataa.view.TextFieldView.SavePortfolio;

import stockdataa.view.TextFieldView.ShowAllUsers;
import stockdataa.view.TextFieldView.WithTextField;

/**
 * This is a Trade controller control a trade program with a GUI. It contains a execute method to
 * run the program.
 */
public class GUIController extends SmartController implements ActionListener {
  //private TradeOperation<Stock> model;
  private ButtonOnly mainView;
  private ButtonOnly showPortfolioView;

  private ButtonOnly checkCostBoth;
  private ButtonOnly checkValueBoth;
  private ButtonOnly getPortfolio;

  private ButtonOnly buyStockChooseAWay;
  private WithTextField createView;

  private WithTextField buyStock;
  private WithTextField sellStock;
  private WithTextField checkCost;
  private WithTextField checkCostByDate;
  private WithTextField checkValue;
  private WithTextField checkValueByDate;

  private SavePortfolio savePortfolio;
  private WithTextField readPortfolio;

  private WithTextField showusers;
  private String str;
  private Map<String, Runnable> actionMap;

 
   public GUIController(OperationsImpl m, ButtonOnly v) {
    super(m, v);
    this.mainView = v;
    mainView.addActionListener(this);
    str = "";
  }




  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> actionMap = new HashMap<>();

    // Create or Load a user here.
    actionMap.put("createUser", () -> {
      createView = new createUser("Create User");
      createView.addActionListener(this);
      ((JFrame) createView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("showAllPortfolio", () -> {

      List<String> list;
      try{
        list = DataHelpers.listPortfoliosList(this.opModel.getUser()); //model.; // initialize a list to get our portfolio list.
      } catch (Exception e) {
        //System.out.println(e.getMessage());
        list = new ArrayList<>();
        //throw new IllegalArgumentException(e.toString());
      }

      showPortfolioView = new ShowAllPortfolios("show portfolios", list);
      showPortfolioView.addActionListener(this);
      ((JFrame) showPortfolioView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("showAllUsers", () -> {
      List<String> list =  DataHelpers.getUserList();//model.getPortfolioList(); // initialize a list to get our user list.
      showusers = new ShowAllUsers("show users", list);
      showusers.addActionListener(this);
      ((JFrame) showusers).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });


    actionMap.put("getPortfolio", () -> {
      getPortfolio = new GetPortfolio("Get Portfolio");
      getPortfolio.addActionListener(this);
      ((JFrame) getPortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    

    actionMap.put("buyStockChooseAWay", () -> {
      buyStockChooseAWay = new ChooseAWayToBuyStock("Choose To Buy/Sell");
      buyStockChooseAWay.addActionListener(this);
      ((JFrame) buyStockChooseAWay).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("buy stocks", () -> {
      buyStock = new BuyStockView("Buy");
      buyStock.addActionListener(this);
      ((JFrame) buyStock).setLocation(((JFrame) this.buyStockChooseAWay).getLocation());
      ((JFrame) this.buyStockChooseAWay).dispose();
    });

    actionMap.put("sell stocks", () -> {
      sellStock = new sellStock("Buy");
      sellStock.addActionListener(this);
      ((JFrame) sellStock).setLocation(((JFrame) this.buyStockChooseAWay).getLocation());
      ((JFrame) this.buyStockChooseAWay).dispose();
    });


    actionMap.put("checkCost", () -> {
      checkCostBoth = new CheckCost("Check Cost");
      checkCostBoth.addActionListener(this);
      ((JFrame) checkCostBoth).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("check cost check cost basis", () -> {
      checkCost = new CheckCostView("Check Cost Basis");
      checkCost.addActionListener(this);
      ((JFrame) checkCost).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkCostBoth).dispose();
    });

    actionMap.put("check cost check cost basis by date", () -> {
      checkCostByDate = new CheckCostByDateView("Check Cost Basis");
      checkCostByDate.addActionListener(this);
      ((JFrame) checkCostByDate).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkCostBoth).dispose();
    });

    actionMap.put("checkValue", () -> {
      checkValueBoth = new CheckValue("Check Value");
      checkValueBoth.addActionListener(this);
      ((JFrame) checkValueBoth).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("check value check total value", () -> {
      checkValue = new CheckValueView("Check Total Value");
      checkValue.addActionListener(this);
      ((JFrame) checkValue).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkValueBoth).dispose();
    });

    actionMap.put("check value check total value by date", () -> {
      checkValueByDate = new CheckValueByDateView("Check Total Value");
      checkValueByDate.addActionListener(this);
      ((JFrame) checkValueByDate).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkValueBoth).dispose();
    });





    actionMap.put("savePortfolioToFile", () -> {
      String portDir = this.opModel.getPath();
      savePortfolio = new SavePortfolio("Save Portfolio",portDir);
      savePortfolio.addActionListener(this);
      ((JFrame) savePortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("readPortfolioFromFile", () -> {
      readPortfolio = new ReadPortfolio("Read Portfolio");
      readPortfolio.addActionListener(this);
      ((JFrame) readPortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("quit", () -> {
      System.exit(0);
    });

    actionMap.put("create", () -> {
      String userPath = createView.getInput();

      if (userPath.length() == 0) {
        createView.setHintMess("empty user name");
        return;
      }
      String[] userNameAndPath = userPath.split("\n");
      String userName = userNameAndPath[0];
      String filePath = userNameAndPath[1];
      try {
    	    Path checkUserPath = Paths.get(filePath);
    	    DataHelpers.createUser(userName, filePath);
        createView.setHintMess("User " + userName + " created.");
        createView.clearField();
      }
      catch (IllegalArgumentException e) {
        createView.setHintMess(e.getMessage());
      }
    });

    actionMap.put("select user", () -> {
      String userName = showusers.getInput();


      if (! DataHelpers.getUserList().contains(userName)) {
        showusers.setHintMess("Can only load existing users. Please check your input.");
        return;
      }

      try {
        String filePath = DataHelpers.getPortfolioDir(userName);
        this.opModel.setUser(userName);
        this.opModel.setPath(filePath);

        showusers.setHintMess("User " + this.opModel.getUser() + " loaded.");
        showusers.clearField();
      }
      catch (IllegalArgumentException e) {
        showusers.setHintMess(e.getMessage());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });



//    actionMap.put("get portfolio detail check", () -> {
//      String portfolioName = getPortfolioDetailView.getInput();
//      if (portfolioName.length() == 0) {
//        getPortfolioDetailView.setHintMess("Empty portfolio name");
//        return;
//      }
//      try {
//        List<IStock> list = null; //model.getPortfolioState(portfolioName);
//        showGetPortfolioDetail = new ShowGetPortfolioDetail("Show Detail of Portfolio",
//            list, portfolioName);
//      }
//      catch (IllegalArgumentException e) {
//        showGetPortfolioDetail = new ShowGetPortfolioDetail("Show Detail of Portfolio",
//            null, portfolioName);
//      }
//      showGetPortfolioDetail.addActionListener(this);
//      ((JFrame) showGetPortfolioDetail).setLocation(((JFrame) this.getPortfolioDetailView).getLocation());
//      ((JFrame) this.getPortfolioDetailView).dispose();
//    });



    actionMap.put("buy", () -> {
      String message;
      String string = buyStock.getInput();
      int index = string.indexOf("\n");
      String stockSymbol = string.substring(0, index);
      if (stockSymbol.length() == 0) {
        buyStock.setHintMess("No stock symbol entered");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String volume = string.substring(0, index);
      if (volume.length() == 0) {
        buyStock.setHintMess("Enter a valid transaction");
        return;
      }
      if(opModel.getPort().isEmpty()){
        message = "Must select a portfolio before buying stock!";
      } else {

        try {
          message = opModel.buyStock(stockSymbol, volume, true);

        }
        catch (IllegalArgumentException e) {
          message = e.getMessage();
        }
      }

      buyStock.clearField();
      buyStock.setHintMess(message);
    });

    actionMap.put("check cost view check", () -> {
      String message;
      try {
        message = opModel.getCostBasis("current");
        //message = "Total cost basis of " + portfolioName + " by now is $" + cost;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkCost.setHintMess(message);
      //checkCost.setHintMess(message);
    });

    actionMap.put("check cost by date view check", () -> {

      String dateString = checkCostByDate.getInput();
      try {
        LocalDate.parse(dateString);
      }
      catch (Exception e) {
        checkCostByDate.setHintMess("Enter a valid date");
        return;
      }
      String message;
      try {
        message = opModel.getCostBasis(dateString);
        //message = "Total cost basis of " + portfolioName + " by now is $" + cost;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkCostByDate.clearField();
      checkCostByDate.setHintMess(message);

      //checkCostByDate.setHintMess(message);
    });

    actionMap.put("check value by date view check", () -> {

      String message;
      if(opModel.getPort().isEmpty()){
        checkValueByDate.setHintMess("First select a portfolio from the portfolios tab.");
        return;
      }
      String date = checkValueByDate.getInput();
      if (date.length() == 0) {
        checkValueByDate.setHintMess("Enter valid date in yyyy-mm-dd format or 'current'");
        return;
      }

      try {
        message = opModel.printPortfolioAt(date);
        //message = "Total value of portfolio " + portfolioName + " by now is $" + value;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkValueByDate.clearField();
      checkValueByDate.setHintMess(message);
    });

    actionMap.put("check value view check", () -> {
      String message;
      if(opModel.getPort().isEmpty()){
        checkValue.setHintMess("First select a portfolio from the portfolios tab.");
        return;
      }

      try {
        message = opModel.printPortfolioAt("current");
        //message = "Total value of portfolio " + portfolioName + " by now is $" + value;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkValue.clearField();
      checkValue.setHintMess(message);

    });







    actionMap.put("save portfolio save", () -> {
      String input = savePortfolio.getInput();
      String message;
      if (input.length() != 0) {
        this.opModel.setPath(input);
      }
      message = this.opModel.savePortfolio();
      savePortfolio.setHintMess(message);
    });

    //
    actionMap.put("read portfolio read", () -> {
      String fileName = readPortfolio.getInput();
      String message;
      if (fileName.length() == 0) {
        message = "Enter valid portfolio name";
        readPortfolio.setHintMess(message);
        return;
      }
      try {
        message = this.opModel.loadPort(fileName);
      }
      catch (Exception e) {
        message = e.getMessage();
      }

      readPortfolio.setHintMess(message);
    });

    actionMap.put("buy stock money sell", () -> {
      String string = sellStock.getInput();
      int index = string.indexOf("\n");
      String stockSymbol = string.substring(0, index);
      if (stockSymbol.length() == 0) {
        sellStock.setHintMess("Enter valid stock symbol");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String shares = string.substring(0, index);
      if (shares.length() == 0 || !shares.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)"
          + "")) {
        sellStock.setHintMess("Invalid input");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String dateString = string.substring(0, index);

      String commFee = string.substring(index + 1, string.length() - 1);

      String message = "";
      if(opModel.getPort().isEmpty()){
        message = "Must select a portfolio before buying stock!";
      } else {

        try {
          opModel.sellStock(stockSymbol, shares, commFee, dateString, true);
          message = "Sold " + Integer.parseInt(shares) + " shares of " + stockSymbol
                  + " from portfolio "
                  + opModel.getPort() + " on " + dateString + "\n";


        }
        catch (IllegalArgumentException e) {
          message = e.getMessage();
        }
      }

      sellStock.clearField();
      sellStock.setHintMess(message);
    });



    actionMap.put("create portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.createView).getLocation());
      ((JFrame) this.createView).dispose();
    });

    actionMap.put("show all portfolios home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.showusers).getLocation());
      ((JFrame) this.showusers).dispose();
    });








    actionMap.put("buy stock home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.buyStock).getLocation());
      ((JFrame) this.buyStock).dispose();
    });

    actionMap.put("check cost view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkCost).getLocation());
      ((JFrame) this.checkCost).dispose();
    });

    actionMap.put("check cost by date view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkCostByDate).getLocation());
      ((JFrame) this.checkCostByDate).dispose();
    });

    actionMap.put("show all users home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.showusers).getLocation());
      ((JFrame) this.checkCostByDate).dispose();
    });

    actionMap.put("check value view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkValue).getLocation());
      ((JFrame) this.checkValue).dispose();
    });

    actionMap.put("check value by date view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkValueByDate).getLocation());
      ((JFrame) this.checkValueByDate).dispose();
    });



    actionMap.put("save portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.savePortfolio).getLocation());
      ((JFrame) this.savePortfolio).dispose();
    });

    actionMap.put("read portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.readPortfolio).getLocation());
      ((JFrame) this.readPortfolio).dispose();
    });

    actionMap.put("buy stock money home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.sellStock).getLocation());
      ((JFrame) this.sellStock).dispose();
    });

    return actionMap;
  }

 
  @Override
  public void execute() {
    actionMap = initializeMap();
  }

  @Override
  public void actionPerformed (ActionEvent e) {
    this.str = e.getActionCommand();
    actionMap.get(str).run();
  }
}
