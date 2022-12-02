package stockdataa.view.buttonsandtexts;



import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;


/**
 * This represents the class with methods which helps in reading a portfolio through GUI.
 */
public class Reading extends JFrame implements Texts {
  private JTextField fileNameEntry;
  private JButton read;
  private JButton home;
  private JLabel output;

  /**
   * This method is used to read a portfolio using GUI.
   *
   * @param tag respective tag for button assignment.
   */
  public Reading(String tag) {
    super(tag);
    JLabel fileName = new JLabel("File Name: ");
    fileNameEntry = new JTextField(20);
    JPanel filePanel = new JPanel();
    filePanel.add(fileName);
    filePanel.add(fileNameEntry);
    read = new JButton("Create / Read");
    home = new JButton("Home");
    read.setActionCommand("read");
    home.setActionCommand("read M");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(read);
    buttonPanel.add(home);
    output = new JLabel();
    output.setText("Please enter the full name of your Portfolio associated " +
            "with the current user (including file extension). \n");
    JLabel hint2 = new JLabel();
    hint2.setText("If you would like to create a new portfolio that will"
            + " be saved to your user directory then enter a unique"
            + " name consisting of only letters, numbers, and/or _ (no file extension).");
    JPanel hintPanel = new JPanel();
    JPanel hintPanel2 = new JPanel();
    hintPanel.add(output);
    hintPanel2.add(hint2);

    JLabel hint3 = new JLabel();
    hint3.setText("If you would like to load directly through a path, please" +
            "provide the full file path to the portfolio here.");
    JPanel hintPanel3 = new JPanel();
    hintPanel3.add(hint3);

    JPanel guiUnit = new JPanel();
    guiUnit.setLayout(new GridLayout(4, 1));
    guiUnit.add(filePanel);
    guiUnit.add(hintPanel);
    guiUnit.add(hintPanel2);
    guiUnit.add(hintPanel3);
    this.add(guiUnit, BorderLayout.PAGE_START);
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
    read.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * This method is used to get input from the user.
   *
   * @return the input given by the user.
   */
  @Override
  public String getInput() {
    return fileNameEntry.getText();
  }

  /**
   * This method is to print a message to the user.
   *
   * @param outToUser is the string that is to be printed.
   */
  @Override
  public void outMess(String outToUser) {
    output.setText(outToUser);
  }

  /**
   * Cleans the text fields.
   */
  @Override
  public void clean() {
    fileNameEntry.setText("");
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
