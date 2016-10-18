package cxb.snail.com.getphone;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public
class
MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;

    static String ISDOUBLE;
    static String SIMCARD;
    static String SIMCARD_1;
    static String SIMCARD_2;
    static boolean isDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        tv2.setText("不知道哪个卡可用");

        // api22以上
        if (Build.VERSION.SDK_INT >= 22) {
            //android5.1 即API22以上使用
            SubscriptionManager manager = SubscriptionManager.from(this);
            List<SubscriptionInfo> sil = manager.getActiveSubscriptionInfoList();
            for (int i = 0; i < sil.size(); i++) {
                Log.i("cbw", "===" + sil.get(i).getNumber());
            }
        } else {
            getNumber();
        }
    }

    private void getNumber() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String phoneNumber1 = tm.getLine1Number();
        //String phoneNumber2 = tm.getGroupIdLevel1();

        //initIsDoubleTelephone(this);
        if (isDouble) {
            //tv1.setText("这是双卡手机!");
            tv1.setText("本机号码是: " + "   " + phoneNumber1 + "  " + "这是双卡手机!");
        } else {
            // tv1.setText("这是单卡手机");
            tv1.setText("本机号码是：" + "   " + phoneNumber1 + "   " + "这是单卡手机");
        }


        Method[] cl = TelephonyManager.class.getMethods();
        for (Method me : cl) {
            try {
                if (me.invoke(tm, new Object[]{new Integer(0)}) != null) {
                    Log.i("cbw", "  ===  " + me.getName());
                }
            } catch (Exception e) {
                Log.i("cbw", "  ==异常==  ");
            }
        }


    }

    public void initIsDoubleTelephone(Context context) {
        isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Method[] cl = TelephonyManager.class.getMethods();
            for (Method me : cl) {

                if (me.invoke(tm, new Object[]{new Integer(0)}) != null) {
                    Log.i("cbw", "  ===  " + me.getName());
                }
            }

            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
            //双卡是各个厂商自己实现的并非google的API标准 所以要反射的方法不名一定叫"getSimStateGemini"
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]
                    {int.class});

            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]
                    {new Integer(0)});
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]
                    {new Integer(1)});
        } catch (SecurityException e) {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("1_ISSINGLETELEPHONE:"+e.toString());
        } catch (NoSuchMethodException e) {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("2_ISSINGLETELEPHONE:"+e.toString());
        } catch (IllegalArgumentException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            isDouble = false;
            e.printStackTrace();
        } catch (Exception e) {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("3_ISSINGLETELEPHONE:"+e.toString());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (isDouble) {
            // 保存为双卡手机
            editor.putBoolean(ISDOUBLE, true);
            // 保存双卡是否可用
            // 如下判断哪个卡可用.双卡都可以用
            if (result_0.toString().equals("5") && result_1.toString().equals("5")) {
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, true);

                tv2.setText("双卡可用");

            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5")) {// 卡二可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "1");
                }
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, true);

                tv2.setText("卡二可用");

            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5")) {// 卡一可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, false);

                tv2.setText("卡一可用");

            } else {// 两个卡都不可用(飞行模式会出现这种种情况)
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, false);

                tv2.setText("飞行模式");
            }
        } else {
            // 保存为单卡手机
            editor.putString(SIMCARD, "0");
            editor.putBoolean(ISDOUBLE, false);
        }
        editor.commit();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
}
