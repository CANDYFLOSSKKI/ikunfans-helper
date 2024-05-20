package com.ctey.ikunfanscommon.Util;

import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ctey.ikunfanscommon.Static.WebModuleStatic.*;

public class AccountTokenUtil {
    public static String createAccountToken(TokenClaimEntity entity) {
        Map<String, Object> claimsMap = new HashMap<>(Map.ofEntries(
                Map.entry("id", entity.getId()),
                Map.entry("account", entity.getAccount())
        ));
        return Jwts.builder()
                .setSubject(TOKEN_SUBJECT)
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, TOKEN_APP_SECRET_KEY)
                .compact();
    }

    public static TokenClaimEntity parseAccountToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(TOKEN_APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return new TokenClaimEntity(
                claims.get("id").toString(),
                claims.get("account").toString()
        );
    }

    public static boolean checkAccountToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(TOKEN_APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().after(new Date());
    }

}
