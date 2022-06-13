package com.sang.prosangserver.entities.lyric;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sang.prosangserver.enums.lyric.LyricStatuses;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sang.prosangserver.entities.account.Account;

import lombok.Data;

@Data
@Entity
@Table(name = "lyric")
public class Lyric {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column
	private Double stars;
	
	@Column
	private String description;

	@Column
	private LyricStatuses status = LyricStatuses.PUBLISH;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToMany(mappedBy = "lyric", cascade = CascadeType.ALL)
	private List<LyricAnonymousComment> lyricAnonymousComments = new ArrayList<>();

	@OneToMany(mappedBy = "lyric", cascade = CascadeType.ALL)
	private List<LyricComment> lyricComments = new ArrayList<>();

	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public List<LyricComment> getValidComments() {
		return this.lyricComments.stream().filter(lyric -> !lyric.getIsDeleted()).collect(Collectors.toList());
	}
}
