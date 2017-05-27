/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

import edu.hm.lipptobusch.shareit.oauth.models.User;

import java.util.Set;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger
 * @author Juliane Seidl
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public interface OAuthService {

    String checkToken(String token);

    String createToken(User user);

}
