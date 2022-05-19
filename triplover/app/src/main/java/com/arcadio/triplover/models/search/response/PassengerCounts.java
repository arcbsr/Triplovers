
package com.arcadio.triplover.models.search.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PassengerCounts {

    @SerializedName("cnn")
    @Expose
    private Integer cnn;
    @SerializedName("inf")
    @Expose
    private Integer inf;
    @SerializedName("adt")
    @Expose
    private Integer adt;
    @SerializedName("ins")
    @Expose
    private Integer ins;

    public Integer getCnn() {
        return cnn;
    }

    public void setCnn(Integer cnn) {
        this.cnn = cnn;
    }

    public Integer getInf() {
        return inf;
    }

    public void setInf(Integer inf) {
        this.inf = inf;
    }

    public Integer getAdt() {
        return adt;
    }

    public void setAdt(Integer adt) {
        this.adt = adt;
    }

    public Integer getIns() {
        return ins;
    }

    public void setIns(Integer ins) {
        this.ins = ins;
    }

}
