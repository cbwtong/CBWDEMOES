package dialtest.snail.com.dt;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/11/17.
 */

public
class
MyApplication extends Application {

    static String TAG = MyApplication.class.getSimpleName();
    private static DbManager.DaoConfig daoConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        //科大讯飞初始化
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=582536e4");

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        //谷歌浏览器查看sqlite数据库的初始化
        //Stetho.initializeWithDefaults(this);

        //数据库初始化
        daoConfig = new DbManager.DaoConfig()
                .setDbName("dialtest.db")//创建数据库名称
                .setDbDir(new File("/sdcard"))//"sdcard"的写法并非最佳实践,不设置此项数据库默认存储在/data/data/你的应用程序/database/xxx.db下
                .setDbVersion(1)//数据库版本
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //TODO 数据库更新操作
                        //db.addColum(...);
                        //db.dropTable(...);
                        //...
                        //or
                        //db.dropDb();
                    }
                });
    }

    public static DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

}
