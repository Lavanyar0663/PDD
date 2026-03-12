package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.frontend.databinding.ActivityDoctorSettingsBinding;

public class DoctorSettingsActivity extends AppCompatActivity {

    private ActivityDoctorSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.ivBack.setOnClickListener(v -> finish());

        // Avatar Edit
        binding.ivEditAvatar
                .setOnClickListener(v -> Toast.makeText(this, "Editing Avatar...", Toast.LENGTH_SHORT).show());

        // Options
        binding.btnEditProfile
                .setOnClickListener(v -> Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show());
        binding.btnNotifications.setOnClickListener(
                v -> Toast.makeText(this, "Notification Preferences clicked", Toast.LENGTH_SHORT).show());
        binding.btnPassword
                .setOnClickListener(v -> Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show());
        binding.btnAbout.setOnClickListener(v -> Toast.makeText(this, "About App clicked", Toast.LENGTH_SHORT).show());

        // Logout
        binding.btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            com.simats.frontend.network.SessionManager sessionManager = new com.simats.frontend.network.SessionManager(this);
            sessionManager.clearSession();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
