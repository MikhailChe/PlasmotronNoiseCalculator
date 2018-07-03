package ltf.plasmotron.swing;

import java.awt.Dimension;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import params.InputParameter;
import params.JParam;
import params.Parameter;
import params.ParameterPool;

public class ButtonBundle extends JPanel {

	public ButtonBundle(final Parameter parameter, final ParameterPool pool, Function<Double, Double> prevValue,
			Function<Double, Double> nextValue) {

		JPanel top = new JPanel();
		BoxLayout topLayout = new BoxLayout(top, BoxLayout.LINE_AXIS);
		top.setLayout(topLayout);

		final JParam label = new JParam(parameter);
		Dimension d = new Dimension(64, 32);
		label.setMinimumSize(d);
		label.setPreferredSize(d);

		final SpinnerModel model = new SpinnerNumberModel() {

			public double value;

			@Override
			public void setValue(Object value) {
				if (value instanceof Number) {
					double oldValue = this.value;
					this.value = ((Number) value).doubleValue();

					if (oldValue != this.value) {
						fireStateChanged();
					}
				}
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public Object getPreviousValue() {
				return prevValue.apply(this.value);
			}

			@Override
			public Object getNextValue() {
				return nextValue.apply(this.value);
			}

		};
		final JSpinner spinner = new JSpinner(model);
		spinner.addChangeListener(evt -> {
			Object o = pool.get(parameter);
			if (o == null || !(o instanceof InputParameter)) {
				o = pool.put(parameter, new InputParameter(1));
			}
			InputParameter ip = (InputParameter) o;
			try {
				ip.setValue((double) spinner.getValue());
			} catch (ClassCastException e) {
				e.printStackTrace();
			}

		});
		NumberEditor editor = new NumberEditor(spinner, "0.0000E00");
		spinner.setEditor(editor);
		editor.getTextField().setColumns(8);

		spinner.setValue(pool.get(parameter).compute(pool));

		top.add(Box.createVerticalStrut(8));
		top.add(label);
		top.add(spinner);
		top.add(Box.createVerticalStrut(8));

		this.add(top);
	}

}
