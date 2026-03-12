package com.simats.frontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.simats.frontend.databinding.ActivityPharmacistMainBinding;
import com.simats.frontend.fragments.PharmacistHomeFragment;
import com.simats.frontend.fragments.PharmacistNotificationsFragment;
import com.simats.frontend.fragments.PharmacistPatientsFragment;

public class PharmacistMainActivity extends AppCompatActivity {

    private ActivityPharmacistMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPharmacistMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Default Fragment
        if (savedInstanceState == null) {
            loadFragment(new PharmacistHomeFragment());
            binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                fragment = new PharmacistHomeFragment();
            } else if (itemId == R.id.nav_patients) {
                fragment = new PharmacistPatientsFragment();
            } else if (itemId == R.id.nav_notifications) {
                fragment = new PharmacistNotificationsFragment();
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
