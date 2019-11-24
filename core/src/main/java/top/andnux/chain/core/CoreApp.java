package top.andnux.chain.core;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

import top.andnux.chain.core.database.DatabaseManager;

public class CoreApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        DatabaseManager.getInstance().init(this);
    }
}
