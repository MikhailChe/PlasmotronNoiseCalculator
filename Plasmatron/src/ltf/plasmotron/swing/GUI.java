package ltf.plasmotron.swing;

import static java.lang.Math.PI;

import java.awt.BorderLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ltf.plasmotron.PlasmatronFiller;
import params.Parameter;
import params.ParameterPool;

public class GUI extends JFrame {
	public GUI() {
		super();
		JPanel content = new JPanel();

		content.setLayout(new BorderLayout(8, 8));

		ParameterPool pool = ParameterPool.getCommonPool();
		PlasmatronFiller.fillPool(pool);
		final Painter painter = new Painter(pool, Parameter.of("L").sub("p"), Parameter.GREEK_SMALL_OMEGA,
				100.0 * 2.0 * PI, 16_000.0 * 2.0 * PI);

		content.add(painter);
		content.add(new JParamChanger(pool), BorderLayout.WEST);

		setContentPane(content);
		new Thread(() -> {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				painter.repaint();
			}
		}).start();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.pack();
		gui.setVisible(true);

	}
}
