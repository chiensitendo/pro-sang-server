package com.sang.prosangserver.controllers;

import javax.validation.Valid;

import com.sang.prosangserver.dto.request.lyric.LyricAddCommentRequest;
import com.sang.prosangserver.dto.request.lyric.LyricAddReplyRequest;
import com.sang.prosangserver.dto.request.lyric.LyricLikeCommentRequest;
import com.sang.prosangserver.dto.request.lyric.LyricLikeReplyRequest;
import com.sang.prosangserver.dto.request.lyric.LyricRateRequest;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.services.LyricService;

@RestController
@RequestMapping("lyric")
public class LyricController {

	private final LyricService lyricService;

	@Autowired
	public LyricController(LyricService lyricService) {
		this.lyricService = lyricService;
	}

	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<GenericResponse> getLyricList(@RequestParam("offset") Integer offset) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getAccountLyricList(offset)));
	}

	@GetMapping("/{id}/list")
	@ResponseBody
	public ResponseEntity<GenericResponse> getLyricListByAccount(@PathVariable("id") Long id) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getAccountLyricListByAccountId(id)));
	}

	@GetMapping("/detail/{id}")
	@ResponseBody
	public ResponseEntity<GenericResponse> getLyricDetail(@PathVariable("id") Long id) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getLyricDetail(id)));
	}

	@PostMapping("/info/comment/{commentId}/like")
	@ResponseBody
	public ResponseEntity<GenericResponse> likeComment(@Valid @RequestBody LyricLikeCommentRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.likeComment(request)));
	}

	@PostMapping("/info/comment/{commentId}/reply/{replyId}/like")
	@ResponseBody
	public ResponseEntity<GenericResponse> likeReply(@Valid @RequestBody LyricLikeReplyRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.likeReply(request)));
	}

	@PostMapping("/info/comment/add")
	@ResponseBody
	public ResponseEntity<GenericResponse> addNewComment(@Valid @RequestBody LyricAddCommentRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.addNewComment(request)));
	}

	@PostMapping("/{lyricId}/comment/{commentId}/reply/add")
	@ResponseBody
	public ResponseEntity<GenericResponse> addNewReplyComment(@PathVariable("lyricId") Long lyricId, @PathVariable("commentId") Long commentId, @Valid @RequestBody LyricAddReplyRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.addNewReplyComment(lyricId, commentId, request)));
	}

	@PostMapping("/{lyricId}/rate")
	@ResponseBody
	public ResponseEntity<GenericResponse> rateLyric(@PathVariable("lyricId") Long lyricId, @Valid @RequestBody LyricRateRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.rateLyric(lyricId, request)));
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<GenericResponse> addLyric( @Valid @RequestBody LyricRequest request) {
		lyricService.addLyric(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/edit/{lyricId}")
	@ResponseBody
	public ResponseEntity<GenericResponse> getLyricDetailForEdit(@PathVariable("lyricId") Long lyricId) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getLyricDetailForEdit(lyricId)));
	}

	@PutMapping("/edit/{lyricId}")
	@ResponseBody
	public ResponseEntity<GenericResponse> editLyric(@PathVariable("lyricId") Long lyricId, @Valid @RequestBody LyricRequest request) {
		lyricService.editLyric(lyricId, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{lyricId}")
	@ResponseBody
	public ResponseEntity<GenericResponse> deleteLyric(@PathVariable("lyricId") Long lyricId) {
		lyricService.deleteLyric(lyricId);
		return ResponseEntity.ok().build();
	}
}
