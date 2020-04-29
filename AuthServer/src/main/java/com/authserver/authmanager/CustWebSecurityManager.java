package com.authserver.authmanager;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
public class CustWebSecurityManager extends AuthorizationServerConfigurerAdapter{

	
	//	Resolved below erro by moving WebSecurity to seperaete class
	//INFO 961 --- [io-8080-exec-22] o.s.s.o.provider.endpoint.TokenEndpoint : Handling error: NestedServletException, Handler dispatch failed; nested exception is java.lang.StackOverflowError

	@Autowired
	AuthenticationManager authenticationManagerBean;
	
	 
	
	PasswordEncoder passEncode=PasswordEncoderFactories.createDelegatingPasswordEncoder();
	  
	 
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients.inMemory().withClient("web").secret(passEncode.encode("webpass")).scopes("READ","WRITE").authorizedGrantTypes("password","authorization_code");
		
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		endpoints.authenticationManager(authenticationManagerBean);
 	}
}
