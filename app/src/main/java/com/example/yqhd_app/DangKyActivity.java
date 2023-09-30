package com.example.yqhd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {
    private EditText medtmaildk, medtpassdk, medtpassdk2 ;
    private TextView mtvdn;
    private Button mbtndangky;
    private FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        medtmaildk = findViewById(R.id.edtmaildk);
        medtpassdk = findViewById(R.id.edtpassdk);
        medtpassdk2 = findViewById(R.id.edtpassdk2);
        mbtndangky = findViewById(R.id.btnDangKy);
        mtvdn = findViewById(R.id.tvdn);


        firestore = FirebaseFirestore.getInstance();

        DangnhapListener(); // cái này dùng để set cho nút đăng nhập bên đăng ký nè hyen
        mbtndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth =FirebaseAuth.getInstance();

                // 3 hàng dưới đây để biến kiểu của 3 cái đó qua dạng string
                String stringemail = medtmaildk.getText().toString();
                String stringpass1 = medtpassdk.getText().toString();
                String stringpass2 = medtpassdk2.getText().toString();

                if (TextUtils.isEmpty(stringemail)){
                    AlertDialog.Builder d = new AlertDialog.Builder(DangKyActivity.this);
                    // thiết lập tiêu đề, nội dung, nút button
                    d.setTitle("Cần có email");
                    d.setTitle("Vui lòng nhập email của bạn để tiếp  tục");
                    d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DangKyActivity.this, DangKyActivity.class);
                            startActivity(intent);
                        }
                    });
                } else if (TextUtils.isEmpty(stringpass1)) {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập mật khẩu.", Toast.LENGTH_SHORT).show();
                    medtpassdk.requestFocus();
                } else if (TextUtils.isEmpty(stringpass2)) {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập lại mật khẩu.", Toast.LENGTH_SHORT).show();
                    medtpassdk2.requestFocus();
                } else if (!stringpass1.equals(stringpass2)) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                    
                }else{
                    auth.createUserWithEmailAndPassword(stringemail, stringpass1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){userID = auth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firestore.collection("USERS").document(userID);
                                        //.collection("Info").document();
//                                        CollectionReference InfocollectionReference = firestore.collection("USERS").document(userID).collection("Info");
                                        Map<String, Object> userinfo = new HashMap<>();
                                        userinfo.put("idUser", userID);
                                        userinfo.put("Fullname",null);
                                        userinfo.put("Mail", stringemail);
                                        userinfo.put("Phone",null);
                                        documentReference.set(userinfo);
//                                        InfocollectionReference.add(userinfo);

                                        Intent intent = new Intent(DangKyActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(DangKyActivity.this, "Gmail đã được sử dụng",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }

    private void DangnhapListener() {
        mbtndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}