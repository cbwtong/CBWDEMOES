package com.example.administrator.cbwapplication.application;



import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.util.SmartLog;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:程序入口 应用初始application<br>
 * Created by cbw on 2015/12/14.
 */
public
class MyApplication extends Application {

    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> unDestroyActivityList = new ArrayList<Activity>();
    public static MyApplication myApplication;
    public static RequestParams requestParams;

    /*对http请求进行参数配置*/
    /*public static RequestParams getRequestParamsInstance(String url) {
        if (requestParams == null) {
            requestParams = new RequestParams(url);
            //缓存存活时间
            requestParams.setCacheMaxAge(10 * 1000);
            //请求超时时间
            requestParams.setConnectTimeout(5 * 1000);
        }
        return requestParams;
    }*/

    @Override
    public void onCreate() {
        if (myApplication == null) {
            myApplication = this;
        }
        super.onCreate();

        //xUtils初始化
        x.Ext.init(this);
        //允许打印log信息 应用发布时关闭打印
        //x.Ext.setDebug(true);
        SmartLog.setDebugMode(true);

        //初始化Fresco图片异步加载框架
        Fresco.initialize(getContext());

        // log日志的开关，true-开启，false-关闭
        //LogUtils.getInstance(myApplication).setDebugModel(true);
    }

    /**
     * 获得上下文
     */
    public static Context getContext() {
        return myApplication.getApplicationContext();
    }

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected() {
        Context context = MyApplication.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void disPlayUrlImage(ImageView imageview, String imageUri) {

        /*if (!TextUtils.isEmpty(imageUri) && !imageUri.startsWith("file://")
                && !imageUri.startsWith(UrlConstants.SERVER)) {
            imageUri = UrlConstants.SERVER + imageUri;
        }*/

        ImageOptions imageoptions = new ImageOptions.Builder()
                //设定图片的宽高 默认自适应大小
                // 小于0时不采样压缩. 等于0时自动识别ImageView的宽高
                .setSize(DensityUtil.dip2px(150), DensityUtil.dip2px(150))
                        //忽略gif图片 默认是加载GIF图片
                .setIgnoreGif(true)
                        //是否使用缓存 默认是true,如果使用本地文件url,添加这个设置可以再本地文件更新后刷新立即生效
                .setUseMemCache(true)
                        //设置成正方形 默认false
                .setSquare(false)
                        //设置成圆形 默认false
                .setCircular(false)
                        //设置自动旋转 默认false
                .setAutoRotate(false)
                        //配置编码 默认RGB_565
                        //.setConfig(Bitmap.Config.RGB_565)
                        //设置渐显效果 默认false
                .setFadeIn(false)
                        //设置动画 默认null
                        //.setAnimation()
                        //设置占位缩放类型 默认CENTER_INSIDE
                        //.setPlaceholderScaleType(ImageView.ScaleType.CENTER_INSIDE)
                        //默认true 强力加载
                .setForceLoadingDrawable(true)
                        //图片半径
                .setRadius(DensityUtil.dip2px(10))
                        //裁剪 如果Imageview的大小不是定义为wrap_content,不要crop.
                .setCrop(true)
                        //加载中或错误图片的ScaleType
                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                        //设置图像比例尺类型
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        //正在加载
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                        //加载失败
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
        //绑定图片
        x.image().bind(imageview, imageUri, imageoptions);

        // 加载本地图片
        // x.image().bind(imgv, "assets://test.gif", options);
        // x.image().bind(iv_big_img, new
        // File("/sdcard/test.gif").toURI().toString(), imageOptions);
        // x.image().bind(iv_big_img, "/sdcard/test.gif", imageOptions);
        // x.image().bind(iv_big_img, "file:///sdcard/test.gif", imageOptions);
        // x.image().bind(iv_big_img, "file:/sdcard/test.gif", imageOptions);

    }

}
