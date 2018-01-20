package music_server;

import Models.ArtistEvent;
import Models.Track;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class MusicAPI {
    
    String apiKey = "klSWCkkVXcXpbcLnaFII"; 
    String secret = "btaqWtpXfdTSJfmEdIzzfiBKpQYcdemr";
    String url;
    ArrayList<ArtistEvent> events;
    
    JSONObject jsonObj;
 
    public MusicAPI(){     
    }
     
    public Track GetTrackInfo(String artist, String name) throws MalformedURLException, Exception{
        
        String artistQuery = (artist == null || artist == "")?"":"&artist=" + artist.replaceAll(" ", "%20");
        String trackQuery = (name == null || name == "")?"":"&track=" + artist.replaceAll(" ", "%20");
                
        url = "https://api.discogs.com/database/search?"
               + "type=release" 
               + artistQuery
               + trackQuery
               + "&key=" + apiKey + "&secret=" + secret;
        url = url.replaceAll(" ", "%20");
        
        
        Track track = new Track();
        
        //GET FIRST RAW DATA
        jsonObj = (JSONObject)ParceUrl(url); 
    
        JSONArray results = (JSONArray) jsonObj.get("results");
        if(!results.isEmpty()){
           
            JSONObject firstResult = (JSONObject)results.get(0);
            track.thumbUrl = (String)firstResult.get("thumb");
            track.year = (String)firstResult.get("year");

            String fullDataURL = (String)firstResult.get("resource_url");
            fullDataURL += "?key=" + apiKey + "&secret=" + secret;
            //GET FULL DATA
            jsonObj = (JSONObject)ParceUrl(fullDataURL); 
            //FULL COVER
            results = (JSONArray) jsonObj.get("images");
            firstResult = (JSONObject)results.get(0);    
            track.coverUrl = (String)firstResult.get("uri");
            
        }
    
     
        track.name = name;
        track.artist = artist;
        track.events = getEvents(artist);
        
        return track;   
    }
    
    public ArrayList<ArtistEvent> getEvents(String artist) throws Exception{
        
        String artistQuery = (artist == null || artist == "")?"":URLEncoder.encode(artist,"UTF8");
        
        if(artistQuery != ""){

            url = "https://rest.bandsintown.com/artists/"
                   + artistQuery 
                   + "/events?app_id=rock-online";
            url = url.replaceAll(" ", "");     
            JSONArray results = (JSONArray)ParceUrl(url); 

            events = new ArrayList<>();

            try{
                for (int i = 0; i < results.size(); i++) {
                    JSONObject ResultRow = (JSONObject)results.get(i); 
                    JSONObject venue = (JSONObject)ResultRow.get("venue");
                    if(venue != null){
                       ArtistEvent newEvent = new ArtistEvent();
                       newEvent.setName((String)venue.get("name"));
                       newEvent.setCountry((String)venue.get("country"));
                       newEvent.setCity((String)venue.get("city"));
                       newEvent.setDatetime((String)ResultRow.get("datetime"));
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
            
    private static Object ParceUrl(String urlString) throws Exception {
        
        JSONParser parser = new JSONParser();
        
        
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return parser.parse(buffer.toString());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
