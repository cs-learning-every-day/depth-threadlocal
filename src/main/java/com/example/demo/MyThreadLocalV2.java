package com.example.demo;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class MyThreadLocalV2<T> {

	private static final AtomicInteger atomic = new AtomicInteger();

	private Integer threadLocalHash = atomic.getAndAdd(0x61c88647);

	private static final HashMap<Thread, HashMap<Integer, Object>> threadLocalMap = new HashMap<>();

	static synchronized HashMap<Integer, Object> getMap() {
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
		if (!map.containsKey(this.threadLocalHash)) {
			map.put(this.threadLocalHash, initialValue());
		}
		return (T) map.get(this.threadLocalHash);
	}

	public void set(T v) {
		var map = getMap();
		map.put(threadLocalHash, v);
	}
}
