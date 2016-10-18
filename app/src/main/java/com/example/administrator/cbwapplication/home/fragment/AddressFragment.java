package com.example.administrator.cbwapplication.home.fragment;

import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.fragment.BaseFragment;
import com.example.administrator.cbwapplication.util.SetViewDrawableUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * 功能:第三tab页<br>
 * Created by cbw on 2015/7/21.
 * 更多特性详见博客:http://blog.csdn.net/y1scp/article/details/49245535
 */

@ContentView(R.layout.tab03)
public
class AddressFragment extends BaseFragment {

    @ViewInject(R.id.tv1)
    private TextView tv1;

    @ViewInject(R.id.tv2)
    private TextView tv2;

    @ViewInject(R.id.tv3)
    private TextView tv3;

    @ViewInject(R.id.tv4)
    private TextView tv4;

    @ViewInject(R.id.tv5)
    private TextView tv5;

    @ViewInject(R.id.tv6)
    private TextView tv6;

    @ViewInject(R.id.tv7)
    private TextView tv7;

    @ViewInject(R.id.tv8)
    private TextView tv8;

    @ViewInject(R.id.tv9)
    private TextView tv9;

    //普通加载
    @ViewInject(R.id.my_simView)
    private SimpleDraweeView img1;
    //有加载进度条
    @ViewInject(R.id.man_sdv)
    private SimpleDraweeView img2;
    //进度条+失败图
    @ViewInject(R.id.img_fail)
    private SimpleDraweeView img3;
    //重试+进度条+失败图
    @ViewInject(R.id.img_retry)
    private SimpleDraweeView img4;
    //重试+进度条+失败+淡入淡出
    @ViewInject(R.id.fad_img_retry)
    private SimpleDraweeView img5;
    //进度条+失败+淡入淡出
    @ViewInject(R.id.fad_man_sdv)
    private SimpleDraweeView img6;
    //重试+进度+失败图加载+淡入淡出动画+背景图+圆形
    @ViewInject(R.id.bakg_man_sdv)
    private SimpleDraweeView img7;
    //普通+叠加
    @ViewInject(R.id.over_man_sdv)
    private SimpleDraweeView img8;
    //重试+进度+失败图加载+淡入淡出动画+背景图+圆角
    @ViewInject(R.id.img_circle)
    private SimpleDraweeView img9;

    private Uri uri;
    boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9 = false;

    @Override
    public void init() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View view) {
        //Toast.makeText(getActivity(), "哈哈哈哈哈哈哈哈哈!!!!!!!!", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.tv1:
                initImg();
                break;
            case R.id.tv2:
                initImg2();
                break;
            case R.id.tv3:
                initImg3();
                break;
            case R.id.tv4:
                initImg4();
                break;
            case R.id.tv5:
                initImg5();
                break;
            case R.id.tv6:
                initImg6();
                break;
            case R.id.tv7:
                initImg7();
                break;
            case R.id.tv8:
                initImg8();
                break;
            case R.id.tv9:
                initImg9();
                break;
            default:
                break;
        }
    }

    /**
     * 普通加载
     */
    public void initImg() {
        if (flag1) {
            img1.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://img.ycwb.com/3c/attachement/jpg/site2/20160121/448a5bb12020180a7be506.jpg");
            //开始下载
            //创建将要加载的图片的uri
            img1.setImageURI(uri);
            flag1 = false;
        } else {
            img1.setVisibility(View.GONE);
            flag1 = true;
        }
    }

    /**
     * 加载过程中带有进度条
     */
    public void initImg2() {
        if (flag2) {
            img2.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://www.qqpk.cn/Article/UploadFiles/201202/20120202153027468.jpg");
            //带有进度条
            // 当图片开始下载一直到图片被完整的下载下来这段时间'正在架子啊图片会一直显示'并自动旋转着(如果设置了自动旋转间隔时间)
            img2.setImageURI(uri);
            flag2 = false;
        } else {
            img2.setVisibility(View.GONE);
            flag2 = true;
        }
    }

    /**
     * 加载失败后显示的图片
     */
    public void initImg3() {
        if (flag3) {
            img3.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://www.qqpk.cn/Article/UploadFiles/201202/202153027468.jpg");
            //将uri2删除一部分形成错误的网络构造链接
            img3.setImageURI(uri);
            flag3 = false;
        } else {
            img3.setVisibility(View.GONE);
            flag3 = true;
        }
    }

    /**
     * 加载失败后点击重试
     */
    public void initImg4() {
        if (flag4) {
            img4.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://www.qqpk.cn/Article/UploadFiles/201202/202153027468.jpg");

            //与uri3一样都是错误链接 方便重试操作
            img4.setImageURI(uri);

            //创建DraweeController 重复加载4次还没加载出来的时候才会显示失败的图片
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    //加载的图片uri地址
                    .setUri(uri)
                            //设置点击重试是否开始
                    .setTapToRetryEnabled(true)
                            //设置旧的Controller
                    .setOldController(img4.getController())
                            //构建
                    .build();
            //设置DraweeController
            img4.setController(controller);
            flag4 = false;
        } else {
            img4.setVisibility(View.GONE);
            flag4 = true;
        }
    }

    /**
     * 进度图 重试 淡入淡出
     * 网络不好或网址错误时
     */
    public void initImg5() {
        if (flag5) {
            img5.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://i-7.vcimg.com/trim/52621f11c5cf2a83d1ab9e4344c05cc974175/trim.jpg");

            //与uri3一样都是错误链接 方便重试操作
            img5.setImageURI(uri);

            //创建DraweeController 重复加载4次还没加载出来的时候才会显示失败的图片
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    //加载的图片uri地址
                    .setUri(uri)
                            //设置点击重试是否开始
                    .setTapToRetryEnabled(true)
                            //设置旧的Controller
                    .setOldController(img5.getController())
                            //构建
                    .build();
            //设置DraweeController
            img5.setController(controller);
            flag5 = false;
        } else {
            img5.setVisibility(View.GONE);
            flag5 = true;
        }
    }

    /**
     * 进度图 淡入淡出
     */
    public void initImg6() {
        if (flag6) {
            img6.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://imgsrc.baidu.com/forum/pic/item/4bed2e738bd4b31c2958c52c87d6277f9f2ff8c5.jpg");
            //带有进度条
            // 当图片开始下载一直到图片被完整的下载下来这段时间'正在架子啊图片会一直显示'并自动旋转着(如果设置了自动旋转间隔时间)
            img6.setImageURI(uri);
            flag6 = false;
        } else {
            img6.setVisibility(View.GONE);
            flag6 = true;
        }
    }

    /**
     * 普通+背景图
     */
    public void initImg7() {
        if (flag7) {
            img7.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://img3.imgtn.bdimg.com/it/u=790478042,3935403635&fm=21&gp=0.jpg");
            //开始下载
            //创建将要加载的图片的uri
            img7.setImageURI(uri);
            flag7 = false;
        } else {
            img7.setVisibility(View.GONE);
            flag7 = true;
        }
    }

    /**
     * 普通+叠加图
     */
    public void initImg8() {
        if (flag8) {
            img8.setVisibility(View.VISIBLE);
            uri = Uri.parse("http://img3.imgtn.bdimg.com/it/u=790478042,3935403635&fm=21&gp=0.jpg");
            //开始下载
            //创建将要加载的图片的uri
            img8.setImageURI(uri);
            flag8 = false;
        } else {
            img8.setVisibility(View.GONE);
            flag8 = true;
        }
    }

    /**
     * 重试+进度+失败图加载+淡入淡出动画+背景图+圆角
     */
    public void initImg9(){
        if (flag9) {
            img9.setVisibility(View.VISIBLE);
            SetViewDrawableUtil.setTextViewDrawableRight(tv9,getResources().getDrawable(R.mipmap.btn_up));
            uri = Uri.parse("http://www.bz55.com/uploads/allimg/150321/139-150321092051-50.jpg");
            //开始下载
            //创建将要加载的图片的uri
            img9.setImageURI(uri);
            flag9 = false;
        } else {
            img9.setVisibility(View.GONE);
            SetViewDrawableUtil.setTextViewDrawableRight(tv9, getResources().getDrawable(R.mipmap.btn_down));
            flag9 = true;
        }
    }

    @Override
    public void handleReturnMessage(Message msg) {
    }
}
