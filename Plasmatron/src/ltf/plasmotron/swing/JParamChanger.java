package ltf.plasmotron.swing;

import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import params.Parameter;
import params.ParameterPool;

public class JParamChanger extends JPanel {
	public JParamChanger(final ParameterPool pool) {
		JPanel top = new JPanel();
		BoxLayout box = new BoxLayout(top, BoxLayout.PAGE_AXIS);
		top.setLayout(box);

		List<Parameter> changeableParameter = Arrays.asList(Parameter.of("S").sub(1), Parameter.of("S").sub(2),
				Parameter.of("l").sub(1), Parameter.of("l").sub(2), Parameter.GREEK_SMALL_RHO, Parameter.of("c"),
				Parameter.of("V").sub(1), Parameter.of("V").sub(2), Parameter.of("F").sub(0), Parameter.of("P").sub(0));

		for (Parameter p : changeableParameter) {
			ButtonBundle bb = new ButtonBundle(p, pool, (d) -> d * .9, (d) -> d / .9);
			top.add(bb);
		}

		add(top);
	}
}
