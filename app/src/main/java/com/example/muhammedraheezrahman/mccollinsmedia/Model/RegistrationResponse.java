package com.example.muhammedraheezrahman.mccollinsmedia.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("iserror")
    @Expose
    private String iserror;
    @SerializedName("message")
    @Expose
    private String message;

    public String getIserror() {
        return iserror;
    }

    public void setIserror(String iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}