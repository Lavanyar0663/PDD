package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.DoctorAdapter;
import com.simats.frontend.databinding.ActivityManageDoctorsBinding;
import com.simats.frontend.models.Doctor;

import java.util.ArrayList;
import java.util.List;

public class ManageDoctorsActivity extends AppCompatActivity {

    private ActivityManageDoctorsBinding binding;
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Setup RecyclerView
        binding.rvDoctors.setLayoutManager(new LinearLayoutManager(this));

        List<Doctor> mockList = new ArrayList<>();
        mockList.add(new Doctor("1", "Dr. Sarah Jenkins", "Periodontics", "ACTIVE"));
        mockList.add(new Doctor("2", "Dr. Michael Chen", "Orthodontics", "ACTIVE"));
        mockList.add(new Doctor("3", "Dr. Alana Smith", "Endodontics", "ACTIVE"));
        mockList.add(new Doctor("4", "Dr. Robert Wilson", "Oral Surgery", "ON LEAVE"));
        mockList.add(new Doctor("5", "Dr. Emily Davis", "Prosthodontics", "ACTIVE"));

        adapter = new DoctorAdapter(this, mockList);
        binding.rvDoctors.setAdapter(adapter);

        // Fab click
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewStaffActivity.class);
            startActivity(intent);
        });
    }
}
