
import java.util.Timer;
import java.util.TimerTask;
import music_server.MusicService;


public class Main {

    public static void main(String[] args) {

        MusicService ms = new MusicService();
        
        TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {  
                    try {
                        ms.Start();
                    } catch (Exception ex) {
                        System.err.println(ex.toString());
                    }
                }
            };

        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(timerTask,0,10000);
 
    }
      
}
