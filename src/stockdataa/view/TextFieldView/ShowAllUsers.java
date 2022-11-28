package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShowAllUsers extends JFrame implements WithTextField{
  private JTextField userNameTxt;
  private JLabel hint;
  private JLabel userNameLabel;
  private JButton create;
  private JButton main;
  private JLabel label;
  private JButton home;

  private JButton select;
  private List<String> list;



  /**
   * Constructor of ShowAllPortfolios. It initialize the ShowAllPortfolios View.
   * @param caption caption.
   * @param list the portfolio list that contains all portfolios.
   */
  public ShowAllUsers(String caption, List<String> list) {


    super(caption);
    this.list = list;
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel hintPanel = new JPanel();
    hint = new JLabel("");
    hintPanel.add(hint);

    String str = "Following are all users: " + "<br/>";
    for (String s : list) {
      str = str + s;
      str = str + "<br/>";
    }
    str = "<html>"+ str + "</html>";
    label = new JLabel(str);
    panel.add(label, BorderLayout.PAGE_START);

    userNameLabel = new JLabel("Choose the User Name: \n");
    userNameTxt = new JTextField(20);
    panel.add(userNameLabel);
    panel.add(userNameTxt);

    select = new JButton("select");
    select.setActionCommand(("select user"));
    buttonPanel.add(select,BorderLayout.WEST);
    home = new JButton("home");
    home.setActionCommand("show all portfolios home");
    buttonPanel.add(home, BorderLayout.SOUTH);
    this.add(panel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.add(hintPanel, BorderLayout.EAST);
    this.setVisible(true);
    this.pack();
  }

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(userNameTxt.getText());
    return sb.toString();
  }

  @Override
  public void setHintMess(String message) {
    hint.setText(message);
  }

  @Override
  public void clearField() {

  }

  /**
   * Add the provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {

    home.addActionListener(listener);
    select.addActionListener(listener);
  }

  /**
   * Reset the focus on the appropriate part of the view.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
