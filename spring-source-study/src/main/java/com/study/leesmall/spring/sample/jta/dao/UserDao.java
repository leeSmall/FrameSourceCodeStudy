package com.study.leesmall.spring.sample.jta.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.study.leesmall.spring.sample.jta.entity.User;

@Component
public class UserDao {
	@Autowired
	@Qualifier("jdbcTemplate1")
	private JdbcTemplate jdbcTemplate;

	public void insert(User u) {
		jdbcTemplate.update("insert t_user(id,user_name) values(?,?)", u.getId(), u.getUserName());
	}

	public void update(User u) {
		jdbcTemplate.update("update t_user set user_name = ? where id= ?", u.getUserName(), u.getId());
	}

	public void delete(String id) {
		jdbcTemplate.update("delete from t_user where id= ?", id);
	}

	public User find(String id) {
		return jdbcTemplate.query("select id,user_name from t_user where id= ?", new Object[] { id },
				new ResultSetExtractor<User>() {

					@Override
					public User extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							User user = new User();
							user.setId(rs.getString(1));
							user.setUserName(rs.getString(2));
							return user;
						}
						return null;
					}

				});
	}

}
