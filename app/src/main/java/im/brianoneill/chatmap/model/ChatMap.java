package im.brianoneill.chatmap.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brianoneill on 12/03/16.
 */
public class ChatMap {

    private String mapName;
    private String timeToLive;



    //internal app constructor
    public ChatMap(String mapName, String timeToLive) {
        this.mapName = mapName;
        this.timeToLive = timeToLive;
    }


//    public ChatMap(String mapName, HashMap<String, Object> dateCreated, double latitude, double longitude) {
//        this.mapName = mapName;
//        this.dateCreated = dateCreated;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }



    //map setter and getter
    public String getMapName(){
        return mapName;
    }

    public void setMapName(String mapName){
        this.mapName = mapName;
    }

    // time to live getter and setter
    public String getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
    }


    //TODO create compute method to generate time on instantiation


}
