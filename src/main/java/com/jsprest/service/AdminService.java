package com.jsprest.service;

import org.springframework.stereotype.Service;

import com.jsprest.entity.Admin;

public interface AdminService {

	public Admin findByEmail(String email);

}
