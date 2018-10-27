package com.kodwell.infodisplay;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by sudha on 5/30/2018.
 */

public class DigitalClockWidget extends LinearLayout {
        private Calendar mTime;
        private TextView tvHour;
        private TextView tvMinute;
        private TextView tvDay;
        private TextView tvDay_name;
        private TextView tvYear;
        private TextView tvMonth;
        private TextView tvSecond;
        private TextView tvMeridian;
        private static final int DALEY = 1000;

        public DigitalClockWidget(Context context) {
            super(context);
        }

        public DigitalClockWidget(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public DigitalClockWidget(Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr, 0);
        }

        public DigitalClockWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDigiClock, defStyleAttr, 0);
            LayoutInflater.from(context).inflate(R.layout.text_clock3, this);
            this.tvHour = (TextView)this.findViewById(R.id.hour);
            this.tvMinute = (TextView)this.findViewById(R.id.minute);
            this.tvDay = (TextView)this.findViewById(R.id.day);
            this.tvDay_name = (TextView)this.findViewById(R.id.day_name);
            this.tvMonth = (TextView)this.findViewById(R.id.month);
            this.tvMeridian = (TextView)this.findViewById(R.id.meridian);
            this.tvSecond = (TextView)this.findViewById(R.id.second);
            this.tvYear = (TextView)this.findViewById(R.id.year);

            try {
                int color = a.getColor(R.styleable.MyDigiClock_color, -16777216);
                int time_color = a.getColor(R.styleable.MyDigiClock_time_color, -16777216);
                this.tvHour.setTextColor(time_color);
                this.tvMinute.setTextColor(time_color);
                this.tvDay.setTextColor(time_color);
                this.tvDay_name.setTextColor(color);
                this.tvMonth.setTextColor(color);
                this.tvMeridian.setTextColor(color);
                this.tvSecond.setTextColor(time_color);
                this.tvYear.setTextColor(time_color);
                this.findViewById(R.id.separador).setBackgroundColor(color);
            } finally {
                a.recycle();
            }

            this.init();
        }

        private void init() {
            this.createTime(TimeZone.getDefault().getID());
            this.setTime();
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    DigitalClockWidget.this.setTime();
                    DigitalClockWidget.this.getHandler().postDelayed(this, 1000L);
                }
            }, 1000L);
        }

        private void createTime(String timeZone) {
            if(timeZone != null) {
                this.mTime = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
            } else {
                this.mTime = Calendar.getInstance();
            }

        }

        private void setTime() {
            this.mTime.setTimeInMillis(System.currentTimeMillis());
            this.tvHour.setText(DateFormat.format("hh", this.mTime));
            this.tvMinute.setText(":"+DateFormat.format("mm", this.mTime));
            this.tvSecond.setText(":" + DateFormat.format("ss", this.mTime));
            this.tvMeridian.setText(DateFormat.format("a", this.mTime));
            this.tvDay.setText(DateFormat.format("dd", this.mTime));
            this.tvYear.setText(DateFormat.format("yyyy", this.mTime));
            this.tvDay_name.setText(DateFormat.format("EEEE", this.mTime));
            this.tvMonth.setText(DateFormat.format("MMM", this.mTime));
        }
}
