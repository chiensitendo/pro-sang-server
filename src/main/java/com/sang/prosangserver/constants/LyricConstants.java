package com.sang.prosangserver.constants;

public class LyricConstants {

    public static final String LYRIC_INFO_FULL_SQL = "select " +
        "l.id, l.account_id, a3.email, a3.username, d3.account_photo_url as photo_url, " +
        "concat(d3.first_name,' ', d3.last_name) as name, l.is_deleted, l.content, l.stars, l.status, l.created_date, l.updated_date, l.title, l.description " +
        " " +
        ", ( select to_json(array_agg(row_to_json(t))) " +
        "               from (select c.id, " +
        "                        json_build_object('id', c.account_id, 'username', a.username, 'email', a.email,'photo_url', ad.account_photo_url, 'name', concat(ad.first_name,' ',ad.last_name)) as user_info, " +
        "                        c.stars, c.content, c.likes, c.updated_date, c.created_date, " +
        "                        (select to_json(array_agg(row_to_json(r))) " +
        "                         from (select rp.id, rp.likes, rp.content, rp.created_date, rp.updated_date, " +
        "                               json_build_object('id', rp.account_id, 'username', a2.username, 'email', a2.email,'photo_url', d.account_photo_url, 'name', concat(d.first_name,' ',d.last_name)) as user_info " +
        "                               from lyric_reply_comment rp " +
        "                               join account a2 on a2.id = rp.account_id " +
        "                               join account_detail d on a2.id = d.account_id " +
        "                               where rp.comment_id = c.id and rp.is_deleted = false order by rp.created_date asc) r " +
        "                         ) as replied_items " +
        "                        from lyric_comment c " +
        "                        join account a on a.id = c.account_id " +
        "                        join account_detail ad on a.id = ad.account_id " +
        "                        where c.lyric_id = l.id and c.is_deleted = false order by c.created_date asc limit 50) t ) gw " +
        ", (select count(*) from lyric_comment c where c.lyric_id = l.id and c.is_deleted = false) as total " +
        "from lyric l " +
        "join public.account a3 on a3.id = l.account_id " +
        "join account_detail d3 on a3.id = d3.account_id " +
        "where l.id = :lyricId and l.is_deleted = false " +
        "group by l.id, l.account_id, a3.email, a3.username, d3.account_photo_url, name, l.is_deleted, l.content, l.stars, l.status, l.created_date, l.updated_date, l.title, l.description;";

    public static final Integer MAX_COMMENT_PER_PAGE = 5;

    public static final Integer MAX_REPLY_PER_COMMENT = 5;

    public static final Integer MAX_ITEM_PER_PAGE = 20;

    public static final String DEFAULT_AVATAR_URL = "https://pickaface.net/gallery/avatar/unr_sample_161118_2054_ynlrg.png";
}
