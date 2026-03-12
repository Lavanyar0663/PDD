package com.simats.frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.simats.frontend.databinding.ActivityRequestAccessBinding;

public class RequestAccessActivity extends AppCompatActivity {

    private ActivityRequestAccessBinding binding;
    private String selectedRole = "Doctor"; // default
    private final String[] departments = new String[] {
            "Orthodontics", "Endodontics", "Oral Surgery", "Pediatric Dentistry", "General Dentistry"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestAccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Dropdown for Department
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                departments);
        binding.actvDepartment.setAdapter(adapter);

        // Initial UI state (Doctor Selected)
        updateRoleSelection(binding.cardDoctor, binding.tvDoctorText, binding.ivDoctorIcon);

        // Click Back
        binding.btnBack.setOnClickListener(v -> finish());

        // Role Toggle
        binding.cardDoctor.setOnClickListener(v -> {
            selectedRole = "Doctor";
            updateRoleSelection(binding.cardDoctor, binding.tvDoctorText, binding.ivDoctorIcon);
        });

        binding.cardPharmacist.setOnClickListener(v -> {
            selectedRole = "Pharmacist";
            updateRoleSelection(binding.cardPharmacist, binding.tvPharmText, binding.ivPharmIcon);
        });

        // Submit Logic
        binding.btnSubmit.setOnClickListener(v -> handleSubmission());
    }

    private void updateRoleSelection(MaterialCardView selectedCard, android.widget.TextView selectedText,
            android.widget.ImageView selectedIcon) {
        // Reset both cards
        resetCard(binding.cardDoctor, binding.tvDoctorText, binding.ivDoctorIcon);
        resetCard(binding.cardPharmacist, binding.tvPharmText, binding.ivPharmIcon);

        // Highlight selected
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        selectedCard.setStrokeWidth(0);
        selectedText.setTextColor(ContextCompat.getColor(this, R.color.white));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));

        // Toggle Department dropdown visibility based on role
        if (selectedRole.equals("Doctor")) {
            binding.tvDepartmentHeader.setVisibility(View.VISIBLE);
            binding.tilDepartment.setVisibility(View.VISIBLE);
        } else {
            binding.tvDepartmentHeader.setVisibility(View.GONE);
            binding.tilDepartment.setVisibility(View.GONE);
        }
    }

    private void resetCard(MaterialCardView card, android.widget.TextView text, android.widget.ImageView icon) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorCardUnselectedBg));
        card.setStrokeColor(ContextCompat.getColor(this, R.color.colorOutline));
        card.setStrokeWidth(2);
        text.setTextColor(ContextCompat.getColor(this, R.color.colorTextSecondary));
        icon.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSecondary));
    }

    private void handleSubmission() {
        String name = binding.etFullName.getText() != null ? binding.etFullName.getText().toString() : "";
        String mobile = binding.etMobile.getText() != null ? binding.etMobile.getText().toString() : "";
        String pwd = binding.etPassword.getText() != null ? binding.etPassword.getText().toString() : "";

        if (name.isEmpty() || mobile.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, "Please fill out all basic fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRole.equals("Doctor")) {
            String dept = binding.actvDepartment.getText().toString();
            if (dept.isEmpty()) {
                Toast.makeText(this, "Doctors must select a department", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Mock success
        Toast.makeText(this, "Request submitted for Admin approval", Toast.LENGTH_LONG).show();
        finish();
    }
}
