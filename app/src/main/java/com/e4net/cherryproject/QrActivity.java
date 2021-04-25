package com.e4net.cherryproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.e4net.cherryproject.REST.RestCommon;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity qrActivity;
    ImageView qrBtn, setBtn, payBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        qrActivity = QrActivity.this;

        _init();
        _eventMapping();
        if(JoinActivity3.joinActivity3 != null){
            JoinActivity3 joinActivity3 = (JoinActivity3) JoinActivity3.joinActivity3;
            joinActivity3.finish();
        }


    }

    private void _init(){
        qrBtn = findViewById(R.id.qrBtn);
        setBtn = findViewById(R.id.setBtn);
        payBtn = findViewById(R.id.payBtn);
    }

    private void _eventMapping(){
        qrBtn.setOnClickListener(this);
        setBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qrBtn:
                Log.d("QrActiviey","QR버튼");
                scanCode();
                break;

            case R.id.setBtn:
                Log.d("QrActiviey","설정하기버튼");
                Intent intent = new Intent(QrActivity.this, SettingActivity.class);
                String user_name = getIntent().getStringExtra("user_name");
                intent.putExtra("user_name",user_name);
                startActivity(intent);
                break;

            case R.id.payBtn:
                Log.d("QrActiviey","기부내역버튼");
                Intent intent1 = new Intent(QrActivity.this, DonateHistoryActivity.class);
                startActivity(intent1);
                break;
        }

    }

    protected void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.setPrompt("사진 테두리 안에 QR코드를 인식해주세요.\nQR코드를 자동으로 인식합니다.");
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("QrActivity", "onActivityResult: .");
        Log.d("QrActivity","requestCode >>> : "+requestCode);
        Log.d("QrActivity","resultCode >>> : "+resultCode);
        Log.d("QrActivity","data >>> : "+data);
        Log.d("QrActivity","IntentIntegrator.REQUEST_CODE >>> : "+IntentIntegrator.REQUEST_CODE);
        Log.d("QrActivity","IntentIntegrator.Activity.RESULT_OK >>> : "+ Activity.RESULT_OK);

        if(resultCode == Activity.RESULT_OK) {

            IntentResult ir = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if (data == null) {
                return;
            }
            Log.d("QrActivity", "ir.getContents() >>>" + ir.getContents());
            String res = ir.getContents();
            Log.d("QrActivity", "res >>> : " + res);
            if (res.startsWith("cheryqr")) {
                String url = RestCommon.BASE_URL + "/qrScreen/qrScreen1";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                //OR code 가 cherryqr 아닐 때
                Toast.makeText(getApplicationContext(), "CherryQR이 아닙니다.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("QrActivity","else>>> ");
        }
    }
}