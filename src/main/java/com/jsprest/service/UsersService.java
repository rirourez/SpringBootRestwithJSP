package com.jsprest.service;

import com.jsprest.entity.User;

import java.util.List;

public interface UsersService {
	
    User saveOrUpdate(User u);

    void delete(User u);
    
    public long countusers();
	
	
	public User getUser(Integer id);
	
	public User save(User u);
	
	public List<User> list();
	
}