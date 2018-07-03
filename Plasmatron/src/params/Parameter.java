package params;

public final class Parameter {
	final String name;
	final Parameter sub;
	final Parameter sup;

	public Parameter(final String name) {
		this(name, null, null);
	}

	public static Parameter of(String name) {
		return new Parameter(name);
	}

	public Parameter(final String name, final Parameter sub, final Parameter sup) {
		this.name = name;
		this.sub = sub;
		this.sup = sup;
		if (this.name == null)
			throw new NullPointerException();
	}

	public Parameter sub(Object _sub) {
		return new Parameter(this.name, new Parameter(_sub == null ? null : _sub.toString()), this.sup);
	}

	public Parameter sub(Parameter _sub) {
		return new Parameter(this.name, _sub, this.sup);
	}

	public Parameter sup(Object _sup) {
		return new Parameter(this.name, this.sub, new Parameter(_sup == null ? null : _sup.toString()));
	}

	public Parameter sup(Parameter _sup) {
		return new Parameter(this.name, this.sub, _sup);
	}

	public boolean hasSubSup() {
		if (sup != null || sub != null)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sub == null) ? 0 : sub.hashCode());
		result = prime * result + ((sup == null) ? 0 : sup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Parameter))
			return false;
		Parameter other = (Parameter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sub == null) {
			if (other.sub != null)
				return false;
		} else if (!sub.equals(other.sub))
			return false;
		if (sup == null) {
			if (other.sup != null)
				return false;
		} else if (!sup.equals(other.sup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder(this.name);
		if (sub != null) {
			output.append("_");
			if (sub.hasSubSup()) {
				output.append("{" + sub.toString() + "}");
			} else {
				output.append(sub.toString());
			}
		}
		if (sup != null) {
			output.append("^");
			if (sup.hasSubSup()) {
				output.append("{" + sup.toString() + "}");
			} else {
				output.append(sup.toString());
			}
		}

		return output.toString();
	}

	public String toHTML() {
		StringBuilder output = new StringBuilder(this.name);
		if (sub != null) {
			output.append("<sub>");
			output.append(sub.toHTML());
			output.append("</sub>");
		}
		if (sup != null) {
			output.append("<sup>");
			output.append(sup.toHTML());
			output.append("</sup>");
		}

		return output.toString();

	}

	public final static Parameter GREEK_CAPITAL_OMEGA = Parameter.of("Ω");
	public final static Parameter GREEK_SMALL_OMEGA = Parameter.of("ω");
	public final static Parameter GREEK_SMALL_RHO = Parameter.of("ρ");
	public final static Parameter GREEK_SMALL_PI = Parameter.of("π");
	public final static Parameter GREEK_SMALL_LAMBDA = Parameter.of("λ");
	public final static Parameter GREEK_SMALL_MU = Parameter.of("μ");
	public final static Parameter GREEK_SMALL_NU = Parameter.of("ν");
}
