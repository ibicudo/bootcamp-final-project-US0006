package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTUtil  {

    protected static final String secret = "mySecretKey";

    protected static final int expirationTime = 3600000;

    public static String getJWT(Account account){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(account.getRole().getId());

        return  Jwts.builder()
                .setSubject(account.getUserName().toString())
                .claim("account_id", account.getUserName())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256,
                        secret).compact();
    }

    public static Claims decodeJWT(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}