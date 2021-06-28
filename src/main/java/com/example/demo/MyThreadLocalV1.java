package com.example.demo;

import java.util.HashMap;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class MyThreadLocalV1<T> {


	private static HashMap<Thread, HashMap<MyThreadLocalV1<?>, Object>>
			threadLocalMap = new HashMap<>();

	synchronized static HashMap<MyThreadLocalV1<?>, Object> getMap() {
		Thread thread = Thread.currentThread();
		if (!threadLocalMap.containsKey(thread)) {
			threadLocalMap.put(thread, new HashMap<>());
		}
		return threadLocalMap.get(thread);
	}

	protected T initialValue() {
		return null;
	}

	public T get() {
		var map = getMap();
		if (!map.containsKey(this)) {
			map.put(this, initialValue());
		}
		return (T) map.get(this);
	}

	public void set(T v) {
		var map = getMap();
		map.put(this, v);
	}
}
