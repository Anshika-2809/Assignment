package com.anshika.customerApplication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
					
		if(userName.equals("test@sunbasedata.com")) {
			return new User("test@sunbasedata.com", "Test@123", new ArrayList<>() );
			
		} else {
			throw new UsernameNotFoundException("User not found");
			
		}
		
	}

}
