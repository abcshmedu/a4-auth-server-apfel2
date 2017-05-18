/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

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
         *  Check if the token is in a list.
         *  The list contains all generated token of this OAuth-Server.
         */

        return "here could be a jwt file";
    }
}
