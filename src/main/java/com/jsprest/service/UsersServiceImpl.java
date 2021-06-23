package com.jsprest.service;

import com.jsprest.entity.User;
import com.jsprest.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository userRepo;

    public User saveOrUpdate(User users) {
        return userRepo.save(users);
    }


    public void delete(User users) {
    	if (users == null)					return;
		userRepo.deleteById(users.getUserId());
        userRepo.delete(users);
    }


	@Override
	public long countusers() {
		return userRepo.count();	}

	@Override
	public User getUser(Integer id) {
		if (id == null)					return null;
		Optional<User> o = userRepo.findById(id);
		if (o == null || o.isEmpty())		return null;
		return (User) o.get();
	}

public List<User> getUsers() {		
		List<User> u = userRepo.findAll();
		return u;
}
	
	@Override
	public User save(User u) {
		if (u == null)					return null;
		return userRepo.save(u);
	}

	@Override
	public List<User> list() {
		List<User> u = userRepo.findAll();
		return u;
	}
}