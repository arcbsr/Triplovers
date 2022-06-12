package com.arcadio.triplover.models.payments.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PaymentReq {

    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("ssl_response")
    @Expose
    private SSLCTransactionInfoModel sslResponse;

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

    public SSLCTransactionInfoModel getSslResponse() {
        return sslResponse;
    }

    public void setSslResponse(SSLCTransactionInfoModel sslResponse) {
        this.sslResponse = sslResponse;
    }
}
