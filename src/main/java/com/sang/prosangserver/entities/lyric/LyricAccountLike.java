package com.sang.prosangserver.entities.lyric;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lyric_account_like")
public class LyricAccountLike {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long commentId;

    @Column
    private Long replyId;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
