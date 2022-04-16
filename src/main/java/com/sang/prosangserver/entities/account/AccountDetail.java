package com.sang.prosangserver.entities.account;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "account_detail")
public class AccountDetail {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String firstName;
	
	@Column
	private String middleName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column
	private String address;
	
	@Column
	private Integer country;
	
	@Column
	private Integer stateOrProvince;
	
	@Column
	private String website;
	
	@Column
	private String phone;
	
	@Column
	private String description;
	
	@Column
	private LocalDate birthday;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_Account_Detail_Account_id"))
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
