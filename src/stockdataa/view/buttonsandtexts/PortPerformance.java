package stockdataa.view.buttonsandtexts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;


import java.awt.event.ActionListener;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


import javax.swing.JFrame;


/**
 * This represents the class which implements the plotting of the performance of portfolio in GUI.
 */

public class PortPerformance extends JFrame implements Texts {

  private static final long serialVersionUID = 1L;

  /**
   * This is the method to plot the graph of the performance of the portoflio.
   *
   * @param title  is the title of the graph.
   * @param data   is the data which has dates and values of portfolio.
   * @param minVal is the minimum value of portfolio.
   * @param maxVal is the maximum value of the portfolio.
   */

  public PortPerformance(String title, Map<LocalDate, Double> data, int minVal, int maxVal) {

    super(title);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset(data);
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart("Portfolio Performance",
            "Time Range", "Portfolio Value",
            dataset, PlotOrientation.VERTICAL, false, false, false);
    chart.setPadding(new RectangleInsets(10, 10, 10, 10));

    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    CategoryAxis p = chart.getCategoryPlot().getDomainAxis();


    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    Font font = new Font("Arial", 0, 8);
    p.setTickLabelFont(font);



    rangeAxis.setRange(minVal, maxVal);
    plot.setRangeAxis(rangeAxis);
    chart.plotChanged(new PlotChangeEvent(plot));
    ChartPanel panel = new ChartPanel(chart);


    this.add(panel);


  }

  private DefaultCategoryDataset createDataset(Map<LocalDate, Double> data) {

    LocalDate start = Collections.min(data.keySet());
    LocalDate end = Collections.max(data.keySet());
    String series1 = "Value of the portfolio printed starting on "
            + start.toString() + " and ending on " + end.toString();


    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    SortedSet<LocalDate> myKeys = new TreeSet<>(LocalDate::compareTo);
    myKeys.addAll(data.keySet());

    for (LocalDate key : myKeys) {
      dataset.addValue(data.get(key), series1, key.toString());
    }

    return dataset;
  }

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */


  @Override
  public void addActionListener(ActionListener listener) {
    //Empty

  }

  /**
   * This method is used to get input from the user.
   *
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    return null;
  }

  /**
   * This method is to print a message to the user.
   *
   * @param outToUser is the string that is to be printed.
   */

  @Override
  public void outMess(String outToUser) {
    //Empty
  }

  /**
   * Cleans the text fields.
   */

  @Override
  public void clean() {
    //Empty

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
