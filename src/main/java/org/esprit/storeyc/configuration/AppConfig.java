//package org.esprit.storeyc.configuration;
//
//import com.flickr4java.flickr.Flickr;
//import com.flickr4java.flickr.REST;
//import com.flickr4java.flickr.RequestContext;
//import com.flickr4java.flickr.auth.Auth;
//import com.flickr4java.flickr.auth.Permission;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppConfig {
//
//    @Value("${flickr.apiKey}")//auto inject from app.prop
//    private String apiKey;
//    @Value("${flickr.apiSecret}")
//    private String apiSecret;
//    @Value("${flickr.appKey}")
//    private String appKey;
//    @Value("${flickr.appSecret}")
//    private String appSecret;
//    @Bean
//    public Flickr getFlickr(){
//        Flickr flickr = new Flickr(apiKey,apiSecret,new REST());
//
//        Auth auth = new Auth();
//        auth.setPermission(Permission.DELETE);
//
//        auth.setToken(appKey);//voila mon token
//        auth.setTokenSecret(appSecret);//secret pour mon api
//        RequestContext requestContext= RequestContext.getRequestContext();
//        requestContext.setAuth(auth);
//
//        flickr.setAuth(auth);
//        return flickr;
//    }
////    @Bean
////    public Flickr flickr() {
////        return new Flickr(apiKey, apiSecret, new REST());
////    }
//}
