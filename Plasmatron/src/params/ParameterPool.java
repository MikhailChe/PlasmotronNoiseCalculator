package params;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterPool implements Map<Parameter, ComputableParameter> {
	private Map<Parameter, ComputableParameter> pool = new HashMap<>();

	final private static ParameterPool commonPool = new ParameterPool();

	public static ParameterPool getCommonPool() {
		return commonPool;
	}

	@Override
	public void clear() {
		pool.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return pool.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return pool.containsValue(value);
	}

	@Override
	public Set<Entry<Parameter, ComputableParameter>> entrySet() {
		return pool.entrySet();
	}

	@Override
	public ComputableParameter get(Object key) {
		return pool.get(key);
	}

	@Override
	public boolean isEmpty() {
		return pool.isEmpty();
	}

	@Override
	public Set<Parameter> keySet() {
		return pool.keySet();
	}

	@Override
	public ComputableParameter put(Parameter key, ComputableParameter value) {
		return pool.put(key, value);
	}

	@Override
	public void putAll(Map<? extends Parameter, ? extends ComputableParameter> m) {
		pool.putAll(m);
	}

	@Override
	public ComputableParameter remove(Object key) {
		return pool.remove(key);
	}

	@Override
	public int size() {
		return pool.size();
	}

	@Override
	public Collection<ComputableParameter> values() {
		return pool.values();
	}

}
