package com.jsprest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprest.entity.Customer;
import com.jsprest.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    CustomerRepository customRepo;
	
	@Override
	public List<Customer> getCustomers() {
		
		List<Customer> p = customRepo.findAll();
		return p;
	}
}
