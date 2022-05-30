
package com.arcadio.triplover.models.passenger.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DocumentInfo {

    @SerializedName("documentType")
    @Expose
    private String documentType = "Passport";
    @SerializedName("documentNumber")
    @Expose
    private String documentNumber = "";
    @SerializedName("expireDate")
    @Expose
    private String expireDate = "";
    @SerializedName("frequentFlyerNumber")
    @Expose
    private String frequentFlyerNumber = "";
    @SerializedName("issuingCountry")
    @Expose
    private String issuingCountry = "";
    @SerializedName("nationality")
    @Expose
    private String nationality = "";

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getFrequentFlyerNumber() {
        return frequentFlyerNumber;
    }

    public void setFrequentFlyerNumber(String frequentFlyerNumber) {
        this.frequentFlyerNumber = frequentFlyerNumber;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}
