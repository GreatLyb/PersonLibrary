package com.lyb.ASPersonlibrary;

import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.i("Lyb", "getModel==: "+ DeviceUtils.getModel());
//        Log.i("Lyb", "isAdbEnabled==: "+ DeviceUtils.isAdbEnabled(this));
//        Log.i("Lyb", "isDeviceRooted==: "+ DeviceUtils.isDeviceRooted());
//
//        //
        try {
//            Runtime.getRuntime().exec("/system/xbin/su");
            Runtime.getRuntime().exec("adb shell settings put global policy_control null");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Lyb", "IOException====: "+e);
        }
//        exusecmd("mount -o rw,remount /data");
//        exusecmd("chmod 777 /data/data/shensi");
    }

    public static boolean exusecmd(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.i("Lyb", "e=-=: "+e);

            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}