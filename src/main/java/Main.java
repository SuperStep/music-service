
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
                        System.out.println("MS Starting...");
                        ms.Start();
                        System.out.println("MS Finished.");
                    } catch (Exception ex) {
                        System.err.println(ex.toString());
                    }
                }
            };

        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(timerTask,0,10000);
 
    }
      
}
