package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DonateDetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView userNmTV, userSetleHstSnTV, setleDtTV, setleAmtTV1,setleAmtTV2, mrhstNmTV,
            bizrnoTV, cprNmTV, zipAdresTV, detailAdresTV, mrhstHmpgUrlTV;
    ImageView closeBtn;
    String mrhstHmpgUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_detail);

        userNmTV =findViewById(R.id.userNmTV);
        userSetleHstSnTV =findViewById(R.id.userSetleHstSnTV);
        setleDtTV =findViewById(R.id.setleDtTV);
        setleAmtTV1 =findViewById(R.id.setleAmtTV1);
        setleAmtTV2 =findViewById(R.id.setleAmtTV2);
        mrhstNmTV =findViewById(R.id.mrhstNmTV);
        bizrnoTV =findViewById(R.id.bizrnoTV);
        cprNmTV =findViewById(R.id.cprNmTV);
        zipAdresTV =findViewById(R.id.zipAdresTV);
        detailAdresTV =findViewById(R.id.detailAdresTV);
        mrhstHmpgUrlTV =findViewById(R.id.mrhstHmpgUrlTV);
        closeBtn =findViewById(R.id.closeBtn);

        mrhstHmpgUrlTV.setOnClickListener(this);
        closeBtn.setOnClickListener(this);

        String userNm =getIntent().getStringExtra("userNm");
        String userSetleHstSn =getIntent().getStringExtra("userSetleHstSn");
        String setleDt =getIntent().getStringExtra("setleDt");
        String setleAmt =getIntent().getStringExtra("setleAmt");
        String mrhstNm =getIntent().getStringExtra("mrhstNm");
        String bizrno =getIntent().getStringExtra("bizrno");
        String cprNm =getIntent().getStringExtra("cprNm");
        String zipAdres =getIntent().getStringExtra("zipAdres");
        String detailAdres =getIntent().getStringExtra("detailAdres");
        mrhstHmpgUrl =getIntent().getStringExtra("mrhstHmpgUrl");

        userNmTV.setText(userNm);
        userSetleHstSnTV.setText(userSetleHstSn);
        setleDtTV.setText(setleDt);
        setleAmtTV1.setText(setleAmt+"원");
        setleAmtTV2.setText(setleAmt+"원");
        mrhstNmTV.setText(mrhstNm);
        bizrnoTV.setText(bizrno);
        cprNmTV.setText(cprNm);
        zipAdresTV.setText(zipAdres);
        detailAdresTV.setText(detailAdres);
        mrhstHmpgUrlTV.setText(mrhstHmpgUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mrhstHmpgUrlTV:
                Log.d("e4net", "mrhstHmpgUrlTV>>> " + mrhstHmpgUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.valueOf(mrhstHmpgUrl)));
                startActivity(intent);
                break;

            case R.id.closeBtn:
                finish();
                break;
        }
    }
}