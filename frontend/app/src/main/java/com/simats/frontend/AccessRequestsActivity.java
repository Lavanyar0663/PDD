package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.RequestAdapter;
import com.simats.frontend.databinding.ActivityAccessRequestsBinding;
import com.simats.frontend.models.AccessRequest;

import java.util.ArrayList;
import java.util.List;

public class AccessRequestsActivity extends AppCompatActivity {

    private ActivityAccessRequestsBinding binding;
    private RequestAdapter adapter;
    private List<AccessRequest> allRequests;
    private List<AccessRequest> filteredRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccessRequestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        // Setup Data
        allRequests = new ArrayList<>();
        allRequests.add(new AccessRequest("1", "Dr. Sarah Jenkins", "Doctor", "Orthodontics", "2m ago", "PENDING"));
        allRequests.add(new AccessRequest("2", "John Doe", "Pharmacist", "Pharmacy A", "1h ago", "PENDING"));
        allRequests.add(new AccessRequest("3", "Dr. A. Smith", "Doctor", "Pediatrics", "3h ago", "PENDING"));
        allRequests.add(new AccessRequest("4", "Maria Kovac", "Pharmacist", "Pharmacy B", "Yesterday", "PENDING"));
        allRequests.add(new AccessRequest("5", "Dr. Emily Chen", "Doctor", "General Dentistry", "2d ago", "PENDING"));

        // Setup RecyclerView
        filteredRequests = new ArrayList<>(allRequests);
        binding.rvRequests.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RequestAdapter(this, filteredRequests, request -> {
            Intent intent = new Intent(this, RequestProfileActivity.class);
            intent.putExtra("request_data", request);
            startActivity(intent);
        });

        binding.rvRequests.setAdapter(adapter);

        // Setup Tabs
        binding.tabPending.setOnClickListener(v -> filterList("PENDING"));
        binding.tabApproved.setOnClickListener(v -> filterList("APPROVED"));
        binding.tabRejected.setOnClickListener(v -> filterList("REJECTED"));
    }

    private void filterList(String status) {
        // Reset tabs UI
        binding.tabPending.setBackground(null);
        binding.tabApproved.setBackground(null);
        binding.tabRejected.setBackground(null);

        // This is a simplification. We'd normally toggle text colors back to grey.
        // For brevity we will just show Toast since mock data is all PENDING.
        Toast.makeText(this, "Showing " + status + " requests", Toast.LENGTH_SHORT).show();

        if (status.equals("PENDING")) {
            binding.tabPending.setBackgroundResource(R.drawable.bg_badge_white_shadow);
        } else if (status.equals("APPROVED")) {
            binding.tabApproved.setBackgroundResource(R.drawable.bg_badge_white_shadow);
        } else {
            binding.tabRejected.setBackgroundResource(R.drawable.bg_badge_white_shadow);
        }

        filteredRequests.clear();
        for (AccessRequest req : allRequests) {
            if (req.getStatus().equalsIgnoreCase(status)) {
                filteredRequests.add(req);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
