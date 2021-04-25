package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WithdrawalSuccessActivity extends AppCompatActivity implements View.OnClickListener {
    TextView loginBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_success);

        loginBtn1 = findViewById(R.id.loginBtn1);
        loginBtn1.setOnClickListener(this);

        //회원탈퇴
        WithdrawalActivity withdrawalActivity = (WithdrawalActivity)WithdrawalActivity.withdrawalActivity;
        withdrawalActivity.finish();

        //셋팅
        SettingActivity settingActivity = (SettingActivity)SettingActivity.settingActivity;
        settingActivity.finish();

        //프라자
        QrActivity qrActivity = (QrActivity)QrActivity.qrActivity;
        qrActivity.finish();

        if(JoinActivity1.joinActivity1 != null){
            //재가입1
            JoinActivity1 joinScreenActivity1 = (JoinActivity1)JoinActivity1.joinActivity1;
            joinScreenActivity1.finish();
        }

        if(JoinActivity2.joinActivity2 != null){
            //재가입2
            JoinActivity2 joinScreenActivity2 = (JoinActivity2)JoinActivity2.joinActivity2;
            joinScreenActivity2.finish();
        }

        if(JoinActivity3.joinActivity3 != null){
            //재가입3
            JoinActivity3 joinScreenActivity3 = (JoinActivity3)JoinActivity3.joinActivity3;
            joinScreenActivity3.finish();
        }

        if(WebViewActivity.webViewActivity != null){
            //웹뷰
            WebViewActivity webViewActivity = (WebViewActivity)WebViewActivity.webViewActivity;
            webViewActivity.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn1 :
                Log.d("e4net","로그인버튼 누름");
                Intent intent = new Intent(WithdrawalSuccessActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                if(LoginActivity.loginActivity != null){
                    //로그인
                    LoginActivity loginActivity = (LoginActivity)LoginActivity.loginActivity;
                    loginActivity.finish();
                }
                break;
        }

    }
}
