package com.lyb.ASPersonlibrary;

import android.content.Context;
import android.util.Log;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.apkfuns.logutils.file.LogFileEngine;
import com.apkfuns.logutils.file.LogFileParam;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import me.pqpo.librarylog4a.LogBuffer;

/**
 * Created by pengwei on 2017/3/30.
 * 日志写入文件的默认引擎
 */

public class CCLogFileEngineFactory implements LogFileEngine {

    //    private static final String LOG_CONTENT_FORMAT = "%s -%s-%s:%s-%s\n";
    private static final String LOG_CONTENT_FORMAT = "%s -%s-<%s>-%s\n";
    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    private static final String FILE_FORMAT_TIME = "yyyy-MM-dd HH:mm";
    private DateFormat dateFormat;
    private DateFormat fileDateFormat;
    private volatile LogBuffer buffer;
    private Context context;

    public CCLogFileEngineFactory(@NonNull Context context) {
        if (context == null) {
            throw new NullPointerException("Context must not null!");
        }
        this.context = context.getApplicationContext();
        dateFormat = new SimpleDateFormat(LOG_DATE_FORMAT, Locale.getDefault());
        fileDateFormat = new SimpleDateFormat(FILE_FORMAT_TIME, Locale.getDefault());
    }

    @Override
    public void writeToFile(File logFile, String logContent, LogFileParam params) {
        if (buffer == null) {
            synchronized (LogFileEngine.class) {
                if (buffer == null) {
                    File bufferFile = new File(context.getFilesDir(), ".log4aCache");
                    buffer = new LogBuffer(bufferFile.getAbsolutePath(), 4096,
                            logFile.getAbsolutePath(), false);
                }
            }
        }
        String nowTime = fileDateFormat.format(new Date());
        String logFileName = logFile.getName().split("\\.")[0];
//        Log.i("Lyb","nowTime=="+nowTime);
//        Log.i("Lyb","logFileName=="+logFileName);
        Log.i("Lyb","buffer=="+buffer.getLogPath());
        if (!logFileName.equals(nowTime)){
            //立即结束写 重新创建一个日志文件txt
            buffer.write("--------------当前分钟已结束日志--------------");
            Log.i("Lyb","obtainNewLogTextName=="+ LogUtils.getLog2FileConfig().obtainNewLogTextName());
            buffer.changeLogPath(LogUtils.getLog2FileConfig().obtainNewLogTextName());
            buffer.write("--------------新的起点--------------");
        }else {
            buffer.write(getWriteString(logContent, params));
        }
    }

    /**
     * 写入文件的内容
     *
     * @param logContent log value
     * @param params     LogFileParam
     * @return file log content
     */
    private String getWriteString(String logContent, LogFileParam params) {
        String time = dateFormat.format(new Date(params.getTime()));
        //        return String.format(LOG_CONTENT_FORMAT, time, getLogLevelString(params.getLogLevel()),
        //                params.getThreadName(), params.getTagName(), logContent);

        return String.format(LOG_CONTENT_FORMAT, time, getLogLevelString(params.getLogLevel()),
                params.getThreadName(), logContent);
    }

    /**
     * 日志等级
     *
     * @param level level
     * @return level string
     */
    private String getLogLevelString(int level) {
        switch (level) {
            case LogLevel.TYPE_VERBOSE:
                return "V";
            case LogLevel.TYPE_ERROR:
                return "E";
            case LogLevel.TYPE_INFO:
                return "I";
            case LogLevel.TYPE_WARM:
                return "W";
            case LogLevel.TYPE_WTF:
                return "Wtf";
        }
        return "D";
    }

    @Override
    public void flushAsync() {
        if (buffer != null) {
            buffer.flushAsync();
        }
    }

    @Override
    public void release() {
        if (buffer != null) {
            buffer.release();
            buffer = null;
        }
    }

}
