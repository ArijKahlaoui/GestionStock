package com.sip.GS.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


@Entity
@Table(name =  "user")
public class User {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "email")
	 @Email(message = "*Please provide a valid Email")
	 @NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	
	@NotEmpty(message = "*Please provide your first name")
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	
	
	
	@Column(name = "active")
	 private int active;

	
	 @ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name ="user_role", joinColumns=@JoinColumn(name= "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	 private Set<Role> roles;
	 //Set<>() est un type de collection qui n'accepte pas la repetition des attribus
	 
	public User() {
		super();
	}
	


	public User(int id,
			@Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email,
			@Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password,
			@NotEmpty(message = "*Please provide your first name") String firstName,
			@NotEmpty(message = "*Please provide your last name") String lastName, int active,
			Set<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.roles = roles;
	}



	public User(@NotEmpty(message = "*Please provide your first name") String firstName,
			@NotEmpty(message = "*Please provide your last name") String lastName,
			@Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email,
			@Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password,
			int active, Set<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	



	public int getActive() {
		return active;
		}
		public void setActive(int active) {
		this.active = active;
		}

	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	
}
