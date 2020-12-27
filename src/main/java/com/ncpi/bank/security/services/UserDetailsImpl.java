package com.ncpi.bank.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ncpi.bank.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
	private String userId;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String username, String userId, String password) {
		this.username = username;
		this.userId = userId;
		this.password = password;
	}

	public static UserDetailsImpl build(User user) {
		return new UserDetailsImpl(
				user.getName(),
				user.getUserId(),
				user.getPassword());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(username, user.username) && Objects.equals(userId,user.userId);
	}
}
