package stockdataa.view.TextFieldView;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CheckValueByDateView extends JFrame implements WithTextField {
  private JLabel portfolioNameLabel;
  private JTextField portfolioNameText;

  private JLabel dateLable;
  private JTextField dateText;

  private JButton check;
  private JButton home;

  private JTextArea hint;

  /**
   * Constructor of CheckValueByDateView, it initialize the view including label, text fields and
   * buttons.
   * @param caption caption.
   */
  public CheckValueByDateView(String caption) {
    super(caption);

    JPanel datePanel = new JPanel();
    dateLable = new JLabel("Date: yyyy-mm-dd");
    dateText = new JTextField(20);
    datePanel.add(dateLable);
    datePanel.add(dateText);

    JPanel buttonPanel = new JPanel();
    check = new JButton("check");
    check.setActionCommand("check value by date view check");
    home = new JButton("home");
    home.setActionCommand("check value by date view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    hint = new JTextArea("");
    Font f = new Font("Courier New", Font.BOLD, 20);
    hint.setFont(f);
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    JPanel composite = new JPanel();
    composite.setLayout(new GridLayout(2,1));
    composite.add(datePanel);

    this.add(composite, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    check.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput() {
    String str = dateText.getText();
    return str;
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess(String message) {

    hint.setRows((int) message.lines().count());
    hint.setText(message);
  }

  /**
   * Clear the text field.
   */
  @Override
  public void clearField() {
    dateText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
