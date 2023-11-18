package com.example.yqhd_app.DonHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryOrderDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryOrderDetailModel> historyOrderDetailModelList;
    HistoryOrderDetailAdapter historyOrderDetailAdapter;
    ImageView btnBack;

    private FirebaseFirestore firestore;
    Button btnYeuCauHuy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donhang_activity_historyorderdetail);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rcvHistoryOrderDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyOrderDetailModelList = new ArrayList<>();

        btnBack = findViewById(R.id.btnBackOfLSDH);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryOrderDetailActivity.this, HistoryOrderActivity.class);
                startActivity(i);
            }
        });


        String madonhang = getIntent().getStringExtra("madonhang");
        firestore.collection("ORDERS").document(madonhang)
                .collection("ORDERINFO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                HistoryOrderDetailModel historyOrderDetailModel = doc.toObject(HistoryOrderDetailModel.class);
                                historyOrderDetailAdapter.add(historyOrderDetailModel);
                                historyOrderDetailAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        historyOrderDetailAdapter = new HistoryOrderDetailAdapter(this, historyOrderDetailModelList);
        recyclerView.setAdapter(historyOrderDetailAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(HistoryOrderDetailActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        btnYeuCauHuy = findViewById(R.id.btnYeuCauHuy);
        int trangthai = getIntent().getIntExtra("trangthai", 0);
        if(trangthai == 3){
            btnYeuCauHuy.setEnabled(false);
            btnYeuCauHuy.setAlpha(0.2f);
        }else {
            btnYeuCauHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("trangthai", 3);
                    firestore.collection("ORDERS").document(madonhang)
                            .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(v.getContext(), "Yêu Cầu Hủy Thành Công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            finish();
        }

    }
}
