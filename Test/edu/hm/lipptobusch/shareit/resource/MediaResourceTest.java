package edu.hm.lipptobusch.shareit.resource;


import edu.hm.lipptobusch.shareit.models.Book;
import org.apache.commons.validator.ISBNValidator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

/**
 * Created by mx on 27.04.17.
 */
public class MediaResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(MediaResource.class);
    }


    @Test
    public void testCreateBook1() {
        // the long way
        final Book firstBook = new Book("title1","author1","9783866801929");

        Entity<Book> entity = Entity.entity(firstBook, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("media/books")
                .request()
                .post(entity);

        assertEquals(200,response.getStatus());
        assertEquals("{\"statusCode\":200,\"message\":\"OK\"}",response.readEntity(String.class));
    }

    @Test
    public void testCreateBook2() {
        // the short way
        final Book firstBook = new Book("title1","author1","9783866801929");

        final String json = target("media/books")
                .request()
                .post(Entity.json(firstBook), String.class);

        //System.out.println(json);

        assertEquals("{\"statusCode\":200,\"message\":\"OK\"}",json);
    }

    @Test
    public void testGetBooks() throws Exception {
        WebTarget source = target("media/books");
        final Book firstBook = new Book("title1","author1","9783813506860");
        final Book sndBook = new Book("title2","author2","9783866801929");

        source.request().post(Entity.json(sndBook));
        source.request().post(Entity.json(firstBook));

        ISBNValidator validator = new ISBNValidator();


        Response list = source.request().get();

        System.out.println(list.readEntity(String.class));
        System.out.println(validator.isValid("3328100342"));
    }

    @Test
    public void testGetBook() throws Exception {

    }

    @Test
    public void testCreateDisc() throws Exception {

    }

    @Test
    public void testGetDiscs() throws Exception {

    }

    @Test
    public void testGetDisc() throws Exception {

    }

    @Test
    public void testUpdateBook() throws Exception {

    }

    @Test
    public void testUpdateDisc() throws Exception {

    }
}