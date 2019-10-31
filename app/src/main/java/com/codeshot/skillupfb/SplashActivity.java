package com.codeshot.skillupfb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.codeshot.skillupfb.Home.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    sendToMainActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.run();


    }

    private void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
