package com.sang.prosangserver.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.prosangserver.constants.LyricConstants;
import com.sang.prosangserver.dto.response.lyric.info.LyricComment;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.info.LyricReplyComment;
import com.sang.prosangserver.models.LyricDetailModel;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LyricConvertService {

    public LyricComment convertCommentsStringToLyricComment(LyricDetailModel model) {
        LyricComment res = new LyricComment();
        if (StringUtils.isNotBlank(model.getGw())) {
            JSONArray array = new JSONArray(model.getGw());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            List<LyricCommentItem> items = array.toList().stream().map(obj -> {
                LyricCommentItem item = objectMapper.convertValue(obj, LyricCommentItem.class);
                LyricReplyComment replyComment = new LyricReplyComment();
                replyComment.setTotal(item.getTotalRepliedCount());
                replyComment.setHasNext(item.getTotalRepliedCount() > LyricConstants.MAX_REPLY_PER_COMMENT);
                item.setReplied(replyComment);
                if (item.getUserInfo() != null && item.getUserInfo().getName() != null) {
                    item.getUserInfo().setName(item.getUserInfo().getName().trim());
                }
                return item;
            }).collect(Collectors.toList());
            res.setComments(items);
        }
        res.setTotal(model.getTotal());
        res.setHasNext(res.getTotal() > LyricConstants.MAX_COMMENT_PER_PAGE);
        return res;
    }

    public LyricCommentUserInfo convertToLyricCommentUserInfoFromString(String userInfoJson) {
        if (!StringUtils.isNotEmpty(userInfoJson)) {
            return null;
        }
        JSONObject object = new JSONObject(userInfoJson);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return new LyricCommentUserInfo(
            getField(Long.class, object.get("id")),
            getField(String.class, object.get("name")),
            getField(String.class, object.get("username")),
            getField(String.class, object.get("email")),
            getField(String.class, object.get("photo_url"))
        );
    }

    private <T> T getField(Class<T> clazz, Object value) {
        if (value == null) {
            return null;
        }
        if (clazz == Long.class) {

            return (T) (Long) Long.parseLong(value.toString());
        } else {
            return (T) value;
        }

    }
}
