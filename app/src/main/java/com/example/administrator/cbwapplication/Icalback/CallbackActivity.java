package com.example.administrator.cbwapplication.Icalback;


import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;


import android.widget.Toast;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


import java.util.ArrayList;

import java.util.List;

/**
 * 功能:接口回调测试<br>
 * 通过接口回调共用在其它地方获取到的数据 以为己用
 * <p>
 * Created by cbw on 2015/12/25.
 */
@ContentView(R.layout.activity_callback)
public
class CallbackActivity extends BaseActivity {

    @ViewInject(R.id.my_card)
    private CardView cardView;

    @ViewInject(R.id.tv_callback)
    private TextView tv_callback;

    int num;
    User user;
    CallBack callback;
    List<String> mList;
    String errno = null;

    @Override
    public void init() {

        num = 0;
        user = new User();
        callback = new CallBack();

        mList = new ArrayList<>();

        cardView.setRadius(15);
        cardView.setElevation(15);

        tv_callback.setOnClickListener(this);

    }

    @Override
    public void onClickEvent(View view) {

        switch (view.getId()) {
            case R.id.tv_callback:
                callback.setCallBack(new LoginListener() {


                    @Override
                    public void LoginSucess(List<String> list, User user) {
                        Toast.makeText(CallbackActivity.this, "success: 用户名 = " + user.getUsername() +
                                ",密码 = " + user.getPassword(), Toast.LENGTH_SHORT).show();

                        CallbackActivity.this.mList = list;
                        CallbackActivity.this.user = user;
                        //TODO 拿到在CallBack里面已经赋过值的list和user以备他用 可以在这个界面使用有值的list和user了
                    }

                    @Override
                    public void LoginFailued(String errno) {
                        Toast.makeText(CallbackActivity.this, "failued: errno = " + errno, Toast.LENGTH_SHORT).show();

                        CallbackActivity.this.errno = errno;
                        //TODO 拿到在CallBack里面已经赋过值的errno以备他用 可以在这个界面使用有值的errno了
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
