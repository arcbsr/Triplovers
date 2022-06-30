
package com.arcadio.triplover.models.uidecoration;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Emergency {

    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("url")
    @Expose
    private String url;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
