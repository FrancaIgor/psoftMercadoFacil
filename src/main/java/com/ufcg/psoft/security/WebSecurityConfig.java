package com.ufcg.psoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/console-h2/**").permitAll().antMatchers(HttpMethod.GET, "/api/produto/")
				.permitAll().antMatchers(HttpMethod.GET, "/api/produto/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/lote/").permitAll().antMatchers(HttpMethod.POST, "/api/produto/")
				.hasRole("ADMIN").antMatchers(HttpMethod.PUT, "/api/produto/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/produto/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "api/produto/{nome}/nome").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "api/produto/{categoria}/categoria").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/produto/{id}/lote").hasRole("ADMIN").anyRequest().authenticated();
		http.authorizeRequests().and().formLogin().permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.csrf().ignoringAntMatchers("/console-h2/**");
		http.headers().frameOptions().sameOrigin();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		 auth.userDetailsService(userDetailsService).passwordEncoder(new
				 BCryptPasswordEncoder());
				}


}
