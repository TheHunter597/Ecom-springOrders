package com.springOrders.springOrdersMain.security;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
    public DecodedJWT verify(String token) {
        var algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
