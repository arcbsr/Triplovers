package com.arcadio.triplover.models.uidecoration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class SearchOfferPlace3 {

    @SerializedName("image")
    @Expose
    private String image = "";
    @SerializedName("link")
    @Expose
    private String link = "";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}