package stockdataa.view.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;



import javax.swing.border.EmptyBorder;


/**
 * This represents the class with methods used to buy/sell stocks main screen in GUI.
 */
public class BuyMain extends JFrame implements Buttons {
  private JButton buyStocksB;
  private JButton sellStocksS;
  private JButton home;

  /**
   * This is the class with method to give option of buying/selling stock to user in GUI.
   * @param tag respective tag for button assignment.
   */
  public BuyMain(String tag) {
    super(tag);
    buyStocksB = new JButton("Buy Stocks");
    buyStocksB.setBackground(Color.ORANGE);
    buyStocksB.setActionCommand("buy stocks");
    sellStocksS = new JButton("Sell Stocks");
    sellStocksS.setBackground(Color.YELLOW);
    sellStocksS.setActionCommand("sell stocks");
    home = new JButton("Home");
    home.setActionCommand("buy main home");
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    panel.add(buyStocksB);
    panel.add(sellStocksS);
    buttonPanel.add(home);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    this.add(panel);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */

  @Override
  public void addActionListener(ActionListener listener) {
    buyStocksB.addActionListener(listener);
    sellStocksS.addActionListener(listener);
    home.addActionListener(listener);
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
