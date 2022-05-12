package com.sip.GS.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired
	 private DataSource dataSource;
	 @Value("${spring.queries.users-query}")
	 private String usersQuery;
	 @Value("${spring.queries.roles-query}")
	 private String rolesQuery;	 
	 
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth)
	 throws Exception {
		 auth.
				 jdbcAuthentication()
				 .usersByUsernameQuery(usersQuery)
				 .authoritiesByUsernameQuery(rolesQuery)
				 .dataSource(dataSource)
				 .passwordEncoder(bCryptPasswordEncoder);
	 }

	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
				.authorizeRequests()
				.antMatchers("/login").permitAll() // accès pour tous users
				.antMatchers("/registration").permitAll() // accèspour tous users
				
				
				
				.antMatchers("/accounts/**").hasAuthority("SUPERADMIN")
				.antMatchers("/role/**").hasAuthority("SUPERADMIN")
				
				.antMatchers("/provider/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
				.antMatchers("/article/**").hasAnyAuthority("USER","ADMIN", "SUPERADMIN").anyRequest()
				
			
				.authenticated().and()
				.csrf().disable()
				.formLogin() // l'accès defait via un formulaire
			
				.loginPage("/login").failureUrl("/login?error=true") // fixer lapage login
			
				.defaultSuccessUrl("/home") // page d'accueil après login avec succès
				.usernameParameter("email") // paramètresd'authentifications login et password
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // route de deconnexion ici /logut
			
				.logoutSuccessUrl("/login").and().exceptionHandling() // une fois deconnecté redirection vers login
			
				.accessDeniedPage("/403");
	}
	// laisser l'accès aux ressources
	@Override
	 public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**","/vendor/**","/fonts/**","/uploads/**"); 
	 }

}
