
package com.arcadio.triplover.models.uidecoration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Init {

    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("last_version")
    @Expose
    private String lastVersion;
    @SerializedName("emergency")
    @Expose
    private Emergency emergency = new Emergency();
    @SerializedName("home")
    @Expose
    private Home home = new Home();

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

}
