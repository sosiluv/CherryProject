package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e4net.cherryproject.REST.PostObject;
import com.e4net.cherryproject.REST.PostTask;
import com.e4net.cherryproject.REST.RestCommon;
import com.e4net.cherryproject.ROOM.Entities.DonateHistoryEntities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DonateHistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private static Toast sToast;
    ImageView lastBtn, nextBtn, backBtn2;
    TextView dateTV, msgTV, upBtn;
    ListView listView;
    CustomAdapter customAdapter;
    ProgressBar pb_donate_history;
    String selectDt = "";

    String year="";
    String month="";
    String date ="";
    String url="";
    String db_date="";
    int Cnt=2;
    ArrayList aList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_history);

        _init();
        _eventMapping();
        nowDateSearch();

        //        long now = System.currentTimeMillis();
//        Date mDate = new Date(now);
//        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
//        SimpleDateFormat monthrDate = new SimpleDateFormat("MM");
//        year = yearDate.format(mDate);
//        month = monthrDate.format(mDate);
//        Log.d("e4net"," year >>> : "+year);
//        Log.d("e4net"," month >>> : "+month);
//        date = year+"."+month;
//        Log.d("e4net","date>>> : "+date);
//        dateTV.setText(date);
//
//        db_date=year+"-"+month;
//        Log.d("e4net","db_date>>> : "+db_date);
        customAdapter = new CustomAdapter(aList);
        listView.setAdapter(customAdapter);
        SelectList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                url = RestCommon.BASE_URL + "/cheryqr/donate/selectDonateDetail";
                String userSetleHstSn = customAdapter.aList.get(position).getUserSetleHstSn();
                Log.d("DonateHistory","selectDonateDetail>>> "+userSetleHstSn);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("userSetleHstSn",userSetleHstSn);
                    Log.d("e4net",">>json :"+jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PostObject po = new PostObject(url,jsonObject);
                new TaskPostTask_detail().execute(po);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void _init(){
        listView = findViewById(R.id.listView);
        msgTV = findViewById(R.id.msgTV);
        lastBtn = findViewById(R.id.lastBtn);
        nextBtn = findViewById(R.id.nextBtn);
        dateTV = findViewById(R.id.dateTV);
        backBtn2 = findViewById(R.id.backBtn2);
        upBtn = findViewById(R.id.upBtn);
        pb_donate_history = findViewById(R.id.pb_donate_history);
    }

    private void _eventMapping(){
        lastBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        backBtn2.setOnClickListener(this);
        upBtn.setOnClickListener(this);

    }


    private void nowDateSearch(){
        Date now = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Log.d("DonateHistory", "dateFormat.format(now) : " + dateFormat.format(now));
        String[] dateSplit = dateFormat.format(now).split(". ");
        String year = dateSplit[0];
        String month = dateSplit[1];
        selectDt = year + "-" + month;
        dateTV.setText(selectDt.replace("-","."));
        Log.d("DonateHistory", "now Date : " + selectDt);
    }

    @Override
    public void onClick(View v) {
        String[] date = selectDt.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        switch (v.getId()){
            case R.id.lastBtn:
                if(month-1 == 0){
                    year-=1;
                    month=12;
                }else{
                    month-=1;
                }
                if(month < 10){
                    selectDt = year + "-0" + month;
                }else{
                    selectDt = year + "-" + month;
                }
                dateTV.setText(selectDt.replace("-","."));
                db_date = year + "-" + month;
                customAdapter.aList.clear();
                SelectList();
                break;
            case R.id.nextBtn:
                if(month+1 == 13){
                    year+=1;
                    month=1;
                }else{
                    month+=1;
                }
                if(month < 10){
                    selectDt = year + "-0" + month;
                }else{
                    selectDt = year + "-" + month;
                }
                dateTV.setText(selectDt.replace("-", "."));
                db_date = year + "-" + month;
                customAdapter.aList.clear();
                SelectList();
                break;
            case R.id.upBtn:
                SelectList();
                break;
            case R.id.back_btn2:
                finish();

                break;
        }
    }

    void SelectList(){
        pb_donate_history.setVisibility(View.VISIBLE);
        url = RestCommon.BASE_URL + "/cheryqr/donate/selectDonateList";
        Log.d("DonateHistory",url);
        Log.d("DonateHistory","db_date aaaaaaaaaaa>>>>>>"+db_date);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.accumulate("selectListCount", customAdapter.aList.size()+1);
            jsonObject.accumulate("selectDt", db_date);
            Log.d("DonateHistory",">>json :"+jsonObject);

        }catch(JSONException e){
            e.printStackTrace();
        }
        PostObject po = new PostObject(url, jsonObject);
        new TestPostTask().execute(po);
    }

    class TestPostTask extends PostTask{
        @Override
        protected void onPostExecute(String s) {
            Log.d("DonateHistory", "TestPostTask 시작");
            Log.d("DonateHistory", "s>>>>>>>>>>>>>" + s);

            if(s != null){
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    String response = jsonObject.getString("response");
                    JSONArray jsonArray = new JSONArray(response);

                    if(customAdapter.aList.size() == 0 && jsonArray.length() == 0){
                        pb_donate_history.setVisibility(View.GONE);
                        upBtn.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                        msgTV.setVisibility(View.VISIBLE);
                        return;
                    }

                    if(jsonArray.length() == 0 ){
                        Toast.makeText(getApplicationContext(), "더이상 가져올 기부내역이 없습니다.", Toast.LENGTH_SHORT).show();
                        pb_donate_history.setVisibility(View.GONE);
                        upBtn.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        msgTV.setVisibility(View.GONE);
                        return;
                    }

                    upBtn.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    msgTV.setVisibility(View.GONE);

                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);
                        String userSetleHstSn = subJsonObject.getString("userSetleHstSn");
                        String setleDt = subJsonObject.getString("setleDt");
                        String mrhstNm = subJsonObject.getString("mrhstNm");
                        String mrhstDc = subJsonObject.getString("mrhstDc");
                        String setleAmt = subJsonObject.getString("setleAmt");
                        String setleSttusCode = subJsonObject.getString("setleSttusCode");
                        Log.d("DonateHistory","de>>>>"+setleDt+" : "+ mrhstNm+" : "+mrhstDc+" : "+setleAmt+ " : "+ setleSttusCode);

                        DonateHistoryEntities de = new DonateHistoryEntities();
                        de.setUserSetleHstSn(userSetleHstSn);
                        de.setSetleDt(setleDt);
                        de.setMrhstNm(mrhstNm);
                        de.setMrhstDc(mrhstDc);
                        de.setSetleAmt(setleAmt);
                        de.setSetleSttusCode(setleSttusCode);

                        customAdapter.aList.add(de);
                        Log.d("DonateHistory", "customAdapter.aList.size : " + customAdapter.aList.size());
                    }
                    customAdapter.notifyDataSetChanged();
                    pb_donate_history.setVisibility(View.GONE);
                }catch(JSONException e){
                    e.printStackTrace();
                    pb_donate_history.setVisibility(View.GONE);
                }
            }else{
                Intent intent = new Intent(DonateHistoryActivity.this, ErrorActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    //영수증
    class TaskPostTask_detail extends PostTask {
        @Override
        protected void onPostExecute(String s) {
            Log.d("DonateHistory","TaskPostTask_detail 시작");

            if(s != null){
                try {
                    JSONObject jsonRes = new JSONObject(s);
                    Log.d("e4net",">>> jsonRes : "+jsonRes);
                    JSONObject subJsonObject = new JSONObject(jsonRes.getString("response"));
                    String userNm = subJsonObject.getString("userNm");
                    String userSetleHstSn = subJsonObject.getString("userSetleHstSn");
                    String setleDt = subJsonObject.getString("setleDt");
                    String setleAmt = subJsonObject.getString("setleAmt");
                    String mrhstNm = subJsonObject.getString("mrhstNm");
                    String bizrno = subJsonObject.getString("bizrno");
                    String cprNm = subJsonObject.getString("cprNm");
                    String zipAdres = subJsonObject.getString("zipAdres");
                    String detailAdres = subJsonObject.getString("detailAdres");
                    String mrhstHmpgUrl = subJsonObject.getString("mrhstHmpgUrl");
                    // String setleSttusCode = subJsonObject.getString("setleSttusCode");

                    Intent intent = new Intent(DonateHistoryActivity.this, DonateDetailActivity.class);
                    intent.putExtra("userNm",userNm);
                    intent.putExtra("userSetleHstSn",userSetleHstSn);
                    intent.putExtra("setleDt",setleDt);
                    intent.putExtra("setleAmt",setleAmt);
                    intent.putExtra("mrhstNm",mrhstNm);
                    intent.putExtra("bizrno",bizrno);
                    intent.putExtra("cprNm",cprNm);
                    intent.putExtra("zipAdres",zipAdres);
                    intent.putExtra("detailAdres",detailAdres);
                    intent.putExtra("mrhstHmpgUrl",mrhstHmpgUrl);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Intent intent = new Intent(DonateHistoryActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
            Log.d("DonateHistory","TaskPostTask_detail 끝");
        }
    }



    class CustomAdapter extends BaseAdapter {
        ArrayList<DonateHistoryEntities> aList;

        public CustomAdapter(ArrayList<DonateHistoryEntities> aList) {
            this.aList = aList;
        }

        @Override
        public int getCount() {
            return aList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.donate_element, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.dtTV = convertView.findViewById(R.id.dtTV);
                viewHolder.nmTV = convertView.findViewById(R.id.nmTV);
                viewHolder.dcTV = convertView.findViewById(R.id.dcTV);
                viewHolder.amtTV = convertView.findViewById(R.id.amtTV);
                viewHolder.payTV = convertView.findViewById(R.id.payTV);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DonateHistoryEntities de = new DonateHistoryEntities();
            de = aList.get(position);
            viewHolder.dtTV.setText(de.getSetleDt());
            viewHolder.nmTV.setText("[" + de.getMrhstNm() + "]");
            viewHolder.dcTV.setText(de.getMrhstDc());
            viewHolder.amtTV.setText(de.getSetleAmt() + "원");
            viewHolder.payTV.setText(de.getSetleSttusCode());

            return convertView;
        }
    }
    private class ViewHolder {
        TextView dtTV;
        TextView nmTV;
        TextView dcTV;
        TextView amtTV;
        TextView payTV;
    }

    public void showToast() {
        if (sToast == null) {
            sToast = Toast.makeText(getApplicationContext(), "조회이력이 더 이상 없습니다", Toast.LENGTH_SHORT);
        } else {
            sToast.setText("조회이력이 더 이상 없습니다");
        }
        sToast.show();
    }
}