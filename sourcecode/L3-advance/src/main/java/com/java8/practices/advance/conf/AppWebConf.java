package com.java8.practices.advance.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppWebConf extends WebSecurityConfigurerAdapter 
{
	@Value("${spring.security.diable-csrf}")
	private boolean csrfDisable;

	@Value("${spring.security.h2.enable}")
	private boolean h2Enabled;

	@Value("${spring.h2.console.path}")
	private String h2Path;

	@Value("${spring.security.custom.configuration}")
	private boolean customConfig;

	@Value("${spring.security.user.name}")
	private String user;

	@Value("${spring.security.user.password}")
	private String pass;

	@Value("${spring.security.user.roles}")
	private String[] roles;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new  BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		if(csrfDisable)	http = http.csrf().disable();

		if(customConfig)
		{
			if(h2Enabled)  //http.csrf().ignoringAntMatchers(h2Path + "/**");
				http.authorizeRequests()
				.antMatchers(h2Path + "/**", "/login**", "/logout**").permitAll()
				.antMatchers("/api/**").hasRole("user")
				.antMatchers("/admin/**").hasRole("admin")
				.anyRequest().fullyAuthenticated()
				.and()
				.httpBasic();
			else
				http.authorizeRequests()
				.antMatchers("/", "/login**", "/logout**").permitAll()
				.antMatchers("/api/**").hasRole("user")
				.antMatchers("/admin/**").hasRole("admin")
				.anyRequest().fullyAuthenticated()
				.and()
				.httpBasic();
			http.headers().frameOptions().disable();
		}
		else
			super.configure(http);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.inMemoryAuthentication().withUser(user).password(passwordEncoder.encode(pass)).roles(roles);
	}
}
