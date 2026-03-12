package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.frontend.databinding.ActivityCreatePrescriptionBinding;

public class CreatePrescriptionActivity extends AppCompatActivity {

    private ActivityCreatePrescriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(v -> finish());

        binding.btnIssue.setOnClickListener(v -> {
            if (!binding.cbCertify.isChecked()) {
                android.widget.Toast
                        .makeText(this, "Please certify the prescription first.", android.widget.Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            startActivity(new Intent(CreatePrescriptionActivity.this, PrescriptionIssuedActivity.class));
            finish();
        });
    }
}
