package com.example.yqhd_app.HomeDashBoard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.yqhd_app.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    MeowBottomNavigation btnav;
//    int selectedMenu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);


        //MeoBottomNavigation
        btnav = findViewById(R.id.btnav);
        loadFragment(new HomeFragmentActivity());
        btnav.show(1,true );
        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavheart));
        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));

        btnav.getId();
        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()){
                    case 1:
                        loadFragment(new HomeFragmentActivity());

//                        Intent homeintent = new Intent(MainActivity.this,);
//                        startActivity(homeintent);
                        break;
                    case 2:
                        loadFragment(new ProductFragmentActivity());
//                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
//                        startActivity(categoryintent);
                        break;
                    case 3:
                        loadFragment(new FavouriteFragmentActivity());
//                        Intent favouritelistintent = new Intent(MainActivity.this, );
//                        startActivity(favouritelistintent);
                        break;
                    case 4:
                        loadFragment(new UserFragmentActivity());
//                        mdrawLo.openDrawer(GravityCompat.START);
                        break;
                }
                return null;
            }
        });
        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });



    private void loadFragment(Fragment fragment){
        FragmentTransaction fmtrans = getSupportFragmentManager().beginTransaction();
        fmtrans.replace(R.id.home_fragment,fragment);
        fmtrans.addToBackStack(null);
        fmtrans.commit();
    }
}
