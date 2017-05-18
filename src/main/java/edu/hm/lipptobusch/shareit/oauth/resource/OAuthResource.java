/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.resource;


import edu.hm.lipptobusch.shareit.oauth.businessLayer.OAuthService;
import edu.hm.lipptobusch.shareit.oauth.businessLayer.OAuthServiceImpl;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Maximilian Lipp, lipp@hm.edu
 * @author Florian Tobusch, tobusch@hm.edu
 * @version 2017-04-19
 */
@Path("users")
public class OAuthResource {

    private static final OAuthService oAuthService = new OAuthServiceImpl();

    @GET
    @Path("login/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkToken(@PathParam("token") String token) {

        String jwt = "here could be a jwt file"; //call oAuthService
        //oAuthService.checkToken(token);

        return Response.status(200).entity(jwt).build();
    }

}
