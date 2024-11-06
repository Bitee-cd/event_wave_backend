package com.bitee.event.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final Integer EXPIRATION_TIME = 1000*60*60*60;

    @Value("${jwt.secret}")
    private String secret;

    public String extractUsername(String token) {return extractClaims(token,Claims::getSubject);}

    public Date extractExpiration(String token){return extractClaims(token,Claims::getExpiration);}

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }
    public String generateToken(String username,String role){
        Map<String,Object> claims  = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }

    public String createToken(Map<String, Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(key()).compact();
    }

    private Key key(){
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    private boolean isTokenExpired(String token) {return extractExpiration(token).before(new Date());}

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
