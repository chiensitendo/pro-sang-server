package com.sang.prosangserver.entities.lyric;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

public class LyricComment {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Double stars;
		
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private Long accountId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lyric_id")
	private Lyric lyric;

	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;
}
