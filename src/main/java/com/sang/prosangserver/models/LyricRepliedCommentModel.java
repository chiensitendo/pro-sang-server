package com.sang.prosangserver.models;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NamedNativeQuery(
    name = "lyric_replied_comment_query",
    query = "select " +
        "rc.id, rc.comment_id, rc.is_deleted, rc.account_id, rc.content, rc.likes, rc.updated_date, rc.created_date " +
        ", a.username, a.email, concat(d.first_name, ' ', d.last_name) as name, d.account_photo_url as photo_url " +
        ", ((select l.id from lyric_account_like l where l.comment_id = rc.comment_id and l.reply_id = rc.id and l.account_id = :accountId and l.is_deleted = false) is not null) as is_liked " +
        "from lyric_reply_comment rc " +
        "join account a on rc.account_id = a.id " +
        "join account_detail d on d.account_id = rc.account_id " +
        "where rc.is_deleted = false and rc.comment_id = :commentId order by rc.created_date desc offset :offset limit :limit",
    resultClass = LyricRepliedCommentModel.class
)
public class LyricRepliedCommentModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(nullable = false)
    private Long accountId;
    @Column
    private Long likes = 0L;
    @Column
    private Boolean isDeleted = false;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String photoUrl;
    @Column
    private Boolean isLiked;
}
