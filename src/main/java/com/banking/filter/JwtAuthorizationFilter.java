package com.banking.filter;

import com.banking.config.JwtConfig;
import com.banking.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String accessToken = jwtConfig.generateToken(authResult);
		String refreshToken = jwtConfig.generateRefreshToken(authResult);

		response.addHeader("Access-Token", jwtConfig.getPrefix() + accessToken);
		response.addHeader("Refresh-Token", jwtConfig.getPrefix() + refreshToken);
	}
}
