package org.esprit.storeyc.test;//package org.esprit.storeyc.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.InputStream;
//
//import org.flickr4java.flickr.Flickr;
//import org.flickr4java.flickr.FlickrException;
//import org.flickr4java.flickr.uploader.UploadMetaData;
//import org.junit.Before;
//import org.junit.Test;
//
//public class FlickrServiceImplTest {
//
//    private FlickrServiceImpl flickrService;
//    private Flickr flickr;
//
//    @Before
//    public void setUp() {
//        flickr = mock(Flickr.class);
//        flickrService = new FlickrServiceImpl(flickr);
//    }
//
//    @Test
//    public void testSavePhoto() throws FlickrException {
//        InputStream photo = mock(InputStream.class);
//        String title = "test photo title";
//
//        String photoId = "123";
//        when(flickr.getUploader().upload(any(InputStream.class), any(UploadMetaData.class))).thenReturn(photoId);
//        when(flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url()).thenReturn("http://example.com/photo");
//
//        String url = flickrService.savePhoto(photo, title);
//
//        assertEquals("http://example.com/photo", url);
//    }
//}
