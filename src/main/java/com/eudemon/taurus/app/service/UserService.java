package com.eudemon.taurus.app.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eudemon.taurus.app.dao.UserDao;
import com.eudemon.taurus.app.entity.PagedEntity;
import com.eudemon.taurus.app.entity.User;

@Component
@Transactional(readOnly = true)
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User getUserById(int id) {
		User user = null;
		try {
			user = userDao.query(id);
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}
	
	public User getUserByName(String name) {
		User user = null;
		try {
			user = userDao.queryByName(name);
		} catch (EmptyResultDataAccessException e) {
		}
		return user;
	}
	
	public List<User> getAllUser() {
		List<User> rs = userDao.queryList();
		
		return rs;
	}

	public PagedEntity<User> getPagedUserList(int pageNo, int pageSize) {
		int start = (pageNo - 1) * pageSize;
		int size = pageSize;
		List<User> userList = userDao.queryList(start, size);
		int count = userDao.count();
		
		PagedEntity<User> rs = new PagedEntity<User>(pageNo, pageSize, count, userList);
		return rs;
	}
	
	@Transactional(readOnly = false)
	public void registerUser(User user) {
		user.setRoles("user");
		user.setPermissions("read");
		user.setRegisterDate(new Timestamp(new Date().getTime()));

		long id = userDao.save(user);
		user.setId((int) id);
	}
	
	@Transactional(readOnly = false)
	public boolean delete(long id){
		return userDao.delete(id);
	}
	
	@Transactional(readOnly = false)
	public boolean modify(int id, String role) {
		User user = userDao.query(id);
		user.setRoles(role);
		boolean rs = userDao.update(user);
		return rs;
	}
	
	@Transactional(readOnly = false)
	public void updatePhoto(int id, MultipartFile file) {
		try {
			userDao.updatePhoto(id, file.getInputStream(), file.getSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getPhoto(int id){
		try {
			byte[] rs = userDao.queryPhoto(id);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
