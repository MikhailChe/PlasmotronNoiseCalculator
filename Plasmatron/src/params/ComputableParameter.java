package params;

import java.util.Map;

@FunctionalInterface
public interface ComputableParameter {
	public double compute(Map<Parameter, ComputableParameter> input);

}
