package ltf.plasmotron.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import params.InputParameter;
import params.Parameter;
import params.ParameterPool;

public class Painter extends JComponent {

	final ParameterPool pool;
	final Parameter output;
	final Parameter input;

	double inputMin;
	double inputMax;

	public Painter(ParameterPool pool, Parameter output, Parameter input, double inputMin, double inputMax) {
		this.pool = pool;
		this.output = output;
		this.input = input;
		this.inputMin = inputMin;
		this.inputMax = inputMax;

		updateGraph();
	}

	public void updateGraph() {
		setLayout(new BorderLayout(8, 8));
		removeAll();

		InputParameter ip = new InputParameter(inputMin);
		pool.put(input, ip);

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("Lp");

		for (double i = inputMin; i <= inputMax; i += (inputMax - inputMin) / (10000.0)) {
			ip.setValue(i);
			try {
				double y = pool.get(output).compute(pool);
				series.add(i / (2.0 * Math.PI) / 1000.0, y);
			} catch (Exception e) {
				return;
			}

		}

		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Звуковая мощность, генерируемая плазмотроном", "f, кГц",
				"Lp, дБ", dataset);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMinimumDrawWidth(100);
		chartPanel.setMinimumDrawHeight(100);
		chartPanel.setMaximumDrawWidth(1280);
		chartPanel.setMaximumDrawHeight(720);

		setMinimumSize(new Dimension(100, 100));
		setPreferredSize(new Dimension(640, 480));
		setMaximumSize(new Dimension(1920, 1080));
		add(chartPanel);
	}
}
