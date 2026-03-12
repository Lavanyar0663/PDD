package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.PharmacistAdapter;
import com.simats.frontend.databinding.ActivityManagePharmacistsBinding;
import com.simats.frontend.models.Pharmacist;

import java.util.ArrayList;
import java.util.List;

public class ManagePharmacistsActivity extends AppCompatActivity {

    private ActivityManagePharmacistsBinding binding;
    private PharmacistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagePharmacistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Setup RecyclerView
        binding.rvPharmacists.setLayoutManager(new LinearLayoutManager(this));

        List<Pharmacist> mockList = new ArrayList<>();
        mockList.add(new Pharmacist("1", "Ph. Sarah Miller", "ACTIVE"));
        mockList.add(new Pharmacist("2", "Ph. John Doe", "ACTIVE"));
        mockList.add(new Pharmacist("3", "Ph. Alex Chen", "ACTIVE"));
        mockList.add(new Pharmacist("4", "Ph. Emily Watson", "ON LEAVE"));
        mockList.add(new Pharmacist("5", "Ph. Robert Green", "ACTIVE"));

        adapter = new PharmacistAdapter(this, mockList);
        binding.rvPharmacists.setAdapter(adapter);

        // Fab click
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewStaffActivity.class);
            startActivity(intent);
        });
    }
}
