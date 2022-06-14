package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.entities.lyric.LyricAnonymousComment;
import com.sang.prosangserver.entities.lyric.LyricComment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LyricCommentMapper {

    @Mapping(target = "userInfo", source = "commentName", qualifiedByName="setUsernameOfAnonymousComment")
    LyricCommentItem anonymousToLyricCommentItem(LyricAnonymousComment comment);

    @Mappings({
        @Mapping(target = "userInfo", source = "accountId", qualifiedByName="setUserInfo"),
    })
    LyricCommentItem toLyricCommentItem(LyricComment comment);

    @Named("setUsernameOfAnonymousComment")
    default LyricCommentUserInfo setUsernameOfAnonymousComment(String commentName) {
        return new LyricCommentUserInfo(commentName);
    }

    @Named("setUserInfo")
    default LyricCommentUserInfo setUserInfo(Long accountId) {
        return new LyricCommentUserInfo(accountId, "","");
    }

    @AfterMapping
    default void setIsAnonymousFalseIfLyricComment(LyricComment comment, @MappingTarget LyricCommentItem item) {
        item.setIsAnonymous(false);
    }

}
