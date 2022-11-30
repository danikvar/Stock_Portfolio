package stockdataa.view.ButtonsAndTextsView;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PortfolioPerformance extends JFrame implements Texts {

  private JLabel sDate;
  private JTextField sDateF;
  private JLabel eDate;
  private JTextField eDateF;
  private JButton home;
  private JButton plot;
  private JLabel output;

  public PortfolioPerformance(String tag)
  {
    super(tag);
    sDate = new JLabel("Enter the start date");
    sDateF = new JTextField(20);
    eDate = new JLabel("Enter the end date");
    eDateF = new JTextField(20);
    plot = new JButton("Plot Graph");
    plot.setActionCommand("plotting");
    home = new JButton("Home");
    home.setActionCommand("pp home");
    output = new JLabel("");

    JPanel upper = new JPanel();
    JPanel upper1 = new JPanel();


    JPanel buttonPanel = new JPanel();


    upper.add(sDate);
    upper.add(sDateF);
    upper1.add(eDate);
    upper1.add(eDateF);

    buttonPanel.add(plot);
    buttonPanel.add(home);

    JPanel hintPanel = new JPanel();
    //hintPanel.setLayout(new GridLayout(3, 1));
    hintPanel.add(output);

    JPanel up = new JPanel();
    up.setLayout(new GridLayout(3,1));
    up.add(upper);
    up.add(upper1);

    this.add(up, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }


  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
    plot.addActionListener(listener);

  }

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();

    sb.append(sDateF.getText());
    sb.append("\n");
    sb.append(eDateF.getText());
    sb.append("\n");
    return sb.toString();
  }

  @Override
  public void outMess(String outToUser) {
    output.setText(outToUser);

  }

  @Override
  public void clean() {
    sDateF.setText("");
    eDateF.setText("");

  }

  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();

  }
}
