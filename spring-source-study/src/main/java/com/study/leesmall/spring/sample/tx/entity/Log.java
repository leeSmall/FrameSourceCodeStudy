package com.study.leesmall.spring.sample.tx.entity;

import java.io.Serializable;

public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5575893900970589345L;

	private String id;

	private String log;

	public Log() {
	}

	public Log(String id, String log) {
		super();
		this.id = id;
		this.log = log;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
