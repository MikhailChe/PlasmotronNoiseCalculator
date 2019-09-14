package ltf.plasmotron.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.sun.javafx.tools.packager.Param;
import params.Parameter;
import params.ParameterPool;

public class JParamChanger extends JPanel {
    public JParamChanger(final ParameterPool pool) {
        JPanel top = new JPanel();
        BoxLayout box = new BoxLayout(top, BoxLayout.PAGE_AXIS);
        top.setLayout(box);


        List<Parameter> changeableParameter = new ArrayList<>();
        for (Parameter param : pool.keySet()) {
            if (param.equals(Parameter.of("L").sub("p")) || param.equals(Parameter.of("f"))) {
                continue;
            }
            changeableParameter.add(param);
        }

        for (Parameter p : changeableParameter) {
            ButtonBundle bb = new ButtonBundle(p, pool, (d) -> d * .9, (d) -> d / .9);
            top.add(bb);
        }

        add(top);
    }
}
