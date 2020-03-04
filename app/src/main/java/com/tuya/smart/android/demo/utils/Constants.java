package com.tuya.smart.android.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * huangdaju
 * 2018/12/17
 **/

public class Constants {

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;
    public static final int EXTERNAL_AUDIO_REQ_CODE = 11;

    public synchronized static boolean hasStoragePermission() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a.log";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                boolean iscreate = file.createNewFile();
                if (iscreate) {
                    file.delete();
                    return true;
                } else {
                    return false;
                }
            } else {
                file.delete();
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
    public synchronized static boolean requestPermission(Context context, String permission, int requestCode, String tip) {
        //判断当前Activity是否已经获得了该权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    permission)) {
                Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},
                        requestCode);
                return false;
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean hasRecordPermission() {
        int minBufferSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        int bufferSizeInBytes = 640;
        byte[] audioData = new byte[bufferSizeInBytes];
        int readSize = 0;
        AudioRecord audioRecord = null;
        try {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 8000,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
            // 开始录音
            audioRecord.startRecording();
        } catch (Exception e) {
            //可能情况一
            if (audioRecord != null) {
                audioRecord.release();
                audioRecord = null;
            }
            return false;
        }
        // 检测是否在录音中,6.0以下会返回此状态
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            //可能情况二
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;
            }
            return false;
        } else {// 正在录音
            readSize = audioRecord.read(audioData, 0, bufferSizeInBytes);
            // 检测是否可以获取录音结果
            if (readSize <= 0) {
                //可能情况三
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                }
                Log.e("ss", "没有获取到录音数据，无录音权限");
                return false;
            } else {
                //有权限，正常启动录音并有数据
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                }
                return true;
            }
        }
    }
}
