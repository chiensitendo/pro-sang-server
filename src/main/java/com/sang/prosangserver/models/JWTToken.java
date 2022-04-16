package com.sang.prosangserver.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken {
	private String token;
	private LocalDateTime expiredTime;
}
