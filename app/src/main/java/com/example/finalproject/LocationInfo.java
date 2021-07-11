package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class LocationInfo extends AppCompatActivity {
    TextView thisLocation;
    ImageView back;
    TextView updateTv;
    //거리두기 단계
    TextView stepTv;
    LinearLayout stepLy;
    //집합금지
    LinearLayout peopleLimitLy;
    ImageView peopleLimitIv;
    TextView peopleLimitTv;
    //시간제한
    LinearLayout timeLimitLy;
    ImageView timeLimitIv;
    TextView timeLimitTv;

    String json="";
    JSONObject jsonObject;
    JSONObject data;

    String distanceStep="";
    String period="";
    String  peopleLimit="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_info);
        thisLocation=(TextView)findViewById(R.id.thisLocation);
        back=(ImageView) findViewById(R.id.back);
        stepLy=(LinearLayout)findViewById(R.id.stepLy);
        Intent intent = getIntent();
        String myData = intent.getStringExtra("selectedRegion");
        thisLocation.setText(myData);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        stepLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Detail.class);
                intent.putExtra("period",period);
                startActivity(intent);
            }
        });

        json=loadJSONFromAsset();
        try {
            jsonObject = new JSONObject(json);
            data = jsonObject.getJSONObject("data");
            JSONObject region=data.getJSONObject("region");
            JSONObject regionObj=null;
            String update=data.getString("update");
            updateTv=(TextView) findViewById(R.id.update);
            updateTv.setText(update);
            switch (myData){
                case "경기":regionObj=region.getJSONObject("경기"); break;
                case "서울":regionObj=region.getJSONObject("서울"); break;
                case "인천":regionObj=region.getJSONObject("인천"); break;
                case "강원":regionObj=region.getJSONObject("강원"); break;
                case "충북":regionObj=region.getJSONObject("충북"); break;
                case "세종":regionObj=region.getJSONObject("세종"); break;
                case "경북":regionObj=region.getJSONObject("경북"); break;
                case "충남":regionObj=region.getJSONObject("충남"); break;
                case "대전":regionObj=region.getJSONObject("대전"); break;
                case "대구":regionObj=region.getJSONObject("대구"); break;
                case "전북":regionObj=region.getJSONObject("전북"); break;
                case "경남":regionObj=region.getJSONObject("경남"); break;
                case "울산":regionObj=region.getJSONObject("울산"); break;
                case "부산":regionObj=region.getJSONObject("부산"); break;
                case "광주":regionObj=region.getJSONObject("광주"); break;
                case "전남":regionObj=region.getJSONObject("전남"); break;
                case "제주":regionObj=region.getJSONObject("제주"); break;
                default:return;
            }
            distanceStep=regionObj.getString("distanceStep");
            period=regionObj.getString("period");
            peopleLimit=regionObj.getString("peopleLimit");
            stepTv=(TextView)findViewById(R.id.stepTv);
            stepTv.setText(distanceStep);
            System.out.println(distanceStep+" "+" "+peopleLimit+" ");
            if(peopleLimit.equals(false)){
                peopleLimitLy=(LinearLayout)findViewById(R.id.peopleLimitLy);
                peopleLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border_off));
                peopleLimitIv=(ImageView)findViewById(R.id.peopleLimitIv);
                peopleLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.people_off));
                peopleLimitTv=(TextView)findViewById(R.id.peopleLimitTv);
                peopleLimitTv.setTextColor(Color.parseColor("#6c7474"));
            }else{
                peopleLimitLy=(LinearLayout)findViewById(R.id.peopleLimitLy);
                peopleLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                peopleLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                peopleLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.people));
                peopleLimitTv=(TextView)findViewById(R.id.peopleLimitTv);
                peopleLimitTv.setTextColor(Color.parseColor("#ffd438"));
            }

            JSONObject step=data.getJSONObject("step");
            JSONObject stepObj=null;
            String time="";
            switch (distanceStep){
                case "1":
                case "1.5":
                            timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                            timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border_off));
                            timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                            timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time_off));
                            timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                            timeLimitTv.setTextColor(Color.parseColor("#6c7474"));
                            break;
                case "2": stepObj=step.getJSONObject("2"); time=stepObj.getString("timeLimit");
                    timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                    timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                    timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time));
                    timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));break;
                case "2.5":stepObj=step.getJSONObject("2.5"); time=stepObj.getString("timeLimit");
                    timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                    timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                    timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time));
                    timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));break;
                case "3":stepObj=step.getJSONObject("3"); time=stepObj.getString("timeLimit");
                    timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                    timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                    timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time));
                    timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));break;
                default:break;
            }
            timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
            if(time.equals("")){
                timeLimitTv.setText("시간 제한");
            }else{
                timeLimitTv.setText(time+"시 제한");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String jsonText="";
        try {
            InputStream is = getAssets().open("jsons/coronainfo.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            jsonText = new String(buffer, "UTF-8");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
            System.out.println(jsonText);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonText;

    }

}
