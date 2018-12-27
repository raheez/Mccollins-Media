package com.example.muhammedraheezrahman.mccollinsmedia.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Destination {

    @SerializedName("iserror")
    @Expose
    private String iserror;
    @SerializedName("data")
    @Expose
    private List<DestinationDetail> data = null;

    public String getIserror() {
        return iserror;
    }

    public void setIserror(String iserror) {
        this.iserror = iserror;
    }

    public List<DestinationDetail> getData() {
        return data;
    }

    public void setData(List<DestinationDetail> data) {
        this.data = data;
    }

    public class DestinationDetail {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("timing")
        @Expose
        private String timing;
        @SerializedName("sitelink")
        @Expose
        private String sitelink;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getSitelink() {
            return sitelink;
        }

        public void setSitelink(String sitelink) {
            this.sitelink = sitelink;
        }

    }

}