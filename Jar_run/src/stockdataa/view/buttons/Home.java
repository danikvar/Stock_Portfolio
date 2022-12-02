package stockdataa.view.buttons;



import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;

import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;






/**
 * This represents the class with the home screen implementation on GUI.
 */


public class Home extends JFrame implements Buttons {
  private JButton createUser;

  private JButton checkCB;
  private JButton checkV;
  private JButton getusers;
  private JButton displayPorts;
  private JButton buysellStock;
  private JButton dollarCostAveraging;
  private JButton graph;
  private JButton savePortfolioToFile;
  private JButton readOrCreate;
  private JButton quit;
  //private JLabel welcome;


  /**
   * This method is used to create the home screen.
   *
   * @param str is the string for command assignment.
   */

  public Home(String str) {
    super(str);
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    GridLayout structure = new GridLayout(6, 2);
    JPanel panel;
    panel = new JPanel();
    panel.setLayout(structure);


    createUser = new JButton("Create User");
    createUser.setBackground(Color.ORANGE);
    getusers = new JButton("Show All Users / Choose a User");
    getusers.setBackground(Color.YELLOW);
    readOrCreate = new JButton("Create a New Portfolio / Load");
    readOrCreate.setBackground(Color.YELLOW);
    displayPorts = new JButton("Show All Portfolio");
    displayPorts.setBackground(Color.ORANGE);
    buysellStock = new JButton("Buy/Sell Stock");
    buysellStock.setBackground(Color.ORANGE);
    quit = new JButton("Quit");
    quit.setBackground(Color.YELLOW);
    checkCB = new JButton("Check Cost Basis");
    checkCB.setBackground(Color.YELLOW);
    checkV = new JButton("Check Total Value");
    checkV.setBackground(Color.YELLOW);
    dollarCostAveraging = new JButton("Dollar Cost Averaging");
    dollarCostAveraging.setBackground(Color.ORANGE);
    graph = new JButton("Portfolio Performance");
    graph.setBackground(Color.ORANGE);
    savePortfolioToFile = new JButton("Save Portfolio To File");
    savePortfolioToFile.setBackground(Color.YELLOW);



    createUser.setActionCommand("createUser");
    displayPorts.setActionCommand("displayPorts");
    buysellStock.setActionCommand("buyStockSellStock");
    getusers.setActionCommand("showAllUsers");
    checkCB.setActionCommand("checkCB");
    checkV.setActionCommand("checkV");
    dollarCostAveraging.setActionCommand("dollarCost");
    graph.setActionCommand("plotGraph");
    savePortfolioToFile.setActionCommand("savePortfolioToFile");
    readOrCreate.setActionCommand("readOrCreate");
    quit.setActionCommand("quit");


    panel.add(createUser);
    panel.add(getusers);
    panel.add(readOrCreate);
    panel.add(displayPorts);
    panel.add(buysellStock);
    panel.add(checkCB);
    panel.add(checkV);
    panel.add(dollarCostAveraging);
    panel.add(graph);
    panel.add(savePortfolioToFile);
    panel.add(quit);


    this.getContentPane().add(panel);
    this.setVisible(true);
    this.pack();
  }

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */


  @Override
  public void addActionListener(ActionListener listener) {
    createUser.addActionListener(listener);
    displayPorts.addActionListener(listener);
    buysellStock.addActionListener(listener);
    quit.addActionListener(listener);
    checkCB.addActionListener(listener);
    checkV.addActionListener(listener);
    getusers.addActionListener(listener);
    dollarCostAveraging.addActionListener(listener);
    savePortfolioToFile.addActionListener(listener);
    readOrCreate.addActionListener(listener);
    graph.addActionListener(listener);
  }

  /**
   * Focus set.
   */

  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();
  }


}
