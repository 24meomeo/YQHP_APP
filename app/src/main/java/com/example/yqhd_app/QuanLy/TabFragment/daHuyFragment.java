package com.example.yqhd_app.QuanLy.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.QuanLy.Activity.ChiTietDonHangActivity;
import com.example.yqhd_app.QuanLy.Model.DonHangAdapter;
import com.example.yqhd_app.QuanLy.Model.DonHangModel;
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


public class daHuyFragment extends Fragment implements DonHangAdapter.onClickItem{


    public daHuyFragment() {
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
    List<DonHangModel> donHangModelList;
    DonHangAdapter donhangadapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.quanly_fragment_donhang_dahuy, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvDonHangDaHuy);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        donHangModelList = new ArrayList<>();
        CollectionReference collectionReference = firestore.collection("ORDERS");
        collectionReference.whereEqualTo("trangthai", 4).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    DonHangModel donHangModel = doc.toObject(DonHangModel.class);
                    donhangadapter.add(donHangModel);
                }
            }
        });
        donhangadapter = new DonHangAdapter(v.getContext(), donHangModelList, this::onClickToDetail);
        recyclerView.setAdapter(donhangadapter);
    }
    @Override
    public void onClickToDetail(String madonhang, int tonggia, String ngaymua, String thoigianmua, String trangthai, int sotrangthai) {
        Intent i = new Intent(getContext(), ChiTietDonHangActivity.class);
        i.putExtra("madonhang", madonhang);
        i.putExtra("tonggia",tonggia);
        i.putExtra("ngaymua",ngaymua);
        i.putExtra("thoigianmua",thoigianmua);
        i.putExtra("trangthai",trangthai);
        i.putExtra("sotrangthai",sotrangthai);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}