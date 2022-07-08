package fr.fms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	public String encodePassword(String password) {
		PasswordEncoder pe = passwordEncoder();
		return pe.encode(password);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder pe = passwordEncoder();

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select username as principal, password as credentials, active from User where username=?")
				.authoritiesByUsernameQuery("select username as principal, role as role from User where username=?")
				.rolePrefix("ROLE_").passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home")
				.failureUrl("/login?error=true").permitAll();

		http.authorizeRequests().antMatchers("/addTask", "/editTask", "/tables", "/user").hasRole("ADMIN")
				.antMatchers("/addTask", "/editTask", "/tables").hasRole("USER").anyRequest().permitAll();

		http.exceptionHandling().accessDeniedPage("/403");

	}

}
