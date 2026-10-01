package com.archivalia.borrow.config;
import com.archivalia.borrow.security.JwtFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.*;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration 
public class SecurityConfig {
 private final JwtFilter jwtFilter;
 public SecurityConfig(JwtFilter jwtFilter) {
	    this.jwtFilter = jwtFilter;
	}
 @Bean SecurityFilterChain chain(HttpSecurity http) throws Exception {
  http.csrf(c->c.disable())
    .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(a->a.requestMatchers("/api/borrow/health").permitAll().anyRequest().authenticated())
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  return http.build();
 }
}
