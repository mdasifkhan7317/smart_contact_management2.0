package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.impl.SecurityCustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class SecurityConfig {
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private OAuthenticationSuccessHandler handler;
	
	// user create and login using java code with in memory service
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user = User.withDefaultPasswordEncoder().username("Asif").password("Asif").roles("ADMIN","USER").build();
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("saif").password("saif").build();
//		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user,user2);
//		
//		return inMemoryUserDetailsManager;
//	}
	
	//configuration of authentication provider
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		//User detail service ka object
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		
		// password encoder ka object
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeHttpRequests(authorize->{
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		httpSecurity.formLogin(formlogin ->{
			formlogin.loginPage("/login");
			formlogin.loginProcessingUrl("/authenticate");
			//formlogin.successForwardUrl("/user/dashboard");
			 formlogin.defaultSuccessUrl("/user/profile", true);
			formlogin.usernameParameter("email");
			formlogin.passwordParameter("password");
		});
		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		
		//oauth configuration
		httpSecurity.oauth2Login(oauth ->{
			oauth.loginPage("/login");
			oauth.successHandler(handler);
		});
		
		httpSecurity.logout(logoutform ->{
			logoutform.logoutUrl("/logout");
		});
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
