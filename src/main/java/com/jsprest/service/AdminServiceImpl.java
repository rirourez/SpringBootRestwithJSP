package com.jsprest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprest.entity.Admin;
import com.jsprest.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
    AdminRepository adminRepo;

	@Override
	public Admin findByEmail(String email) {		
		return adminRepo.findByEmail(email);
	}
}
