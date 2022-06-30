
package com.arcadio.triplover.models.uidecoration;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class UiDecoration {

    @SerializedName("init")
    @Expose
    private Init init= new Init();

    public Init getInit() {
        return init;
    }

    public void setInit(Init init) {
        this.init = init;
    }

}
