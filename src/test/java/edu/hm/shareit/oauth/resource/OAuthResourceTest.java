package edu.hm.shareit.oauth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.oauth.businessLayer.OAuthService;
import edu.hm.shareit.oauth.businessLayer.OAuthServiceImpl;
import edu.hm.shareit.oauth.businessLayer.OAuthServiceResult;
import edu.hm.shareit.oauth.models.User;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger, c.direnberger@hm.edu
 * @author Juliane Seidl, seidl5@hm.edu
 * @author Maximilian Lipp, lipp@hm.edu
 */
public class OAuthResourceTest {

    @Before
    public void resetData() {
        OAuthServiceImpl.reset();
    }

    @Test
    public void createTokenAndLogoutTest() {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(0);
        assertEquals(null, user.getToken());

        Response response = auth.login(user);
        assertEquals(new JSONObject(response.getEntity().toString()).getString("token"),user.getToken());

        auth.logout(user);
        assertEquals(null, user.getToken());
    }

    @Test
    public void ttlToken() throws InterruptedException {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();
        User user = users.get(0);
        assertEquals(null, user.getToken());

        Response response = auth.login(user);
        //noinspection deprecation
        user.setTTLSeconds(2);
        assertEquals(new JSONObject(response.getEntity().toString()).getString("token"), user.getToken());
        Thread.sleep(3000);
        assertEquals(null, user.getToken());

        auth.logout(user);
        assertEquals(null, user.getToken());
    }


    @Test
    public void tokenInvalid() {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(0);
        assertEquals(null, user.getToken());

        auth.login(user);

        Response response = auth.checkToken("blabla");

        assertEquals(response.getStatus(), OAuthServiceResult.INVALID_TOKEN.getStatusCode());

        auth.logout(user);

    }


    @Test
    public void tokenValid() {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(0);
        assertEquals(null, user.getToken());

        auth.login(user);

        Response response = auth.checkToken(user.getToken());


        assertEquals(response.getStatus(), OAuthServiceResult.OK.getStatusCode());

        auth.logout(user);

    }


    @Test
    public void passwordWrong() {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(0);
        assertEquals(null, user.getToken());

        Response response = auth.login(new User(user.getUsername(), "bla", user.isAdmin()));

        assertEquals(response.getStatus(), OAuthServiceResult.USERNAME_PASSWORD_WRONG.getStatusCode());

        auth.logout(user);

    }


    @Test
    public void adminGetUsers() throws JsonProcessingException {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(1);

        auth.login(user);

        Response response = auth.getAllUser(user.getToken());

        assertEquals(response.getStatus(), OAuthServiceResult.OK.getStatusCode());

        assertEquals((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(users), response.getEntity());

        auth.logout(user);

    }


    @Test
    public void userGetsNoUsers() throws JsonProcessingException {
        OAuthResource auth = new OAuthResource();
        OAuthService oAuthService  = new OAuthServiceImpl();

        List<User> users = oAuthService.getUsers();

        User user = users.get(0);

        auth.login(user);

        Response response = auth.getAllUser(user.getToken());

        assertEquals(response.getStatus(), OAuthServiceResult.INVALID_TOKEN.getStatusCode());

        assertEquals("Permission denied.", response.getEntity());

        auth.logout(user);

    }
}