package music_server;

import Models.ArtistEvent;
import ru.blizzed.discogsdb.model.release.Release;

import java.util.ArrayList;

public class MusicService {

    String currentTitle = "";
    String newTitle;
    
    memcached_client cache = new memcached_client();
    MusicAPI musicAPI = new MusicAPI();
    StreamMetadata metadata = new StreamMetadata();

    
    public MusicService(){     
    }
    
    public void Start() {

        try {
            newTitle = metadata.getTitle();

            if (currentTitle != newTitle) {

                cache.Connect();
                currentTitle = newTitle;
                ArrayList<ArtistEvent> events = musicAPI.getEvents(metadata.getArtist());
                Release release = musicAPI.getRelease(metadata.getArtist(), metadata.getSong());

                cache.SaveRelease(release);
                cache.SaveEvents(events);
                cache.SaveTitle(currentTitle);

                System.out.println("Updated title: " + currentTitle);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
