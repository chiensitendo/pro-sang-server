package com.sang.prosangserver.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import org.json.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LyricDetailCommentItemConverter implements
    AttributeConverter<List<LyricCommentItem>, String> {

    @Override
    public String convertToDatabaseColumn(List<LyricCommentItem> lyricCommentItems) {
        if (lyricCommentItems == null || lyricCommentItems.size() == 0) {
            return null;
        }
        return new Gson().toJson(lyricCommentItems);
    }

    @Override
    public List<LyricCommentItem> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        JSONArray array = new JSONArray(s);
        if (array.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        List<LyricCommentItem> items = array.toList().stream().map(obj -> {
            LyricCommentItem item = objectMapper.convertValue(obj, LyricCommentItem.class);
            return item;
        }).collect(Collectors.toList());
        return items;
    }
}
