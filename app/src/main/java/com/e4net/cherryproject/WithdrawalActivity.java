package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.e4net.cherryproject.REST.PostObject;
import com.e4net.cherryproject.REST.PostTask;
import com.e4net.cherryproject.REST.RestCommon;
import com.e4net.cherryproject.common.codeCommon;

import org.json.JSONException;
import org.json.JSONObject;

public class WithdrawalActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity withdrawalActivity;
    TextView withBackBtn, withdrawalBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        withdrawalActivity = WithdrawalActivity.this;
        withBackBtn = findViewById(R.id.withBackBtn);
        withdrawalBtn1 = findViewById(R.id.loginBtn1);

        withBackBtn.setOnClickListener(this);
        withdrawalBtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withBackBtn:
                Log.d("e4net", "뒤로가기");
                finish();
                break;

            case R.id.loginBtn1:
                Log.d("e4net", "회원탈퇴");
                String url = RestCommon.BASE_URL + "/cheryqr/membershipWithdrawal/updateMembershipWithdrawal";
                Log.d("e4net", "url >>> : " + url);

                JSONObject jsonObject = new JSONObject();
                PostObject po = new PostObject(url, null);
                new TaskPostTask().execute(po);
                break;

        }
    }

    class TaskPostTask extends PostTask {
        @Override
        protected void onPostExecute(String s) {
            Log.d("e4net","TaskPostTask 시작");
            Log.d("e4net","s>>>"+s);// s>>>{"code":"0000","message":"회원탈퇴 성공하였습니다.","timestamp":"20201126164412","response":null}

            String code="";
            if(s == null){
                Log.d("e4net","회원탈퇴 is null...??");
                Intent intent = new Intent(WithdrawalActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                code = jsonObject.getString("code");
                Log.d("e4net", "code>>> : " + code);

                if(code.equals(codeCommon.SUCCESS)){
                    Log.d("e4net", "0000");
                    RestCommon.ID_TOKEN = "";
                    Intent intent = new Intent(WithdrawalActivity.this, WithdrawalSuccessActivity.class);
                    startActivity(intent);
                }else if(code.equals(codeCommon.SERVER_ERROR)){
                    Log.d("e4net", "0001");
                    Toast.makeText(getApplicationContext(),"에러.",Toast.LENGTH_LONG).show();
                    Log.d("e4net", "code 0001 >>> : error ");
                }else if(code.equals(codeCommon.INFORMATION_NOT_OBTAINED)){
                    Log.d("e4net", "0002  >> 회원탈퇴 실패");
                    Toast.makeText(getApplicationContext(),"회원탈퇴 실패.",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}