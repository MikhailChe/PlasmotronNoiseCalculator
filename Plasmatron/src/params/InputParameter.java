package params;

import java.util.Map;

public class InputParameter implements ComputableParameter {

	volatile public double value;

	public InputParameter(double value) {
		setValue(value);
	}

	@Override
	public double compute(Map<Parameter, ComputableParameter> input) {
		// TODO Auto-generated method stub
		return value;
	}

	public double setValue(double value) {
		this.value = value;
		return value;
	}

}
