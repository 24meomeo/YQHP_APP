package com.example.yqhd_app.QuanLy.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.QuanLy.Model.DonHangChiTietAdapter;
import com.example.yqhd_app.QuanLy.Model.DonHangChiTietModel;
import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
        mbtnHuy = findViewById(R.id.btnHuyDon);

        recyclerView = findViewById(R.id.rcvQLChiTietDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mbtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        mtvTongGia = findViewById(R.id.tvTongGia);
        mtvNgayMua = findViewById(R.id.tvNgay);
        mtvThoiGianMua = findViewById(R.id.tvThoiGianMua);
        mtvTrangThai = findViewById(R.id.tvTrangThai);
        int tongGia = getIntent().getIntExtra("tonggia", 0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(tongGia);
        mtvTongGia.setText(giatien + " VNĐ");
        String ngayMua = getIntent().getStringExtra("ngaymua");
        mtvNgayMua.setText(ngayMua);
        String thoigianMua= getIntent().getStringExtra("thoigianmua");
        mtvThoiGianMua.setText(thoigianMua);
        String trangthai = getIntent().getStringExtra("trangthai");
        mtvTrangThai.setText(trangthai);

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
        madonhang = getIntent().getStringExtra("madonhang");
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