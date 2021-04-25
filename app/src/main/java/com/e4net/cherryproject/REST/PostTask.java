package com.e4net.cherryproject.REST;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostTask extends AsyncTask<PostObject, String, String> {

    final String TAG = getClass().getSimpleName();

    @Override
    protected String doInBackground(PostObject... postObject) {
        HttpURLConnection conn = null;
        BufferedReader br = null;



        try{
            URL url = new URL(postObject[0].url);
            Log.d(TAG, "postObject[0].url : "+ postObject[0].url);
            Log.d(TAG, "postObject[1].url : "+ postObject[0].jsonObject);

            if(RestCommon.ID_TOKEN!=null){
                Log.d(TAG, "ID_TOKEN : " + RestCommon.ID_TOKEN);
            }else{
                Log.d(TAG, "ID_TOKEN : null");
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + RestCommon.ID_TOKEN);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/html");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept","*/*");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();


            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            if(postObject[0].jsonObject !=null){
                bw.write(postObject[0].jsonObject.toString());
                Log.d("e4net","postObj[0].jsonObject.toString() : "+postObject[0].jsonObject.toString());
            }

            bw.flush();
            bw.close();

            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = br.readLine()) != null){
                buffer.append(line);
            }

            return buffer.toString();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }

            try{
                if(br != null){
                    br.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        return null;
    }
}
