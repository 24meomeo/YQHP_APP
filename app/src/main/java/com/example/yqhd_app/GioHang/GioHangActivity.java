package com.example.yqhd_app.GioHang;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.yqhd_app.GioHang.ThanhToan.ThanhToanActivity;
//import com.example.yqhd_app.HomeDashboard.HomeActivity;
import com.example.yqhd_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class GioHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<GioHangItemModel> cartModelList;
    GioHangAdapter cartAdapter;


    // biến chứa tổng giá tiền của cart
    int totalBill;
    int totalQuantity;
    TextView AllAmount;

    Button btnThanhToan;
    TextView btnBack;
    TextView btnHome;
    CollectionReference collectionReference;


    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giohang_activity_layoutgiohang);

        // ánh xạ
        AllAmount = findViewById(R.id.tvCartTotalPrice);
        btnBack = (TextView) findViewById(R.id.iconCartBack);
        btnHome = (TextView) findViewById(R.id.iconCartHome);
        btnThanhToan = (Button) findViewById(R.id.btnCartThanhToan);
        //Nhấn nút trở về
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
//                startActivity(i);
                finish();
            }
        });

//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(GioHangActivity.this, HomeActivity.class);
//                startActivity(i);
//            }
//        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(cartItemRemovedReceiver, new IntentFilter("CartItemRemoved"));

        // get dữ liệu tổng giá tiền từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));
//
//        // get dữ liệu tổng sản phẩm từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(qMessageReceiver, new IntentFilter("MyTotalQuantity"));

//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(cartItemRemovedReceiver, new IntentFilter("CartItemRemoved"));

        //set dữ liệu
        recyclerView = findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new GioHangAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);


//        totalQuantity = getIntent().getStringExtra("totalQuantity");

        collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("AddToCart");
//        collectionReference.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
//                    MyCartModel myCartModel = doc.toObject(MyCartModel.class);
//                    cartModelList.add(myCartModel);
//                    cartAdapter.notifyDataSetChanged();
//                }
//            }
//        });

        //Nhấn nút thanh toán
//        btnThanhToan.setOnClickListener(view -> {
//            Intent i = new Intent(GioHangActivity.this, ThanhToanActivity.class);
//            i.putExtra("totalPriceFromCart", totalBill);
//            i.putExtra("totalQuantityFromCart", totalQuantity);
//            startActivity(i);
//        });
    }
    private boolean checkCartEmpty() {
        return cartModelList.isEmpty();
    }
    private void updateCartStatus() {
        if (checkCartEmpty()) {
            btnThanhToan.setEnabled(false);
            AllAmount.setText("Chưa có sản phẩm");
        } else {
            btnThanhToan.setEnabled(true);
        }
    }
    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onResume() {
        super.onResume();
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<GioHangItemModel> collect = task.getResult().getDocuments().stream().
                        map(s -> Objects.requireNonNull(s.toObject(GioHangItemModel.class)))
                        .collect(Collectors.toList());

                cartModelList.clear();
                cartModelList.addAll(collect);
                int totalAmount = cartModelList.stream().mapToInt(s -> s.getTotalQuantity() * s.getPrice()).sum();
                totalBill = totalAmount;
                AllAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(totalAmount)+" VNĐ");
//                AllAmount.setText(totalAmount + " VNĐ");
                cartAdapter.notifyDataSetChanged();
                if (checkCartEmpty()) {
                    btnThanhToan.setEnabled(false);
                    AllAmount.setText("Chưa có sản phẩm");
                } else {
                    btnThanhToan.setEnabled(true);
                }
            }
        });
    }
    // hàm nhận tổng giá tiền từ cart adapter
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<GioHangItemModel> lst = intent.getParcelableArrayListExtra("data");
            if (lst != null) {
                int totalAmount = lst.stream().mapToInt(s -> s.getTotalQuantity() * s.getPrice()).sum();
                totalBill = totalAmount;
                AllAmount.setText(totalAmount + " VNĐ");
            }
        }
    };

    private BroadcastReceiver cartItemRemovedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Lấy dữ liệu sản phẩm bị xóa và số lượng sản phẩm còn lại từ Intent
            GioHangItemModel removedItem = intent.getParcelableExtra("removedItem");
            int remainingQuantity = intent.getIntExtra("remainingQuantity", 0);

            // Cập nhật tổng tiền trong giỏ hàng
            totalBill -= (removedItem.getPrice() * removedItem.getTotalQuantity());
            AllAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(totalBill) + " VNĐ");

            updateCartStatus();
        }
    };
    // hàm nhận tổng sản phẩm từ cart adapter
    public BroadcastReceiver qMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalQuantity = intent.getIntExtra("totalQuantity", 1);
        }
    };

}