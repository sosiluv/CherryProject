package com.e4net.cherryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.e4net.cherryproject.REST.PostObject;
import com.e4net.cherryproject.REST.PostTask;
import com.e4net.cherryproject.REST.RestCommon;
import com.e4net.cherryproject.common.codeCommon;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity loginActivity;
    ProgressBar progressBar;
    private static final String TAG = "firebaseTag";
    private SignInButton btn_firebase;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private String name;
    private String email;
    private String token;
    private String pnum;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Log.i(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>"+ Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

        mAuth = FirebaseAuth.getInstance();

//        if (mAuth.getCurrentUser() != null) {
//            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//            startActivity(intent);
//            finish();
//        }
        signOut();
        _init();
        _eventMapping();

    }

    private void _init(){
        btn_firebase = findViewById(R.id.btn_firebase);
        progressBar = findViewById(R.id.pb_login);
    }

    private void _eventMapping(){
        btn_firebase.setOnClickListener(this);
    }

    void ShowAlert(){
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(LoginActivity.this);
        myAlertBuilder.setTitle("!알림");
        myAlertBuilder.setMessage("등록된 회원정보가 존재하지 않습니다.");
        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity1.class);
                startActivity(intent);
            }
        });
        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myAlertBuilder.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_firebase:
                Toast.makeText(LoginActivity.this,"구글 로그인",Toast.LENGTH_LONG).show();
                signIn();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void signIn() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("signOut","Sign Out!!!");
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);
                            Log.d(TAG, "signInWithCredential : SUCCESS");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "idToken : "+ idToken);
                            Log.d(TAG,"userDisplayName : "+user.getDisplayName());
                            Log.d(TAG,"usergetEmail : "+user.getEmail());
                            Log.d(TAG,"usergetPhoneNumber : "+user.getPhoneNumber());
                            Log.d(TAG,"userDisplaytoken : "+user.getIdToken(true));
                            Log.d(TAG,"user.getProviderId() : "+user.getProviderId());


                            Task<GetTokenResult> gettask = user.getIdToken(true);
                            gettask.addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.VISIBLE);
                                        token = task.getResult().getToken();
                                        Log.d(TAG,"TOKEN : " + token);
                                        String url = RestCommon.BASE_URL + "/cheryqr/login/login";
                                        Log.d(TAG,url);
                                        RestCommon.ID_TOKEN = token;
                                        JSONObject jo = new JSONObject();
                                        JSONObject json = new JSONObject();
                                        try {
                                            jo.put("userId",user.getEmail());
                                            jo.put("user",user.getDisplayName());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        PostObject po = new PostObject(url, null);
                                        new TestPostTask().execute(po);
                                    }
                                }
                            });


//                            updateUI(user);
                        }else{
                            Log.w(TAG, "signInWithCredential : Failed",task.getException());
//                            updateUI(null);
                        }
                    }
                });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch ( ApiException e ){
                Log.w(TAG, "Google 로그인 실패", e);

            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        signOut();
        Log.d("signout",">>>>>>>>onRestart");
    }

    class TestPostTask extends PostTask {
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute() :" + s);
            String code = "";
            String userId = "";
            String userStatusCode = "";

            if(s == null){
                Intent intent = new Intent(LoginActivity.this, ErrorActivity.class);
                startActivity(intent);
            }else{
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    code = jsonObject.getString("code");

                    Log.d("e4net", "code>>> : " + code);
                    Log.d("e4net", "jsonObject>>> : " + jsonObject);

                    try {
                        JSONObject subJsonObject = new JSONObject(jsonObject.getString("response"));
                        userId = subJsonObject.getString("userId");
                        userStatusCode = subJsonObject.getString("userSttusCode");
                        Log.d("e4net", "userId>>> : " + userId);
                        Log.d("e4net", "userSttusCode>>> : " + userStatusCode);
                        progressBar.setVisibility(View.GONE);
                    }catch(JSONException e){

                    }

                    if (code.equals(codeCommon.SUCCESS)) {
                        Log.d("e4net", "0000");
                        if (userStatusCode.equals("90")) {
                            Log.d("e4net", "90");
                            progressBar.setVisibility(View.GONE);
                            ShowAlert();
                        } else if (userStatusCode.equals("01")) {
                            progressBar.setVisibility(View.GONE);
                            Log.d("e4net", "01");
                            Intent intent = new Intent(LoginActivity.this, QrActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else if (code.equals(codeCommon.SERVER_ERROR)) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("e4net", "0001");
                        Log.d("e4net", "code 0002 >>> : error ");
                        Intent intent = new Intent(LoginActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    } else if (code.equals(codeCommon.INFORMATION_NOT_OBTAINED)) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("e4net", "0002  >> 회원가입");
                        ShowAlert();

                        Log.d("e4net", "code 0002 >>> : error ");
                    }
                    progressBar.setVisibility(View.GONE);
                }catch(JSONException e){
                    e.printStackTrace();

                }
            }



        }
    }
}