package stockdataa.view.ButtonOnlyView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ShowAllPortfolios extends JFrame implements ButtonOnly {

  private JLabel label;
  private JButton home;
  private List<String> list;


  public ShowAllPortfolios(String caption, List<String> list) {
    super(caption);
    this.list = list;
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String str = "Following are all portfolios: " + "<br/>";
    for (String s : list) {
      str = str + s;
      str = str + "<br/>";
    }
    str = "<html>"+ str + "</html>";
    label = new JLabel(str);
    panel.add(label, BorderLayout.PAGE_START);

    home = new JButton("home");
    home.setActionCommand("show all portfolios home");
    buttonPanel.add(home, BorderLayout.SOUTH);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();
  }


  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
  }


  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

@Override
public void showOptions() {
	// TODO Auto-generated method stub
	
}

@Override
public void showOptions1() {
	// TODO Auto-generated method stub
	
}

@Override
public void getportName() {
	// TODO Auto-generated method stub
	
}

@Override
public void addAgain() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails2() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetails3() {
	// TODO Auto-generated method stub
	
}

@Override
public void showporttype() {
	// TODO Auto-generated method stub
	
}

@Override
public void chooseOption() {
	// TODO Auto-generated method stub
	
}

@Override
public void getDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void showSmartopt() {
	// TODO Auto-generated method stub
	
}

@Override
public void fromDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void endDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void costDate() {
	// TODO Auto-generated method stub
	
}

@Override
public void addStockdetailsmart2() {
	// TODO Auto-generated method stub
	
}

}
