package com.sourcecodestudy.spring.samples;

public interface Driver {

	void start();

	default void stop() {

	}
}
