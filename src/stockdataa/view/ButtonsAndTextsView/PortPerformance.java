package stockdataa.view.ButtonsAndTextsView;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class PortPerformance extends JFrame implements Texts  {

  private static final long serialVersionUID = 1L;


  public PortPerformance(String title, Map<LocalDate, Double> data, int minVal, int maxVal) {

    super(title);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset(data);
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart("Portfolio Performance",
            "Time Range", "Portfolio Value",
            dataset,PlotOrientation.VERTICAL,false,false,false);
    chart.setPadding(new RectangleInsets(10, 10, 10, 10));

    CategoryPlot plot = (CategoryPlot) chart.getPlot();

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

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

    for(LocalDate key: myKeys) {
      dataset.addValue(data.get(key), series1, key.toString());
    }
    
    return dataset;
  }


  @Override
  public void addActionListener(ActionListener listener) {

  }

  @Override
  public String getInput() {
    return null;
  }

  @Override
  public void outMess(String outToUser) {

  }

  @Override
  public void clean() {

  }

  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();

  }
}
