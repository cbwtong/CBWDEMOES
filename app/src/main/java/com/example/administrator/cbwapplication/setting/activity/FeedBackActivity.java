package com.example.administrator.cbwapplication.setting.activity;


import android.os.Message;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import android.widget.EditText;
import android.widget.LinearLayout;


import android.widget.TextView;


import android.widget.Toast;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;


import org.xutils.view.annotation.ViewInject;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能:意见反馈界面
 * Created by cbw on 2016/1/6.
 */
@ContentView(R.layout.activity_feedback)
public
class FeedBackActivity extends BaseActivity {

    @ViewInject(R.id.btn_top_left)
    private LinearLayout mBack;

    @ViewInject(R.id.title_name)
    private TextView mTitle;

    @ViewInject(R.id.edit_advice)
    private EditText edit_advance;

    @ViewInject(R.id.advance_num)
    private TextView advance_num;

    @ViewInject(R.id.btn_commit)
    private TextView btn_commit;

    @Override
    public void init() {
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("意见反馈");

        mBack.setOnClickListener(this);
        edit_advance.addTextChangedListener(mTextWatcher);
        btn_commit.setOnClickListener(this);

    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_top_left:
                finish();
                break;
            case R.id.btn_commit:
                String content = edit_advance.getText().toString().trim();
                if(content != null && !"".equals(content)){
                    //TODO 提交操作

                }else {
                    Toast.makeText(this,"请先输入内容",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 字符串过滤 空格 换行 回车
     *
     * @param mstring
     * @return
     */
    private String getString(String mstring) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(mstring);
        String msg = m.replaceAll("");
        return msg;
    }

    /**
     * 输入框最大长度控制
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            advance_num.setText(String.valueOf(150 - s.length()));
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = edit_advance.getSelectionStart();
            editEnd = edit_advance.getSelectionEnd();
            if (temp.length() > 150) {
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                edit_advance.setText(s);
                edit_advance.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void handleReturnMessage(Message msg) {
    }
}
