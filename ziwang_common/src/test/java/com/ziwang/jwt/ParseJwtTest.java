package com.ziwang.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "124";
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey("ziwang")
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println(claims.getId());
            System.out.println(claims.getSubject());
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
            System.out.println(claims.get("role"));

        } catch (ExpiredJwtException e) {
            System.out.println("登录信息过期");
        }


    }
}
