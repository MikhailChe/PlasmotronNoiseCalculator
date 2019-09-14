package ltf.plasmotron.swing;

import ltf.plasmotron.PlasmotronFiller2;
import params.Parameter;
import params.ParameterPool;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame {
    public GUI() {
        super();
        JPanel content = new JPanel();

        content.setLayout(new BorderLayout(8, 8));

        ParameterPool pool = ParameterPool.getCommonPool();
        PlasmotronFiller2.fillPool(pool);
        final Painter painter = new Painter(pool, Parameter.of("L").sub("p"), Parameter.of("f"),
                2.5 * 1_000, 80. * 1_000);

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
