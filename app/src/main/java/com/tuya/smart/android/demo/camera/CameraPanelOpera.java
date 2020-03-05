package com.tuya.smart.android.demo.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tuya.smart.android.panel.TuyaPanelSDK;
import com.tuya.smart.android.panel.api.ITuyaPanelLoadCallback;
import com.tuya.smart.camera.TuyaCameraPanelSDK;
import com.tuya.smart.camera.blackpanel.activity.CameraPanelActivity;
import com.tuya.smart.camera.blackpanel.activity.CameraPlaybackActivity;
import com.tuya.smart.ipc.camera.doorbellpanel.activity.DoorBellCallingActivity;
import com.tuya.smart.ipc.camera.doorbellpanel.activity.DoorBellDirectCameraActivity;
import com.tuya.smart.ipc.cloud.panel.activity.CameraCloudActivity;
import com.tuya.smart.ipc.localphotovideo.activity.LocalPhotoOrVideoActivity;
import com.tuya.smart.ipc.messagecenter.activity.IPCCameraMessageCenterActivity;
import com.tuya.smart.ipc.panelmore.activity.CameraSettingActivity;
import com.tuya.smart.sdk.bean.DeviceBean;

/**
 * @author chenbj
 * 面板跳转相关
 */
public class CameraPanelOpera {

    private CameraPanelOpera() {
    }

    public static CameraPanelOpera getInstance() {
        return CameraPanelOpera.CameraPanelOperaHolder.INSTANCE;
    }

    private static class CameraPanelOperaHolder {
        private static CameraPanelOpera INSTANCE = new CameraPanelOpera();

        private CameraPanelOperaHolder() {
        }
    }

    /**
     * 跳转到摄像头面板
     *
     * @param devBean
     */
    public void gotoCameraPanel(Context context, long homeId, DeviceBean devBean) {
        if (null == devBean || null == devBean.getProductBean()) {
            return;
        }
        //判断是什么面板
        if ("RN".equals(devBean.getProductBean().getUiInfo().getType())) {
            if (context instanceof Activity) {
                gotoRNCameraPanel((Activity) context, homeId, devBean.getDevId());
            }
        } else if ("NA".equals(devBean.getProductBean().getUiInfo().getType())) {
            if (context instanceof Activity) {
                gotoNativeCameraPanel(context, homeId, devBean.getDevId());
            }
        }
    }

    /**
     * 跳转到camera 预览界面入口
     *
     * @param context
     * @param deviceId
     */
    public void gotoNativeCameraPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        //方式一
        Bundle bundle = new Bundle();
        bundle.putString("extra_camera_uuid", deviceId);
        Intent intent = new Intent(context, CameraPanelActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        //方式二
//        UrlRouterUtils.gotoCameraPanelActivity(context, deviceId);


    }

    /**
     * 跳转到camera rn 预览界面入口
     *
     * @param context
     * @param homeId
     * @param deviceId
     */
    public void gotoRNCameraPanel(Activity context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        TuyaPanelSDK.getPanelInstance().gotoPanelViewControllerWithDevice(context, homeId, deviceId, new ITuyaPanelLoadCallback() {
            @Override
            public void onStart(String s) {
                //面板加载开始
            }

            @Override
            public void onError(String s, int i, String s1) {
                //面板加载错误
            }

            @Override
            public void onSuccess(String s) {
                //面板加载成功
            }

            @Override
            public void onProgress(String s, int i) {
                //面板加载中...
            }
        });
    }

    /**
     * 跳转到camera 门铃呼叫界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoDoorbellCallingPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Bundle bundle = new Bundle();
        bundle.putString("devId", deviceId);
        Intent intent = new Intent(context, DoorBellCallingActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
//        UrlBuilder urlBuilder = new UrlBuilder(context, ACTIVITY_CAMERA_DOOR_BELL).putExtras(bundle);
//        UrlRouter.execute(urlBuilder);
    }

    /**
     * 跳转到camera 视频流门铃呼叫界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoAcDoorbellCallingPanel(Context context, long homeId, String deviceId, long startTime) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Bundle bundle = new Bundle();
        bundle.putString("extra_camera_uuid", deviceId);
        bundle.putLong("doorbell_start_time", startTime);
        Intent intent = new Intent(context, DoorBellDirectCameraActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
//        UrlBuilder urlBuilder = new UrlBuilder(context, ACTIVITY_CAMERA_ACTION_DOORBELL).putExtras(bundle);
//        UrlRouter.execute(urlBuilder);
    }

    /**
     * 跳转到camera 回放界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoCameraPlaybackPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Intent intent = new Intent(context, CameraPlaybackActivity.class);
        intent.putExtra("extra_camera_uuid", deviceId);
        context.startActivity(intent);
    }

    /**
     * 跳转到camera 云存储界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoCameraCloudStoragePanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Intent intent = new Intent(context, CameraCloudActivity.class);
        intent.putExtra("extra_camera_uuid", deviceId);
        context.startActivity(intent);
    }

    /**
     * 跳转到camera 消息中心界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoCameraMessageCenterPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Intent intent = new Intent(context, IPCCameraMessageCenterActivity.class);
        intent.putExtra("extra_camera_uuid", deviceId);
        context.startActivity(intent);
    }

    /**
     * 跳转到camera 设置界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoCameraSettingPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Intent intent = new Intent(context, CameraSettingActivity.class);
        intent.putExtra("extra_camera_uuid", deviceId);
        context.startActivity(intent);
    }


    /**
     * 跳转到camera 本地相册界面
     *
     * @param context
     * @param deviceId
     */
    public void gotoCameraLocalAlbumPanel(Context context, long homeId, String deviceId) {
        TuyaCameraPanelSDK.setCurrentHomeId(homeId);
        Intent intent = new Intent(context, LocalPhotoOrVideoActivity.class);
        intent.putExtra("extra_camera_uuid", deviceId);
        context.startActivity(intent);
    }
}
