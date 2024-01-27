package com.uni.bau.managers;

import android.content.Context;
import com.uni.bau.helpers.ApiConstants;
import java.util.HashMap;
import java.util.Map;

public class ChatBusinessManager {

    public void getChatAPI(Context context,String lectureId,String page, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("LectureId",lectureId);
        Params.put("page",page);
        ChatConnectionManager.GET(context, ApiConstants.getChat,Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    callResponse.onSuccess(responseObject, responseMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });
    }

    public void sendMessageAPI(Context context,String LectureId,String Msg, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("LectureId",LectureId);
        Params.put("Msg",Msg);
        new ChatConnectionManager().PostRAW(context, ApiConstants.sendMessageChat,Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    callResponse.onSuccess(responseObject, responseMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });
    }

    public void sendMessageAPIDR(Context context,String LectureId,String Msg,String File, String Extension, final ApiCallResponse callResponse) {
        Map<String, String> Params = new HashMap<>();
        Params.put("LectureId",LectureId);
        Params.put("Msg",Msg);
        Params.put("File",File);
        Params.put("Extension",Extension);
        new ChatConnectionManager().PostRAW(context, ApiConstants.sendMessageChat,Params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    callResponse.onSuccess(responseObject, responseMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
                callResponse.onFailure(errorResponse);
            }
        });
    }
}
