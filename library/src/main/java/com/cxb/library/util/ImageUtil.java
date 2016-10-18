package com.cxb.library.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.cxb.library.http.HttpUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 功能：图片文件上传工具类<br>
 * Created by cbw on 2015/12/14.
 */
public
class ImageUtil {

    // log标签
    private static final String TAG = "ImageUtil";

    /**
     * 上传图片文件
     *
     * @param url       服务器链接
     * @param imageFile 图片路径
     * @return
     * @throws Exception
     */
    @SuppressLint("NewApi")
    public static String uploadImageFile(String url, String imageFile)
            throws Exception {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(imageFile)) {
            LogUtils.i(TAG, "uploadImageFile参数为null，取消上传");
            return null;
        }
        // ��ȡͼƬ����
        Bitmap bitmap = decodeSampledBitmapFromResource(imageFile, 200, 150);
        if (bitmap == null) {
            LogUtils.i(TAG, "uploadImageFile读取图片为null，取消上传");
            return null;
        }
        String result = null;
        File file = File.createTempFile("image", ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 图片转换为jpeg
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // ios不支持webp,修改压缩格式
        // if (VERSION.SDK_INT >= 14) {
        // bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        // } else {
        // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        LogUtils.i(TAG, "上传图片，图片大小：" + baos.size());
        fos.flush();
        fos.close();
        baos.close();
        result = HttpUtils.uploadFile(url, file.getAbsolutePath());
        file.delete();
        // ���bitmap
        bitmap.recycle();
        bitmap = null;
        if (file != null && file.exists()) {
            file.delete();
        }
        return result;
    }

    /**
     * 计算压缩比例
     *
     * @param options
     * @param reqWidth  宽
     * @param reqHeight 高
     * @return 压缩比例
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        while ((options.outHeight / inSampleSize) > reqHeight
                || (options.outWidth / inSampleSize) > reqWidth) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /**
     * 按要求宽高压缩图片
     *
     * @param imageFile
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String imageFile,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile, options);
    }

}
