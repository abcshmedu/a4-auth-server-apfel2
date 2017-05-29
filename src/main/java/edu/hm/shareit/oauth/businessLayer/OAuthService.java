package edu.hm.shareit.oauth.businessLayer;

import edu.hm.shareit.oauth.models.User;

import java.util.List;

/**
 * The OAuth server.
 *
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger, c.direnberger@hm.edu
 * @author Juliane Seidl, seidl5@hm.edu
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public interface OAuthService {

    /**
     * Checks whether the given token is valid or not.
     *
     * @param token A unique token
     * @return Admin status of the user when valid otherwise an empty string
     */
    String checkToken(String token);

    /**
     * Creates a new unique token.
     *
     * @param user The user the token is created for
     * @return The created token
     */
    String createToken(User user);

    /**
     * Retuns List with users
     *
     * @return userlist
     */
    List<User> getUsers();

}
