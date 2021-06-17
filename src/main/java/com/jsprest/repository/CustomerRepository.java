package com.jsprest.repository;

import com.jsprest.entity.Customer;
import com.jsprest.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
