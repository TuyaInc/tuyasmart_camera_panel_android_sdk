package com.tuya.smart.android.demo.base.event;

import com.tuya.smart.android.base.event.BaseEventSender;
import com.tuya.smart.android.demo.personal.FriendEventModel;
import com.tuya.smart.android.demo.personal.PersonalInfoEventModel;
import com.tuya.smart.home.sdk.bean.PersonBean;

/**
 * Created by letian on 16/7/12.
 */
public class EventSender extends BaseEventSender {

    /**
     * 用户好友编辑更新通知
     *
     * @param person
     * @param position
     * @param operation
     */

    public static void friendUpdate(PersonBean person, int position, int operation) {
        FriendEventModel friendEventModel = new FriendEventModel();
        friendEventModel.setOperation(operation);
        friendEventModel.setPerson(person);
        friendEventModel.setPosition(position);
        send(friendEventModel);
    }

    /**
     * 用户好友添加更新通知
     *
     * @param person
     * @param operation
     */
    public static void friendUpdate(PersonBean person, int operation) {
        friendUpdate(person, 0, operation);
    }

    /**
     * 主页设别列表更新（不携带新的设备列表信息，即需要重新拉取）
     */
    public static void updateDeviceList() {
        send(new DeviceListUpdateModel());
    }

    public static void personalInfoChanged() {
        send(new PersonalInfoEventModel());
    }

}
