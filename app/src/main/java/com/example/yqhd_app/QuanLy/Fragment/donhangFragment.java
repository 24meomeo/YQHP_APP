package com.example.yqhd_app.QuanLy.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.yqhd_app.QuanLy.TabFragment.customViewPager;
import com.example.yqhd_app.QuanLy.TabFragment.donhangViewPagerAdapter;
import com.example.yqhd_app.R;
import com.google.android.material.tabs.TabLayout;


public class donhangFragment extends Fragment {
    private TabLayout tabLayout;
    private customViewPager viewPager;
    private View v;
    public donhangFragment() {
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
        v = inflater.inflate(R.layout.fragment_quanly_donhang, container, false);
        tabLayout = v.findViewById(R.id.tablayoutdonhang);
        viewPager = v.findViewById(R.id.viewpagerdonhang);

        donhangViewPagerAdapter adapter = new donhangViewPagerAdapter(getChildFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
}