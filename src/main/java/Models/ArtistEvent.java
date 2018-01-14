package Models;

import java.io.Serializable;

public class ArtistEvent implements Serializable {
    
    public String url;
    public String on_sale_datetime;
    public String datetime;
    public String name;
    public String latitude;
    public String longitude;
    public String city;
    public String region;
    public String country;
    
    //Tickets
    public String ticketsUrl;
    public String ticketsStatus;
    
    public void ArtistEvent(){
  
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDatetime(String datetime){
        this.datetime = datetime;
    }
      
    public String getCity(){
        return city;
    }
    
    public String getCountry(){
        return country;
    }    
    
    public String getName(){
       return name;
    }

    public String getDatetime(){
       return datetime;
    }
}
