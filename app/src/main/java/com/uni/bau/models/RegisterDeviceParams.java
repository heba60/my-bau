package com.uni.bau.models;

public class RegisterDeviceParams {
    private String MobileDeviceId;
    private String DeviceId;
    private String Token;
    private String OS;

    public String getMobileDeviceId() {
        return MobileDeviceId;
    }

    public void setMobileDeviceId(String mobileDeviceId) {
        MobileDeviceId = mobileDeviceId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }
}

