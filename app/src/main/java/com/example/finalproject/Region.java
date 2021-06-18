package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Region extends AppCompatActivity {
    ImageView arrow;
    ImageView menu;
    String selectedRegion="";
    View dialogView;
    Button btnG,btnI,btnD,btnK,btnJ,btnC,btnS,btnSE,btnCN,btnKB,btnDG,btnUS,btnBS,btnKN,btnGJ,btnJB,btnJN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region);
        btnG=(Button)findViewById(R.id.btnG);
        btnI=(Button)findViewById(R.id.btnI);
        btnD=(Button)findViewById(R.id.btnD);
        btnK=(Button)findViewById(R.id.btnK);
        btnJ=(Button)findViewById(R.id.btnJ);
        btnC=(Button)findViewById(R.id.btnC);
        btnS=(Button)findViewById(R.id.btnS);
        btnSE=(Button)findViewById(R.id.btnSE);
        btnCN=(Button)findViewById(R.id.btnCN);

        btnKB=(Button)findViewById(R.id.btnKB);
        btnDG=(Button)findViewById(R.id.btnDG);
        btnUS=(Button)findViewById(R.id.btnUS);
        btnBS=(Button)findViewById(R.id.btnBS);
        btnKN=(Button)findViewById(R.id.btnKN);
        btnGJ=(Button)findViewById(R.id.btnGJ);
        btnJB=(Button)findViewById(R.id.btnJB);
        btnJN=(Button)findViewById(R.id.btnJN);




        arrow=(ImageView)findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String myData = intent.getStringExtra("regionString");
        try {
            JSONObject jsonObject = new JSONObject(myData);
            btnG.setText("경기 "+jsonObject.getJSONObject("경기").getString("distanceStep"));
            btnS.setText("서울 "+jsonObject.getJSONObject("서울").getString("distanceStep"));
            btnI.setText("인천 "+jsonObject.getJSONObject("인천").getString("distanceStep"));
            btnD.setText("대전 "+jsonObject.getJSONObject("대전").getString("distanceStep"));
            btnK.setText("강원 "+jsonObject.getJSONObject("강원").getString("distanceStep"));
            btnJ.setText("제주 "+jsonObject.getJSONObject("제주").getString("distanceStep"));
            btnC.setText("충북 "+jsonObject.getJSONObject("충북").getString("distanceStep"));
            btnSE.setText("세종 "+jsonObject.getJSONObject("세종").getString("distanceStep"));
            btnCN.setText("충남 "+jsonObject.getJSONObject("충남").getString("distanceStep"));

            btnKB.setText("경북"+jsonObject.getJSONObject("경북").getString("distanceStep"));
            btnDG.setText("대구 "+jsonObject.getJSONObject("대구").getString("distanceStep"));
            btnUS.setText("울산 "+jsonObject.getJSONObject("울산").getString("distanceStep"));
            btnBS.setText("부산"+jsonObject.getJSONObject("부산").getString("distanceStep"));
            btnKN.setText("경남 "+jsonObject.getJSONObject("경남").getString("distanceStep"));
            btnGJ.setText("광주 "+jsonObject.getJSONObject("광주").getString("distanceStep"));
            btnJB.setText("전북 "+jsonObject.getJSONObject("전북").getString("distanceStep"));
            btnJN.setText("전남 "+jsonObject.getJSONObject("전남").getString("distanceStep"));




        } catch (JSONException e) {
            e.printStackTrace();
        }
        menu=(ImageView)findViewById(R.id.menuIv);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogView=(View) View.inflate(Region.this,R.layout.menu_dialog,null);
               AlertDialog.Builder dlg=new AlertDialog.Builder(Region.this);
               dlg.setTitle("지역 설정");
               dlg.setView(dialogView);
               final RadioGroup rg=(RadioGroup) dialogView.findViewById(R.id.radioGroup);


               dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       int id=rg.getCheckedRadioButtonId();
                       RadioButton rb=(RadioButton)dialogView.findViewById(id);
                       selectedRegion=rb.getText().toString();
                       Intent intent = new Intent(Region.this, LocationInfo.class);
                       intent.putExtra("selectedRegion",selectedRegion);
                       startActivity(intent);
                   }
               });
               dlg.setNegativeButton("취소",null);

               dlg.show();
            }
        });
    }
}
