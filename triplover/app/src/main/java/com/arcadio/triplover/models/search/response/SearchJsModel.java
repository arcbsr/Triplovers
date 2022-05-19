
package com.arcadio.triplover.models.search.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SearchJsModel {

    @SerializedName("item1")
    @Expose
    private Item1 item1;
    @SerializedName("item2")
    @Expose
    private List<Item2> item2 = null;

    public Item1 getItem1() {
        return item1;
    }

    public void setItem1(Item1 item1) {
        this.item1 = item1;
    }

    public List<Item2> getItem2() {
        return item2;
    }

    public void setItem2(List<Item2> item2) {
        this.item2 = item2;
    }

}
