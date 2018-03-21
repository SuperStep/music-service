package music_server;


import net.moraleboost.streamscraper.Scraper;
import net.moraleboost.streamscraper.Stream;
import net.moraleboost.streamscraper.scraper.IceCastScraper;

import java.net.URI;
import java.util.List;

public class StreamMetadata {

    Scraper scraper = new IceCastScraper();
    Stream stream;

    public StreamMetadata(){

    }

    public Stream getMetadata(){
        List<Stream> streams = null;
        try {
            streams = scraper.scrape(new URI("http://skycast.su:2007/rock-online"));
            stream = streams.get(14);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return stream;
    }

    public String getArtist(){
        stream = getMetadata();
        String streamTitle = stream.getCurrentSong();
        String title = streamTitle.substring(0, streamTitle.indexOf("-"));
        return title.trim();
    }

    public String getSong() {
        stream = getMetadata();
        String streamTitle = stream.getCurrentSong();
        String artist = streamTitle.substring(streamTitle.indexOf("-") + 1);
        return artist.trim();
    }

    public String getTitle(){
        stream = getMetadata();
        return stream.getCurrentSong();
    }
}
