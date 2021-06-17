package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class LocationInfo extends AppCompatActivity {
    TextView thisLocation;
    ImageView back;
    String json="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_info);
        thisLocation=(TextView)findViewById(R.id.thisLocation);
        back=(ImageView) findViewById(R.id.back);

        Intent intent = getIntent();
        String myData = intent.getStringExtra("selectedRegion");
        thisLocation.setText(myData);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        loadJSONFromAsset();

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("jsons/coronainfo.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
            System.out.println(json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
