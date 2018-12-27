package com.example.muhammedraheezrahman.mccollinsmedia.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginDetails {




        @SerializedName("iserror")
        @Expose
        private String iserror;
        @SerializedName("data")
        @Expose
        private List<User> data = null;

        public String getIserror() {
            return iserror;
        }

        public void setIserror(String iserror) {
            this.iserror = iserror;
        }

        public List<User> getData() {
            return data;
        }

        public void setData(List<User> data) {
            this.data = data;
        }

    public class User {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("fname")
        @Expose
        private String fname;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("userimage")
        @Expose
        private String userimage;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUserimage() {
            return userimage;
        }

        public void setUserimage(String userimage) {
            this.userimage = userimage;
        }

    }



}
