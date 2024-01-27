package com.uni.bau.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.sdsmdg.tastytoast.ConfusingToastView;
import com.sdsmdg.tastytoast.DefaultToastView;
import com.sdsmdg.tastytoast.ErrorToastView;
import com.sdsmdg.tastytoast.InfoToastView;
import com.sdsmdg.tastytoast.SuccessToastView;
import com.sdsmdg.tastytoast.WarningToastView;

public class CustomTastyToast {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;


    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int INFO = 4;
    public static final int DEFAULT = 5;
    public static final int CONFUSING = 6;

    static SuccessToastView successToastView;
    static WarningToastView warningToastView;
    static ErrorToastView errorToastView;
    static InfoToastView infoToastView;
    static DefaultToastView defaultToastView;
    static ConfusingToastView confusingToastView;

    public static Toast makeText(Context context, String msg, int length, int type) {

        Toast toast = new Toast(context);


        switch (type) {
            case 1: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.success_toast_layout, null, false);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                text.setTypeface(Utils.SetTFace(context));
                text.setText(msg);
                successToastView = (SuccessToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.successView);
                successToastView.startAnim();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.success_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 2: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.warning_toast_layout, null, false);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                text.setText(msg);

                warningToastView = (WarningToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.warningView);
                SpringSystem springSystem = SpringSystem.create();
                final Spring spring = springSystem.createSpring();
                spring.setCurrentValue(1.8);
                SpringConfig config = new SpringConfig(40, 5);
                spring.setSpringConfig(config);
                spring.addListener(new SimpleSpringListener() {

                    @Override
                    public void onSpringUpdate(Spring spring) {
                        float value = (float) spring.getCurrentValue();
                        float scale = (float) (0.9f - (value * 0.5f));

                        warningToastView.setScaleX(scale);
                        warningToastView.setScaleY(scale);
                    }
                });
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        spring.setEndValue(0.4f);
                    }
                });

                t.start();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.warning_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 3: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.error_toast_layout, null, false);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                text.setText(msg);
                errorToastView = (ErrorToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.errorView);
                errorToastView.startAnim();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.error_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 4: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.info_toast_layout, null, false);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                text.setText(msg);
                infoToastView = (InfoToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.infoView);
                infoToastView.startAnim();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.info_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 5: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.default_toast_layout, null, false);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                text.setText(msg);
                defaultToastView = (DefaultToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.defaultView);
                defaultToastView.startAnim();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.default_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 6: {
                View layout = LayoutInflater.from(context).inflate(com.sdsmdg.tastytoast.R.layout.confusing_toast_layout, null, false);
                layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                TextView text = (TextView) layout.findViewById(com.sdsmdg.tastytoast.R.id.toastMessage);
                text.setText(msg);
                confusingToastView = (ConfusingToastView) layout.findViewById(com.sdsmdg.tastytoast.R.id.confusingView);
                confusingToastView.startAnim();
                text.setBackgroundResource(com.sdsmdg.tastytoast.R.drawable.confusing_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
        }
        toast.setDuration(length);
        toast.show();
        return toast;
    }

}

