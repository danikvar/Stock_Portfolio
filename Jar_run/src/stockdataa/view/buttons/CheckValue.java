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
 * This class represents the view when user do check value operation.
 */
public class CheckValue extends JFrame implements Buttons {
  private JButton checkTotalValue;
  private JButton checkTotalValueByDate;
  private JButton home;

  /**
   * This method is used to get the total value of the portfolio screen on GUI.
   *
   * @param tag is the respective tag for button assignment.
   */


  public CheckValue(String tag) {
    super(tag);
    checkTotalValue = new JButton("Check Total Value");
    checkTotalValue.setBackground(Color.ORANGE);
    checkTotalValue.setActionCommand("check value check total value");
    checkTotalValueByDate = new JButton("Check Total Value By Date");
    checkTotalValueByDate.setBackground(Color.YELLOW);
    checkTotalValueByDate.setActionCommand("check value check total value by date");
    home = new JButton("Home");
    home.setActionCommand("check val home");
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    panel.add(checkTotalValue);
    panel.add(checkTotalValueByDate);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    buttonPanel.add(home);
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
    checkTotalValue.addActionListener(listener);
    checkTotalValueByDate.addActionListener(listener);
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
