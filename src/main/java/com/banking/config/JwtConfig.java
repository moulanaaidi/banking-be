package com.banking.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

//@Component
public class JwtConfig {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private Long jwtExpiration;

	@Value("${jwt.refreshExpiration}")
	private Long jwtRefreshExpiration;

	@Value("${jwt.header}")
	private String jwtHeader;

	@Value("${jwt.prefix}")
	private String jwtPrefix;

	public String generateToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return createToken(userDetails.getUsername());
	}

	public String generateRefreshToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return createRefreshToken(userDetails.getUsername());
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token);
	}

	public String getHeader() {
		return jwtHeader;
	}

	public String getPrefix() {
		return jwtPrefix;
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}

	private Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private String createToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	private String createRefreshToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
}
