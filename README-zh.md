# 涂鸦智能摄像机 Panel SDK  - Android 版

[中文版](https://github.com/TuyaInc/tuyasmart_camera_panel_android_sdk/blob/master/README-zh.md) | [English](https://github.com/TuyaInc/tuyasmart_camera_panel_android_sdk/blob/master/README.md)

------

## 功能概述

涂鸦智能摄像机面板SDK（简称：TuyaCameraPanelSDK）为方便用户，减少摄像头面板开发成本，提供了涂鸦摄像头相关的面板，主要包括：

- Rn面板(详见 [TuyaPanelSDK文档](https://tuyainc.github.io/tuyasmart_panel_android_sdk_doc/))
- 原生native面板
- 普通门铃接听面板  （界面显示：实时图片+接听挂断）
- 视频流门铃接听面板（界面显示：实时视频流+接听挂断）
- 云存储面板
- 回放面板
- 设置面板
- 消息中心面板
- 本地相册面板
- 实现自定义面板
    - 自定义回放面板
    - 自定义云存储面板
    - 自定义本地相册面板
    - 自定义消息中心面板
    - 自定义设置面板
        - 自定义修改设备名称/头像
        - 自定义意见反馈

## 快速集成

**请使用 AndroidStudio (版本号 3.1.3及更高版本)**
> AndroidStudio的使用请参考: [AndroidStudio Guides](https://developer.android.com/studio/)

### 集成前提
TuyaCameraPanelSDK 是基于涂鸦全屋智能SDK（TuyaHomeSdk） 3.13.0版本上开发

集成 TuyaCameraPanelSDK 之前，需要做以下工作：
1. 集成TuyaHomeSdk完成，参考[集成文档](https://tuyainc.github.io/tuyasmart_home_android_sdk_doc/zh-hans/)（包括申请tuya App ID和App Secret、安全图片配置相关环境）
2. 摄像头设备配网完成

### 集成SDK

- 创建项目工程，接入 TuyaCameraPanelSDK 并配置完成
- 在根目录build.gradle添加maven地址：

    ```java
    allprojects {
            repositories {
                //***** required start ****//
                maven { url 'https://raw.githubusercontent.com/TuyaInc/mavenrepo/master/snapshots' }
                maven { url 'https://raw.githubusercontent.com/TuyaInc/mavenrepo/master/releases' }
                maven {
                url "https://maven-other.tuya.com/repository/maven-releases/"
                }
                maven { url 'https://jitpack.io' }
                //***** required end ****//
                google()
                jcenter()
                mavenCentral()
                
            }
        }
        
        buildscript {
            repositories {
                maven { url 'https://raw.githubusercontent.com/TuyaInc/mavenrepo/master/snapshots' }
                maven { url 'https://raw.githubusercontent.com/TuyaInc/mavenrepo/master/releases' }
                maven { url "https://jitpack.io" }
                mavenLocal()
                mavenCentral()
                google()
                jcenter()
            }
            dependencies {
                classpath 'com.android.tools.build:gradle:3.5.0'
                classpath 'com.antfortune.freeline:gradle:0.8.6'
                classpath "com.google.protobuf:protobuf-gradle-plugin:0.8.6"
                classpath 'org.apache.httpcomponents:httpclient:4.4.1'
                classpath 'com.tuya.android.module:tymodule-config:0.4.0'
            }
        }
    
    ````

- 在模块的build.gradle中添加如下代码:

    ```java   
    apply plugin: 'com.android.application'
    apply plugin: 'tymodule-config'
    
    android {
        //... 其他默认配置
        defaultConfig {
            //...其他默认配置
            multiDexEnabled true
            ndk {
                abiFilters "armeabi-v7a", "arm64-v8a"
            }
        }
        
    
        //***** 强制配置 开始 ****//
        compileOptions {
            sourceCompatibility 1.8
            targetCompatibility 1.8
        }
    
        packagingOptions {
            pickFirst 'lib/*/libc++_shared.so'
            pickFirst 'lib/*/libgnustl_shared.so'
        }
    
        lintOptions {
            abortOnError false
            disable 'InvalidPackage'
        }
        //***** 强制配置 结束 ****//
    }
    
    dependencies {
    
        //***** Camera Panel SDK模块必须依赖 开始 ****//
        //涂鸦摄像头面板SDK
        implementation 'com.tuya.smart:tuyasmart-camera-panel-blackpanel:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-cloudpanel:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-doorbellpanel:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-local-album:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-message-center:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-set:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-preset-point:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-rn:1.0.2'
    
        implementation 'com.tuya.smart.rnplugin:tyrctcameramanager:1.1.3'
        implementation 'com.tuya.smart.rnplugin:tyrctcameraplayer:1.0.6'
        implementation 'com.tuya.smart.rnplugin:tyrctcameratimelineviewmanager:1.0.7'
        implementation 'com.tuya.smart.rnplugin:tyrcttuyacameraplayer:1.0.4'
    
        implementation 'com.tuya.smart:tuyasmart-camera-panel-api:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-ui:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-uiview:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-libs:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-devicecontrol:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-middleware:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-base:1.0.2'
        implementation 'com.tuya.smart:tuyasmart-camera-panel-utils:1.0.2'
        
        implementation 'com.tuya.smart:panel-sdk:0.4.0'
    
        implementation 'com.tuya.smart:tuyasmart-video:3.12.6r125'
        implementation 'com.tuya.smart:tuyasmart-imagepipeline-okhttp3:0.0.1'
        implementation 'com.tuya.android.module:tymodule-annotation:0.0.7.2'
        implementation 'com.tuya.smart:tuyasmart-webcontainer:3.12.6r125-h1'
        implementation 'com.tuya.smart:tuyasmart-appshell:3.10.0'
        implementation 'com.tuya.smart:tuyasmart:3.13.0'
        implementation "com.tuya.smart:tuyasmart-TuyaRNApi:5.22.54-open"
        //此处上传头像需要
        implementation 'com.tuya.smart:tuyasmart-rpc:3.12.0r123'
        implementation 'com.tuya.smart:tuyasmart-security:1.0.0'
        implementation 'com.tuya.smart:tuyasmart-wkvideoplayer:1.0.0'
    
        //*****  Camera Panel SDK模块必须依赖 结束 ****//
    
        //第三方依赖
        implementation 'com.weigan:loopView:0.1.1'
        implementation 'com.facebook.infer.annotation:infer-annotation:0.11.2'
        implementation 'com.facebook.soloader:soloader:0.8.0'
        implementation 'com.facebook.fresco:fresco:1.3.0'
        implementation 'com.facebook.fresco:animated-gif:1.3.0'
        implementation "com.facebook.fresco:imagepipeline-okhttp3:1.3.0"
        implementation 'com.squareup.okhttp3:okhttp-urlconnection:3.2.0'
        implementation 'javax.inject:javax.inject:1'
        implementation 'com.alibaba:fastjson:1.1.67.android'
        implementation 'com.facebook.react:react-native:0.51.1.11'
        implementation 'com.airbnb.android:lottie:2.7.0'
        implementation 'org.apache.commons:commons-compress:1.9'
        implementation 'com.kyleduo.switchbutton:library:1.4.2'
        implementation 'com.github.PhilJay:MPAndroidChart:v3.0.3'
        implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0'
        implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
        implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
        implementation 'com.hannesdorfmann:adapterdelegates3:3.1.0'
        implementation 'com.android.support.constraint:constraint-layout:1.1.3'
        implementation 'com.android.support:multidex:1.0.3'
        implementation "com.android.support:appcompat-v7:28.0.0"
        implementation "com.android.support:cardview-v7:28.0.0"
        implementation "com.android.support:recyclerview-v7:28.0.0"
        implementation "com.android.support:support-annotations:28.0.0"
        implementation "com.android.support:support-compat:28.0.0"
        implementation "com.android.support:support-fragment:28.0.0"
        implementation "com.android.support:design:28.0.0"
        implementation "com.android.support:support-v4:28.0.0"
        //***** 必须依赖模块 结束 ****//
    
        //... 其他默认配置
    }
    ```
- 在AndroidManifest.xml文件里配置appkey和appSecret，在配置相应的权限等
    ```java  
    <!-- sdcard -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <!-- 网络 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission
        android:name="android.permission.ACCESS_FINE_LOCATION"
        android:required="false" />
    <uses-permission
        android:name="android.permission.WAKE_LOCK"
        android:required="false" />
    <uses-permission
        android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE"
        android:required="false" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <application >

        <meta-data
            android:name="TUYA_SMART_APPKEY"
            android:value="用户申请的应用Appkey" />
        <meta-data
            android:name="TUYA_SMART_SECRET"
            android:value="用户申请的应用密钥AppSecret" />
    
       <!--    ... -->
    </application>
    ```
- 混淆配置
    ```java  
    //...
    #fastJson
    -keep class com.alibaba.fastjson.**{*;}
    -dontwarn com.alibaba.fastjson.**
    
    #mqtt
    -keep class org.eclipse.paho.client.mqttv3.** { *; }
    -dontwarn org.eclipse.paho.client.mqttv3.**
    
    -dontwarn okio.**
    -dontwarn rx.**
    -dontwarn javax.annotation.**
    -keep class com.squareup.okhttp.** { *; }
    -keep interface com.squareup.okhttp.** { *; }
    -keep class okio.** { *; }
    -dontwarn com.squareup.okhttp.**
    
    -keep class com.tuya.**{*;}
    -dontwarn com.tuya.**
    ```

## 开发文档

   更多请参考: [涂鸦智能摄像机面板 SDK 使用说明](https://github.com/TuyaInc/tuyasmart_camera_panel_android_sdk_doc)

## 更新日志

- 2020.3.4
    - 涂鸦智能摄像机面板SDK TuyaCameraPanelSDK 1.0版本上线

