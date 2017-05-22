/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

import edu.hm.lipptobusch.shareit.oauth.models.User;

/**
 * @author Maximilian Lipp, lipp@hm.edu
 * @author Florian Tobusch, tobusch@hm.edu
 * @version 2017-05-19
 */
public class OAuthServiceImpl implements OAuthService {

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

        return "here could be a jwt file";
    }

    @Override
    public String createToken(User user) {
        return null;
    }
}
