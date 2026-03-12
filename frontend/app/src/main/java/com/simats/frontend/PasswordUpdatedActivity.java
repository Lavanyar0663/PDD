package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.simats.frontend.databinding.ActivityPasswordUpdatedBinding;

public class PasswordUpdatedActivity extends AppCompatActivity {

    private ActivityPasswordUpdatedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordUpdatedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackToLogin.setOnClickListener(v -> navigateToLogin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(PasswordUpdatedActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
