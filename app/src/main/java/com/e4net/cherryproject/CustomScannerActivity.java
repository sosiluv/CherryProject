package com.e4net.cherryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomScannerActivity extends Activity implements DecoratedBarcodeView.TorchListener {
    private CaptureManager captureManager;
    private DecoratedBarcodeView barcodeView;
    private ImageView switchFlashButton;
    private TextView cancelBtn;
    private Boolean switchFlashButtonCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);
        switchFlashButtonCheck = true;
        switchFlashButton = findViewById(R.id.switch_flashlight);
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setTorchListener(this);
        captureManager = new CaptureManager(this, barcodeView);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
        if (!hasFlash()) {
            Log.d("e4net","hasFlash");
            switchFlashButton.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    public void switchFlashlight(View view){
        if(switchFlashButtonCheck){
            barcodeView.setTorchOn();
        }else{
            barcodeView.setTorchOff();
        }
    }

    private boolean hasFlash() {
        Log.d("e4net","hasFlash");
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        Log.d("e4net","onTorchOn");
        switchFlashButton.setImageResource(R.drawable.ic_flash_on_cherry);
        switchFlashButtonCheck = false;

    }

    @Override
    public void onTorchOff() {
        Log.d("e4net","onTorchOff");
        switchFlashButton.setImageResource(R.drawable.ic_flash_off_cherry);
        switchFlashButtonCheck = true;

    }
}