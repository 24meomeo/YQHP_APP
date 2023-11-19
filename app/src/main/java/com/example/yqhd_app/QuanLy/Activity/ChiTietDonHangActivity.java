package com.example.yqhd_app.QuanLy.Activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.QuanLy.Model.DonHangChiTietAdapter;
import com.example.yqhd_app.QuanLy.Model.DonHangChiTietModel;
import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChiTietDonHangActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //    String userID = firebaseAuth.getCurrentUser().getUid();
    TextView mtvTongGia;
    TextView mtvNgayMua;
    TextView mtvThoiGianMua;
    TextView mtvTrangThai;
    Button mbtnXacNhan, mbtnHuy;
    Button btnChiTiet;
    String ten, loai, gia, mota;
    Long gi;
    private Uri imageUri = null;
    private String imageURL; //đường dẫn ảnh
    private HashMap<String, Object> map;
    RecyclerView recyclerView;
    List<DonHangChiTietModel> donHangChiTietModelList;
    DonHangChiTietAdapter donHangChiTietAdapter;
    String madonhang;
    ImageView mbtnQuayVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlchitietdonhang1);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mbtnXacNhan = findViewById(R.id.btnXacNhanDon);
//        mbtnHuy = findViewById(R.id.btnHuyDon);

        recyclerView = findViewById(R.id.rcvQLChiTietDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mtvTongGia = findViewById(R.id.tvTongGia);
        mtvNgayMua = findViewById(R.id.tvNgay);
        mtvThoiGianMua = findViewById(R.id.tvThoiGianMua);
        mtvTrangThai = findViewById(R.id.tvTrangThai);
        int tongGia = getIntent().getIntExtra("tonggia", 0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(tongGia);
        mtvTongGia.setText(giatien + " VNĐ");
        String ngayMua = getIntent().getStringExtra("ngaymua");
        mtvNgayMua.setText(ngayMua);
        String thoigianMua = getIntent().getStringExtra("thoigianmua");
        mtvThoiGianMua.setText(thoigianMua);
        String trangthai = getIntent().getStringExtra("trangthai");
        mtvTrangThai.setText(trangthai);

        madonhang = getIntent().getStringExtra("madonhang");
        int sotrangthai = getIntent().getIntExtra("sotrangthai", 0);

        if (sotrangthai == 2) {
            mbtnXacNhan.setEnabled(false);
            mbtnXacNhan.setAlpha(0.2f);
        } else if (sotrangthai == 1 || sotrangthai == 4) {
            mbtnXacNhan.setText("Xóa");
            mbtnXacNhan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F62D2B")));
            mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firestore.collection("ORDERS").document(madonhang)
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ChiTietDonHangActivity.this, "Xóa đơn thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChiTietDonHangActivity.this, "Xóa đơn thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });


                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        } else if (sotrangthai == 3) {
            mbtnXacNhan.setText("Hủy");
            mbtnXacNhan.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F62D2B")));
            mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("trangthai", 4);
                    firestore.collection("ORDERS").document(madonhang)
                            .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(v.getContext(), "Hủy thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        } else {
            mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("trangthai", 1);
                    firestore.collection("ORDERS").document(madonhang)
                            .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ChiTietDonHangActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChiTietDonHangActivity.this, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });


                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
//        mbtnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            }
//        });

//        mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("trangthai", 1);
//                firestore.collection("ORDERS").document(madonhang)
//                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(ChiTietDonHangActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ChiTietDonHangActivity.this, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//                finish();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            }
//        });
        mbtnQuayVe = findViewById(R.id.btnQuayVe);
        mbtnQuayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        donHangChiTietModelList = new ArrayList<>();
        firestore.collection("ORDERS").document(madonhang)
                .collection("ORDERINFO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                DonHangChiTietModel donHangChiTietModel = doc.toObject(DonHangChiTietModel.class);
                                donHangChiTietAdapter.add(donHangChiTietModel);
                                donHangChiTietAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        donHangChiTietAdapter = new DonHangChiTietAdapter(this, donHangChiTietModelList);
        recyclerView.setAdapter(donHangChiTietAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(ChiTietDonHangActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}