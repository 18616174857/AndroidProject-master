package com.ljy.demo.common;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.ljy.demo.BuildConfig;
import com.ljy.demo.helper.AppFrontBackHelper;
import com.ljy.demo.other.EventBusManager;
import com.ljy.demo.ui.activity.CrashActivity;
import com.ljy.demo.ui.activity.HomeActivity;
import com.ljy.image.ImageLoader;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.ljy.umeng.UmengClient;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 项目中的 Application 基类
 */
public final class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSDK(this);

    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSDK(Application application) {
        // 这个过程专门用于堆分析的 leak 金丝雀
        // 你不应该在这个过程中初始化你的应用程序
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }

        // 内存泄漏检测
        LeakCanary.install(application);

        // 友盟统计、登录、分享 SDK
        UmengClient.init(application);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });
        // 吐司工具类
        ToastUtils.init(application);

        // 图片加载器
        ImageLoader.init(application);

        // EventBus 事件总线
        EventBusManager.init();

        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, BuildConfig.BUGLY_ID, false);

        // Crash 捕捉界面
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .trackActivities(true)
                .minTimeBetweenCrashesMs(2000)
                // 重启的 Activity
                .restartActivity(HomeActivity.class)
                // 错误的 Activity
                .errorActivity(CrashActivity.class)
                // 设置监听器
                //.eventListener(new YourCustomEventListener())
                .apply();

        //App 前后台切换监听
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(application, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                Log.e("监听前后台操作", "APP返回前台");
                Toast.makeText(application, "APP返回前台", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                Log.e("监听前后台操作", "APP遁入后台");
                Toast.makeText(application, "APP遁入后台", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        //MultiDex.install(this);
    }

}