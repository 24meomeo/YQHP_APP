package com.example.yqhd_app.QuanLy.TabFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class taikhoanViewPagerAdapter extends FragmentStatePagerAdapter {
    public taikhoanViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tkKhachHangHoatDongFragment();
            case 1:
                return new tkKhachHangKhoaFragment();
            default:
                return new tkKhachHangHoatDongFragment();
//            case 2:
//                return new taikhoanFragment();
        }
//        return null;
    }
    //Trả về số lượng item (tương ứng với từng trang ở thanh trượt dưới)
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        switch (position){
            case 0:
                return "Hoạt Động";
            case 1:
                return "Đã Khóa";
            default:
                return "Hoạt Động";
        }
    }
}
