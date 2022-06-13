package com.sang.prosangserver.controllers;

import javax.validation.Valid;

import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	@ResponseBody
	public ResponseEntity<?> createLyric(@Valid @RequestBody LyricRequest request) {
		
		lyricService.createLyric(request);
		
		return ResponseEntity.ok().build();
	}

	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<GenericResponse> getLyricList() {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(lyricService.getAccountLyricList()));
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
}
