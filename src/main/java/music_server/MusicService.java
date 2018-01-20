package music_server;

import Models.Track;
import java.net.URL;
import java.util.Timer;
import javax.jws.WebService;

@WebService
public class MusicService {
    
    URL streamURL;
    String currentTitle;
    public Track currentTrack;
    
    Stream.IcyStreamMeta stream = new Stream.IcyStreamMeta();
    MusicAPI musicAPI = new MusicAPI();
    
    Timer updateTimer = new Timer(true);
    
    public MusicService(){     
    }
    
    public void Start() throws Exception{
        //GET STREAM METADATA 
        stream.setStreamUrl(new URL("http://skycast.su:2007/rock-online"));
        stream.refreshMeta();
        
        
        if(currentTitle != stream.getStreamTitle()){

            memcached_client cache = new memcached_client();
            cache.Connect();

            currentTitle = stream.getStreamTitle();
            currentTrack = musicAPI.GetTrackInfo(stream.getArtist(), stream.getTitle());
            
            cache.SaveTrack(currentTrack);
            cache.SaveTitle(currentTitle);
            
            System.out.println("Updated title: " + currentTitle);
            
        }else{
            System.out.println("Title " + currentTitle + "is same.");
        }
    } 
}
 
