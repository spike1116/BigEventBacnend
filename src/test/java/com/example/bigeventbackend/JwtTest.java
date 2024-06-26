package com.example.bigeventbackend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "spike");

        //生成JWT的代码
        String token = JWT.create().withClaim("user", claims)//添加payload 载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //添加Token的过期时间
                .sign(Algorithm.HMAC256("spikecom"));//指定算法，配置密钥

        System.out.println(token);
    }

    @Test
    public void parseToken() {
        //篡改了头部和载荷的信息验证失败
        //Token过期了验证也会失败
        String token = "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InNwaWtlIn0sImV4cCI6MTcxOTM2MjcxMn0.bEXTK5n9erj9iAUXedL_Epm8V4tZf1fQS0dpjbCRiIM";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("spikecom")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.toString());
        System.out.println(claims.get("user"));

    }
}
