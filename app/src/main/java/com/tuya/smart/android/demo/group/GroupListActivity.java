package com.tuya.smart.android.demo.group;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tuya.smart.android.demo.R;
import com.tuya.smart.android.demo.base.activity.BaseActivity;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by letian on 16/8/27.
 */
public class GroupListActivity extends BaseActivity {


    public SwipeRefreshLayout swipeRefreshLayout;

    public ListView mDevListView;

    GroupDeviceAdapter mGroupDeviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        initToolbar();
        setTitle(R.string.group_device);
        setDisplayHomeAsUpEnabled();
        TuyaSdk.getEventBus().register(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mDevListView = (ListView) findViewById(R.id.lv_device_list);

        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
//                TuyaHomeSdk.getDataInstance().queryDevList();
            }
        });

        initAdapter();

        loadStart();

//        TuyaHomeSdk.getDataInstance().queryDevList();


    }

    protected void setDisplayHomeAsUpEnabled() {
        setDisplayHomeAsUpEnabled(R.drawable.tysmart_back_white, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TuyaSdk.getEventBus().unregister(this);
    }

    private void initAdapter() {
        mGroupDeviceAdapter = new GroupDeviceAdapter(this, R.layout.list_common_device_item, new ArrayList<GroupBean>());
        mDevListView.setAdapter(mGroupDeviceAdapter);
        mDevListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupBean groupBean = (GroupBean) parent.getAdapter().getItem(position);
                if (groupBean != null) {
                    Intent intent = new Intent(GroupListActivity.this, GroupActivity.class);
                    intent.putExtra(GroupCommonPresenter.INTENT_GROUP_ID, groupBean.getId());

                    startActivity(intent);
                }
            }
        });

    }

    public void loadStart() {
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }

    public void loadFinish() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void updateData(List<GroupBean> groupBeen) {
        if (mGroupDeviceAdapter != null) {
            mGroupDeviceAdapter.setData(groupBeen);
        }
        loadFinish();
    }

}
