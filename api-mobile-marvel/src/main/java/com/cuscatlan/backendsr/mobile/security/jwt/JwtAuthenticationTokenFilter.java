package com.cuscatlan.backendsr.mobile.security.jwt;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private final UserDetailsService userDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
			@Value("Authorization") String tokenHeader) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
			    	   username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                   
			} catch (IllegalArgumentException e) {
				log.warn("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.warn("{JWT Token has expired");
			}catch(Exception e) {
				log.warn("{{}", e.getMessage());
			}
		} else {
			log.warn("JWT Token does not begin with Bearer String");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security
				// Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		 chain.doFilter(request, response);
	}

}