package com.example.Netflix.Security;

import com.example.Netflix.Accesseror.AuthAccessor;
import com.example.Netflix.Accesseror.Model.Auth.AuthDto;
import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.UserAccessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    UserAccessor userAccessor;
    AuthAccessor authAccessor;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        if(userAccessor == null)
            userAccessor = (UserAccessor) applicationContext.getBean("userAccessor");
        if(authAccessor == null)
            authAccessor = (AuthAccessor) applicationContext.getBean("authAccessor");
        try {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }catch (MalformedJwtException | BadCredentialsException ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String authToken = request.getHeader("Authorization");
        String prefix = "Bearer ";

        if(authToken!=null && authToken.startsWith(prefix)){
            String token = authToken.replace(prefix,"");
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationTime = claims.getExpiration();
            if(expirationTime.after(new Date(System.currentTimeMillis()))){
                AuthDto authDto = authAccessor.getAuthByToken(token);
                if(authDto!=null){
                    UserDto user = userAccessor.getUserByEmail(claims.getSubject());
                    if(user!=null){
                        return new UsernamePasswordAuthenticationToken(user,user.getPassword(),
                                Arrays.asList(new SimpleGrantedAuthority(user.getRole().name())));
                    }
                }
            }
        }
        return new UsernamePasswordAuthenticationToken(null,null,Arrays.asList(new SimpleGrantedAuthority(Roles.Anonymous)));
    }

}
