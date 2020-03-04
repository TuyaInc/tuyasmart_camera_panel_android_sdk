package com.tuya.smart.android.demo.camera;

import android.content.Context;

import com.tuya.smart.android.demo.base.utils.ToastUtil;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.panel.base.data.DeviceInfoRepositoryImpl;
import com.tuya.smart.panel.base.interactor.ModifyDevInfoInteractor;
import com.tuya.smart.panel.base.interactor.impl.ModifyDevInfoInteractorImpl;
import com.tuya.smart.panel.base.interactor.repository.DeviceInfoRepository;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.smart.sdk.api.ITuyaDevice;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.uispecs.component.ProgressUtils;

import java.io.File;

/**
 * 修改设备信息相关
 */
public class CameraModifyDeviceInfoHelper {

    /**
     * 修改名称
     *
     * @param context
     * @param deviceId   设备id
     * @param deviceName 设备重命名名称
     */
    public void renameDevice(final Context context, String deviceId, String deviceName) {
        ITuyaDevice mDevice = TuyaHomeSdk.newDeviceInstance(deviceId);
        mDevice.renameDevice(deviceName, new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                ToastUtil.showToast(context, code + " " + error);

            }

            @Override
            public void onSuccess() {
                ToastUtil.showToast(context, "success");
            }
        });
    }

    /**
     * 修改设备头像
     *
     * @param context
     * @param deviceId     设备id
     * @param iconFilePath 设备头像地址
     */
    public void uploadIcon(final Context context, String deviceId, String iconFilePath) {
        DeviceBean deviceBean = TuyaHomeSdk.getDataInstance().getDeviceBean(deviceId);
        String panelName = "";
        if (deviceBean != null) {
            panelName = deviceBean.getName();
        }
        DeviceInfoRepository deviceInfoRepository = new DeviceInfoRepositoryImpl(context);
        ModifyDevInfoInteractor mModifyDevInfoInteractor = new ModifyDevInfoInteractorImpl(deviceInfoRepository);
        ProgressUtils.showLoadingViewFullPage(context);
        mModifyDevInfoInteractor.modifyDeviceImg(deviceId, panelName, new File(iconFilePath),
                new ModifyDevInfoInteractor.ModifyDeviceImgCallback() {
                    @Override
                    public void onModifyDeviceImgSuccess(String url) {
                        ToastUtil.shortToast(context, url);
                        ProgressUtils.hideLoadingViewFullPage();
                    }

                    @Override
                    public void onModifyDeviceImgFailure() {
                        ToastUtil.shortToast(context, "failure");
                        ProgressUtils.hideLoadingViewFullPage();
                    }
                });
    }
}
