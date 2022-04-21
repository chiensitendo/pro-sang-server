package com.sang.prosangserver.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@Autowired
	private LyricService lyricService;

	@PostMapping
	@ResponseBody
	public ResponseEntity<?> createLyric(@Valid @RequestBody LyricRequest request) {
		
		lyricService.createLyric(request);
		
		return ResponseEntity.ok().build();
	}
}
