package com.example.yqhd_app.QuanLy.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.QuanLy.Activity.ChiTietDonHangActivity;
import com.example.yqhd_app.QuanLy.Model.DonHangAdapter;
import com.example.yqhd_app.QuanLy.Model.DonHangModel;
import com.example.yqhd_app.QuanLy.Model.TaiKhoanKhachHangAdapter;
import com.example.yqhd_app.QuanLy.Model.TaiKhoanKhachHangModel;
import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class taikhoanKhachHangFragment extends Fragment {


    public taikhoanKhachHangFragment() {
        // Required empty public constructor
    }
    View v;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String userID;

    ImageView mimgSP;
    FloatingActionButton mbtnThem;
    RecyclerView recyclerView;
    //firestore
    FirebaseAuth auth;
    List<TaiKhoanKhachHangModel> taiKhoanKhachHangModelList;
    TaiKhoanKhachHangAdapter taikhoankhachhangadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.quanly_fragment_taikhoan_khachhang, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvQLKhachHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        taiKhoanKhachHangModelList = new ArrayList<>();
        CollectionReference collectionReference = firestore.collection("USERS");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    TaiKhoanKhachHangModel taikhoankhachhangmodel = doc.toObject(TaiKhoanKhachHangModel.class);
                    taikhoankhachhangadapter.add(taikhoankhachhangmodel);
                }
            }
        });
        taikhoankhachhangadapter = new TaiKhoanKhachHangAdapter(v.getContext(), taiKhoanKhachHangModelList);
        recyclerView.setAdapter(taikhoankhachhangadapter);
//        Toast.makeText(getContext(), "onResumeChuaXacNhan", Toast.LENGTH_SHORT).show();
    }
}