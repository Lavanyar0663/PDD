package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.simats.frontend.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String selectedRole = "Admin"; // Default selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup initial UI state
        updateRoleSelection(binding.cardAdmin, binding.tvAdminText);

        // Click Listeners for roles
        binding.cardAdmin.setOnClickListener(v -> {
            selectedRole = "Admin";
            updateRoleSelection(binding.cardAdmin, binding.tvAdminText);
        });

        binding.cardDoctor.setOnClickListener(v -> {
            selectedRole = "Doctor";
            updateRoleSelection(binding.cardDoctor, binding.tvDoctorText);
        });

        binding.cardPharmacist.setOnClickListener(v -> {
            selectedRole = "Pharmacist";
            updateRoleSelection(binding.cardPharmacist, binding.tvPharmText);
        });

        // Click Listener for Login Button
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString() : "";
            String pwd = binding.etPassword.getText() != null ? binding.etPassword.getText().toString() : "";

            if (email.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(email, pwd, selectedRole);
            }
        });

        // Click Listener for Request Access
        binding.tvRequestAccess.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RequestAccessActivity.class));
        });

        // Forgot Password
        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Custom behavior to ensure it goes to Splash instead of exiting directly if needed
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void performLogin(String email, String password, String role) {
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Logging in...");

        com.google.gson.JsonObject credentials = new com.google.gson.JsonObject();
        credentials.addProperty("email", email);
        credentials.addProperty("password", password);
        credentials.addProperty("role", role.toLowerCase());

        com.simats.frontend.network.ApiInterface apiInterface = com.simats.frontend.network.ApiClient.getClient(this)
                .create(com.simats.frontend.network.ApiInterface.class);
        retrofit2.Call<com.google.gson.JsonObject> call = apiInterface.login(credentials);

        call.enqueue(new retrofit2.Callback<com.google.gson.JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<com.google.gson.JsonObject> call,
                    retrofit2.Response<com.google.gson.JsonObject> response) {
                binding.btnLogin.setEnabled(true);
                binding.btnLogin.setText("Log In");

                if (response.isSuccessful() && response.body() != null) {
                    com.google.gson.JsonObject data = response.body();
                    String token = data.get("token").getAsString();

                    com.google.gson.JsonObject user = data.getAsJsonObject("user");
                    String userId = user.get("id").getAsString();
                    String role = user.get("role").getAsString();
                    String name = user.get("name").getAsString();

                    // Ensure role selected matches backend role (optional strict check)
                    com.simats.frontend.network.SessionManager sessionManager = new com.simats.frontend.network.SessionManager(
                            LoginActivity.this);
                    sessionManager.saveSession(token, userId, user.get("email").getAsString(), role, name);

                    Toast.makeText(LoginActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                    navigateBasedOnRole(role);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed. Invalid credentials.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.google.gson.JsonObject> call, Throwable t) {
                binding.btnLogin.setEnabled(true);
                binding.btnLogin.setText("Log In");
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateBasedOnRole(String backendRole) {
        if ("pharmacist".equalsIgnoreCase(backendRole)) {
            startActivity(new Intent(LoginActivity.this, PharmacistMainActivity.class));
            finish();
        } else if ("doctor".equalsIgnoreCase(backendRole)) {
            startActivity(new Intent(LoginActivity.this, DoctorMainActivity.class));
            finish();
        } else if ("admin".equalsIgnoreCase(backendRole)) {
            startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Unknown role: " + backendRole, Toast.LENGTH_LONG).show();
        }
    }

    private void updateRoleSelection(MaterialCardView selectedCard, android.widget.TextView selectedText) {
        // Reset all cards
        resetCard(binding.cardAdmin, binding.tvAdminText, binding.ivAdminIcon);
        resetCard(binding.cardDoctor, binding.tvDoctorText, binding.ivDoctorIcon);
        resetCard(binding.cardPharmacist, binding.tvPharmText, binding.ivPharmIcon);

        // Highlight selected card
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        selectedCard.setStrokeWidth(0);
        selectedText.setTextColor(ContextCompat.getColor(this, R.color.white));

        // Find image view inside the clicked card to tint it white
        if (selectedCard == binding.cardAdmin) {
            binding.ivAdminIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
        } else if (selectedCard == binding.cardDoctor) {
            binding.ivDoctorIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
        } else if (selectedCard == binding.cardPharmacist) {
            binding.ivPharmIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }
    }

    private void resetCard(MaterialCardView card, android.widget.TextView text, android.widget.ImageView icon) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorCardUnselectedBg));
        card.setStrokeColor(ContextCompat.getColor(this, R.color.colorOutline));
        card.setStrokeWidth(2);
        text.setTextColor(ContextCompat.getColor(this, R.color.colorTextSecondary));
        icon.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSecondary));
    }
}
