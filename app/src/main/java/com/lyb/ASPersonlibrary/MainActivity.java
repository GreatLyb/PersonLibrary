package com.lyb.ASPersonlibrary;

import android.os.Bundle;
import android.view.View;

import com.haibin.calendarview.CalendarView;
import com.kongzue.dialog.v3.CustomDialog;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomDialog.build(this, R.layout.view_choose_time, new CustomDialog.OnBindView() {
            @Override
            public void onBind(CustomDialog dialog, View v) {
                CalendarView calendarView =v.findViewById(R.id.calendarView);
                //                datePicker.setMode(DPMode.SINGLE);
                //                datePicker.setDPDecor(new DPDecor() {
                //                    @Override
                //                    public void drawDecorTL(Canvas canvas, Rect rect, Paint paint) {
                //                        paint.setColor(ContextCompat.getColor(getContext(),R.color.main_color));
                //                        canvas.drawRect(rect, paint);
                //                    }
                //
                //                    @Override
                //                    public void drawDecorTR(Canvas canvas, Rect rect, Paint paint) {
                //                        paint.setColor(Color.BLUE);
                //                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                //                    }
                //                });
                //                Calendar calendar=Calendar.getInstance();
                //                datePicker.setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));
            }
        }).setFullScreen(true).setAlign(CustomDialog.ALIGN.DEFAULT).show();

    }



}