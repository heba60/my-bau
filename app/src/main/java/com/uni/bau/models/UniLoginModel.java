package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniLoginModel {

    public class Data {

        @SerializedName("Id")
        @Expose
        private String id;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("GenderId")
        @Expose
        private Integer genderId;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Token")
        @Expose
        private String token;
        @SerializedName("StudentNumber")
        @Expose
        private String studentNumber;
        @SerializedName("Specialty")
        @Expose
        private String specialty;
        @SerializedName("College")
        @Expose
        private String college;
        @SerializedName("LinkedinAccount")
        @Expose
        private String linkedinAccount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Integer getGenderId() {
            return genderId;
        }

        public void setGenderId(Integer genderId) {
            this.genderId = genderId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getLinkedinAccount() {
            return linkedinAccount;
        }

        public void setLinkedinAccount(String linkedinAccount) {
            this.linkedinAccount = linkedinAccount;
        }

    }


        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

}
