package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.PatientAdapter;
import com.simats.frontend.databinding.ActivityManagePatientsBinding;
import com.simats.frontend.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class ManagePatientsActivity extends AppCompatActivity {

    private ActivityManagePatientsBinding binding;
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagePatientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back action
        binding.btnBack.setOnClickListener(v -> finish());

        // Setup RecyclerView
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(this));

        List<Patient> mockList = new ArrayList<>();
        mockList.add(new Patient("Michael Ross", "General Dental", "PID-8920", "32 / Male", "Oct 12"));
        mockList.add(new Patient("Sarah Jenkins", "Orthodontics", "PID-4421", "28 / Female", "Oct 15"));
        mockList.add(new Patient("David Miller", "Periodontics", "PID-1092", "45 / Male", "Sep 28"));
        mockList.add(new Patient("Emma Watson", "General Dental", "PID-7732", "24 / Female", "Oct 05"));
        mockList.add(new Patient("Robert Chen", "Surgery", "PID-5520", "51 / Male", "Oct 11"));

        adapter = new PatientAdapter(this, mockList);
        binding.rvPatients.setAdapter(adapter);

        // Fab action
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewPatientActivity.class);
            startActivity(intent);
        });
    }
}
