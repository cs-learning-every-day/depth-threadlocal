package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
public class StatController {
	static HashSet<Val<Integer>> set = new HashSet<>();

	synchronized static void addSet(Val<Integer> v) {
		set.add(v);
	}

	static ThreadLocal<Val<Integer>> c = new ThreadLocal<>() {
		@Override
		protected Val<Integer> initialValue() {
			Val<Integer> initVal = new Val<>();
			initVal.set(0);
			addSet(initVal);
			return initVal;
		}
	};

	private void _add() throws InterruptedException {
		Thread.sleep(100);
		Val<Integer> val = c.get();
		val.set(val.get() + 1);
	}

	@GetMapping("/add")
	public void add() throws InterruptedException {
		this._add();
	}

	@GetMapping("/stat")
	public Integer stat() {
		return set.stream()
				.map(v -> v.get())
				.reduce((a, x) -> a + x).orElse(null);
	}
}
