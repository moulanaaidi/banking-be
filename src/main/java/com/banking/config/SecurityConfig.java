package com.banking.config;

import com.banking.filter.AuthenticationFilter;
import com.banking.filter.JwtAuthenticationFilter;
import com.banking.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class SecurityConfig {
//
//	private final JwtConfig jwtConfig;
//	private final AuthenticationConfiguration authenticationConfiguration;
//
//	@Autowired
//	public SecurityConfig(JwtConfig jwtConfig, AuthenticationConfiguration authenticationConfiguration) {
//		this.jwtConfig = jwtConfig;
//		this.authenticationConfiguration = authenticationConfiguration;
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//		return new JwtAuthenticationFilter(authenticationManager(), jwtConfig);
//	}
//
//	@Bean
//	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
//		return new JwtAuthorizationFilter(authenticationManager(), jwtConfig);
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager() throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
//
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.authorizeRequests().antMatchers("/api/login").permitAll().antMatchers("/api/**").authenticated().and()
//				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//				.addFilter(jwtAuthorizationFilter()).headers().frameOptions().disable();
//	}
//}
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
          .disable()
          .authorizeRequests()
              .antMatchers("/swagger-ui/**").permitAll() // Allowing access to Swagger UI
              .antMatchers("/**").authenticated()
//          .antMatchers("/**")
//          .permitAll()
          .and()
          .httpBasic()
          .and()
          .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
