package com.simats.frontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.simats.frontend.databinding.ActivityDoctorMainBinding;
import com.simats.frontend.fragments.DoctorDashboardFragment;
import com.simats.frontend.fragments.DoctorNotificationsFragment;
import com.simats.frontend.fragments.DoctorPatientsFragment;

public class DoctorMainActivity extends AppCompatActivity {

    private ActivityDoctorMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Default Fragment
        if (savedInstanceState == null) {
            loadFragment(new DoctorDashboardFragment());
            binding.bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                fragment = new DoctorDashboardFragment();
            } else if (itemId == R.id.nav_patients) {
                fragment = new DoctorPatientsFragment();
            } else if (itemId == R.id.nav_notifications) {
                fragment = new DoctorNotificationsFragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private long backPressedTime;
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            android.widget.Toast.makeText(this, "Press back again to exit", android.widget.Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
