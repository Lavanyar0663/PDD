package com.simats.frontend;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.frontend.databinding.ActivityRequestProfileBinding;
import com.simats.frontend.models.AccessRequest;

public class RequestProfileActivity extends AppCompatActivity {

    private ActivityRequestProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        // Get Request Data
        AccessRequest request = (AccessRequest) getIntent().getSerializableExtra("request_data");

        if (request != null) {
            setupProfile(request);
        } else {
            Toast.makeText(this, "Error loading profile data", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Actions
        binding.btnProfileApprove.setOnClickListener(v -> {
            android.content.Intent approveIntent = new android.content.Intent(this, RequestApprovedActivity.class);
            approveIntent.putExtra("applicant_name", request != null ? request.getName() : "The user");
            startActivity(approveIntent);
            finish();
        });

        binding.btnProfileReject.setOnClickListener(v -> {
            Toast.makeText(this, "Rejected " + (request != null ? request.getName() : ""), Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setupProfile(AccessRequest request) {
        binding.tvProfileName.setText(request.getName());
        binding.tvProfileRole.setText(request.getRole().toUpperCase());
        binding.tvProfileDept.setText(request.getDepartment());
        binding.tvProfileTime.setText(request.getTimeAgo());
        binding.tvProfilePhone.setText(request.getPhone());

        // Role Badge Color
        if (request.getRole().equalsIgnoreCase("Pharmacist")) {
            binding.tvProfileRole.setBackgroundResource(R.drawable.bg_badge_teal);
            binding.tvProfileRole.setTextColor(Color.parseColor("#00897B"));
        } else {
            binding.tvProfileRole.setBackgroundResource(R.drawable.bg_badge_blue);
            binding.tvProfileRole.setTextColor(Color.parseColor("#0052CC"));
        }
    }
}
