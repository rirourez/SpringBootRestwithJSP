package com.jsprest.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="projects")
public class Project implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String name;

    @Lob
    private String description;

    @CreationTimestamp
    @ReadOnlyProperty
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;


    private boolean status;
    
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    @ReadOnlyProperty
    private Customer customer;


    public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Long getProjectId() {
        return projectId;
    }


    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDate getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }


    public boolean isStatus() {
        return status;
    }
    
    public String getStatusStr() {
        if (status)		return "ACTIVE";
    	return "INACTIVE";
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


	@Override
	public String toString() {
		return projectId + "";
	}


}
