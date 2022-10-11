package com.sang.prosangserver.entities.lyric;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "lyric_comment")
public class LyricComment {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Double stars;

	@Column(nullable = false, columnDefinition = "text")
	private String content;

	@Column(nullable = false)
	private Long accountId;

	@Column
	private Long likes = 0L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lyric_id")
	private Lyric lyric;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	private List<LyricReplyComment> replyComments = new ArrayList<>();

	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;
}
