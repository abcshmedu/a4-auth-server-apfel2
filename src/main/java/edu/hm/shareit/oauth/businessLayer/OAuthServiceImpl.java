package edu.hm.shareit.oauth.businessLayer;

import edu.hm.shareit.oauth.models.User;
import edu.hm.shareit.oauth.resource.OAuthResource;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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
     * A list of all registered users
     */
    private static List<User> USERS = new ArrayList<User>() {{
        add(new User("Hannah", "Nana", false));
        add(new User("admin", "admin", true));
    }};


    /**
     * @return The list of all registered users
     */
    public List<User> getUsers() {
        return USERS;
    }

    /**
     * The constructor for OAuthServiceImpl.
     */
    public OAuthServiceImpl() {

    }

    public static void reset() {
        USERS = new ArrayList<User>() {
            {
                add(new User("Hannah", "Nana", false));
                add(new User("admin", "admin", true));
            }
        };
    }



    @Override
    public String checkToken(String token) {

        /*
         *  - Check if the token is stored in a collection.
         *    The collection contains all generated token of this OAuth-Server.
         *  - check if the token is valid (TTL, ...)
         *  - generate JWT. The file "JwtTest" shows an example for using JWTs in java.
         *    It can be found in the folder test/java/edu/hm/shareit/oauth.
         */


        for (User u : getUsers()) {
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
