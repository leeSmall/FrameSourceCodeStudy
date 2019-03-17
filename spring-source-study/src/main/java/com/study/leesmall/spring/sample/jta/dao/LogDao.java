package com.study.leesmall.spring.sample.jta.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.study.leesmall.spring.sample.jta.entity.Log;

@Component
public class LogDao {

	@Autowired
	@Qualifier("jdbcTemplate2")
	private JdbcTemplate jdbcTemplate;

	public void insert(Log log) {
		jdbcTemplate.update("insert t_log(id,log) values(?,?)", log.getId(), log.getLog());
	}

}
