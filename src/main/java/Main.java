

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import music_server.MusicService;


public class Main {

    public static void main(String[] args) {
        
        MusicService ms = new MusicService();
            
        while(true) {
            try {
                Thread.sleep(10000);
            } catch(InterruptedException e) {}
            ms.Start();
        }
      
    }
      
}
