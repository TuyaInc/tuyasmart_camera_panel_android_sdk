package com.tuya.smart.android.demo.config;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.common.utils.WiFiUtil;
import com.tuya.smart.android.demo.R;
import com.tuya.smart.android.demo.base.utils.ToastUtil;
import com.tuya.smart.android.demo.family.FamilyManager;
import com.tuya.smart.android.demo.utils.QRCodeUtil;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.builder.TuyaCameraActivatorBuilder;
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken;
import com.tuya.smart.sdk.api.ITuyaCameraDevActivator;
import com.tuya.smart.sdk.api.ITuyaSmartCameraActivatorListener;
import com.tuya.smart.sdk.bean.DeviceBean;

public class QrCodeConfigActivity extends AppCompatActivity implements ITuyaSmartCameraActivatorListener {
    TextView textView;
    EditText editText;

    private String wifiSSId = "";
    private String token = "";
    private String wifiPwd = "Tuya.140616";
    private ImageView mIvQr;
    private ITuyaCameraDevActivator mTuyaActivator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_config);
        mIvQr = findViewById(R.id.iv_qrcode);
        init();
    }

    private void init() {
        wifiSSId = WiFiUtil.getCurrentSSID(this);

        textView = findViewById(R.id.name);
        textView.setText(wifiSSId);

        editText = findViewById(R.id.pwd);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuyaHomeSdk.getActivatorInstance().getActivatorToken(FamilyManager.getInstance().getCurrentHomeId(), new ITuyaActivatorGetToken() {
                    @Override
                    public void onSuccess(String s) {
                        token = s;
                        createQrcode();
                    }

                    @Override
                    public void onFailure(String s, String s1) {

                    }
                });
            }
        });
    }

    private void createQrcode() {
        wifiPwd = editText.getText().toString();
        TuyaCameraActivatorBuilder builder = new TuyaCameraActivatorBuilder()
                .setToken(token).setPassword(wifiPwd).setSsid(wifiSSId).setListener(this);
        mTuyaActivator = TuyaHomeSdk.getActivatorInstance().newCameraDevActivator(builder);
        mTuyaActivator.createQRCode();
        mTuyaActivator.start();
    }


    @Override
    public void onQRCodeSuccess(String s) {
        final Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(s, 300, 300);
        QrCodeConfigActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIvQr.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onError(String s, String s1) {
        L.e("wifi", "config failed:" + s1);
    }

    @Override
    public void onActiveSuccess(DeviceBean deviceBean) {
        ToastUtil.shortToast(QrCodeConfigActivity.this, " config success！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTuyaActivator.stop();
        mTuyaActivator.onDestroy();
    }
}
