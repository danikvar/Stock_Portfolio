package stockdataa.view.ButtonOnlyView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import stockdataa.view.TextFieldView.ShowAllUsers;


public class MainView extends JFrame implements ButtonOnly {
  private JButton createUser;

  private JButton checkCost;
  private JButton checkValue;
  private JButton getusers;
  private JButton showAllPortfolio;
  private JButton buysellStock;
  private JButton setCommissionFee;
  private JButton setAPI;
  private JButton savePortfolioToFile;
  private JButton readPortfolioFromFile;
  private JButton quit;

  
  public MainView (String str) {
    super(str);
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    GridLayout layout = new GridLayout(6, 2);
    JPanel panel;
    panel = new JPanel();
    panel.setLayout(layout);

    createUser = new JButton("Create User");
    getusers = new JButton("Show All/ Choose User");
    showAllPortfolio = new JButton("Show All Portfolio");

    buysellStock = new JButton("Buy/Sell Stock");
    quit = new JButton("Quit");
    checkCost = new JButton("Check Cost Basis");
    checkValue = new JButton("Check Total Value");
    setCommissionFee = new JButton("TBD - 1");
    setAPI = new JButton("TBD - 2");
    savePortfolioToFile = new JButton("Save Portfolio To File");
    readPortfolioFromFile = new JButton("Load or Create New Portfolio");

    createUser.setActionCommand("createUser");
    showAllPortfolio.setActionCommand("showAllPortfolio");
    buysellStock.setActionCommand("buyStockChooseAWay");
    getusers.setActionCommand("showAllUsers");
    //getPortfolio.setActionCommand("getPortfolio");
    checkCost.setActionCommand("checkCost");
    checkValue.setActionCommand("checkValue");
    setCommissionFee.setActionCommand("setCommissionFee");
    setAPI.setActionCommand("setAPI");
    savePortfolioToFile.setActionCommand("savePortfolioToFile");
    readPortfolioFromFile.setActionCommand("readPortfolioFromFile");
    quit.setActionCommand("quit");

    panel.add(createUser);
    panel.add(getusers);
    panel.add(showAllPortfolio);
    //panel.add(getPortfolio);

    panel.add(buysellStock);
    panel.add(checkCost);
    panel.add(checkValue);
    panel.add(setCommissionFee);
    panel.add(setAPI);
    panel.add(savePortfolioToFile);
    panel.add(readPortfolioFromFile);
    panel.add(quit);

    this.getContentPane().add(panel);
    this.setVisible(true);
    this.pack();
  }

  
  @Override
  public void addActionListener(ActionListener listener) {
    createUser.addActionListener(listener);
    showAllPortfolio.addActionListener(listener);
    buysellStock.addActionListener(listener);
    quit.addActionListener(listener);
    checkCost.addActionListener(listener);
    checkValue.addActionListener(listener);
    //getPortfolio.addActionListener(listener);
    getusers.addActionListener(listener);
    setCommissionFee.addActionListener(listener);
    savePortfolioToFile.addActionListener(listener);
    readPortfolioFromFile.addActionListener(listener);
    setAPI.addActionListener(listener);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

@Override
public void showOptions() {
	// TODO Auto-generated method stub
	
}

@Override
public void showOptions1() {
	// TODO Auto-generated method stub
	
}

@Override
public void getportName() {
	// TODO Auto-generated method stub
	
}

@Override
public void addAgain() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails2() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails3() {
	// TODO Auto-generated method stub
	
}

@Override
public void showporttype() {
	// TODO Auto-generated method stub
	
}

@Override
public void chooseOption() {
	// TODO Auto-generated method stub
	
}

@Override
public void getDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void showSmartopt() {
	// TODO Auto-generated method stub
	
}

@Override
public void fromDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void endDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void costDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetailsmart2() {
	// TODO Auto-generated method stub
	
}


}
