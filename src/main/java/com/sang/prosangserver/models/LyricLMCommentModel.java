package com.sang.prosangserver.models;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.time.LocalDateTime;


@Data
@Entity
@NamedNativeQuery(
    name = "lyric_load_more_comment_query",
    query = "select c.id, " +
        "    json_build_object('id', c.account_id, 'username', a.username, 'email', a.email,'photo_url', ad.account_photo_url, 'name', concat(ad.first_name,' ',ad.last_name)) as user_info_json, " +
        "    c.stars, c.content, c.likes, c.updated_date, c.created_date, " +
        "    ((select l.id from lyric_account_like l  where l.comment_id = c.id and l.account_id = :accountId and l.reply_id is null and l.is_deleted = false) is not null) as is_liked, " +
        "    (select count(*) from lyric_reply_comment lr where lr.comment_id = c.id and lr.is_deleted = false) as total_replied_count " +
        "    from lyric_comment c " +
        "    join account a on a.id = c.account_id " +
        "    join account_detail ad on a.id = ad.account_id " +
        "    where c.lyric_id = :lyricId and c.is_deleted = false order by c.created_date desc offset :offset limit :limit",
    resultClass = LyricLMCommentModel.class
)
public class LyricLMCommentModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Double stars;

    @Column(columnDefinition = "text")
    private String content;

    @Column
    private Long likes;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Column
    private Boolean isLiked;

    @Column
    private Long totalRepliedCount;

    @Column
    private String userInfoJson;
}
