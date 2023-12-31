package com.example.yqhd_app.QuanLy.TabFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class donhangViewPagerAdapter extends FragmentStatePagerAdapter {
    public donhangViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new chuaXacNhanFragment();
            case 1:
                return new daXacNhanFragment();
            case 2:
                return new daHuyFragment();
            default:
                return new chuaXacNhanFragment();
//            case 2:
//                return new taikhoanFragment();
        }
//        return null;
    }
    //Trả về số lượng item (tương ứng với từng trang ở thanh trượt dưới)
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        switch (position){
            case 0:
                return "Chờ Xác Nhận";
            case 1:
                return "Đã Xác Nhận";
            case 2:
                return "Đã Hủy";
            default:
                return "Chờ Xác Nhận";
        }
    }
}
