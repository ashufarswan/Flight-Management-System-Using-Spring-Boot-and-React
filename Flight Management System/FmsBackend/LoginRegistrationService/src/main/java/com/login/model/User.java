package com.login.model;

import java.util.Date;

import javax.xml.crypto.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="user")
public class User {

	@Id
	private String id;
	
	@NotBlank(message = "Please enter user name")
	private String name;
	
	@Email(message = "Please enter valid email")
	private String email;
	
	@NotBlank(message = "Enter Password")
	private String password;
	
	@NotBlank(message = "Enter Phone Number")
	@Size(max=10, min=10,message = "Phone number must be of 10 digits")
	private String phone;
	private String role;
	
	@CreatedDate
	private Date createdAt;
	
	@LastModifiedDate
	private Date updatedAt;
	
}
