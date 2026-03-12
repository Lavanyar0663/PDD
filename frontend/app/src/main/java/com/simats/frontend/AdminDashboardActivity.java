package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.RequestAdapter;
import com.simats.frontend.databinding.ActivityAdminDashboardBinding;
import com.simats.frontend.models.AccessRequest;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;
    private RequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup RecyclerView for Recent Requests (Preview)
        setupRecentRequests();

        // Setup Card Clicks
        binding.cardManageDoctors.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageDoctorsActivity.class);
            startActivity(intent);
        });

        binding.cardManagePharm.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManagePharmacistsActivity.class);
            startActivity(intent);
        });

        binding.cardManagePatients.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManagePatientsActivity.class);
            startActivity(intent);
        });

        // View All Requests
        binding.tvViewAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccessRequestsActivity.class);
            startActivity(intent);
        });

        // Open Profile on Avatar click
        binding.ivUserAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminProfileActivity.class);
            startActivity(intent);
        });
    }

    private long backPressedTime;
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void setupRecentRequests() {
        binding.rvRecentRequests.setLayoutManager(new LinearLayoutManager(this));

        List<AccessRequest> mockList = new ArrayList<>();
        // As per mockup: Dr. Sarah Jenkins and John Doe
        mockList.add(new AccessRequest("1", "Dr. Sarah Jenkins", "Doctor", "Periodontics", "2m ago", "PENDING"));
        mockList.add(new AccessRequest("2", "John Doe", "Pharmacist", "Pharmacy Level 1", "1h ago", "PENDING"));

        adapter = new RequestAdapter(this, mockList, request -> {
            // Click to view profile
            Intent intent = new Intent(this, RequestProfileActivity.class);
            intent.putExtra("request_data", request);
            startActivity(intent);
        });

        binding.rvRecentRequests.setAdapter(adapter);
    }
}
