package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e4net.cherryproject.REST.RestCommon;

public class JoinActivity1 extends AppCompatActivity implements View.OnClickListener {
    public static Activity joinActivity1;
    ImageView back_btn;
    Button btn_next;
    CheckBox ck_all,ck_1,ck_2,ck_3;
    TextView show1,show2,show3;
    TextView tv_1, tv_bar;
    ProgressBar progressBar;
    String url = RestCommon.BASE_URL;
    Intent intent = new Intent(Intent.ACTION_VIEW);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);
        joinActivity1 = JoinActivity1.this;
        _init();
        _eventMapping();
    }

    private void _init(){
        back_btn = findViewById(R.id.back_btn);
        btn_next = findViewById(R.id.btn_next);
        ck_all = findViewById(R.id.ck_all);
        ck_1 = findViewById(R.id.ck_1);
        ck_2 = findViewById(R.id.ck_2);
        ck_3 = findViewById(R.id.ck_3);
        show1 = findViewById(R.id.show1);
        show2 = findViewById(R.id.show2);
        show3 = findViewById(R.id.show3);

    }

    private void _eventMapping(){

        back_btn.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        ck_all.setOnClickListener(this);
        ck_1.setOnClickListener(this);
        ck_2.setOnClickListener(this);
        ck_3.setOnClickListener(this);
        show1.setOnClickListener(this);
        show2.setOnClickListener(this);
        show3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_next:
                if(ck_all.isChecked()){
                    Intent intent = new Intent(JoinActivity1.this, JoinActivity2.class);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(JoinActivity1.this, "모두 동의하여 주십시오.", Toast.LENGTH_SHORT); toast.show();
                }
                break;
            case R.id.ck_all:
                if(ck_all.isChecked()){
                    ck_1.setChecked(true);
                    ck_2.setChecked(true);
                    ck_3.setChecked(true);
                }else{
                    ck_1.setChecked(false);
                    ck_2.setChecked(false);
                    ck_3.setChecked(false);
                }
                break;
            case R.id.ck_1:
                checkToggle();
                break;
            case R.id.ck_2:
                checkToggle();
                break;
            case R.id.ck_3:
                checkToggle();
                break;
            case R.id.show1:
                Log.d("show","서비스이용약관 누름");
                url = RestCommon.BASE_URL + "/terms/terms/termsOfService";
                intent = new Intent(JoinActivity1.this,WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                break;
            case R.id.show2:
                Log.d("show","개인정보처리방침 누름");
                url = RestCommon.BASE_URL + "/terms/terms/privacyPolicy";
                intent = new Intent(JoinActivity1.this,WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                break;
            case R.id.show3:
                Log.d("show","이용자유의사항 누름");
                url = RestCommon.BASE_URL + "/terms/terms/userNotice";
                intent = new Intent(JoinActivity1.this,WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                break;

        }
    }

    void checkToggle(){
        if(ck_1.isChecked() && ck_2.isChecked() && ck_3.isChecked()){
            ck_all.setChecked(true);
        }else{
            ck_all.setChecked(false);
        }
    }
}