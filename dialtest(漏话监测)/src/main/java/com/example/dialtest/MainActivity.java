package dialtest.snail.com.dt;

import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import java.util.Timer;
import java.util.TimerTask;

import dialtest.snail.com.dt.util.EmailUtil;
import dialtest.snail.com.dt.util.TimeViewHelper;

public
class
MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_dialing;
    private EditText ed_phnum;
    private EditText ed_interval;
    private TextView btn_click;
    private TextView btn_cancle;
    private TextView mResultText;
    private TextView tv_second;
    private TimeViewHelper timeView = null;
    private String phone_no;
    private String dialing;
    private String interval_time;
    private DbManager db;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private Dial dial;
    //录音类
    private MediaRecorder mediaRecorder;
    private boolean isRecording;
    //语音听写对象
    SpeechRecognizer mIat;
    //"guochao@snailgame.com","qigq@snailgame.com"
    String toEmail[] = {"weibichao@snail.com", "guochao@snail.com", "qigq@snail.com", "zhaohx@snail.com"};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //开始录音
                    startRecord();
                    Log.i("cbw", "录音开始");

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //一分钟后停止录音
                            stopRecord();
                            //识别录音文件
                            audioFileRecognize();
                        }
                    }, 60 * 1000);
                    break;
                case 2://录音结束
                    Log.i("cbw", "录音结束");
                    Toast.makeText(MainActivity.this, "录音结束", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIat = SpeechRecognizer.createRecognizer(MainActivity.this, null);
        db = x.getDb(MyApplication.getDaoConfig());

        ed_dialing = (EditText) findViewById(R.id.ed_dialing);
        ed_phnum = (EditText) findViewById(R.id.ed_phnum);
        ed_interval = (EditText) findViewById(R.id.ed_interval);
        btn_click = (TextView) findViewById(R.id.btn_click);
        btn_cancle = (TextView) findViewById(R.id.btn_cancle);
        mResultText = (TextView) findViewById(R.id.tv_result);
        tv_second = (TextView) findViewById(R.id.tv_second);
        btn_click.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                timeDial();
                break;
            case R.id.btn_cancle:
                dialCancle();
                /*if (isRecording) {
                    stopRecord();
                }*/
                break;
            default:
                break;
        }
    }

    /**
     * 计时器监听
     */
    TimeViewHelper.OnFinishListener timerLister = new TimeViewHelper.OnFinishListener() {
        @Override
        public void finish() {
            timeDial();
        }
    };

    /**
     * 计时拨号
     */
    private void timeDial() {
        phone_no = ed_phnum.getText().toString().trim();
        interval_time = ed_interval.getText().toString().trim();
        dialing = ed_dialing.getText().toString().trim();
        if (!TextUtils.isEmpty(dialing) && !TextUtils.isEmpty(phone_no) && !TextUtils.isEmpty(interval_time)) {
            tv_second.setVisibility(View.VISIBLE);
            btn_click.setClickable(false);
            btn_click.setText("正在拨测...");
            mResultText.setText("");
            dial();

            voiceInput();//语音识别

            int max = Integer.valueOf(interval_time);
            //计时
            timeView = new TimeViewHelper(tv_second, max, "秒后重新拨打", 1);
            timeView.start();
            Log.i("cbw", "计时开始");
            timeView.setOnFinishListener(timerLister);

            /*Message msg = Message.obtain();
            msg.what = 1;
            handler.sendMessage(msg);*/
        } else {
            Toast.makeText(this, "请输入要测试的蜗牛号码和测试间隔时间!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拨打电话
     */
    private void dial() {
        Intent phoneIntent = new Intent(
                "android.intent.action.CALL", Uri.parse("tel:"
                + phone_no));
        startActivity(phoneIntent);
        Log.i("cbw", "拨打电话");
    }

    /**
     * 停止拨测
     */
    private void dialCancle() {
        mIat.cancel();
        mIat.destroy();

        if (timeView != null) {
            timeView.countDownTimer.cancel();
            timeView = null;
            Log.i("cbw", "计时结束");
        }
        tv_second.setVisibility(View.GONE);
        btn_click.setClickable(true);
        btn_click.setText("点击打电话并开启讯飞识别");
    }

    /**
     * 音频文件识别
     */
    private void audioFileRecognize() {
        //清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        //设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        //语音输入超时
        mIat.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "60*1000");
        //前端点超时 静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "30*1000");
        //后端点超时 即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "30*1000");
        //音频采样率
        mIat.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
        mIat.setParameter(SpeechConstant.VOLUME, "100");
        //设置音频来源外部文件
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        //mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "/sdcard/record/mediarecorder.wav");
        //函数调用返回值
        int ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            Toast.makeText(MainActivity.this, "识别失败,错误码: " + ret, Toast.LENGTH_SHORT).show();
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream("/sdcard/record/mediarecorder.wav");
                byte[] data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                fileInputStream.close();
                if (null != data) {
                    mIat.writeAudio(data, 0, data.length);
                    mIat.stopListening();
                } else {
                    mIat.cancel();
                    Toast.makeText(MainActivity.this, "读取音频流失败", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 听写监听
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            Log.i("cbw", "onBeginOfSpeech");
        }

        @Override
        public void onEndOfSpeech() {
            Log.i("cbw", "onEndOfSpeech");
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.i("cbw", "onError: " + speechError.getPlainDescription(true));
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.i("cbw", "onEvent");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.i("cbw", "onResult: " + recognizerResult.getResultString());
            printResult(recognizerResult);
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            Log.d("cbw", "返回音频数据：" + bytes.length + "  音量: " + String.valueOf(i));
        }
    };

    /**
     * 语音输入
     */
    private void voiceInput() {
        //创建RccognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, null);
        //设置accent language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //音量
        mDialog.setParameter(SpeechConstant.VOLUME, "80");
        //音频源
        //mDialog.setParameter(SpeechConstant.AUDIO_SOURCE, "/sdcard/record/mediarecorder.amr");
        //语音输入超时
        mDialog.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "30000");
        //前端点超时
        mDialog.setParameter(SpeechConstant.VAD_BOS, "30000");
        //后端点超时
        mDialog.setParameter(SpeechConstant.VAD_EOS, "15000");
        //若要将UI空间用于语义理解,必须添加一下参数设置,设置之后onResult回调返回将是语义理解
        //结果
        //mDialog.setParameter("asr_sch","1");
        //mDialog.setParameter("nip_version","2.0");

        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
                Log.i("cbw", "" + recognizerResult.getResultString());
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog,接收语音输入
        mDialog.show();
        Toast.makeText(this, "请开始说话!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 回调结果
     */
    private void printResult(RecognizerResult result) {
        String text = parseIatResult(result.getResultString());
        //自动填写地址
        mResultText.append(text);
        //直至出现句号字符串为止
        if (text.contains("。") || text.contains("！")) {
            monitor(mResultText.getText().toString().trim());
        }
    }

    //解析json
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 判断返回信息是否异常
     *
     * @param str
     */
    private void monitor(String str) {
        if (!str.contains("蜗牛移动") && !str.contains("微信通知")
                && !str.contains("免商店") && !str.contains("短信")) {
            dataSave(str);
            dataRead();
        }
    }

    /**
     * 数据存储
     */
    private void dataSave(String str) {
        try {
            dial = new Dial();
            dial.setDialing(ed_dialing.getText().toString().trim());
            dial.setCalled(ed_phnum.getText().toString().trim());
            dial.setTime(dateFormat.format(new Date()));
            dial.setContent(str);
            db.save(dial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据读取
     */
    private void dataRead() {
        try {
            List<Dial> list = db.selector(Dial.class).findAll();
            if (list.size() > 0) {
                Log.i("cbw", "==size==" + String.valueOf(list.size()));
                Log.i("cbw", "==被叫==" + list.get(list.size() - 1).getDialing());
                Log.i("cbw", "==被叫==" + list.get(list.size() - 1).getCalled());
                Log.i("cbw", "==时间==" + list.get(list.size() - 1).getTime());
                Log.i("cbw", "==内容==" + list.get(list.size() - 1).getContent());
                //发送邮件
                /*sendMail("1315197071@qq.com", "漏话提醒异常报告",
                        "测试邮件,请不要回复!", null);*/

                sendMessage();
                Toast.makeText(MainActivity.this, "邮件发送成功", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     */
    private void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < toEmail.length; i++) {
                        HtmlEmail email = new HtmlEmail();
                        email.setHostName("smtp.qq.com");
                        email.setTLS(true);
                        email.setSSL(true);
                        email.setCharset("gbk");
                        email.addTo(toEmail[i]);
                        email.setFrom("2278655427@qq.com");
                        email.setAuthentication("2278655427", "ottnvexlweomdjae");
                        email.setSubject("漏话提醒异常报告");
                        email.setMsg("漏话提醒业务异常: \n\n" + "    主叫号码: " + dial.getDialing()
                                + "\n    被叫号码: " + dial.getCalled() + "\n    异常时间: " + dial.getTime() + "\n    异常内容: " + dial.getContent());
                        email.send();
                    }
                    db.delete(Dial.class);//清空数据库
                } catch (EmailException e) {
                    Log.i("cbw", "-------EmailException--------->" + e.getMessage());
                } catch (DbException e) {
                    Log.i("cbw", "-------DbException--------->" + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * 发邮件
     */
    private void sendMail(final String toMail, final String title,
                          final String body, final String path) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                EmailUtil emailUtil = new EmailUtil();
                try {
                    //专门用来发邮件的网易邮箱
                    String account = "2267862235@qq.com";
                    String password = "qftwzzfmozaoeagg";
                    emailUtil.sendMail(toMail, account, "smtp.qq.com",
                            account, password, title, body, path);
                } catch (Throwable e) {
                    Log.i("cbw", "---------------->" + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * 开始录音
     */
    private void startRecord() {
        try {
            File file = new File("/sdcard/record/mediarecorder.wav");
            if (file.exists()) {
                //如果文件存在,删除它,保证设备上只有一个录音文件
                file.delete();
            }
            mediaRecorder = new MediaRecorder();
            //设置音频录入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //音频采样率
            mediaRecorder.setAudioSamplingRate(8000);
            //音频编码比特率
            //mediaRecorder.setAudioEncodingBitRate(16);
            //设置录制音频的输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //设置音频的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置录制音频文件输出文件路径
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置最大持续时间 单位毫秒
            mediaRecorder.setMaxDuration(60 * 1000);

            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int i, int i1) {
                    //发生错误,停止录音
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    Toast.makeText(MainActivity.this, "录音发生错误", Toast.LENGTH_SHORT).show();
                }
            });
            //准备 开始
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束录音
     */
    private void stopRecord() {
        if (isRecording) {
            //如果正在录音,停止并释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;

            Message msg = Message.obtain();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        if (timeView != null) {
            timeView.countDownTimer.cancel();
            timeView = null;
            Log.i("cbw", "计时结束");
        }
        if (isRecording) {
            //如果正在录音,停止并释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        //退出时释放连接
        mIat.cancel();
        mIat.destroy();

        super.onDestroy();
    }
}
