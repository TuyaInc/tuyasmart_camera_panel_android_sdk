package com.tuya.smart.android.demo.base.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.demo.R;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.bean.scene.SceneBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.home.sdk.callback.ITuyaResultCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeKitTest extends AppCompatActivity {

    private static final String TAG = "HomeKitTest";
    private List<HomeBean> mHomeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kit_test);

        findViewById(R.id.tv_add_dev_to_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> rooms = new ArrayList<>();
                rooms.add("房间1");
                rooms.add("房间2");
                rooms.add("房间3");
                TuyaHomeSdk.getHomeManagerInstance().createHome("小爱的家", 0, 0, "nothing", rooms, new ITuyaHomeResultCallback() {
                    @Override
                    public void onSuccess(HomeBean bean) {
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {

                    }
                });

            }
        });

        findViewById(R.id.tv_query_hoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
                    @Override
                    public void onSuccess(final List<HomeBean> homeBeans) {
                        mHomeList = homeBeans;
//                        TuyaHomeSdk.newHomeInstance(homeBeans.get(0).getHomeId()).getHomeDetail(new ITuyaHomeResultCallback() {
//                            @Override
//                            public void onSuccess(HomeBean bean) {
//                                TuyaHomeSdk.newRoomInstance(bean.getRooms().get(0).getRoomId()).addDevice(bean.getDeviceList().get(0).getDevId(), new IResultCallback() {
//                                    @Override
//                                    public void onError(String s, String s1) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess() {
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onError(String errorCode, String errorMsg) {
//
//                            }
//                        });

                        TuyaHomeSdk.getSceneManagerInstance().getSceneList(homeBeans.get(0).getHomeId(), new ITuyaResultCallback<List<SceneBean>>() {
                            @Override
                            public void onSuccess(List<SceneBean> result) {
                                L.d(TAG, JSONObject.toJSONString(result));
                            }

                            @Override
                            public void onError(String errorCode, String errorMessage) {
                                L.d(TAG, "errorCode: " + errorCode);
                            }
                        });
                    }

                    @Override
                    public void onError(String errorCode, String error) {

                    }
                });
            }
        });
    }
}
