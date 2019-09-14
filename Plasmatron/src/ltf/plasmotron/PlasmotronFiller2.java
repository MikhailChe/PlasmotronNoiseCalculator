package ltf.plasmotron;

import params.ComputableParameter;
import params.InputParameter;
import params.Parameter;

import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class PlasmotronFiller2 {

    public static void fillPool(final Map<Parameter, ComputableParameter> pool) {
        pool.put(Parameter.of("P").sub("10"), new InputParameter(1.0e5));
        pool.put(Parameter.of("a"), new InputParameter(0.25e-3));
        pool.put(Parameter.of("l"), new InputParameter(5e-3));
        pool.put(Parameter.of("P").sub("0"), new InputParameter(1.e-12)); //const
        pool.put(Parameter.GREEK_SMALL_RHO, new InputParameter(1.2)); //const
        pool.put(Parameter.of("c"), new InputParameter(340)); //const


        pool.put(Parameter.of("f"), new InputParameter(10000));
        pool.put(Parameter.of("L").sub("p"), (p) -> {

            double P_10 = p.get(Parameter.of("P").sub("10")).compute(p);
            double P_2_10 = P_10 * P_10;

            double a = p.get(Parameter.of("a")).compute(p);

            double l = p.get(Parameter.of("l")).compute(p);

            double f = p.get(Parameter.of("f")).compute(p);
            double c = p.get(Parameter.of("c")).compute(p);

            double k = 2 * Math.PI * f / c;


            double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);
            double P_0 = p.get(Parameter.of("P").sub("0")).compute(p);
            double lambda = c / f;
            double S = Math.PI * a * a;

            if (k * a < 1) {
                return 10.0 * Math.log10(
                        (
                                (P_2_10 * S) / (4. * rho * c * P_0)
                        ) * (
                                Math.sinh(k * k * a * a / 4.) / (
                                        Math.sinh(k * k * a * a / 8.) + Math.pow(
                                                (Math.sin(Math.PI * (
                                                        2. * k * a / (Math.PI * Math.PI) + 2 * l / lambda
                                                ))),
                                                2.
                                        )
                                )
                        )
                );
            } else {
                return 10.0 * Math.log10(
                        (
                                (P_2_10 * S) / (2. * rho * c * P_0)
                        ) * (
                                (
                                        Math.sinh(Math.log(Math.PI * k * a))
                                ) / (
                                        Math.pow(
                                                Math.sinh(
                                                        0.5 * Math.log(Math.PI * k * a)
                                                ),
                                                2.
                                        ) + Math.pow(
                                                Math.sin(
                                                        2. * Math.PI * l / lambda - Math.PI / 4.
                                                ),
                                                2.
                                        )
                                )
                        )
                );
            }
        });

    }

    private static double sqr(double v) {
        return v * v;
    }

}
