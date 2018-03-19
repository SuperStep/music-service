

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import music_server.MusicService;


public class Main {

    public static void main(String[] args) {
        
        Stream.IcyStreamMeta stream = new Stream.IcyStreamMeta();

        
        try {
            stream.setStreamUrl(new URL("http://skycast.su:2007/rock-online"));
            stream.refreshMeta();
            System.out.println(stream.getStreamTitle());           
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        

//        MusicService ms = new MusicService();
//        
//        
//        while(true) {
//            try {
//                Thread.sleep(10000);
//            } catch(InterruptedException e) {}
//            ms.Start();
//        }
        
 
    }
      
}
