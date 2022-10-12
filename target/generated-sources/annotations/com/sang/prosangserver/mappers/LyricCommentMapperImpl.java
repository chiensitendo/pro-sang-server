package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.entities.lyric.LyricAnonymousComment;
import com.sang.prosangserver.entities.lyric.LyricComment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-11T22:45:02+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class LyricCommentMapperImpl implements LyricCommentMapper {

    @Override
    public LyricCommentItem anonymousToLyricCommentItem(LyricAnonymousComment comment) {
        if ( comment == null ) {
            return null;
        }

        LyricCommentItem lyricCommentItem = new LyricCommentItem();

        lyricCommentItem.setUserInfo( setUsernameOfAnonymousComment( comment.getCommentName() ) );
        lyricCommentItem.setId( comment.getId() );
        lyricCommentItem.setContent( comment.getContent() );
        lyricCommentItem.setLikes( comment.getLikes() );
        lyricCommentItem.setStars( comment.getStars() );
        lyricCommentItem.setCreatedDate( comment.getCreatedDate() );
        lyricCommentItem.setUpdatedDate( comment.getUpdatedDate() );

        return lyricCommentItem;
    }

    @Override
    public LyricCommentItem toLyricCommentItem(LyricComment comment) {
        if ( comment == null ) {
            return null;
        }

        LyricCommentItem lyricCommentItem = new LyricCommentItem();

        lyricCommentItem.setUserInfo( setUserInfo( comment.getAccountId() ) );
        lyricCommentItem.setId( comment.getId() );
        lyricCommentItem.setContent( comment.getContent() );
        lyricCommentItem.setLikes( comment.getLikes() );
        lyricCommentItem.setStars( comment.getStars() );
        lyricCommentItem.setCreatedDate( comment.getCreatedDate() );
        lyricCommentItem.setUpdatedDate( comment.getUpdatedDate() );

        setIsAnonymousFalseIfLyricComment( comment, lyricCommentItem );

        return lyricCommentItem;
    }
}
