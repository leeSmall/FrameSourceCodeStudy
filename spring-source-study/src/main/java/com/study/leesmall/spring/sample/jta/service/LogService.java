package com.study.leesmall.spring.sample.jta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.leesmall.spring.sample.jta.dao.LogDao;
import com.study.leesmall.spring.sample.jta.entity.Log;

@Service
public class LogService {

	@Autowired
	private LogDao logDao;

	@Transactional
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	// @Transactional(propagation = Propagation.NESTED)
	public void insertLog(Log log) {
		this.logDao.insert(log);
	}

}
