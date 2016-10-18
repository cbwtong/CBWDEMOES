package com.example.administrator.cbwapplication.mvp.view;


import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.activity.BaseActivity;
import com.example.administrator.cbwapplication.mvp.model.entity.User;
import com.example.administrator.cbwapplication.mvp.presenter.UserLoginPresenter;
import com.example.administrator.cbwapplication.widget.ClearEditText;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 功能:负责view的绘制以及用户的交互 处理MVP中的View其实就是Activity<br>
 * 该操作需要什么？（getUserName, getPassword）
 * 该操作的结果，对应的反馈？(toMainActivity, showFailedError)
 * 该操作过程中对应的友好的交互？(showLoading, hideLoading)
 * Created by cbw on 2015/12/25.
 */

@ContentView(R.layout.activity_mvptest)
public
class UserLoginActivity extends BaseActivity implements IUserLoginView {

    @ViewInject(R.id.edit_unam)
    private ClearEditText edit_unam;

    @ViewInject(R.id.edit_pwd)
    private ClearEditText edit_pwd;

    @ViewInject(R.id.tv_login)
    private TextView mLogin;

    @ViewInject(R.id.tv_cancel)
    private TextView mCancel;

    @ViewInject(R.id.load_progress)
    private ProgressBar mProgressbar;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    public void init() {

        mLogin.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                mUserLoginPresenter.login();
                break;
            case R.id.tv_cancel:
                mUserLoginPresenter.clear();
                break;
            default:
                break;
        }
    }

    @Override
    public void handleReturnMessage(Message msg) {
    }

    @Override
    public String getUsername() {
        return edit_unam.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edit_pwd.getText().toString().trim();
    }

    @Override
    public void clearUsername() {
        edit_unam.setText("");
    }

    @Override
    public void clearpassword() {
        edit_pwd.setText("");
    }

    @Override
    public void showLoading() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void toManinActivity(User user) {
        Toast.makeText(this, user.getUsername() +
                        " login success , to MainActivity",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }
}
