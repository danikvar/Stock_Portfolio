package stockdataa.view.ButtonOnlyView;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class CheckCost extends JFrame implements ButtonOnly {
  private JButton checkCostBasis;
  private JButton checkCostBasisByDate;

  private JButton home;


  public CheckCost(String caption) {
    super(caption);
    checkCostBasis = new JButton("Check Cost Basis");
    checkCostBasis.setActionCommand("check cost check cost basis");

    checkCostBasisByDate = new JButton("Check Cost Basis By Date");
    checkCostBasisByDate.setActionCommand("check cost check cost basis by date");
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    panel.add(checkCostBasis);
    panel.add(checkCostBasisByDate);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(100, 85, 100, 85)));
    this.add(panel);


    this.add(panel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();

    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }


  @Override
  public void addActionListener (ActionListener listener) {
    checkCostBasis.addActionListener(listener);
    checkCostBasisByDate.addActionListener(listener);

  }


  @Override
  public void resetFocus () {
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
