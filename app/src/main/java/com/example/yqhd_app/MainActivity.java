package com.example.yqhd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { nextActiity();}
                
            }, 2000);
        }

    private void nextActiity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(this, DangNhapActivity.class);
        startActivity(intent);
    }

}