package com.jrp.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	DataSource dataSource;

	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz.requestMatchers("/projects/new").hasRole("ADMIN")
		// .requestMatchers("/projects/save").hasRole("ADMIN")
		// .requestMatchers("/employees/new").hasRole("ADMIN")
//				.requestMatchers("/employees/save").hasRole("ADMIN")
				.requestMatchers("/", "/**").permitAll().anyRequest().authenticated()).formLogin();
		http.csrf().disable();

		return http.build();
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(authz -> authz.requestMatchers("/projects/new").hasRole("ADMIN")
//				.requestMatchers("/employees/new").hasRole("ADMIN").anyRequest().authenticated()).formLogin();
//
//		return http.build();
//	}

	@Bean
	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		// Custom query to fetch users
		jdbcUserDetailsManager
				.setUsersByUsernameQuery("select username, password, enabled from user_accounts where username = ?");

		// Custom query to fetch authorities (roles) with a fixed space before FROM
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("select username, role from user_accounts where username = ?");

		return jdbcUserDetailsManager;
	}

//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
//		return bCryptEncoder;
//	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(JdbcUserDetailsManager userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder); // Set the password encoder here
		return provider;
	}

//	@Bean
//	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
//		UserDetails user = User.withDefaultPasswordEncoder().username("myuser").password("pass").roles("USER").build();
//
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("max").password("pass2").roles("USER").build();
//
//		UserDetails user3 = User.withDefaultPasswordEncoder().username("managerUser").password("pass3").roles("ADMIN")
//				.build();
//		return new JdbcUserDetailsManager(dataSource);
//	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("myuser").password("pass").roles("USER").build();
//
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("max").password("pass2").roles("USER").build();
//
//		UserDetails user3 = User.withDefaultPasswordEncoder().username("managerUser").password("pass3").roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user, user2, user3);
//	}

//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}

//@Configuration
//
//@EnableWebSecurity
//
//public class SecurityConfiguration {
// 
//	@Autowired
//
//	DataSource dataSource;
//
//	@Autowired
//	BCryptPasswordEncoder bCryptEncoder;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//				.usersByUsernameQuery("select username, password, enabled " + "from user_accounts where username = ?")
//				.authoritiesByUsernameQuery("select username, role " + "from authorities where username = ?")
//				.dataSource(dataSource).passwordEncoder(bCryptEncoder);
//	}

//	@Bean
//
//	public UserDetailsManager users(DataSource dataSource) {
//
//		UserDetails user = User.withUsername("user")
//
//				.password(bCryptEncoder.encode("password"))
//
//				.roles("USER")
//
//				.build();
//
//		UserDetails adminUser = User.withUsername("admin")
//
//				.password(bCryptEncoder.encode("password"))
//
//				.roles("ADMIN")
//
//				.build();
//
//		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//		users.createUser(user);
//
//		users.createUser(adminUser);
//
//		return users;
//
//	}

//	@Bean
//
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//		return http
//
//				.csrf(csrf -> csrf.disable()) // For accessing h2 console
//
//				.authorizeHttpRequests(auth -> auth
//
//						.requestMatchers("/projects/new").hasRole("ADMIN")
//
//						.requestMatchers("/projects/save").hasRole("ADMIN")
//
//						.requestMatchers("/employees/new").hasRole("ADMIN")
//
//						.requestMatchers("/employees/save").hasRole("ADMIN")
//
//						.requestMatchers("/h2-console/**").hasRole("ADMIN") // For accessing h2 console
//
//						.requestMatchers("/", "/**").permitAll()
//
//						.anyRequest().authenticated())
//
//				.httpBasic(Customizer.withDefaults())
//
//				.headers().frameOptions().disable().and() // For accessing h2 console
//
//				.build();
//
//	}

//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().requestMatchers("/projects/new").hasRole("ADMIN").requestMatchers("/projects/save")
//				.hasRole("ADMIN").requestMatchers("/employees/new").hasRole("ADMIN").requestMatchers("/employees/save")
//				.hasRole("ADMIN").requestMatchers("/", "/**").permitAll().and().formLogin();
//
//		http.csrf().disable();
//
//	}

}
