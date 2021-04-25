package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.e4net.cherryproject.REST.RestCommon;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity settingActivity;
    ImageButton backBtn, pinBtn, accBtn, chServiceBtn, serviceBtn, privacyBtn, noticeBtn;
    TextView emailbtn, withdrawalBtn;

    private static final int RC_SIGN_IN = 9001;
    String token = "";
    private FirebaseAuth mAuth;
    String url = RestCommon.BASE_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingActivity = SettingActivity.this;

        backBtn = findViewById(R.id.backBtn);
        pinBtn = findViewById(R.id.pinBtn);
        accBtn = findViewById(R.id.accBtn);
        // chServiceBtn = findViewById(R.id.chServiceBtn);
        serviceBtn = findViewById(R.id.serviceBtn);
        privacyBtn = findViewById(R.id.privacyBtn);
        noticeBtn = findViewById(R.id.noticeBtn);
        emailbtn = findViewById(R.id.emailbtn);
        withdrawalBtn = findViewById(R.id.withdrawalBtn);

        backBtn.setOnClickListener(this);
        pinBtn.setOnClickListener(this);
        accBtn.setOnClickListener(this);
        //  chServiceBtn.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        privacyBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        emailbtn.setOnClickListener(this);
        withdrawalBtn.setOnClickListener(this);

        //동적링크
        //createDeepLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                Log.d("e4net", "뒤로가기버튼 누름");
                finish();
                break;

            case R.id.pinBtn:
                Log.d("e4net", "기부비밀번호번경 누름");
                url = RestCommon.BASE_URL + "/pinResetScreen/pinResetScreen1";
                Log.d("e4net", "urlaaaaaaaaaa >>>> " + url);

                Intent it1 = new Intent(SettingActivity.this, WebViewActivity.class);
                it1.putExtra("url", url);
                startActivity(it1);
                break;

            case R.id.accBtn:
                Log.d("e4net", "계좌관리 누름");
                url = RestCommon.BASE_URL + "/accountScreen/accountScreen1";
                Log.d("e4net", "urlaaaaaaaaaa >>>> " + url);

                Intent it2 = new Intent(SettingActivity.this, WebViewActivity.class);
                it2.putExtra("url", url);
                startActivity(it2);
                break;


            case R.id.serviceBtn:
                Log.d("e4net", "서비스이용약관 누름");
                url = RestCommon.BASE_URL + "/terms/terms/termsOfService";
                Intent it3 = new Intent(SettingActivity.this, WebViewActivity.class);
                it3.putExtra("url", url);
                startActivity(it3);
                break;

            case R.id.privacyBtn:
                Log.d("e4net", "개인정보처리방침 누름");
                url = RestCommon.BASE_URL + "/terms/terms/privacyPolicy";
                Intent it4 = new Intent(SettingActivity.this, WebViewActivity.class);
                it4.putExtra("url", url);
                startActivity(it4);
                break;

            case R.id.noticeBtn:
                Log.d("e4net", "이용자유의사항 누름");
                url = RestCommon.BASE_URL + "/terms/terms/userNotice";
                Intent it5 = new Intent(SettingActivity.this, WebViewActivity.class);
                it5.putExtra("url", url);
                startActivity(it5);
                break;

            case R.id.emailbtn:
                Log.d("e4net", "문의하기 누름");
                String user_name = getIntent().getStringExtra("user_name");

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"support@cherry.charity"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                //email.putExtra(Intent.EXTRA_SUBJECT,user_name+"님 문의사항");
                //email.putExtra(Intent.EXTRA_TEXT,"보낼 email 내용을 미리 적어 놓을 수 있습니다.\n");
                startActivity(email);
                break;

            case R.id.withdrawalBtn:
                Log.d("e4net", "회원탈퇴 누름");
                Intent intent1 = new Intent(SettingActivity.this, WithdrawalActivity.class);
                startActivity(intent1);
                break;
        }
    }
}