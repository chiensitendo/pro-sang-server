package com.sang.prosangserver.models;

import com.sang.prosangserver.enums.lyric.LyricStatuses;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NamedNativeQuery(
    name = "lyric_detail_query",
    query = "select " +
        "l.id, l.account_id, a3.email, a3.username, d3.account_photo_url as photo_url, " +
        "concat(d3.first_name,' ', d3.last_name) as name, l.is_deleted, l.content, l.rate, l.status, l.created_date, l.updated_date, l.title, l.description, l.composers, l.singers, l.owners " +
        " " +
        ", ( select to_json(array_agg(row_to_json(t))) " +
        "               from (select c.id, " +
        "                        json_build_object('id', c.account_id, 'username', a.username, 'email', a.email,'photo_url', ad.account_photo_url, 'name', concat(ad.first_name,' ',ad.last_name)) as user_info, " +
        "                        c.stars, c.content, c.likes, c.updated_date, c.created_date, " +
        "                        ((select l.id from lyric_account_like l  where l.comment_id = c.id and l.account_id = :accountId and l.reply_id is null and l.is_deleted = false) is not null) as is_liked, " +
        "                        (select count(*) from lyric_reply_comment lr where lr.comment_id = c.id and lr.is_deleted = false) as total_replied_count " +
        "                        from lyric_comment c " +
        "                        join account a on a.id = c.account_id " +
        "                        join account_detail ad on a.id = ad.account_id " +
        "                        where c.lyric_id = l.id and c.is_deleted = false order by c.created_date desc limit :limit) t ) gw " +
        ", (select count(*) from lyric_comment c where c.lyric_id = l.id and c.is_deleted = false) as total " +
        ", (select avg(l2.stars) from lyric l2 where l2.account_id = l.account_id and l2.is_deleted = false) as average_rate " +
        ", (select count(*) from lyric l2 where l2.account_id = l.account_id  and l2.is_deleted = false) as lyric_number " +
        ", (l.rate_account_list like :likeAccountId) as is_rated " +
        "from lyric l " +
        "join public.account a3 on a3.id = l.account_id " +
        "join account_detail d3 on a3.id = d3.account_id " +
        "where l.id = :lyricId and l.is_deleted = false and ((:accountId = -1 and l.status = 1) or (:accountId != -1))" +
        "group by l.id, l.account_id, a3.email, a3.username, d3.account_photo_url, name, l.is_deleted, l.content, l.stars, l.status, l.created_date, l.updated_date, l.title, l.description;",
    resultClass = LyricDetailModel.class
)
public class LyricDetailModel implements Serializable {

    @Id
    @Column
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column
    private Double rate;
    @Column(columnDefinition = "text")
    private String description;
    @Column
    private LyricStatuses status = LyricStatuses.PUBLISH;
    @Column
    private Long accountId;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String photoUrl;
    @Column
    private String name;
    @Column
    private Boolean isDeleted = false;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private Long total;
    @Column
    private Float averageRate;
    @Column
    private Long lyricNumber;
    @Column(columnDefinition = "text")
    private String gw;
    @Column
    private Boolean isRated;
    @Column
    private String composers;
    @Column
    private String singers;
    @Column
    private String owners;
}
