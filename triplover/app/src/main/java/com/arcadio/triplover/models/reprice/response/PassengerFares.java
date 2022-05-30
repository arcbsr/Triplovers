
package com.arcadio.triplover.models.reprice.response;

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
    private Object cnn;
    @SerializedName("inf")
    @Expose
    private Object inf;
    @SerializedName("ins")
    @Expose
    private Object ins;

    public Adt getAdt() {
        return adt;
    }

    public void setAdt(Adt adt) {
        this.adt = adt;
    }

    public Object getCnn() {
        return cnn;
    }

    public void setCnn(Object cnn) {
        this.cnn = cnn;
    }

    public Object getInf() {
        return inf;
    }

    public void setInf(Object inf) {
        this.inf = inf;
    }

    public Object getIns() {
        return ins;
    }

    public void setIns(Object ins) {
        this.ins = ins;
    }

}
