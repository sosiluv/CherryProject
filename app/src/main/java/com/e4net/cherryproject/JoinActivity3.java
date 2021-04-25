package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.e4net.cherryproject.REST.PostTask;
import com.e4net.cherryproject.common.codeCommon;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity3 extends AppCompatActivity implements View.OnClickListener {
    public static Activity joinActivity3;
    Button btn_next3;
    String user_id="";
    String user_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);
        joinActivity3 = JoinActivity3.this;

        btn_next3 = findViewById(R.id.btn_next3);
        btn_next3.setOnClickListener(this);

        JoinActivity1 joinActivity1 = (JoinActivity1) JoinActivity1.joinActivity1;
        joinActivity1.finish();

        JoinActivity2 joinActivity2 = (JoinActivity2) JoinActivity2.joinActivity2;
        joinActivity2.finish();

        WebViewActivity webViewActivity = (WebViewActivity) WebViewActivity.webViewActivity;
        webViewActivity.finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next3:
                Log.d("e4net","확인");
                Intent intent = new Intent(JoinActivity3.this, QrActivity.class);
                String user_name = getIntent().getStringExtra("user_name");
                intent.putExtra("user_name",user_name);
                startActivity(intent);
                break;
        }
    }

    class TaskPostTask extends PostTask {
        @Override
        protected void onPostExecute(String s) {
            Log.d("e4net","TaskPostTask 시작");
            Log.d("e4net","s>>>"+s);
            // s>>>{"code":"0000","message":"회원가입 성공하였습니다.","timestamp":"20201127101509","response":null}

            String code="";
            if(s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    code = jsonObject.getString("code");
                    Log.d("e4net", "code>>> : " + code);
                    if(code.equals(codeCommon.SUCCESS)){
                        Log.d("e4net", "0000");
//                        Intent intent = new Intent(JoinActivity3.this, QrActivity.class);
//                        startActivity(intent);

                    }else if(code.equals(codeCommon.SERVER_ERROR)){
                        Log.d("e4net", "0001");
                        Log.d("e4net", "code 0001 >>> : error ");
                        Intent intent = new Intent(JoinActivity3.this, ErrorActivity.class);
                        startActivity(intent);
                    }else if(code.equals(codeCommon.INFORMATION_NOT_OBTAINED)){
                        Log.d("e4net", "0002  >> 회원성공 실패");
                        Intent intent = new Intent(JoinActivity3.this, ErrorActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Intent intent = new Intent(JoinActivity3.this, ErrorActivity.class);
                startActivity(intent);
            }


        }
    }


}