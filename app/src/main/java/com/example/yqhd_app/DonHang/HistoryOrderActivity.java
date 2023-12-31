package com.example.yqhd_app.DonHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity implements HistoryOrderAdapter.onClickItem{
    RecyclerView recyclerView;
    List<HistoryOrderModel> historyOrderModelList;
    HistoryOrderAdapter historyOrderAdapter;

    ImageView btnBack;

    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = auth.getCurrentUser();
        userID = user.getUid();

        btnBack = findViewById(R.id.btnBackOfLSDH);

        //set dữ liệu



//        DocumentReference HistoryOrderDocumnetReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                .collection("Orders").document("TongOrder");
//        HistoryOrderDocumnetReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        HistoryOrderModel historyOrderModel = document.toObject(HistoryOrderModel.class);
//                        historyOrderAdapter.add(historyOrderModel);
//                    }
//                }
//            }
//        });

        recyclerView = findViewById(R.id.rcvHistoryOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

//                Intent i = new Intent(HistoryOrderActivity.this, HomeActivity.class);
//                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        historyOrderModelList = new ArrayList<>();
        CollectionReference HistoryOrderDocumnetReference = firestore.collection("ORDERS");
        HistoryOrderDocumnetReference.whereEqualTo("makhachhang", userID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    HistoryOrderModel historyOrderModel = doc.toObject(HistoryOrderModel.class);
                    historyOrderAdapter.add(historyOrderModel);
                }
            }
        });

        historyOrderAdapter = new HistoryOrderAdapter(this, historyOrderModelList, this);
        recyclerView.setAdapter(historyOrderAdapter);
    }

    @Override
    public void onClickToDetail(String madonhang, String makhachhang, int trangthai) {
        Intent i = new Intent(HistoryOrderActivity.this, HistoryOrderDetailActivity.class);
        i.putExtra("madonhang",madonhang);
        i.putExtra("makhachhang",makhachhang);
        i.putExtra("trangthai",trangthai);
        startActivity(i);
        HistoryOrderActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
