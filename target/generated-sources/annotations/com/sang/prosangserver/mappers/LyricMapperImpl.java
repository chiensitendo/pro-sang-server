package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.entities.lyric.Lyric;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-13T15:40:51+0700",
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
        lyric.setStars( request.getStars() );
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
}
