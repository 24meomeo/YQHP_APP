package com.example.yqhd_app.QuanLy;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.yqhd_app.QuanLy.Fragment.ViewPagerAdapter;
import com.example.yqhd_app.QuanLy.TabFragment.ThuongHieuFragment;
import com.example.yqhd_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuanLyActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_quanlychinh);
        viewPager = findViewById(R.id.viewpagerquanly);
        bottomNavigationView = findViewById(R.id.bottomnavquanly);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        //Sự kiện khi vuốt viewPager
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:     //Nếu không có setChecked(true) thì lúc lướt qua lướt lại thì cái thanh bottom nav sẽ không chuyển icon
                        bottomNavigationView.getMenu().findItem(R.id.menukho).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menudonhang).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menukho:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menudonhang:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menutaikhoan:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        int userId = 0;
        if (userId == 0) {

        }
//        if (userId == 0) {
//            // Vô hiệu hóa chức năng chuyển đến Item 3
//
//            bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
////                    System.out.println("aaa");
////                    Snackbar.make(viewPager, "Bạn không có quyền truy cập", Snackbar.LENGTH_SHORT).show();
//                    Toast.makeText(QuanLyActivity.this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
////            bottomNavigationView.getMenu().findItem(R.id.menutaikhoan).setEnabled(false);
//
//            // Hiển thị thông báo với Snackbar
//
//
//            // Hoặc hiển thị thông báo với Toast
//            // Toast.makeText(this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
//        }

    }

    public void gotoAddProducts() {
//        if(isAdded()){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        ThuongHieuFragment taikhoanFragment = new ThuongHieuFragment();
        fmtrans.replace(R.id.viewpagerquanly, taikhoanFragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
//        }
    }
}
