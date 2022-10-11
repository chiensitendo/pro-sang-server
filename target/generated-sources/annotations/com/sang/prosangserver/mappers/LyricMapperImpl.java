package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricEditDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricInfoDetailResponse;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentBaseItem;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import com.sang.prosangserver.entities.lyric.Lyric;
import com.sang.prosangserver.models.LyricDetailModel;
import com.sang.prosangserver.models.LyricLMCommentModel;
import com.sang.prosangserver.models.LyricRepliedCommentModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-11T22:45:02+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class LyricMapperImpl extends LyricMapper {

    @Override
    public Lyric lyricRequestToLyricEntity(LyricRequest request) {
        if ( request == null ) {
            return null;
        }

        Lyric lyric = new Lyric();

        lyric.setTitle( request.getTitle() );
        lyric.setContent( request.getContent() );
        lyric.setComposers( request.getComposers() );
        lyric.setSingers( request.getSingers() );
        lyric.setOwners( request.getOwners() );
        lyric.setDescription( request.getDescription() );
        if ( request.getStatus() != null ) {
            lyric.setStatus( map( request.getStatus().intValue() ) );
        }

        return lyric;
    }

    @Override
    public LyricDetailResponse toLyricDetail(Lyric lyric) {
        if ( lyric == null ) {
            return null;
        }

        LyricDetailResponse lyricDetailResponse = new LyricDetailResponse();

        lyricDetailResponse.setComments( toComments( lyric ) );
        lyricDetailResponse.setId( lyric.getId() );
        lyricDetailResponse.setTitle( lyric.getTitle() );
        lyricDetailResponse.setContent( lyric.getContent() );
        lyricDetailResponse.setDescription( lyric.getDescription() );
        lyricDetailResponse.setStars( lyric.getStars() );
        lyricDetailResponse.setStatus( lyric.getStatus() );
        lyricDetailResponse.setIsDeleted( lyric.getIsDeleted() );
        lyricDetailResponse.setCreatedDate( lyric.getCreatedDate() );
        lyricDetailResponse.setUpdatedDate( lyric.getUpdatedDate() );

        return lyricDetailResponse;
    }

    @Override
    public LyricInfoDetailResponse lyricDetailModelToLyricInfoDetailResponse(LyricDetailModel model) {
        if ( model == null ) {
            return null;
        }

        LyricInfoDetailResponse lyricInfoDetailResponse = new LyricInfoDetailResponse();

        lyricInfoDetailResponse.setAccountInfo( toLyricAccountInfo( model ) );
        lyricInfoDetailResponse.setComment( toLyricComment( model ) );
        lyricInfoDetailResponse.setCurrentOwners( model.getOwners() );
        lyricInfoDetailResponse.setId( model.getId() );
        lyricInfoDetailResponse.setTitle( model.getTitle() );
        if ( model.getRate() != null ) {
            lyricInfoDetailResponse.setRate( model.getRate().floatValue() );
        }
        lyricInfoDetailResponse.setStatus( model.getStatus() );
        lyricInfoDetailResponse.setComposers( model.getComposers() );
        lyricInfoDetailResponse.setSingers( model.getSingers() );
        lyricInfoDetailResponse.setContent( model.getContent() );
        lyricInfoDetailResponse.setDescription( model.getDescription() );
        lyricInfoDetailResponse.setIsDeleted( model.getIsDeleted() );
        lyricInfoDetailResponse.setIsRated( model.getIsRated() );
        lyricInfoDetailResponse.setCreatedDate( model.getCreatedDate() );
        lyricInfoDetailResponse.setUpdatedDate( model.getUpdatedDate() );

        return lyricInfoDetailResponse;
    }

    @Override
    public LyricCommentBaseItem toLyricCommentBaseItem(LyricRepliedCommentModel model) {
        if ( model == null ) {
            return null;
        }

        LyricCommentBaseItem lyricCommentBaseItem = new LyricCommentBaseItem();

        lyricCommentBaseItem.setUserInfo( toLyricCommentUserInfo( model ) );
        lyricCommentBaseItem.setId( model.getId() );
        lyricCommentBaseItem.setContent( model.getContent() );
        lyricCommentBaseItem.setLikes( model.getLikes() );
        lyricCommentBaseItem.setIsLiked( model.getIsLiked() );
        lyricCommentBaseItem.setCreatedDate( model.getCreatedDate() );
        lyricCommentBaseItem.setUpdatedDate( model.getUpdatedDate() );

        return lyricCommentBaseItem;
    }

    @Override
    public LyricEditDetailResponse LyricEditDetailResponse(Lyric lyric) {
        if ( lyric == null ) {
            return null;
        }

        LyricEditDetailResponse lyricEditDetailResponse = new LyricEditDetailResponse();

        lyricEditDetailResponse.setId( lyric.getId() );
        lyricEditDetailResponse.setTitle( lyric.getTitle() );
        lyricEditDetailResponse.setContent( lyric.getContent() );
        lyricEditDetailResponse.setStatus( lyric.getStatus() );
        lyricEditDetailResponse.setDescription( lyric.getDescription() );
        lyricEditDetailResponse.setComposers( lyric.getComposers() );
        lyricEditDetailResponse.setSingers( lyric.getSingers() );
        lyricEditDetailResponse.setOwners( lyric.getOwners() );
        if ( lyric.getRate() != null ) {
            lyricEditDetailResponse.setRate( lyric.getRate().floatValue() );
        }
        lyricEditDetailResponse.setIsDeleted( lyric.getIsDeleted() );
        lyricEditDetailResponse.setCreatedDate( lyric.getCreatedDate() );
        lyricEditDetailResponse.setUpdatedDate( lyric.getUpdatedDate() );

        return lyricEditDetailResponse;
    }

    @Override
    public LyricCommentItem toLyricCommentItemResponse(LyricLMCommentModel model) {
        if ( model == null ) {
            return null;
        }

        LyricCommentItem lyricCommentItem = new LyricCommentItem();

        lyricCommentItem.setUserInfo( toLyricCommentUserInfo( model ) );
        lyricCommentItem.setReplied( toLyricReplyComment( model ) );
        lyricCommentItem.setId( model.getId() );
        lyricCommentItem.setContent( model.getContent() );
        lyricCommentItem.setLikes( model.getLikes() );
        lyricCommentItem.setCreatedDate( model.getCreatedDate() );
        lyricCommentItem.setUpdatedDate( model.getUpdatedDate() );
        lyricCommentItem.setStars( model.getStars() );
        lyricCommentItem.setIsLiked( model.getIsLiked() );
        lyricCommentItem.setTotalRepliedCount( model.getTotalRepliedCount() );

        return lyricCommentItem;
    }
}
