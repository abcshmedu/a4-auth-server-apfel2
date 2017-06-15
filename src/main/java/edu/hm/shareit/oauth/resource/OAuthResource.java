package edu.hm.shareit.oauth.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.oauth.businessLayer.OAuthService;
import edu.hm.shareit.oauth.businessLayer.OAuthServiceImpl;
import edu.hm.shareit.oauth.businessLayer.OAuthServiceResult;
import edu.hm.shareit.oauth.models.User;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger, c.direnberger@hm.edu
 * @author Juliane Seidl, seidl5@hm.edu
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
@Path("users")
public class OAuthResource {

    /**
     * A JSON ObjectMapper
     */
    private final ObjectMapper jsonMapper = new ObjectMapper();
    /**
     * The authorization server
     */
    public final OAuthService O_AUTH_SERVICE = new OAuthServiceImpl();




    /**
     * Check if a token is valid.
     *
     * Possible Error: Token was never created
     * Possible Error: TTL of token is in the past
     *
     * @param token A unique token
     * @return A response with admin information of user when token valid otherwise error message
     */
    @GET
    @Path("login/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkToken(@PathParam("token") String token) {

        String jwt = O_AUTH_SERVICE.checkToken(token);
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
     * @param user The user who wants to login
     * @return A response with the created token as JSON when login was successful or an error message
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {



        for (User u : O_AUTH_SERVICE.getUsers()) {
            if (u.equals(user)) {
                String token = O_AUTH_SERVICE.createToken(user);
                u.setToken(token);
                String tokenJSON = new JSONObject().put("token", token).toString();
                return Response.status(OAuthServiceResult.OK.getStatusCode()).entity(tokenJSON).build();
            }
        }

        //System.out.println("createToken(): " + user.toString());

        return Response.status(OAuthServiceResult.USERNAME_PASSWORD_WRONG.getStatusCode()).entity(OAuthServiceResult.USERNAME_PASSWORD_WRONG).build();
    }

    /**
     * Logout a user and delete the token.
     * <p>
     * Possible Error: User not in the database
     *
     * @param user The user who wants to logout
     * @return A response whether logout was successful or failed
     */
    @SuppressWarnings("UnusedReturnValue")
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(User user) {

        for (User u : O_AUTH_SERVICE.getUsers()) {
            if (u.equals(user)) {
                u.setToken(null);
                return Response.status(OAuthServiceResult.OK.getStatusCode()).entity(OAuthServiceResult.OK.getMessage()).build();
            }
        }

        return Response.status(OAuthServiceResult.ERROR.getStatusCode()).entity(OAuthServiceResult.ERROR.getMessage()).build();
    }

    /**
     * Shows a list to an admin of all registered USERS.
     * <p>
     * Possible Errors: User is not an admin. Permission gets denied.
     *
     * @param token The current token of logged-in user
     * @return A response with the list of USERS if successful otherwise error message accordingly
     */
    @GET
    @Path("getAllUsers/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser(@PathParam("token") String token) {

        String jwt = O_AUTH_SERVICE.checkToken(token);

        if (jwt.contains("true")) {
            try {
                return Response.status(OAuthServiceResult.OK.getStatusCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(O_AUTH_SERVICE.getUsers())).build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return Response.status(OAuthServiceResult.INVALID_TOKEN.getStatusCode()).entity("Permission denied.").build();
    }

}
