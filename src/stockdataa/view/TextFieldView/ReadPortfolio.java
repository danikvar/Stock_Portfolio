package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ReadPortfolio extends JFrame implements WithTextField {
  private JLabel fileName;
  private JTextField fileNameText;
  private JButton read;
  private JButton home;
  private JLabel hint;

  /**
   * Constructor of ReadPortfolio, it initializes the ReadPortfolio view.
   * @param caption caption.
   */
  public ReadPortfolio(String caption) {
    super(caption);
    fileName = new JLabel("File Name: ");
    fileNameText = new JTextField(20);
    JPanel filePanel = new JPanel();
    filePanel.add(fileName);
    filePanel.add(fileNameText);
    read = new JButton("Read");
    home = new JButton("Home");
    read.setActionCommand("read portfolio read");
    home.setActionCommand("read portfolio home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(read);
    buttonPanel.add(home);
    hint = new JLabel();
    hint.setText("Please enter the full name of your Portfolio associated " +
            "with the current user (including file extension). \n");
    JLabel hint2 = new JLabel();
    hint2.setText("If you would like to create a new portfolio that will"
            + " be saved to your user directory then enter a unique"
            + " name consisting of only letters, numbers, and/or _ (no file extension).");
    JPanel hintPanel = new JPanel();
    JPanel hintPanel2 = new JPanel();
    hintPanel.add(hint);
    hintPanel2.add(hint2);

    JLabel hint3 = new JLabel();
    hint3.setText("If you would like to load directly through a path, please" +
            "provide the full file path to the portfolio here.");
    JPanel hintPanel3 = new JPanel();
    hintPanel3.add(hint3);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(4,1));
    composition.add(filePanel);
    composition.add(hintPanel);
    composition.add(hintPanel2);
    composition.add(hintPanel3);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    read.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput () {
    return fileNameText.getText();
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
   * Clear the text field.
   */
  @Override
  public void clearField () {
    fileNameText.setText("");
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
