
package com.arcadio.triplover.models.search.response;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Item2 {

    @SerializedName("apiRef")
    @Expose
    private Integer apiRef;
    @SerializedName("uniqueTransID")
    @Expose
    private String uniqueTransID;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("requestTime")
    @Expose
    private String requestTime;
    @SerializedName("responseTime")
    @Expose
    private String responseTime;
    @SerializedName("conversionTime")
    @Expose
    private String conversionTime;
    @SerializedName("timeTicks")
    @Expose
    private Long timeTicks;
    @SerializedName("message")
    @Expose
    private Object message;

    public Integer getApiRef() {
        return apiRef;
    }

    public void setApiRef(Integer apiRef) {
        this.apiRef = apiRef;
    }

    public String getUniqueTransID() {
        return uniqueTransID;
    }

    public void setUniqueTransID(String uniqueTransID) {
        this.uniqueTransID = uniqueTransID;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(String conversionTime) {
        this.conversionTime = conversionTime;
    }

    public Long getTimeTicks() {
        return timeTicks;
    }

    public void setTimeTicks(Long timeTicks) {
        this.timeTicks = timeTicks;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}
