
package com.arcadio.triplover.models.payments.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PassengerFares {

    @SerializedName("adt")
    @Expose
    private Adt adt;
    @SerializedName("cnn")
    @Expose
    private Adt cnn;
    @SerializedName("inf")
    @Expose
    private Adt inf;
    @SerializedName("ins")
    @Expose
    private Adt ins;

    public Adt getAdt() {
        return adt;
    }

    public void setAdt(Adt adt) {
        this.adt = adt;
    }

    public Adt getCnn() {
        return cnn;
    }

    public void setCnn(Adt cnn) {
        this.cnn = cnn;
    }

    public Adt getInf() {
        return inf;
    }

    public void setInf(Adt inf) {
        this.inf = inf;
    }

    public Adt getIns() {
        return ins;
    }

    public void setIns(Adt ins) {
        this.ins = ins;
    }

}
