/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.lipptobusch.shareit.oauth.businessLayer.OAuthService;
import edu.hm.lipptobusch.shareit.oauth.businessLayer.OAuthServiceImpl;
import edu.hm.lipptobusch.shareit.oauth.businessLayer.OAuthServiceResult;
import edu.hm.lipptobusch.shareit.oauth.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger
 * @author Juliane Seidl
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
@Path("users")
public class OAuthResource {

    private ObjectMapper jsonMapper = new ObjectMapper();
    private static final OAuthService oAuthService = new OAuthServiceImpl();
    private static final List<User> users = new ArrayList<User>() {{
        add(new User("Hannah", "Nana", false));
        add(new User("admin", "admin", true));
    }};

    public static List<User> getUsers() {
        return users;
    }

    /**
     * Check if a token is valid.
     * <p>
     * Possible Error: token was never created
     * Possible Error: TTL of token is in the past
     *
     * @param token a unique token
     * @return jwt including information (...)
     */
    @GET
    @Path("login/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkToken(@PathParam("token") String token) {

        String jwt = oAuthService.checkToken(token);
        if (jwt.isEmpty()) {
            return Response.status(OAuthServiceResult.INVALID_TOKEN.getStatusCode()).entity(OAuthServiceResult.INVALID_TOKEN.getMessage()).build();
        }
        return Response.status(OAuthServiceResult.OK.getStatusCode()).entity(jwt).build();
    }

    /**
     * Will redirect the caller to a login-page.
     *
     * @return redirection to login page
     */
    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createToken() {

        URI uri = URI.create("../index.html");
        return Response.seeOther(uri).build();
    }

    /**
     * Get User-information from login-page and deliver a token.
     * <p>
     * Possible Error: user not in the database
     * Possible Error: password or username not correct
     *
     * @param user Object User including username and password
     * @return
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {

        for (User u : users) {
            if (u.equals(user)) {
                String token = oAuthService.createToken(user);
                u.setToken(token);
                return Response.status(200).entity(token).build();
            }
        }

        //System.out.println("createToken(): " + user.toString());

        return Response.status(OAuthServiceResult.USERNAME_PASSWORD_WRONG.getStatusCode()).entity(OAuthServiceResult.USERNAME_PASSWORD_WRONG).build();
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(User user) {

        for (User u : users) {
            if (u.equals(user)) {
                u.setToken(null);
                return Response.status(200).entity("successful logout").build();
            }
        }

        return Response.status(OAuthServiceResult.ERROR.getStatusCode()).entity("Logout failed").build();
    }


    @GET
    @Path("getAll/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser(@PathParam("token") String token) {

        String jwt = oAuthService.checkToken(token);


        if (jwt.contains("true")) {
            try {
                return Response.status(OAuthServiceResult.OK.getStatusCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users)).build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        return Response.status(OAuthServiceResult.INVALID_TOKEN.getStatusCode()).entity("Permission denied.").build();
    }

}
