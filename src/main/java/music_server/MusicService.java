package music_server;

import Models.ArtistEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.jws.WebService;
import ru.blizzed.discogsdb.model.release.Release;

@WebService
public class MusicService {
    
    URL streamURL;
    String currentTitle;
    
    String newTitle;
    
    memcached_client cache = new memcached_client();
    Stream.IcyStreamMeta stream = new Stream.IcyStreamMeta();
    MusicAPI musicAPI = new MusicAPI();
    
    public MusicService(){     
    }
    
    public void Start(){
        //GET STREAM METADATA 
        try{
            stream.setStreamUrl(new URL("http://skycast.su:2007/rock-online"));
            stream.refreshMeta();
            newTitle = stream.getStreamTitle();

            if(currentTitle != newTitle){

                cache.Connect();
                currentTitle = newTitle;
                ArrayList<ArtistEvent> events = musicAPI.getEvents(stream.getArtist());
                Release release = musicAPI.getRelease(stream.getArtist(), stream.getTitle());

                cache.SaveRelease(release);
                cache.SaveEvents(events);
                cache.SaveTitle(currentTitle);

                System.out.println("Updated title: " + currentTitle);

            }else{
                System.out.println("Title " + currentTitle + "is same.");
            }
        
        }catch(Exception ex){
            System.err.println(ex.toString());
        }
    } 
}
 
