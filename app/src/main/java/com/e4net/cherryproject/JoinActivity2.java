package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.e4net.cherryproject.REST.RestCommon;

public class JoinActivity2 extends AppCompatActivity implements View.OnClickListener {
    public static Activity joinActivity2;
    ImageView back_btn2;
    Button btn_next2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);
        joinActivity2 = JoinActivity2.this;
        _init();
        _eventMapping();
    }

    private void _init(){
        back_btn2 = findViewById(R.id.back_btn2);
        btn_next2 = findViewById(R.id.btn_next2);

    }

    private void _eventMapping(){

        back_btn2.setOnClickListener(this);
        btn_next2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_btn2:
                finish();
                break;
            case R.id.btn_next2:
                Intent intent = new Intent(JoinActivity2.this, WebViewActivity.class);
                String url = RestCommon.BASE_URL + "/joinScreen/joinScreen1";
                intent.putExtra("url",url);
                startActivity(intent);

//                Intent intent = new Intent(JoinActivity2.this,  JoinActivity3.class);
//                String user_name = getIntent().getStringExtra("user_name");
//                intent.putExtra("user_name",user_name);
//                startActivity(intent);
                break;

        }
    }
}