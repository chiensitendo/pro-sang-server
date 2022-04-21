package com.sang.prosangserver.entities.account;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sang.prosangserver.entities.lyric.Lyric;
import com.sang.prosangserver.entities.lyric.LyricComment;
import com.sang.prosangserver.enums.Roles;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private Roles role;
	
	@Column
	private LocalDateTime lastLoginTime;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AccountAuth auth;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AccountDetail detail;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Lyric> lyrics = new ArrayList<Lyric>();
	
//	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private List<LyricComment> lyricComments = new ArrayList<>();
	
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
}
