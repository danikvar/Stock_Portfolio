package stockdataa.view.ButtonOnlyView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the view when user do check value operation.
 */
public class CheckValue extends JFrame implements ButtonOnly {
  private JButton checkTotalValue;
  private JButton checkTotalValueByDate;


  public CheckValue(String caption) {
    super(caption);
    checkTotalValue = new JButton("Check Total Value");
    checkTotalValue.setActionCommand("check value check total value");
    checkTotalValueByDate = new JButton("Check Total Value By Date");
    checkTotalValueByDate.setActionCommand("check value check total value by date");
    JPanel panel = new JPanel();
    panel.add(checkTotalValue);
    panel.add(checkTotalValueByDate);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(100, 85, 100, 85)));
    this.add(panel);

    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }


  @Override
  public void addActionListener (ActionListener listener) {
    checkTotalValue.addActionListener(listener);
    checkTotalValueByDate.addActionListener(listener);
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
