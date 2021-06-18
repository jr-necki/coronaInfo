package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detail extends AppCompatActivity {
    TextView periodTv;
    ImageView backBtn;
    TextView ddayTv;
    String startM="";
    String startD="";
    String finishM="";
    String finishD="";
    int leftDays=0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_step_detail);
        backBtn=(ImageView)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //날짜 넣기
        Intent intent = getIntent();
        String myData = intent.getStringExtra("period");
        String[] data1=myData.split("~");
        String start=data1[0];
        String end =data1[1];

        final String targetDay = end;

        // 목표 날짜
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        final Date targetDate;
        try {
            targetDate = yyyyMMdd.parse(targetDay);
            // 현재 날짜
            final Date todayDate = new Date();
            final String todayDay = yyyyMMdd.format(todayDate);

            long gap = targetDate.getTime() - todayDate.getTime();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(gap / (24 * 60 * 60 * 1000) + 1 + "일 (" + targetDay + " ~ " + todayDay + ")");
            ddayTv=(TextView)findViewById(R.id.dday);
            gap=gap / (24 * 60 * 60 * 1000) + 1;
            ddayTv.setText("D-" +gap+ "");
        } catch (ParseException e) {
            e.printStackTrace();
        }



        periodTv=(TextView)findViewById(R.id.periodTv);
        periodTv.setText(myData);

    }

}
