package com.sang.prosangserver.entities.account;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "account_auth")
public class AccountAuth {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String password;
	
	@Column
	private String token;
	
	@Column
	private String refreshToken;
	
	@Column
	private LocalDateTime tokenExpiredTime;
	
	@Column
	private LocalDateTime passwordExpiredTime;
	
	@Column
	private LocalDateTime refreshTokenExpiredTime;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_Account_Auth_Account_id"))
	private Account account;
	
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;
}
