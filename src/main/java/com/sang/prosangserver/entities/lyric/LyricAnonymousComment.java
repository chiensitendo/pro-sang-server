package com.sang.prosangserver.entities.lyric;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "lyric_anonymous_comment")
public class LyricAnonymousComment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Double stars;
	
	@Column(nullable = false)
	private String commentName;
	
	@Column(nullable = false)
	private String content;
	
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
