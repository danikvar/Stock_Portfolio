package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SetCommissionFee extends JFrame implements WithTextField {
  private JLabel fee;
  private JTextField feeText;
  private JButton set;
  private JButton home;
  private JLabel hint;

  /**
   * Constructor of SetCommissionFee, it initializes the SetCommissionFee view.
   * @param caption caption.
   */
  public SetCommissionFee(String caption) {
    super(caption);
    fee = new JLabel("Enter a commission fee");
    feeText = new JTextField(10);

    set = new JButton("Set");
    home = new JButton("Home");

    set.setActionCommand("set commission fee set");
    home.setActionCommand("set commission fee home");
    JPanel panel = new JPanel();
    panel.add(fee);
    panel.add(feeText);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(set);
    buttonPanel.add(home);

    hint = new JLabel();
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    set.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput () {
    return feeText.getText();
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess (String message) {
    hint.setText(message);
  }

  /**
   * Clear all fields.
   */
  @Override
  public void clearField () {
    feeText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus () {
    this.setFocusable(true);
    this.requestFocus();
  }
}
