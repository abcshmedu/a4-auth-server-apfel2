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

/**
 * The OAuth server implementation.
 *
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger, c.direnberger@hm.edu
 * @author Juliane Seidl, seidl5@hm.edu
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public class OAuthServiceImpl implements OAuthService {
    /**
     * A SecureRandom number to generate a RANDOM token
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int TOKEN_LENGTH = 16;

    /**
     * The constructor for OAuthServiceImpl.
     */
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
        long longToken = Math.abs(RANDOM.nextLong());
        String random = Long.toString(longToken, TOKEN_LENGTH);
        return (user.getUsername() + ":" + random);
    }


}
