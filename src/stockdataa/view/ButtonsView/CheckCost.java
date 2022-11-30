package stockdataa.view.ButtonsView;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This represents the class with methods used to check cost basis of portfolio through GUI.
 */
public class CheckCost extends JFrame implements Buttons {
  private JButton checkCostBasis;
  private JButton checkCostBasisByDate;

  private JButton home;



  /**
   * This is the method to calculate the cost basis through GUI.
   * @param tag respective tag for button assignment.
   */
  public CheckCost(String tag) {
    super(tag);
    checkCostBasis = new JButton("Check Cost Basis");

    checkCostBasis.setActionCommand("check cost check cost basis");

    checkCostBasisByDate = new JButton("Check Cost Basis By Date");
    checkCostBasisByDate.setActionCommand("check cost check cost basis by date");

    home = new JButton("Home");
    home.setActionCommand("check cost home");

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    panel.add(checkCostBasis);
    panel.add(checkCostBasisByDate);
    buttonPanel.add(home);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    this.add(panel);


    this.add(panel);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();

    this.setPreferredSize(new Dimension(500,500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * This method is to add action listener.
   * @param listener is the object of ActionListener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    checkCostBasis.addActionListener(listener);
    checkCostBasisByDate.addActionListener(listener);
    home.addActionListener(listener);

  }

  /**
   * Focus set.
   */
  @Override
  public void focus () {
    this.setFocusable(true);
    this.requestFocus();
  }



}
