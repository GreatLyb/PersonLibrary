package com.lyb.ASPersonlibrary;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    long a=240000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv_mac);
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("21A")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
        LogUtils.getLog2FileConfig().configLog2FileEnable(true)
                // targetSdkVersion >= 23 需要确保有写sdcard权限
                .configLog2FilePath("/sdcard/AAA/")
                .configLog2FileNameFormat("%d{yyyy-MM-dd HH:mm}.txt")
                .configLogFileEngine(new CCLogFileEngineFactory(this));
        CountDownTimer countDownTimer=new CountDownTimer(480000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                a=a-1000;
                LogUtils.i("第"+a+"行日志");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}