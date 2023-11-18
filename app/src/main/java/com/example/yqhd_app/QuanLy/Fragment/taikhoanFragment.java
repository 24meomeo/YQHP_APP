package com.example.yqhd_app.QuanLy.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.yqhd_app.QuanLy.TabFragment.customViewPager;
import com.example.yqhd_app.QuanLy.TabFragment.taikhoanViewPagerAdapter;
import com.example.yqhd_app.R;
import com.google.android.material.tabs.TabLayout;


public class taikhoanFragment extends Fragment {
    private TabLayout tabLayout;
    private customViewPager viewPager;
    private View v;
    public taikhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_quanly_taikhoan, container, false);
        tabLayout = v.findViewById(R.id.tablayouttaikhoan);
        viewPager = v.findViewById(R.id.viewpagertaikhoan);

        taikhoanViewPagerAdapter adapter = new taikhoanViewPagerAdapter(getChildFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
}