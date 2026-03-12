package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.simats.frontend.databinding.ActivityNewPatientBinding;
import com.simats.frontend.network.ApiClient;
import com.simats.frontend.network.ApiInterface;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPatientActivity extends AppCompatActivity {

    private ActivityNewPatientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Auto-generate Patient ID for UI demo
        String generatedId = "P-2024-" + String.format("%04d", new Random().nextInt(10000));
        binding.tvPatientId.setText(generatedId);

        // Setup Sex Dropdown
        String[] genders = new String[]{"Male", "Female", "Other"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genders);
        binding.tvSexDropdown.setAdapter(genderAdapter);

        // Setup Dept Dropdown
        String[] depts = new String[]{"General Dentistry", "Orthodontics", "Periodontics", "Endodontics", "Oral Surgery"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, depts);
        binding.tvDeptDropdown.setAdapter(deptAdapter);

        binding.btnRegister.setOnClickListener(v -> registerPatient());
    }

    private void registerPatient() {
        String fullName = binding.etFullName.getText().toString().trim();
        String mobile = binding.etMobileNumber.getText().toString().trim();
        String ageStr = binding.etAge.getText().toString().trim();
        String gender = binding.tvSexDropdown.getText().toString().trim();
        String department = binding.tvDeptDropdown.getText().toString().trim();

        if (fullName.isEmpty() || mobile.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        String generatedEmail = fullName.split(" ")[0].toLowerCase() + "." + mobile.substring(Math.max(0, mobile.length() - 4)) + "@patient.diasrx.com";

        ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", fullName);
        requestBody.addProperty("email", generatedEmail);
        requestBody.addProperty("password", "password123"); // Default for new patients
        requestBody.addProperty("role", "patient");
        requestBody.addProperty("age", age);
        requestBody.addProperty("gender", gender);

        binding.btnRegister.setEnabled(false);
        binding.btnRegister.setText("Registering...");

        Call<JsonObject> call = apiInterface.registerUser(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.btnRegister.setEnabled(true);
                binding.btnRegister.setText("Register Patient");

                if (response.isSuccessful()) {
                    Toast.makeText(NewPatientActivity.this, "Patient Registered Successfully!", Toast.LENGTH_LONG).show();
                    finish(); // Go back to patients list
                } else {
                    Toast.makeText(NewPatientActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.btnRegister.setEnabled(true);
                binding.btnRegister.setText("Register Patient");
                Toast.makeText(NewPatientActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
