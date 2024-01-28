package com.anshika.customerApplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.anshika.customerApplication.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	@Autowired
	public CustomUserDetailsService customUserDetailsService;
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain  filterChain(HttpSecurity http) throws Exception {
		
		
		AuthenticationManager authenticationManager = authenticationManagerBean(http);
		
		http
			.csrf((csrf)-> csrf.disable())
			.cors().and()
			.authorizeHttpRequests((authz)-> authz
					.antMatchers("/api/customers/token")
					.permitAll()
					.anyRequest()
					.authenticated())
			.authenticationManager(authenticationManager)
			.sessionManagement((session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
		
		return authenticationManagerBuilder.build();
    }
}
