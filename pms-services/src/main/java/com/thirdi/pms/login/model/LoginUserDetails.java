package com.thirdi.pms.login.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private LoginUser loginUser;	
	private Collection<GrantedAuthority> authortities = null; 
	
	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authortities;
	}

	public Collection<GrantedAuthority> getAuthortities() {
		return authortities;
	}

	public void setAuthortities(Collection<GrantedAuthority> authorities) {
		this.authortities = authorities;
	}

	public String getPassword() {
		return loginUser.getPassword();
	}

	public String getUsername() {
		return loginUser.getUsername();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
