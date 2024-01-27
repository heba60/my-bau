package com.uni.bau.utilities;

import android.content.Context;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginingHandler {

    public void handleExpiredSesstion (Context context,ApiCallResponse apiCallResponse)
    {
        ProgressUtil.INSTANCE.showLoading(context);
        new BusinessManager().captchaAPI(context, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                Document html = Jsoup.parse(responseObject.toString());
                String viewState = null;
                try {
                    viewState = html.getElementById("j_id1:javax.faces.ViewState:0").attr("value");
                    new Utils().setCaptaValue(viewState,context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (viewState == null){
                    apiCallResponse.onFailure("No secure variable");
                    return;
                }
                try {
                    new BusinessManager().loginNow(context,new Utils().getUserData(context).getUsername(),new Utils().getUserData(context).getPassword(),viewState, new ApiCallResponse() {
                        @Override
                        public void onSuccess(Object responseObject, String responseMessage) {
                            apiCallResponse.onSuccess(responseObject.toString(),"");
                        }
                        @Override
                        public void onFailure(String errorResponse) {
                            ProgressUtil.INSTANCE.hideLoading();
                        }
                    });
                }catch (Exception e)
                {
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();

            }
        });
    }
}
