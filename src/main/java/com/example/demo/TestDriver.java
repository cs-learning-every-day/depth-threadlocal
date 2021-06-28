package com.example.demo;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TestDriver {
	static MyThreadLocalV2<Long> v = new MyThreadLocalV2<>() {
		@Override
		protected Long initialValue() {
			return Thread.currentThread().getId();
		}
	};

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				System.out.println(v.get());
			}).start();
		}
	}
}
