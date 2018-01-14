
package Models;

import java.io.Serializable;
import java.util.ArrayList;


public class Track implements Serializable{
    
    public String name;
    public String artist;
    public String thumbUrl;
    public String coverUrl;
    public String videoUrl;
    public String year;
    
    public transient ArrayList<ArtistEvent> events;
    
    public Track(){
        
    }
    
}
