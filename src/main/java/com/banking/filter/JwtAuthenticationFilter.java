package com.banking.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.banking.config.JwtConfig;
import com.banking.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {

			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword(), new ArrayList<>());

			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) {

		String token = jwtConfig.generateToken(authResult);
		String refreshToken = jwtConfig.generateRefreshToken(authResult);

		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
		response.addHeader("Refresh-Token", refreshToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Authentication failed: " + failed.getMessage());
	}
}
