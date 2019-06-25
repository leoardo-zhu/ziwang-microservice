package com.ziwang.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {

    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("1132622905625153536")
                .setSubject("小龙龙")        //用户名
                .setIssuedAt(new Date())    //设置签发时间
                .signWith(SignatureAlgorithm.HS256, "ziwang")  //设置签名密钥
                .setExpiration(new Date(new Date().getTime()+ 1000* 60*60*24*72)) //设置三天过期时间
                .claim("role","admin");


        System.out.println(jwtBuilder.compact());
    }
}
