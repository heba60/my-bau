package com.uni.bau.managers;

/**
 * Created by moayed on 9/21/17.
 */

public interface ApiCallResponse {

    void onSuccess(Object responseObject, String responseMessage);

    void onFailure(String errorResponse);


}

