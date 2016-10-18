package com.example.administrator.cbwapplication;


import android.content.Context;
import android.content.Intent;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import android.widget.ToggleButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


import org.xutils.x;

@ContentView(R.layout.activity_main)
public
class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.my_btn)
    private ToggleButton my_btn;

    @ViewInject(R.id.my_view)
    private SurfaceView surfaceView;

    private static final int CAPTURE_CODE = 0 * 123;
    private MediaProjectionManager projectionManager;
    private int screenDensity;
    private int displayWidth = 1080;
    private int displayHeight = 1920;
    private boolean screenSharing;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        //获取屏幕分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;
        //获取应用界面上的SurfaceView组件
        surface = surfaceView.getHolder().getSurface();
        //控制界面上的surfaceview组件的宽度和高度
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.height = displayHeight;
        lp.width = displayWidth;
        surfaceView.setLayoutParams(lp);

        //获取MediaProjectionManager管理器 屏幕捕捉的核心
        projectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        my_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    shareScreen();
                } else {
                    stopScreenSharing();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

    public void shareScreen() {
        screenSharing = true;
        if (surface == null) {
            return;
        }
        if (mediaProjection == null) {
            //创建一个屏幕捕捉的Intent
            Intent intent = projectionManager.createScreenCaptureIntent();
            //开始屏幕捕捉
            startActivityForResult(intent, CAPTURE_CODE);
            return;
        }
    }

    public void stopScreenSharing() {
        screenSharing = false;
        if (virtualDisplay == null) {
            return;
        }
        virtualDisplay.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_CODE) {
            //如果requestcode不等于RESULT_OK,表名用户拒绝了屏幕捕捉
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "用户取消了屏幕捕捉", Toast.LENGTH_SHORT).show();
                return;
            }
            //获取MediaProjection对象 程序调用MediaProjection的方法就可以将捕捉的图像显示到指定Surface中
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            virtualDisplay = mediaProjection.createVirtualDisplay("屏幕捕捉",
                    displayWidth, displayHeight, screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    surface, null, null);
        }
    }
}
