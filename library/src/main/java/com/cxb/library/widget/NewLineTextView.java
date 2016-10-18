package com.cxb.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cxb.library.util.DensityUtil;

/**
 * 功能:TextView文字整齐换行处理<br>
 * Created by cbw on 2016/1/29.
 */
public
class NewLineTextView extends TextView {
    private final String namespace = "http://www.angellecho.com/";
    private String text;
    private float textSize;
    private float paddingLeft;
    private int textColor;
    private Paint paint1 = new Paint();
    private float textShowWidth;

    @SuppressWarnings("deprecation")
    public NewLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取属性值
        text = attrs.getAttributeValue(
                "http://schemas.android.com/apk/res/android", "text");
        textSize = attrs.getAttributeIntValue(namespace, "textSize",
                DensityUtil.dpToPx(getContext(), 16));
        textColor = attrs.getAttributeIntValue(namespace, "textColor",
                Color.GRAY);
        paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft",
                DensityUtil.dpToPx(getContext(), 20));
        // set属性值
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        // paint1.setLetterSpacing(lineSpacingExtra);
        paint1.setAntiAlias(true);
        textShowWidth = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth()
                - DensityUtil.dpToPx(getContext(), 100);
    }

    // 按计算出的固定宽度来画出textview
    @Override
    protected void onDraw(Canvas canvas) {
        int lineCount = 0;
        text = this.getText().toString();// .replaceAll("\n", "\r\n");
        if (text == null)
            return;
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);

            if (textCharArray[i] == '\n') {
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }
            // canvas.drawLine();
            canvas.drawText(textCharArray, i, 1, drawedWidth + paddingLeft,
                    (lineCount + 1) * textSize, paint1);
            drawedWidth += charWidth;
        }

        setHeight((lineCount + 1) * (int) textSize + 5);

    }
}
