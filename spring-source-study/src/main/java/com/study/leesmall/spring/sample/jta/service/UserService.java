package com.study.leesmall.spring.sample.jta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.study.leesmall.spring.sample.jta.dao.UserDao;
import com.study.leesmall.spring.sample.jta.entity.Log;
import com.study.leesmall.spring.sample.jta.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private LogService logService;

	@Transactional
	public User insertUser(User u) {

		this.userDao.insert(u);
		Log log = new Log(System.currentTimeMillis() + "", System.currentTimeMillis() + "-" + u.getUserName());
		this.logService.insertLog(log);

		return this.userDao.find(u.getId());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void doAddUser(User u) {
		this.userDao.insert(u);
	}

	@Transactional()
	public User updateUser(User u) {
		this.userDao.update(u);
		return this.userDao.find(u.getId());
	}

	public User findById(String id) {
		System.err.println("根据id=" + id + "获取用户对象，从数据库中获取");
		return this.userDao.find(id);
	}

	public void deleteById(String id) {
		this.userDao.delete(id);
	}
}
