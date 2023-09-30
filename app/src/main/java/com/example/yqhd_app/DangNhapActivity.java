package com.example.yqhd_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DangNhapActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private TextView tvdk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvdk = findViewById(R.id.tvdk);
        Dangnhap();
        Dangky();
    }


    private void Dangnhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void Dangky() {
        tvdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(b);
            }
        });
    }

}