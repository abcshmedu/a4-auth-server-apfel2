/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger, c.direnberger@hm.edu
 * @author Juliane Seidl, seidl5@hm.edu
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OAuthServiceResult {


    /*
        HTTP-Status-Codes

        200     OK
        201     Created
        204     No Content

        400	    Bad Request
        401     Unauthorized
        404     Not found

        500     Internal Server Error
        501     Not Implemented
        503     Service unavailable

     */

    OK(200, "OK"),
    USERNAME_PASSWORD_WRONG(401, "Username or password wrong"),
    INVALID_TOKEN(404, "Token not valid"),
    ERROR(400, "Action failed");

    /**
     * The status code.
     */
    private final int statusCode;
    /**
     * The status message.
     */
    private final String message;

    /**
     * The constructor for OAuthServiceResult.
     *
     * @param code    The status code
     * @param message The status message
     */
    OAuthServiceResult(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

//    OAuthServiceResult() {
//        statusCode = 0;
//        message = "";
//    }

    /**
     * @return The statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return The status message
     */
    public String getMessage() {
        return message;
    }
}
