package pers.jaxon.funtravel.security.jwt;

import pers.jaxon.funtravel.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Write your code to make this filter works.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil,JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestWrapper = new HttpServletRequestWrapper(request);
        // Get token
        String token = requestWrapper.getHeader("Authorization");
        if (token != null) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                // get user info
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // set login
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                    logger.info("Invalid token.");
                }
            }
        }
        filterChain.doFilter(requestWrapper, response);
    }
}
