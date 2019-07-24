package com.thirdi.pms.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdi.pms.login.dao.LoginDao;
import com.thirdi.pms.login.model.LoginUser;

@Service("userDetailsService")
@Transactional(readOnly=true)
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	CustomAuthenticationProvider authenticationProvider;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("Authentication request of User: "+username);
			if(!username.equals("")) {
				LoginUser loginUserDetails = loginDao.getLoginUserByName(username);
				if(loginUserDetails != null){
					String password = "";
					boolean accountNotExpired = true;
					boolean credentialsNonExpired = true;
					boolean accountNotLocked = true;
					boolean isEnabled=true;
					Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(loginUserDetails.getRole()));
					org.springframework.security.core.userdetails.User securityUser = new 
							org.springframework.security.core.userdetails.User(username, password, isEnabled, accountNotExpired,credentialsNonExpired,accountNotLocked, authorities) ;
					return securityUser;
				}else {
					throw new UsernameNotFoundException("User Not found !!!");
				}
			}else {
				throw new UsernameNotFoundException("Username is empty !!!");
			}
		}catch(Exception e) {
			System.out.println("Username not found"); 
			e.printStackTrace();
		}
		return null;
	}
	
}
