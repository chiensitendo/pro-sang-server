package com.sang.prosangserver.entities;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sang.prosangserver.enums.Roles;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column
	private Roles role;
	
	@Column
	private Boolean isDeleted = false;
	
	@Column
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
}
