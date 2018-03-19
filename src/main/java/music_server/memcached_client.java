
package music_server;


import Models.ArtistEvent;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import ru.blizzed.discogsdb.model.release.Release;

public class memcached_client {
    
    MemcachedClient client;
    String redisLabsURL = "memcached-16414.c1.us-west-2-2.ec2.cloud.redislabs.com:16414";
    Gson gson = new Gson();
    
    public memcached_client() {
                 
    }
    
    public void Connect(){
        
        System.out.println("Connecting..."); 
        isReachable(redisLabsURL);  
        
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
					AddrUtil.getAddresses("memcached-16414.c1.us-west-2-2.ec2.cloud.redislabs.com:16414"));
	// Must use binary protocol                            
	builder.setCommandFactory(new BinaryCommandFactory());
        try {
            client = builder.build();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }       
    }
    
    public void Disconnect(){
        if(client!=null){
           //client.shutdown();
           client = null;
        }
    }
    
    public void SaveTitle(String title) throws Exception{
        client.set("current_title", 0, title);      
    } 
    
    public void SaveRelease(Release release) throws Exception{       
        String stringRelease = gson.toJson(release); 
        client.set("current_release", 0, stringRelease);
    }
    
    public void SaveEvents(ArrayList<ArtistEvent> events) throws Exception{      
        client.set("currentTrackEvents", 0, events); 
    }

    public static void isReachable(String address) {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            boolean isReachable = inetAddress.isReachable(5000);
            System.out.println("Is the address [%s] reachable? -%s\n", address, isReachable ? "Yes" : "No");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


}
  