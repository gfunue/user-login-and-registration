package com.ngemba.loginandregistration.applicationUser;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
	
	private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
	private final AppUserRepository APPUSERREPO = null;

	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {
		return APPUSERREPO.findByEmail(email).orElseThrow(()->
		new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

}

