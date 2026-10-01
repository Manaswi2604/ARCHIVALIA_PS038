package com.archivalia.borrow.security;
import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
 private static final String SECRET = "ArchivaliaPS038Review1SecretKeyForJwt2026Secure";
 private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)
   throws ServletException,IOException {
  String h=req.getHeader("Authorization");
  if(h!=null && h.startsWith("Bearer ")) {
   try {
    Claims c=Jwts.parser().verifyWith(key).build().parseSignedClaims(h.substring(7)).getPayload();
    String email=c.getSubject(); String role=c.get("role",String.class);
    var auth=new UsernamePasswordAuthenticationToken(email,null,List.of(new SimpleGrantedAuthority(role)));
    SecurityContextHolder.getContext().setAuthentication(auth);
   } catch(Exception ignored) {}
  }
  chain.doFilter(req,res);
 }
}
