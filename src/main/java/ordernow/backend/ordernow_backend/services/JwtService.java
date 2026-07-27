package ordernow.backend.ordernow_backend.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.services.JwtService;;


@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id_user", user.getIdUser());
        extraClaims.put("role", "ROLE_RESTAURANT");

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60 * 12))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("idUser", Long.class);
    }
    
    public String extractName(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("username", String.class);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
       return extractAllClaims(token).getExpiration().before(new java.util.Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}