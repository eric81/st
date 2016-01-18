package com.eudemon.taurus.app.dao;

import java.io.InputStream;
import java.util.List;

import com.eudemon.taurus.app.entity.User;

public interface UserDao extends BaseDao<User>{
	public List<User> queryList();
	
	public List<User> queryList(int start, int size);
	
	public int count();
	
	public User queryByName(String userName);
	
	public byte[] queryPhoto(int id);

	public void updatePhoto(int id, InputStream inputStream, long size);
}
