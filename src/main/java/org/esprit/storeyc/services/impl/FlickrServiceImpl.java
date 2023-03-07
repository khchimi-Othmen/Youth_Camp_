package org.esprit.storeyc.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.services.interfaces.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {
/*
    @Value("${flickr.apiKey}")
    private String apiKey;
    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;
    @Value("${flickr.appSecret}")
    private String appSecret;
*/
    private Flickr flickr;

    @Autowired//injec par const
    public FlickrServiceImpl(Flickr flickr) {
        this.flickr = flickr;
    }
    //save sur cloud
    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }
    //config hedhi najmou n3awdhouha fil config taw
/*
    private void connect(){//nous permet de connecte a chaque fois a flickr flickr
        flickr = new Flickr(apiKey,apiSecret,new REST());

        Auth auth = new Auth();
        auth.setPermission(Permission.DELETE);

        auth.setToken(appKey);//voila mon token
        auth.setTokenSecret(appSecret);//secret pour mon api
        RequestContext requestContext= RequestContext.getRequestContext();
        requestContext.setAuth(auth);

        flickr.setAuth(auth);
    }*/
}
