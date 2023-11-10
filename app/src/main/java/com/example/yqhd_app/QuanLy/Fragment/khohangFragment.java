package com.example.yqhd_app.QuanLy.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.yqhd_app.MainActivity;
import com.example.yqhd_app.QuanLy.QuanLyActivity;
import com.example.yqhd_app.QuanLy.TabFragment.ThuongHieuFragment;
import com.example.yqhd_app.QuanLy.TabFragment.customViewPager;
import com.example.yqhd_app.QuanLy.TabFragment.khohangViewPagerAdapter;
import com.example.yqhd_app.R;
import com.google.android.material.tabs.TabLayout;

public class khohangFragment extends Fragment {
    private TabLayout tabLayout;
    private customViewPager viewPager;
    private View v;
    private QuanLyActivity mQuanLyActivity;
    public khohangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_quanly_khohang, container, false);
        tabLayout = v.findViewById(R.id.tablayoutkhohang);
        viewPager = v.findViewById(R.id.viewpagerkhohang);
        mQuanLyActivity = (QuanLyActivity) getActivity();

        khohangViewPagerAdapter adapter = new khohangViewPagerAdapter(getChildFragmentManager()
        , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
    public void gotoAddProducts(){
//        if(isAdded()){
            FragmentTransaction fmtrans = getChildFragmentManager().beginTransaction();
            ThuongHieuFragment taikhoanFragment = new ThuongHieuFragment();
            fmtrans.replace(R.id.viewpagerkhohang, taikhoanFragment);
            fmtrans.addToBackStack(null);
            fmtrans.commit();
//        }
    }
}