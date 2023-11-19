package com.example.yqhd_app.GioHang.ThanhToan;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.HomeDashBoard.HomeActivity;
import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

//import vn.zalopay.sdk.Environment;
//import vn.zalopay.sdk.ZaloPayError;
//import vn.zalopay.sdk.ZaloPaySDK;
//import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ThanhToanModel> thanhToanModelList;
    ThanhToanAdapter thanhToanAdapter;

    TextView tvToTalPriceOfPay, tvPriceOfPayContentItem4, tvTitleVoucher;

    Button btnMuaHang;
    int totalPrice;
    int totalQuantity;
    String totalPriceZaloPay;
    // firebase
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseUser user;
    String userID;
    EditText medtDiaChi;
    CheckBox cbDiaChi;
//    boolean isCheckboxChecked = false;
//    boolean isEditTextFilled = false;
    String diachi, ten, sodienthoai;
    TextView btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        userID = user.getUid();

        btnMuaHang = findViewById(R.id.btnPay);
        tvToTalPriceOfPay = findViewById(R.id.tvPriceOfPayFooter);
//        tvTitleVoucher = findViewById(R.id.tvTitleVoucher);
//        tvPriceOfPayContentItem4 = findViewById(R.id.tvPriceOfPayContentItem4);

        medtDiaChi = findViewById(R.id.edtDiaChi);
        btnHome = findViewById(R.id.iconCartHome);

        //ZaloPay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
//        ZaloPaySDK.init(2553, Environment.SANDBOX);



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThanhToanActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        cbDiaChi = findViewById(R.id.checkboxDiaChi);


        cbDiaChi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isCheckboxChecked = isChecked;
                if (isChecked) {
                    // Lấy tài khoản có sẵn từ Firestore và hiển thị
                    DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
                    InfoProfiledocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String diachi = documentSnapshot.getString("Address");
                            medtDiaChi.setText(diachi);
                        }
                    });

                    medtDiaChi.setEnabled(false);
                    medtDiaChi.setAlpha(0.5f); // Làm mờ EditText
                    // Thực hiện lấy tên tài khoản từ Firestore và cập nhật lên giao diện
                    // ...
                } else {
                    medtDiaChi.setEnabled(true);
                    medtDiaChi.setAlpha(1.0f); // Đặt lại trạng thái bình thường của EditText
                    medtDiaChi.setText(null);
                }
//                checkPaymentButtonEnabled();

            }
        });

//        medtDiaChi.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // Không cần thực hiện hành động trước khi văn bản được thay đổi
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // Không cần thực hiện hành động khi văn bản đang thay đổi
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String accountName = s.toString();
//                isEditTextFilled = !TextUtils.isEmpty(accountName);
//                if (isEditTextFilled) {
//                    cbDiaChi.setEnabled(false);
//                    cbDiaChi.setAlpha(0.5f); // Làm mờ CheckBox
//                    // Sử dụng accountName làm tên tài khoản để thanh toán
//                    diachi = medtDiaChi.getText().toString().trim();
//                } else {
//                    cbDiaChi.setEnabled(true);
//                    cbDiaChi.setAlpha(1.0f); // Đặt lại trạng thái bình thường của CheckBox
//                }
//                checkPaymentButtonEnabled();
//            }
//        });
        // load và nhận dữ liệu tổng tiền từ cart activity
        totalPrice = getIntent().getIntExtra("totalPriceFromCart", 0);
        // format giá
        String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalPrice);
        totalPriceZaloPay = Integer.toString(totalPrice);
        tvToTalPriceOfPay.setText(totalGia + " VNĐ");

        //load và nhận dữ liệu tổng sản phẩm
//        totalQuantity = getIntent().getIntExtra("totalQuantityFromCart", 1);

        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(view -> {
            finish();
        });

        //Voucher
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(mMessageReceiverVoucher, new IntentFilter("Voucher"));
//
//        FrameLayout frameLayout = findViewById(R.id.framOfPayContentItem2);
//        frameLayout.setOnClickListener(view -> {
//            startActivity(new Intent(ThanhToanActivity.this, VoucherActivity.class));
//        });

        recyclerView = findViewById(R.id.rvThanhToan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        thanhToanModelList = new ArrayList<>();

        thanhToanAdapter = new ThanhToanAdapter(this, thanhToanModelList);
        recyclerView.setAdapter(thanhToanAdapter);

        firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ThanhToanModel thanhToanModel = doc.toObject(ThanhToanModel.class);
                                thanhToanModelList.add(thanhToanModel);
                                thanhToanAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        collectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid()).collection("AddToCart");

        DocumentReference InfoProfiledocumentReference = firestore.collection("USERS").document(userID);
        InfoProfiledocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ten = documentSnapshot.getString("Fullname");
                sodienthoai = documentSnapshot.getString("Phone");
            }
        });
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isCheckboxChecked || isEditTextFilled) {
//                    // Thực hiện thanh toán
//                    // ...
//
//                } else {
//                    // Hiển thị thông báo yêu cầu nhập đầy đủ thông tin
//                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
                diachi = medtDiaChi.getText().toString().trim();
                if(TextUtils.isEmpty(diachi)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else if(diachi.length()>100){
                    Toast.makeText(getApplicationContext(), "Địa chỉ không quá 100 ký tự", Toast.LENGTH_SHORT).show();
                }else if (ten.length() == 0 || sodienthoai.length() == 0){
                    Toast.makeText(getApplicationContext(), "Vui lòng cập nhật đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    collectionReference.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().getDocuments().isEmpty()) {
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    collectionReference.document(doc.getId()).delete();
                                }
                            }
                        }
                    });
                    placedOrder();
                }
            }
        });
    }

//    private void checkPaymentButtonEnabled() {
//        btnMuaHang.setEnabled(isCheckboxChecked || isEditTextFilled);
//    }
    private void placedOrder() {

        // các biến lưu thời gian mua hàng
        String saveCurrentDate, saveCurrentTime;
        Calendar callForDate = Calendar.getInstance();
        Calendar callForTime = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        // nạp chi tiết đơn đặt hàng
        if(thanhToanModelList != null && thanhToanModelList.size() > 0){
            for(ThanhToanModel model : thanhToanModelList){
                Map<String, Object> infoorder = new HashMap<>();
                infoorder.put("Tên", model.getName());
                infoorder.put("Số lượng", model.getTotalQuantity());
                infoorder.put("Giá", model.getPrice());

                // tham chiếu documnet dùng hàm set
                // còn tham chiếu collection dùng hàm add
//                DocumentReference OrderInfocollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                        .collection("Orders").document("TongOrder").collection("Chi tiết Order").document();
//                OrderInfocollectionReference.set(infoorder);
//
//                CollectionReference OrderInfocollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                        .collection("Orders").document().collection("Chi tiết đơn hàng");
//                OrderInfocollectionReference.add(infoorder);
            }
        }
        int trangthai = 0;
        String id = UUID.randomUUID().toString();
        // nạp tổng đơn đặt hàng
        Map<String, Object> dateInfo = new HashMap<>();
        dateInfo.put("madonhang", id);
        dateInfo.put("makhachhang", userID);
        dateInfo.put("ten", ten);
        dateInfo.put("sodienthoai", sodienthoai);
        dateInfo.put("diachi", diachi);
//        dateInfo.put("tongSoLuong", totalQuantity);
        dateInfo.put("tongGia",totalPrice);
        dateInfo.put("ngayMua", saveCurrentDate);
        dateInfo.put("thoigianMua", saveCurrentTime);
        dateInfo.put("trangthai", trangthai);



//        CollectionReference OrderInfocollectionReference = firestore.collection("ORDERS").document(id)
//                .collection("ORDERINFO");

        DocumentReference documentReference = firestore.collection("ORDERS").document(id);
        documentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                for (ThanhToanModel model: thanhToanModelList){
                    String idDetail = UUID.randomUUID().toString();
                    DocumentReference OrderInfoDocumentReference = firestore.collection("ORDERS").document(id)
                            .collection("ORDERINFO").document(idDetail);
                    Map<String, Object> orderDetail = new HashMap<>();
                    orderDetail.put("madonhang", id);
                    orderDetail.put("machitietdonhang", idDetail);
                    orderDetail.put("name", model.getName());
                    orderDetail.put("soluong", model.getTotalQuantity());
                    orderDetail.put("anh", model.getAnh());
                    orderDetail.put("price", model.getPrice());
//            orderDetail.put("tongGia",totalPrice);
//            orderDetail.put("ngayMua", saveCurrentDate);
//            orderDetail.put("thoigianMua", saveCurrentTime);
//            orderDetail.put("trangthai", trangthai);

//            OrderInfocollectionReference.add(orderDetail);
                    OrderInfoDocumentReference.set(orderDetail);
                }
//                requestZaloPay(id);

                Intent i = new Intent(ThanhToanActivity.this, SuccessOrderActivity.class);
                startActivity(i);
            }
        });



        //Test chi tiết hiển thị trong hệ thống
//        for (String key: dateInfo.keySet()) {
//            System.out.println(key);
//            System.out.println(dateInfo.get(key));
//        }
//        for (ThanhToanModel model: thanhToanModelList){
//            System.out.println(model.getName());
//            System.out.println(model.getPrice());
//        }

        //ZaloPayRequest


//        DocumentReference TongOrderdocumentReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                .collection("Orders").document("TongOrder");
//        TongOrderdocumentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent i = new Intent(ThanhToanActivity.this, SuccessActivity.class);
//                startActivity(i);
//            }
//        });

//        DocumentReference TongOrderdocumentReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
//                .collection("Orders").document(id);

//        DocumentReference documentReference = firestore.collection("ORDERS").document(id);
//        documentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent i = new Intent(ThanhToanActivity.this, SuccessOrderActivity.class);
//                startActivity(i);
//            }
//        });


//        TongOrderdocumentReference.set(dateInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent i = new Intent(ThanhToanActivity.this, SuccessOrderActivity.class);
//                startActivity(i);
//            }
//        });
    }


//    private void requestZaloPay(String idDonHang){
//        CreateOrder orderApi = new CreateOrder();
//
//        try {
//            JSONObject data = orderApi.createOrder(totalPriceZaloPay);
////            Log.d("Amount", txtAmount.getText().toString());
////            lblZpTransToken.setVisibility(View.VISIBLE);
//
//            //Mã trạng thái (đại loại vậy)
//            String code = data.getString("return_code");
//            Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
//
//            if (code.equals("1")) {
////                lblZpTransToken.setText("zptranstoken");
//                //Mã giao dịch
//                String token = data.getString("zp_trans_token");
//
//                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
//                    @Override
//                    public void onPaymentSucceeded(String s, String s1, String s2) {
//                        //Nếu thanh toán thành công thì sẽ đưa lên mã giao dịch cho bên admin kiểm tra và đưa đơn hàng lên dữ liệu
//
//                    }
//
//                    @Override
//                    public void onPaymentCanceled(String s, String s1) {
//                        //Nếu thanh toán bị hủy thì sẽ
//                    }
//
//                    @Override
//                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
//
//                    }
//                });
////                txtToken.setText(data.getString("zp_trans_token"));
////                IsDone();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        ZaloPaySDK.getInstance().onResult(intent);
//    }

//Voucher
//    public BroadcastReceiver mMessageReceiverVoucher = new BroadcastReceiver() {
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            VoucherModel voucherModel = intent.getParcelableExtra("voucherItem");
//            if (voucherModel != null) {
//                tvTitleVoucher.setText(voucherModel.getTitle());
//                int payVoucher = (voucherModel.getCode() * totalPrice) / 100;
//                tvPriceOfPayContentItem4.setText(NumberFormat.getNumberInstance(Locale.US).format(payVoucher) + " VNĐ");
//                int payTotal = totalPrice - ((voucherModel.getCode() * totalPrice) / 100);
//                tvToTalPriceOfPay.setText(NumberFormat.getNumberInstance(Locale.US).format(payTotal) + " VNĐ");
//            }
//        }
//    };
}