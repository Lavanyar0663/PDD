package com.simats.frontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.simats.frontend.adapters.PrescriptionHistoryAdapter;
import com.simats.frontend.databinding.ActivityPrescriptionHistoryBinding;
import com.simats.frontend.models.PrescriptionHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionHistoryActivity extends AppCompatActivity {

    private ActivityPrescriptionHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(v -> finish());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));

        List<PrescriptionHistoryItem> items = new ArrayList<>();

        items.add(new PrescriptionHistoryItem(
                "Sarah Jenkins", "SJ", "OPD #8921", "+1 (555) 012-3456",
                "ANUG, Gingivitis", "Amoxicillin, Metronidazole", "10:42 AM", "Pending"));

        items.add(new PrescriptionHistoryItem(
                "John Doe", "JD", "OPD #8911", "+1 (555) 444-5555",
                "Dental Abscess", "Penicillin V, Ibuprofen", "09:15 AM", "Dispensed"));

        items.add(new PrescriptionHistoryItem(
                "Anita Lee", "AL", "OPD #8915", "+1 (555) 333-2222",
                "Root Canal Post-Op", "Paracetamol", "Yesterday", "Dispensed"));

        items.add(new PrescriptionHistoryItem(
                "Elena Kravtsov", "EK", "OPD #8892", "+1 (555) 231-9087",
                "Pulpitis", "Amoxicillin 500mg, Diclofenac", "Yesterday", "Dispensed"));

        PrescriptionHistoryAdapter adapter = new PrescriptionHistoryAdapter(this, items);
        binding.rvHistory.setAdapter(adapter);
    }
}
