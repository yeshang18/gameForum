package com.gameForum.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class TokenUtil {

    private static final String SECRET = "iwgyf";
    public static String tokenCreate(Integer userId){

        return JWT.create().withClaim("userId",userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+7*24*60*60*1000))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Integer getUserId(String token){
        return JWT.decode(token).getClaim("userId").asInt();
    }

    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //.withClaim("username, username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
