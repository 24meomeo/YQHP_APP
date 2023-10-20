package com.example.yqhd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class DangNhapActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private TextView tvdk, mtvquenmk;
    private EditText medtmail, medtpass;
    private boolean isResendClicked = false;
    private CountDownTimer countDownTimer;
    private FirebaseAuth auth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvdk = findViewById(R.id.tvdk);
        medtmail = findViewById(R.id.edtmaildn);
        medtpass = findViewById(R.id.edtpassdn);
        mtvquenmk = findViewById(R.id.tvqmk);


//        medtmailquenpass = findViewById(R.id.edtemailquenpass);
//        mbtnthoat = findViewById(R.id.btnthoatquenpass);
//        mbtngui = findViewById(R.id.btnreset);

        Dangnhap();
        btnDangNhap.performClick();
        Dangky();
        quenmatkhau();
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

    private void Dangnhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                String stringemail = medtmail.getText().toString();
                String stringpass1 = medtpass.getText().toString();
                if (TextUtils.isEmpty(stringemail)) {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
                    medtmail.requestFocus();
                } else if (TextUtils.isEmpty(stringpass1)) {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập mật khẩu.", Toast.LENGTH_SHORT).show();
                    medtpass.requestFocus();
                    medtpass.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(stringemail, stringpass1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
//
                                    if (task.isSuccessful()) {
                                        if ((medtmail.getText().toString()).equals("yenquochangpu@gmail.com") && (medtpass.getText().toString()).equals("hehe321")) {
                                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Boolean verification = auth.getCurrentUser().isEmailVerified();
                                            if (verification == true) {
                                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                AlertDialog.Builder d = new AlertDialog.Builder(DangNhapActivity.this);
                                                // thiết lập tiêu đề, nội dung, nút button
                                                d.setTitle("Xác nhận gmail");
                                                d.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (!isResendClicked) {   // Nếu button resend chưa được nhấn trong lần nào trước đó
                                                            mUser.sendEmailVerification()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(DangNhapActivity.this, "Vui lòng xác nhận gmail " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                                                // isResendClicked = true;    // Đánh dấu là button resend đã được nhấn 1 lần
                                                                                //setTimer();                // Thiết lập đếm ngược thời gian chờ giữa các lần gửi email xác minh
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Nhận OTP thất bại", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Vui lòng đợi trong giây lát", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                d.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                d.create().show();
                                            }
                                        }
//
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(DangNhapActivity.this, "Email hoặc mật khẩu của bạn không đúng.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }

    private void quenmatkhau() {
        mtvquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DangNhapActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_quenpass, null);
                EditText emailbox = dialogView.findViewById(R.id.edtemailquenpass);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnthoatquenpass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder d = new AlertDialog.Builder(DangNhapActivity.this);
                        // thiết lập tiêu đề, nội dung, nút button
                        d.setTitle("QUAY LẠI");
                        d.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DangNhapActivity.this, DangNhapActivity.class);
                                startActivity(intent);
                            }
                        });
                        d.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        d.create().show();

                    }
                });
                dialogView.findViewById(R.id.btnreset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailbox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(DangNhapActivity.this, "Vui lòng nhập email đã đăng kí", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                        Toast.makeText(DangNhapActivity.this, "Vui lòng kiểm tra gmail của bạn!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });
    }
}