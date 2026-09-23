import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Map;

public class Analytics {

    public static JPanel createPieChart(Map<String, Double> categoryTotals, boolean isDark) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (String category : categoryTotals.keySet())
            dataset.setValue(category, categoryTotals.get(category));

        JFreeChart chart = ChartFactory.createPieChart(
                "Category-wise Expense Distribution",
                dataset, true, true, false);
                
        applyChartTheme(chart, isDark);

        return new ChartPanel(chart);
    }

    public static JPanel createBarChart(Map<String, Double> categoryTotals, boolean isDark) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String cat : categoryTotals.keySet())
            dataset.addValue(categoryTotals.get(cat), "Expenses", cat);

        JFreeChart chart = ChartFactory.createBarChart(
                "Expenses by Category",
                "Category", "Amount",
                dataset);
                
        applyChartTheme(chart, isDark);

        return new ChartPanel(chart);
    }

    public static JPanel createComparisonChart(double curMonth, double prevMonth, double curYear, double prevYear, boolean isDark) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(prevMonth, "Last Month", "Monthly");
        dataset.addValue(curMonth, "This Month", "Monthly");
        dataset.addValue(prevYear, "Last Year", "Yearly");
        dataset.addValue(curYear, "This Year", "Yearly");

        JFreeChart chart = ChartFactory.createBarChart(
                "Time Period Comparison",
                "Period", "Amount",
                dataset);
                
        applyChartTheme(chart, isDark);

        return new ChartPanel(chart);
    }
    
    private static void applyChartTheme(JFreeChart chart, boolean isDark) {
        java.awt.Color bg = isDark ? new java.awt.Color(45, 45, 45) : java.awt.Color.WHITE;
        java.awt.Color fg = isDark ? java.awt.Color.WHITE : java.awt.Color.BLACK;
        java.awt.Color gridCol = isDark ? new java.awt.Color(100, 100, 100) : java.awt.Color.LIGHT_GRAY;

        chart.setBackgroundPaint(bg);
        chart.getTitle().setPaint(fg);
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(bg);
            chart.getLegend().setItemPaint(fg);
        }

        org.jfree.chart.plot.Plot plot = chart.getPlot();
        plot.setBackgroundPaint(bg);
        plot.setOutlinePaint(null);

        if (plot instanceof org.jfree.chart.plot.CategoryPlot) {
            org.jfree.chart.plot.CategoryPlot cPlot = (org.jfree.chart.plot.CategoryPlot) plot;
            cPlot.getDomainAxis().setTickLabelPaint(fg);
            cPlot.getDomainAxis().setLabelPaint(fg);
            cPlot.getDomainAxis().setAxisLinePaint(fg);
            
            cPlot.getRangeAxis().setTickLabelPaint(fg);
            cPlot.getRangeAxis().setLabelPaint(fg);
            cPlot.getRangeAxis().setAxisLinePaint(fg);
            cPlot.setRangeGridlinePaint(gridCol);
        } else if (plot instanceof org.jfree.chart.plot.PiePlot) {
            org.jfree.chart.plot.PiePlot pPlot = (org.jfree.chart.plot.PiePlot) plot;
            pPlot.setLabelBackgroundPaint(bg);
            pPlot.setLabelPaint(fg);
            pPlot.setLabelOutlinePaint(bg);
            pPlot.setLabelShadowPaint(null);
        }
    }
}
