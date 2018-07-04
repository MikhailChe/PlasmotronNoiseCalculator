package ltf.plasmotron;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

import java.util.Map;
import java.util.stream.IntStream;

import params.ComputableParameter;
import params.InputParameter;
import params.Parameter;

public class PlasmotronFiller {

	public static void fillPool(final Map<Parameter, ComputableParameter> pool) {
		pool.put(Parameter.of("S").sub(1), new InputParameter(75E-6));
		pool.put(Parameter.of("S").sub(2), new InputParameter(12.15E-6));
		pool.put(Parameter.of("l").sub(1), new InputParameter(5E-3));
		pool.put(Parameter.of("l").sub(2), new InputParameter(4.5E-3));

		pool.put(Parameter.GREEK_SMALL_RHO, new InputParameter(3.0));
		pool.put(Parameter.of("c"), new InputParameter(340.0));

		pool.put(Parameter.of("V").sub(1), new InputParameter(11.07E-7));
		pool.put(Parameter.of("V").sub(2), new InputParameter(10.56E-7));
		pool.put(Parameter.of("F").sub(0), new InputParameter(1));// TODO: Вставить настоящее число. А вообще, оно
																	// влияет только на
		// масштаб
		pool.put(Parameter.of("P").sub(0), new InputParameter(1E-12));

		IntStream.of(1, 2).forEach(massindex -> {

			pool.put(Parameter.of("l").sub(massindex + "эф"), (p) -> {
				double square = p.get(Parameter.of("S").sub(massindex)).compute(p);
				double l = p.get(Parameter.of("l").sub(massindex)).compute(p);
				return l + Math.sqrt(square) * .8;
			});

			pool.put(Parameter.GREEK_SMALL_OMEGA.sub(massindex), (p) -> {
				double K = p.get(Parameter.of("K").sub(massindex)).compute(p);
				double m = p.get(Parameter.of("m").sub(massindex)).compute(p);
				return Math.sqrt(K / m);
			});

			pool.put(Parameter.of("m").sub(massindex), (p) -> {
				double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);
				double square = p.get(Parameter.of("S").sub(massindex)).compute(p);
				double l = p.get(Parameter.of("l").sub(massindex + "эф")).compute(p);
				return rho * square * l;
			});

		});

		pool.put(Parameter.of("K").sub(1), (p) -> {
			double K1_1 = p.get(Parameter.of("K").sub(1).sup(1)).compute(p);
			double K_2 = p.get(Parameter.of("K").sub(2)).compute(p);
			return K1_1 + K_2;
		});
		pool.put(Parameter.of("K").sub(2), (p) -> {
			double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);
			double c = p.get(Parameter.of("c")).compute(p);
			double S_2 = p.get(Parameter.of("S").sub(2)).compute(p);
			double V_2 = p.get(Parameter.of("V").sub(2)).compute(p);

			return rho * c * c * S_2 * S_2 / V_2;
		});
		pool.put(Parameter.of("K").sub(1).sup(1), (p) -> {
			double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);
			double c = p.get(Parameter.of("c")).compute(p);
			double S_1 = p.get(Parameter.of("S").sub(1)).compute(p);
			double V_1 = p.get(Parameter.of("V").sub(1)).compute(p);

			return rho * c * c * S_1 * S_1 / V_1;
		});

		pool.put(Parameter.of("K").sub(2).sup(1), (p) -> {
			double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);
			double c = p.get(Parameter.of("c")).compute(p);
			double S_1 = p.get(Parameter.of("S").sub(1)).compute(p);
			double V_2 = p.get(Parameter.of("V").sub(2)).compute(p);

			return rho * c * c * S_1 * S_1 / V_2;
		});
		pool.put(Parameter.of("P").sub(10), (p) -> {
			double F_0 = p.get(Parameter.of("F").sub(0)).compute(p);
			double S_2 = p.get(Parameter.of("S").sub(2)).compute(p);
			return F_0 / S_2;
		});

		pool.put(Parameter.of("L").sub("p"), (p) -> {
			double K_2_1 = p.get(Parameter.of("K").sub(2).sup(1)).compute(p);
			double P_10 = p.get(Parameter.of("P").sub(10)).compute(p);
			double S_2 = p.get(Parameter.of("S").sub(2)).compute(p);

			double m_1 = p.get(Parameter.of("m").sub(1)).compute(p);
			double m_2 = p.get(Parameter.of("m").sub(2)).compute(p);

			double P_0 = p.get(Parameter.of("P").sub(0)).compute(p);

			double omega = p.get(Parameter.GREEK_SMALL_OMEGA).compute(p);
			double omega_1 = p.get(Parameter.GREEK_SMALL_OMEGA.sub(1)).compute(p);
			double omega_2 = p.get(Parameter.GREEK_SMALL_OMEGA.sub(2)).compute(p);

			double rho = p.get(Parameter.GREEK_SMALL_RHO).compute(p);

			double c = p.get(Parameter.of("c")).compute(p);

			double term1 = (K_2_1 * P_10 * P_10 * S_2) / (m_1 * m_2 * P_0);
			double term2 = Math.abs((sqr(omega) - sqr(omega_2)) / (sqr(omega) - sqr(omega_1)));
			double term3 = 4.0 * sqr(sqr(omega) - sqr(omega_1)); /// Здесь кажется ошибка в формуле 4.41. omega_1
																	/// исправлено мной на omega_2. Так результаты
																	/// больше огласуются
			double term4 = (sqr(rho) * pow(S_2, 4) * pow(omega, 6)) / (4.0 * sqr(PI) * sqr(c) * sqr(m_2));
			return 10.0 * Math.log10(term1 * term2 / (term3 + term4));

		});

	}

	private static double sqr(double v) {
		return v * v;
	}

}
