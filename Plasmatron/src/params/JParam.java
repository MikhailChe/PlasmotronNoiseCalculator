package params;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class JParam extends JComponent {

	public JParam(Parameter param) {
		super();
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel("<html>" + param.toHTML() + "</html>");
		label.setFont(new Font(Font.SERIF, Font.ITALIC, 16));
		this.add(label);
	}

}
