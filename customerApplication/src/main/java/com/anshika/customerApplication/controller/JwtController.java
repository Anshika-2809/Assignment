package com.anshika.customerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshika.customerApplication.models.JwtRequest;
import com.anshika.customerApplication.models.JwtResponse;
import com.anshika.customerApplication.service.CustomUserDetailsService;
import com.anshika.customerApplication.util.JwtUtil;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/customers")
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		System.out.println(jwtRequest);
		
		try {
			
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getLoginId(), jwtRequest.getPassword()));
			
		}catch (UsernameNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Bad exception");
		}
		
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getLoginId());
		
		String token= this.jwtUtil.generateToken(userDetails);
		System.out.println("jWT" + token);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
