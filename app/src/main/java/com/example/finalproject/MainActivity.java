package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    GPSTracker gpsTracker;
    TextView addrTv;
    ImageView editIcon;
    Button mapBtn;
    LinearLayout stepLy;
    TextView updateTv;
    //거리두기 단계
    TextView stepTv;
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
    JSONObject region;
    String regionString;

    String distanceStep="";
    String period="";
    String  peopleLimit="";

    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsTracker = new GPSTracker(MainActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String address = getCurrentAddress(latitude, longitude);
        addrTv=(TextView) findViewById(R.id.addrTv);
        addrTv.setText(address);
        String[] values=address.split(" ");
        String nowRegion=values[1];
        settingData(nowRegion);
        editIcon=(ImageView) findViewById(R.id.editIcon);
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView=(View) View.inflate(MainActivity.this,R.layout.edit_location,null);
                AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("수정");
                dlg.setView(dialogView);
                final EditText editAddr=(EditText)dialogView.findViewById(R.id.editAddr);
                editAddr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editAddr.setText("");
                    }
                });
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        regionString=(editAddr.getText().toString());
                        addrTv.setText(regionString);
                        settingData(regionString);
                    }
                });
                dlg.setNegativeButton("취소",null);
                dlg.show();
            }
        });

    }

    private void settingData(String nowRegion) {

        //지역 이름 정하기
        if(nowRegion.contains("경기")){
            nowRegion="경기";
        }else if(nowRegion.contains("서울")){
            nowRegion="서울";
        }else if(nowRegion.contains("인천")){
            nowRegion="인천";
        }else if(nowRegion.contains("강원")){
            nowRegion="강원";
        }else if(nowRegion.contains("충북")||nowRegion.contains("충청북도")){
            nowRegion="충북";
        }else if(nowRegion.contains("세종")){
            nowRegion="세종";
        }else if(nowRegion.contains("경북")||nowRegion.contains("경상북도")){
            nowRegion="경북";
        }else if(nowRegion.contains("충남")||nowRegion.contains("충청남도")){
            nowRegion="충남";
        }else if(nowRegion.contains("대전")){
            nowRegion="대전";
        }else if(nowRegion.contains("대구")){
            nowRegion="대구";
        }else if(nowRegion.contains("전북")||nowRegion.contains("전라북도")){
            nowRegion="전북";
        }else if(nowRegion.contains("전남")||nowRegion.contains("전라남도")){
            nowRegion="전남";
        }else if(nowRegion.contains("광주")){
            nowRegion="광주";
        }else if(nowRegion.contains("경남")||nowRegion.contains("경상남도")){
            nowRegion="경남";
        }else if(nowRegion.contains("울산")){
            nowRegion="울산";
        }else if(nowRegion.contains("부산")){
            nowRegion="부산";
        }else{
            nowRegion="제주";
        }

        mapBtn=(Button)findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Region.class);
                intent.putExtra("regionString",regionString);
                startActivity(intent);
            }
        });
        stepTv=(TextView)findViewById(R.id.stepTv);
        stepLy=(LinearLayout)findViewById(R.id.stepLy);
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
            String update=data.getString("update");
            updateTv=(TextView) findViewById(R.id.update);
            updateTv.setText(update);
            regionString=data.getString("region");
            region=data.getJSONObject("region");
            JSONObject regionObj=null;
            switch (nowRegion){
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
                default:regionObj=region.getJSONObject("서울"); break;
            }
            distanceStep=regionObj.getString("distanceStep");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+distanceStep+"단계");
            stepTv.setText(distanceStep);
            period=regionObj.getString("period");
            peopleLimit=regionObj.getString("peopleLimit");



            System.out.println(distanceStep+" "+" "+peopleLimit+" ");
            if(peopleLimit.equals(false)){
                peopleLimitLy=(LinearLayout)findViewById(R.id.peopleLimitLy);
                peopleLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border_off));
                peopleLimitIv=(ImageView)findViewById(R.id.peopleLimitIv);
                peopleLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.people_off));
                peopleLimitTv=(TextView)findViewById(R.id.peopleLimitTv);
                peopleLimitTv.setTextColor(Color.parseColor("#6c7474"));
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
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));
                break;
                case "2.5":stepObj=step.getJSONObject("2.5"); time=stepObj.getString("timeLimit");
                    timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                    timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                    timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time));
                    timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));
                break;
                case "3":stepObj=step.getJSONObject("3"); time=stepObj.getString("timeLimit");
                    timeLimitLy=(LinearLayout)findViewById(R.id.timeLimitLy);
                    timeLimitLy .setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    timeLimitIv=(ImageView)findViewById(R.id.timeLimitIv);
                    timeLimitIv.setBackground(ContextCompat.getDrawable(this, R.drawable.time));
                    timeLimitTv=(TextView)findViewById(R.id.timeLimitTv);
                    timeLimitTv.setTextColor(Color.parseColor("#ffd438"));
                break;
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

    ////////////////////////////////////주소/////////////////////////////////////////////////////////////////////
    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 100);
           for(int i=0;i<addresses.size();i++){
               System.out.println(addresses.get(i));
           }
        } catch (IOException e) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "지오코더 서비스 사용불가";

        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
            return "주소 미발견";
        }
        Address address = addresses.get(1);
        return address.getAddressLine(0).toString() + "\n";
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, 20001);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2001: //사용자가 GPS 활성 시켰는지 검사
                Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                break;
        }

    }

}