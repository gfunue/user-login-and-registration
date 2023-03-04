package com.ngemba.loginandregistration.applicationUser;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngemba.loginandregistration.registration.token.ConfirmationToken;
import com.ngemba.loginandregistration.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
	
	private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
	private final AppUserRepository APPUSERREPO;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {
		return APPUSERREPO.findByEmail(email).orElseThrow(()->
		new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}
	
	public String signUpUser(AppUser appUser) {
		boolean userExist = APPUSERREPO
				.findByEmail(appUser.getEmail()).isPresent();
		if(userExist) {
			throw new IllegalStateException("Email already taken");
		}
		
		String encodedPassword = bCryptPasswordEncoder
				.encode(appUser.getPassword());
		
		appUser.setPassword(encodedPassword);
		
		APPUSERREPO.save(appUser);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				appUser);
		
		confirmationTokenService.saveConfirmationToken(
				confirmationToken);
		
		return token;
	}
	
	public int enableAppUser(String email) {
        return APPUSERREPO.enableAppUser(email);
    }

}

