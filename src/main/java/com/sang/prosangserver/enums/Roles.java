package com.sang.prosangserver.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
	SUPER_ADMIN(0),
	ADMIN(1),
	USER(2);
	
	private int id;
	
	private Roles(int id) {
		this.id = id;
	}
	
	@JsonValue
	public int getId() {
		return this.id;
	}
	
	public Roles getRole(int id) {
		for (Roles role: Roles.values()) {
			if (role.getId() == id) {
				return role;
			}
		}
		return null;
	}
}
