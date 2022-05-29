package com.alkemy.challenge.challenge.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    private String SECRET_KEY = "secret"; //guarda los token => no es buena practica hacer esto

    //extraigo el username del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //extraigo la fecha de expiracion del token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //me fijo si el token expiró o no
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    //para crear el token
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                             //hago que el token valga por 10 horas
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                            //algoritmo que crea el token, y la clave
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        //extrae el username del token
        final String username = extractUsername(token);
        //validaciones: se fija si el username del token es el mismo que el del userdetails y se fija si el token no está expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        //si es valido se queda en el contexto de spring
    }
}
