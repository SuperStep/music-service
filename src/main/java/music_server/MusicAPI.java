package music_server;

import Models.ArtistEvent;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import ru.blizzed.discogsdb.DiscogsAuthData;
import ru.blizzed.discogsdb.DiscogsDBApi;
import ru.blizzed.discogsdb.model.Page;
import ru.blizzed.discogsdb.model.release.Release;
import ru.blizzed.discogsdb.model.search.ReleaseSearchResult;
import ru.blizzed.discogsdb.params.DiscogsDBParams;
public class MusicAPI {
    
    String apiKey = "klSWCkkVXcXpbcLnaFII"; 
    String secret = "btaqWtpXfdTSJfmEdIzzfiBKpQYcdemr";
    String url;
    ArrayList<ArtistEvent> events;
    
    Gson gson = new Gson();
 
    public MusicAPI(){    
        DiscogsDBApi.initialize(new DiscogsAuthData("klSWCkkVXcXpbcLnaFII",
                "btaqWtpXfdTSJfmEdIzzfiBKpQYcdemr"));
    }
    
    public Release getRelease(String artist, String name) throws Exception{

        Page<ReleaseSearchResult> page = DiscogsDBApi.searchRelease(
               DiscogsDBParams.ARTIST.of(artist),
               DiscogsDBParams.TRACK.of(name)
        ).execute(); 
        
        if(!page.getContent().isEmpty()){
            ReleaseSearchResult releaseSearchResult = page.getContent().get(0);
            Long releaseId = releaseSearchResult.getId();
            Release release = DiscogsDBApi.getRelease(releaseId).execute();
            return release;
        }else{
            return null;
        }
          
    }
   
    public ArrayList<ArtistEvent> getEvents(String artist) throws Exception{
        
        String artistQuery = (artist == null || artist == "")?"":artist.replaceAll(" ", "%20");
        
        if(artistQuery != ""){

            url = "https://rest.bandsintown.com/artists/"
                   + artistQuery 
                   + "/events?app_id=rock-online";
            url = url.replaceAll(" ", "");     
            
            Reader reader = new InputStreamReader(new URL(url).openStream());
            
         
            JsonArray results = gson.fromJson(reader, JsonArray.class);         
            events = new ArrayList<>();

            try{
                for (int i = 0; i < results.size(); i++) {
                    JsonObject ResultRow = (JsonObject)results.get(i); 
                    JsonObject venue = (JsonObject)ResultRow.get("venue");
                    if(venue != null){
                       ArtistEvent newEvent = new ArtistEvent();
                       newEvent.setName(venue.get("name").getAsString());
                       newEvent.setCountry(venue.get("country").getAsString());
                       newEvent.setCity(venue.get("city").getAsString());
                       newEvent.setDatetime(ResultRow.get("datetime").getAsString());
                       events.add(newEvent);                   
                    }   
                }
                
            return events;
            }
            catch(Exception e){return null;}           
        }else{
            return null;
        }
          
    }
            

}
