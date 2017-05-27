/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

import edu.hm.lipptobusch.shareit.oauth.models.User;
import edu.hm.lipptobusch.shareit.oauth.resource.OAuthResource;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Set;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger
 * @author Juliane Seidl
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public class OAuthServiceImpl implements OAuthService {

    private static final SecureRandom random = new SecureRandom();

    public OAuthServiceImpl() {

    }

    @Override
    public String checkToken(String token) {

        /**
         *  - Check if the token is stored in a collection.
         *    The collection contains all generated token of this OAuth-Server.
         *  - check if the token is valid (TTL, ...)
         *  - generate JWT. The file "JwtTest" shows an example for using JWTs in java.
         *    It can be found in the folder test/java/edu/hm/lipptobusch/shareit/oauth.
         */


        for (User u : OAuthResource.getUsers()) {
            if (token.equals(u.getToken())) {

                if (u.isAdmin()) {
                    return new JSONObject().put("admin", "true").toString();
                } else {
                    return new JSONObject().put("admin", "false").toString();
                }


                //token valid
            }
        }

        return ""; //token not valid

    }

    @Override
    public String createToken(User user) {
        long longToken = Math.abs(random.nextLong());
        String random = Long.toString(longToken, 16);
        return (user.getUsername() + ":" + random);
    }


}
