package com.webank.cloudface.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.webank.facelight.ui.FaceVerifyStatus;

/**
 * Created by leoraylei on 16/9/19.
 */

public class AppHandler {
    public static final int ERROR_DATA = -100;
    public static final int ERROR_LOCAL = -101;
    public static final String DATA_MODE_REFLECT = "data_mode_reflect";
    public static final String DATA_MODE_REFLECT_DESENSE = "data_mode_reflect_desense";
    public static final String DATA_MODE_ACT = "data_mode_act";
    public static final String DATA_MODE_ACT_DESENSE = "data_mode_act_desense";
    public static final String DATA_MODE_NUM = "data_mode_num";
    private static final int WHAT_SIGN = 1;
    private static final int ARG1_SUCCESS = 1;
    private static final int ARG1_FAILED = 2;
    private static final String DATA_MODE = "data_mode";
    private static final String DATA_SIGN = "data_sign";
    private static final String DATA_CODE = "data_code";
    private static final String DATA_MSG = "data_msg";
    private FaceVerifyDemoActivity activity;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_SIGN) {
                if (msg.arg1 == ARG1_SUCCESS) {
                    String sign = msg.getData().getString(DATA_SIGN);

                    String mode = msg.getData().getString(DATA_MODE);
                    switch (mode) {
                        case DATA_MODE_REFLECT:
                            activity.openCloudFaceService(FaceVerifyStatus.Mode.REFLECTION, sign);
                            break;
                        case DATA_MODE_REFLECT_DESENSE:
                            activity.getFaceId(FaceVerifyStatus.Mode.REFLECTION, sign);
                            break;
                        case DATA_MODE_ACT:
                            activity.openCloudFaceService(FaceVerifyStatus.Mode.ACT, sign);
                            break;
                        case DATA_MODE_ACT_DESENSE:
                            activity.getFaceId(FaceVerifyStatus.Mode.ACT, sign);
                            break;
                        case DATA_MODE_NUM:
                            activity.openCloudFaceService(FaceVerifyStatus.Mode.NUM, sign);
                            break;
                        default:
                            break;
                    }
                } else {
                    int code = msg.getData().getInt(DATA_CODE);
                    String message = msg.getData().getString(DATA_MSG);
                    Log.e("AppHandler", "请求失败:[" + code + "]," + message);
                    activity.hideLoading();
                }
            }
        }
    };


    public AppHandler(FaceVerifyDemoActivity activity) {
        this.activity = activity;
    }

    public void sendSignError(int code, String msg) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_FAILED;
        final Bundle data = new Bundle();
        data.putInt(DATA_CODE, code);
        data.putString(DATA_MSG, msg);
        message.setData(data);
        handler.sendMessage(message);
    }

    public void sendSignSuccess(String mode, String sign) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_SUCCESS;
        final Bundle data = new Bundle();
        data.putString(DATA_SIGN, sign);
        data.putString(DATA_MODE, mode);
        message.setData(data);
        handler.sendMessage(message);
    }
}
