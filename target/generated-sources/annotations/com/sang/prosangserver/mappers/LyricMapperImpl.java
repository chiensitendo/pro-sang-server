package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.lyric.Lyric;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-20T10:48:19+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (Amazon.com Inc.)"
)
@Component
public class LyricMapperImpl implements LyricMapper {

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

        return lyric;
    }
}
