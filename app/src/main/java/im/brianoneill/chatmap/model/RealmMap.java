package im.brianoneill.chatmap.model;

import io.realm.RealmObject;

/**
 * Created by brianoneill on 27/04/16.
 */
public class RealmMap extends RealmObject {

    private String realmMapName;
    private double realmLatitude;
    private double realmLongitude;
    private String realmDate;
    private String realmTime;

    public void setRealmMapName(String realmMapName) {
        this.realmMapName = realmMapName;
    }

    public void setRealmLatitude(double realmLatitude) {
        this.realmLatitude = realmLatitude;
    }

    public void setRealmLongitude(double realmLongitude) {
        this.realmLongitude = realmLongitude;
    }

    public void setRealmDate(String realmDate) {
        this.realmDate = realmDate;
    }

    public void setRealmTime(String realmTime) {
        this.realmTime = realmTime;
    }

}
