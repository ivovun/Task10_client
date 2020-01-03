package com.websystique.springmvc.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;


 public class UserProfile implements GrantedAuthority {
 	private Long id;

 	private String type = UserProfileType.USER.getUserProfileType();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserProfile that = (UserProfile) o;
		return type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public String toString() {
		return type;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + type;
	}

	public enum UserProfileType implements Serializable{
		USER("USER"),
		DBA("DBA"),
		ADMIN("ADMIN");

		String userProfileType;

		UserProfileType(String userProfileType){
			this.userProfileType = userProfileType;
		}

		public String getUserProfileType(){
			return userProfileType;
		}

	}
}

