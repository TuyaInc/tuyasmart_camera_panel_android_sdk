package com.tuya.smart.android.demo.camera;

import com.tuya.smart.android.camera.api.ITuyaHomeCamera;
import com.tuya.smart.android.camera.api.bean.CameraPushDataBean;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.ITuyaGetBeanCallback;

/**
 * 消息推送，内部实现
 * 可以从系统的推送信息中获取，其中如果消息体中携带有type=doorbell，为普通门铃推送消息；type =ac_doorbell，为视频流门铃推送。
 * 注册门铃推送消息监听，在app进程活着的时候，提升消息的到达率和速度
 */
public class CameraPushHelper {
    private static final String TAG = "CameraPushHelper";

    private ITuyaHomeCamera homeCamera;

    private static ITuyaGetBeanCallback<CameraPushDataBean> mTuyaGetBeanCallback = new ITuyaGetBeanCallback<CameraPushDataBean>() {
        @Override
        public void onResult(CameraPushDataBean o) {
            L.d(TAG, "onMqtt_43_Result on callback");
            L.d(TAG, "消息时间戳：timestamp=" + o.getTimestamp());
            L.d(TAG, "设备id：devid=" + o.getDevId());
            L.d(TAG, "消息id：msgid=" + o.getEdata());
            L.d(TAG, "消息类型：etype=" + o.getEtype());
        }
    };

    /**
     * 在账号登陆成功之后注册
     */
    public void registerCameraPushListener() {
        homeCamera = TuyaHomeSdk.getCameraInstance();
        if (homeCamera != null) {
            homeCamera.registerCameraPushListener(mTuyaGetBeanCallback);
        }
    }

    /**
     * 在账号注销之后进行反注册
     */
    public void unRegisterCameraPushListener() {
        if (homeCamera != null) {
            homeCamera.unRegisterCameraPushListener(mTuyaGetBeanCallback);
        }
    }
}
