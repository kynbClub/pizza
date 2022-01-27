package org.pizza.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 高巍
 * @since 2020/12/18 6:05 下午
 **/
public class TokenUtil {

    private TokenUtil() {
    }

    /**
     * 生成token.
     *
     * @param params
     * @param secret
     */
    public static String generateToken(Map<String, String> params, String secret, Date expireAt) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        JWTCreator.Builder builder = JWT.create().withHeader(header);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder = builder.withClaim(entry.getKey(), entry.getValue());
            }
        }
        if (expireAt != null) {
            builder.withExpiresAt(expireAt);
        }
        return builder.sign(algorithm);
    }

    /**
     * 校验token.
     *
     * @param token
     * @param secret
     */
    public static boolean verify(String token, String secret) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    public static String getParamValue(String token, String secret, String key) throws SignatureVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(key).asString();
    }
}
