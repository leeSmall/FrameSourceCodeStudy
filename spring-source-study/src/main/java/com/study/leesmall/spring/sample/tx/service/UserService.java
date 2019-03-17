package com.study.leesmall.spring.sample.tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.study.leesmall.spring.sample.tx.dao.LogDao;
import com.study.leesmall.spring.sample.tx.dao.UserDao;
import com.study.leesmall.spring.sample.tx.entity.Log;
import com.study.leesmall.spring.sample.tx.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private LogDao logDao;

	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	private LogService logService;

	@Transactional
	public User insertUser(User u) {

		// // 1、创建事务定义
		// TransactionDefinition definition = new
		// DefaultTransactionDefinition();
		// // 2、根据定义开启事务
		// TransactionStatus status = txManager.getTransaction(definition);
		//
		// try {

		this.userDao.insert(u);
		Log log = new Log(System.currentTimeMillis() + "", "-" + u.getUserName());
		// this.doAddUser(u);
		// this.logDao.insert(log);
		this.logService.insertLog(log);
		// 3、提交事务
		// txManager.commit(status);
		// } catch (Exception e) {
		// // 4、异常了，回滚事务
		// txManager.rollback(status);
		// throw e;
		// }

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
