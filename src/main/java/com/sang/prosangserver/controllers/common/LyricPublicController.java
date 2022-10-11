package com.sang.prosangserver.controllers.common;

import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.services.LyricService;
import com.sang.prosangserver.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/lyric")
public class LyricPublicController {

    private final LyricService lyricService;

    @Autowired
    public LyricPublicController(LyricService lyricService) {
        this.lyricService = lyricService;
    }

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity health() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{lyricId}/comment/{commentId}/replies")
    @ResponseBody
    public ResponseEntity<GenericResponse> getLyricRepliedComments(@PathVariable("lyricId") Long lyricId, @PathVariable("commentId") Long commentId, @RequestParam("offset") Integer offset) {
        return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getLyricRepliedComments(lyricId, commentId, offset)));
    }

    @GetMapping("/{ref}")
    @ResponseBody
    public ResponseEntity<GenericResponse> getLyricDetailByRef(@PathVariable("ref") String ref) {
        return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getLyricInfoDetailByRef(ref)));
    }

    @GetMapping("/{lyricId}/comments")
    @ResponseBody
    public ResponseEntity<GenericResponse> loadMoreComments(@PathVariable("lyricId") Long lyricId, @RequestParam("offset") Long offset) {
        return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.loadMoreComments(lyricId, offset)));
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<GenericResponse> getLyricList( @RequestParam("offset") Integer offset, @RequestParam(value = "searchText", required = false) String searchText) {
        return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getLyricList(offset, searchText)));
    }
}
