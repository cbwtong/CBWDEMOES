package cxb.snail.com.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxb.library.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public
class
MainActivity extends BaseActivity {

    public static final String APP_PACKAGE_NAME = "com.android.stk";//包名

    @ViewInject(R.id.tv_click)
    private TextView tv_click;

    @Override
    public void init() {
        tv_click.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_click:
                launchapp();
                break;
            default:
                break;
        }
    }

    /**
     * 启动app
     */
    public void launchapp() {
        //判断是否安装过App
        if (isAppInstalled(APP_PACKAGE_NAME)) {
            //打开第三方应用 此处打开SIM卡管理应用
            Intent intent = new Intent();
            //包名 包名+类名(全路径)
            intent.setClassName(APP_PACKAGE_NAME, "com.android.stk.StkMain");
            startActivity(intent);

            //startActivity(getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME));

        } else {
            Toast.makeText(this, "您的手机没有SIM卡应用!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检测某个应用是否安装
     *
     * @param packageName
     * @return
     */
    public boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
