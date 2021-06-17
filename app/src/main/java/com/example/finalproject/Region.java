package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Region extends AppCompatActivity {
    ImageView arrow;
    ImageView menu;
    String selectedRegion="";
    View dialogView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region);
        arrow=(ImageView)findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menu=(ImageView)findViewById(R.id.menuIv);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogView=(View) View.inflate(Region.this,R.layout.menu_dialog,null);
               AlertDialog.Builder dlg=new AlertDialog.Builder(Region.this);
               dlg.setTitle("지역 설정");
               dlg.setView(dialogView);
               final RadioGroup rg=(RadioGroup) dialogView.findViewById(R.id.radioGroup);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
               System.out.println(dialogView);

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
