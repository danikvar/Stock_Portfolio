package stockdataa.view.buttonsandtexts;


import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

import javax.swing.JTextField;



/**
 * This represents the class with methods to save the portfolio through the GUI.
 */
public class Saving extends JFrame implements Texts {
  private JLabel portfolioName;
  private JTextField fileNameEntry;
  private JButton save;
  private JButton home;
  private JLabel output;

  private String path;

  /**
   * This method is used to save a portfolio through the GUI.
   *
   * @param tag  respective tag for the button assignment.
   * @param path is the file path to save the file.
   */
  public Saving(String tag, String path) {
    super(tag);
    this.path = path;
    portfolioName = new JLabel("Current Path: " + this.path);
    JLabel fileName = new JLabel("New Path: ");
    fileNameEntry = new JTextField(20);
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.add(portfolioName);
    JPanel filePanel = new JPanel();
    filePanel.add(fileName);
    filePanel.add(fileNameEntry);
    save = new JButton("Save");
    home = new JButton("Home");
    save.setActionCommand("save");
    home.setActionCommand("save M");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(save);
    buttonPanel.add(home);
    output = new JLabel();
    output.setText("If you want to specify a new file path, add it in the box. eg: C:/filename");
    JPanel hintPanel = new JPanel();
    hintPanel.add(output);
    JPanel guiUnit = new JPanel();
    guiUnit.setLayout(new GridLayout(3, 1));
    guiUnit.add(portfolioPanel);
    guiUnit.add(filePanel);
    guiUnit.add(hintPanel);
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
    save.addActionListener(listener);
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
   * This method is used to set the path of the folder to which the file is to be saved.
   *
   * @param outToUser is the message printed to user.
   */
  public void setPath(String outToUser) {

    this.path = outToUser;
    portfolioName.setText("Current Path: " + outToUser);
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
