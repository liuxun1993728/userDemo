package com.anrong.user.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class TokenSerevice {
    @Value("${jwtSecret}")
    String _secret = "";

    public String genToken(String key, String value) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(_secret);
            token = JWT.create()
                    .withClaim(key, value)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported
            // token = exception.getMessage();
            token = null; // 便于区分

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            //token = exception.getMessage();
            token=null;
        }

        return token;
    }

    public String deCodeToken(String token, String key) {
        String strRtn = "";

        try {
            Algorithm algorithm = Algorithm.HMAC256(_secret);
            JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance

            DecodedJWT jwt = verifier.verify(token);
            strRtn = jwt.getClaim(key).asString();

        } catch (Exception exception) {
            //Invalid signature/claims
            // strRtn = "ERROR|" + exception.getMessage();
            strRtn=null;
        }

        return strRtn;
    }
}
