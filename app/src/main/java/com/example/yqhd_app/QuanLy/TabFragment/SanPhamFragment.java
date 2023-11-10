package com.example.yqhd_app.QuanLy.TabFragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.QuanLy.Fragment.khohangFragment;
import com.example.yqhd_app.QuanLy.QuanLyActivity;
import com.example.yqhd_app.QuanLy.SanPhamAdapter;
import com.example.yqhd_app.QuanLy.SanPhamModel;
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

public class SanPhamFragment extends Fragment {
    View v;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String userID;

    ImageView mimgSP;
    FloatingActionButton mbtnThem;
    RecyclerView recyclerView;
    List<SanPhamModel> SPModelList;
    SanPhamAdapter SPAdapter;
    //firestore
    FirebaseAuth auth;
    private Uri imageUri = null;
    khohangFragment mkhohangFragment;
    QuanLyActivity mQuanLyActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.quanly_fragment_sanpham, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
        recyclerView = v.findViewById(R.id.rcvSP);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SPModelList = new ArrayList<>();

        CollectionReference SPcollectionReference = firestore.collection("PRODUCTS");
        SPcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list){
                    SanPhamModel spModel = doc.toObject(SanPhamModel.class);
                    SPAdapter.add(spModel);
                }
            }
        });
        SPAdapter = new SanPhamAdapter(/*v.getContext()*/getActivity(), this, SPModelList);
        recyclerView.setAdapter(SPAdapter);

        mQuanLyActivity = (QuanLyActivity) getActivity();
        mkhohangFragment = new khohangFragment();
        mbtnThem = v.findViewById(R.id.btnFloatingAddProduct);
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {
                //Tắt hiển thị khoFragment để addFragment hiện lên
//                loadFragment(new ThuongHieuFragment());
                mQuanLyActivity.gotoAddProducts();
//                mkhohangFragment.gotoAddProducts();
            }
        });
        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getChildFragmentManager().beginTransaction();
        fmtrans.replace(R.id.viewpagerkhohang, fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
}