
package com.arcadio.triplover.models.payments.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("item1")
    @Expose
    private Item1 item1;
    @SerializedName("item2")
    @Expose
    private Item2 item2;

    public Item1 getItem1() {
        return item1;
    }

    public void setItem1(Item1 item1) {
        this.item1 = item1;
    }

    public Item2 getItem2() {
        return item2;
    }

    public void setItem2(Item2 item2) {
        this.item2 = item2;
    }

}
