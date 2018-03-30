package music_server;

import Models.ArtistEvent;
import com.google.gson.Gson;
import java.net.URL;
import java.util.ArrayList;
import ru.blizzed.discogsdb.model.release.Release;

public class MusicService {
    
    URL streamURL;
    
    String currentTitle;
    
    IcyStreamMeta stream = new IcyStreamMeta();
    MusicAPI musicAPI = new MusicAPI();
    
    Release release;
    ArrayList<ArtistEvent> events;
    
    Gson gson = new Gson();
    
    public MusicService(){     
    }

    public String getCurrentTitle() {
        return currentTitle;
    }
    
    public String getRelease() {
        return gson.toJson(release);
    }
    
    public void Start(){
        try{
            stream.setStreamUrl(new URL("http://skycast.su:2007/rock-online"));
            stream.refreshMeta();

            if(currentTitle != stream.getStreamTitle()){

                currentTitle = stream.getStreamTitle();
                
                events = musicAPI.getEvents(stream.getArtist());
                release = musicAPI.getRelease(stream.getArtist(), stream.getTitle());

                
                System.out.println("Updated title: " + currentTitle);

            }else{
                System.out.println("Title " + currentTitle + "is the same.");
            }
        
        }catch(Exception ex){
            System.err.println(ex.toString());
        }
    } 
}
