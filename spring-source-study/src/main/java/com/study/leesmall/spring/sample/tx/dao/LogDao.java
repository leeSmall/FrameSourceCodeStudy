package com.study.leesmall.spring.sample.tx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.study.leesmall.spring.sample.tx.entity.Log;

@Component
public class LogDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// @Insert("insert t_log(id,log) values(#{id},#{log})")
	public void insert(Log log) {
		jdbcTemplate.update("insert t_log(id,log) values(?,?)", log.getId(), log.getLog());
	}

}
