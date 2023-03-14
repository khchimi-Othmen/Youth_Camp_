package org.esprit.storeyc.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlickrServiceImplTest {

    @Test
    public void testSavePhoto() throws FlickrException {
        // initialize the Flickr API client with test credentials and configuration
        String apiKey = "b22d6ffd55489bc7a97f7a19bc15dee0";
        String apiSecret = "8a9dddce841a310b";
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setToken("test-app-key");
        auth.setTokenSecret("test-app-secret");
        RequestContext.getRequestContext().setAuth(auth);

        // create a FlickrService object with the real Flickr API client
        FlickrServiceImpl flickrService = new FlickrServiceImpl(flickr);

        // call the savePhoto method with a test photo
        InputStream photo = getClass().getResourceAsStream("/test-photo.jpg");
        String title = "Test photo";
        String photoUrl = flickrService.savePhoto(photo, title);

        // verify that the correct photo URL is returned
        assertTrue(photoUrl.startsWith("https://www.flickr.com/photos/197799163@N02/52741791433/in/dateposted-public/"));
    }

}
