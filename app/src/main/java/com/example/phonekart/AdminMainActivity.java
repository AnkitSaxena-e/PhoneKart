package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.phonekart.Fragments.AdminHomeFragment;
import com.example.phonekart.Fragments.AdminSeeUsersFragment;
import com.example.phonekart.Fragments.AdminSettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {

    private boolean doubleclickk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        BottomNavigationView navView = findViewById(R.id.nav_vieww);

        navView.setOnNavigationItemSelectedListener(onNavi);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new AdminHomeFragment()).commit();

        Window window = AdminMainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#303F9F"));

    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavi = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;

            if (menuItem.getItemId() == R.id.navigation_home_admin) {
                fragment = new AdminHomeFragment();
            } else if (menuItem.getItemId() == R.id.navigation_dashboard_admin) {
                fragment = new AdminSeeUsersFragment();
            } else if (menuItem.getItemId() == R.id.navigation_notifications_admin) {
                fragment = new AdminSettingFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment).commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {

        if (doubleclickk) {
            Intent hi = new Intent(Intent.ACTION_MAIN);
            hi.addCategory(Intent.CATEGORY_HOME);
            hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(hi);
            return;
        }

        this.doubleclickk = true;
        Toast.makeText(AdminMainActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleclickk = false;
            }
        }, 2000);
    }
}