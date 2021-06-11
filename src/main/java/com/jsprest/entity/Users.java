package com.jsprest.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "user_name")
    private String user_name;
    
    @Column(name = "password")
    private String password;

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
    private String email;


    @Column(name = "phone")
    private String phone;


    @Column(name = "address")
    private String address;

    @Column(name = "etat")
    private Integer etat;

    public Integer getEtat() {
		return etat;
	}
    
    public String getEtatStr() {
    	if (etat == null)		return "";
    	if (etat == 0)			return "En attente";
    	if (etat == 1)			return "Actif";
    	if (etat == 2)			return "Bloqué";
		return etat+"";
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> role = new HashSet<>();


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

}