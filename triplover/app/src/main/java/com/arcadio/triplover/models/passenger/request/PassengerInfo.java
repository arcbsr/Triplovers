
package com.arcadio.triplover.models.passenger.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PassengerInfo {
    public String title = "";
    @SerializedName("nameElement")
    @Expose
    private NameElement nameElement;
    @SerializedName("contactInfo")
    @Expose
    private ContactInfo contactInfo;
    @SerializedName("documentInfo")
    @Expose
    private DocumentInfo documentInfo;
    @SerializedName("passengerType")
    @Expose
    private String passengerType;
    @SerializedName("gender")
    @Expose
    private String gender = "Male";
    private String genderDyn = "MR";
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("passengerKey")
    @Expose
    private String passengerKey = "0";
    @SerializedName("isLeadPassenger")
    @Expose
    private Boolean isLeadPassenger = true;
    @SerializedName("meal")
    @Expose
    private Object meal;

    public NameElement getNameElement() {
        if (nameElement == null) {
            nameElement = new NameElement();
        }
        return nameElement;
    }

    public void updatePassengerInfo(PassengerInfo passengerInfo) {
        this.nameElement = passengerInfo.nameElement;
        this.documentInfo = passengerInfo.documentInfo;
        this.contactInfo = passengerInfo.contactInfo;
        this.gender = passengerInfo.gender;
        this.passengerType = passengerInfo.passengerType;
        this.dateOfBirth = passengerInfo.dateOfBirth;
    }

    public void setNameElement(NameElement nameElement) {
        this.nameElement = nameElement;
    }

    public ContactInfo getContactInfo() {
        if (contactInfo == null) {
            contactInfo = new ContactInfo();
        }
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public DocumentInfo getDocumentInfo() {
        if (documentInfo == null) {
            documentInfo = new DocumentInfo();
        }
        return documentInfo;
    }

    public void setDocumentInfo(DocumentInfo documentInfo) {
        this.documentInfo = documentInfo;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGenderDyn() {
        return genderDyn;
    }

    public void setGenderDyn(String genderDyn) {
        this.genderDyn = genderDyn;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassengerKey() {
        return passengerKey;
    }

    public void setPassengerKey(String passengerKey) {
        this.passengerKey = passengerKey;
    }

    public Boolean getIsLeadPassenger() {
        return isLeadPassenger;
    }

    public void setIsLeadPassenger(Boolean isLeadPassenger) {
        this.isLeadPassenger = isLeadPassenger;
    }

    public Object getMeal() {
        return meal;
    }

    public void setMeal(Object meal) {
        this.meal = meal;
    }

}
