package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security.model.MyUserDetails;
import com.security.model.User;
import com.security.repository.UserRepo;



@Component
public class JwtUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("UserDetails"+username);
	
		User user = userRepo.findByUsername(username).orElse(null);
		
		if(user!=null) {
			
			return new MyUserDetails(user);
		}else {
	
			throw new UsernameNotFoundException("User not found with username: " + username);

		}
	}

}
