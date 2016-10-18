package com.example.administrator.cbwapplication.home.fragment;



import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.fragment.BaseFragment;
import com.example.administrator.cbwapplication.common.network.Params;
import com.example.administrator.cbwapplication.common.network.UrlConstants;
import com.example.administrator.cbwapplication.util.SmartLog;
import com.example.administrator.cbwapplication.util.http.HttpUtils;
import com.example.administrator.cbwapplication.util.http.ResultListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 功能:第一tab页<br>
 * Created by cbw on 2015/7/21.
 */

@ContentView(R.layout.tab01)
public
class WeiXinFragment extends BaseFragment {

    protected final String TAG = getClass().getName();

    @ViewInject(R.id.cbw_tv)
    private TextView cbw_tv;

    private HttpUtils httpUtils;

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.cbw_tv:
                //网络连接
                getConnect();
                break;
            default:
                break;
        }
    }

    @Override
    public void init() {
        cbw_tv.setOnClickListener(this);
        phnum = "17097001482";
        type = 1;
        count = 2;
        pageNum = 1;
    }

    private String phnum;
    private int type;
    private int count;
    private int pageNum;

    private ResultListener resultListener = new ResultListener() {
        @Override
        public void getHttpResult(String result) {
            SmartLog.i("cbw", "=====回调result======" + result);
            paserJson(result);
        }
    };

    /**
     * 访问网络数据
     */
    public void getConnect() {
        httpUtils = new HttpUtils();
        try {
            httpUtils.post(UrlConstants.GET_APPLIST, Params.getMyParams(type, pageNum, count, phnum), resultListener);
        } catch (Exception e) {
            SmartLog.w(TAG, "getHttpResult-exception: ", e);
        }

        /*x.http().post(Params.getMyParams(), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //获取状态码
                String code = JsonUtil.getJsonCode(result);
                SmartLog.i(TAG, "HttpUtil请求(post)状态码:" + code);

                if (code != null && !"".equals(code)) {
                    //判断请求响应状态码，状态码为200便是服务端成功响应了客户端的请求
                    if (Integer.parseInt(code) == 200) {
                        paserJson(result);
                        SmartLog.i(TAG, "服务器返回信息(post): result = " + result);
                    } else {
                        SmartLog.i(TAG, "服务器返回异常!");
                    }
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //吐司弹框 错误信息
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                SmartLog.i(TAG, "HttpUtil请求(post)错误信息:" + ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                SmartLog.i(TAG, "HttpUtil请求(post)完成");
            }
        });*/
    }

    /**
     * 解析json数据
     *
     * @param result
     */
    public void paserJson(String result) {
        //TODO 解析json数据
        Toast.makeText(getContext(), "准备解析json串...", Toast.LENGTH_SHORT).show();
        SmartLog.i("cbw", "准备解析json...");
    }


    //private final MyHandle<WeiXinFragment> myHandle = new MyHandle<>(WeiXinFragment.this);

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
