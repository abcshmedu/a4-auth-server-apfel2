/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

import edu.hm.lipptobusch.shareit.oauth.models.User;

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

}
