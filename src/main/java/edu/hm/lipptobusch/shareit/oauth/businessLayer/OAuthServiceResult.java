/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.businessLayer;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Maximilian Lipp, lipp@hm.edu
 * @author Florian Tobusch, tobusch@hm.edu
 * @version 2017-05-19
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OAuthServiceResult {


    /*
        HTTP-Status-Codes

        200     OK
        201     Created
        204     No Content (z.B. nach erfolgreichem DELETE)

        400	    Bad Request
        401     Unauthorized
        404     Not found

        500     Internal Server Error
        501     Not Implemented
        503     Service unavailable

     */

    OK(200,"OK");

    private final int statusCode;
    private final String message;

    OAuthServiceResult(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    private OAuthServiceResult() {statusCode = 0; message = "";}

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
