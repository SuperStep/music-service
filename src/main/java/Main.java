

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
