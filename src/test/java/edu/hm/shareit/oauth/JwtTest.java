package edu.hm.shareit.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class JwtTest {
    public static void main(String[] args) {

        /*
         * Example with JWT from: https://stormpath.com/blog/beginners-guide-jwts-in-java
         */

        try {


            //############################
            //generate new JWT

            Date expirationDate = new Date(System.currentTimeMillis() + 60000);

            String jwt = Jwts.builder()
                    .setSubject("Reply to Access-Token")
                    .setExpiration(expirationDate)
                    .claim("name", "Robert Token Man") //claims contain the body elements
                    .claim("scope", "self groups/admins")
                    .signWith(
                            SignatureAlgorithm.HS256, "secret".getBytes("UTF-8")
                    )
                    .compact();


            //############################
            //validating JWT
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
            String scope = (String) claims.getBody().get("scope");
            assertEquals(scope, "self groups/admins");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
