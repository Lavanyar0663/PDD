package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.frontend.databinding.ActivityAdminProfileBinding;
import com.simats.frontend.network.SessionManager;

public class AdminProfileActivity extends AppCompatActivity {

    private ActivityAdminProfileBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        // Populate fields from session
        binding.tvAdminName.setText(sessionManager.getName());
        binding.tvAdminEmail.setText(sessionManager.getEmail());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnEditProfile.setOnClickListener(v -> 
            Toast.makeText(this, "Edit Profile feature coming soon", Toast.LENGTH_SHORT).show()
        );

        binding.btnSettings.setOnClickListener(v -> 
            Toast.makeText(this, "Security Settings feature coming soon", Toast.LENGTH_SHORT).show()
        );

        binding.btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            sessionManager.clearSession();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
