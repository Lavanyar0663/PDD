package com.simats.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.simats.frontend.databinding.ActivityNewStaffBinding;
import com.simats.frontend.network.ApiClient;
import com.simats.frontend.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewStaffActivity extends AppCompatActivity {

    private ActivityNewStaffBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Setup Role Dropdown
        String[] roles = new String[]{"Admin", "Doctor", "Pharmacist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, roles);
        binding.tvRoleDropdown.setAdapter(adapter);

        binding.btnRegister.setOnClickListener(v -> registerStaff());
    }

    private void registerStaff() {
        String fullName = binding.etFullName.getText().toString().trim();
        String mobile = binding.etMobileNumber.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String selectedRole = binding.tvRoleDropdown.getText().toString().trim().toLowerCase();

        if (fullName.isEmpty() || mobile.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate an email for backend compatibility
        String generatedEmail = fullName.split(" ")[0].toLowerCase() + "." + mobile.substring(Math.max(0, mobile.length() - 4)) + "@diasrx.com";

        ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", fullName);
        requestBody.addProperty("email", generatedEmail);
        requestBody.addProperty("password", password);
        requestBody.addProperty("role", selectedRole);

        binding.btnRegister.setEnabled(false);
        binding.btnRegister.setText("Registering...");

        Call<JsonObject> call = apiInterface.registerUser(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.btnRegister.setEnabled(true);
                binding.btnRegister.setText("Register User");

                if (response.isSuccessful() && response.body() != null) {
                    // Navigate to success screen
                    Intent intent = new Intent(NewStaffActivity.this, StaffRegistrationCompleteActivity.class);
                    intent.putExtra("STAFF_NAME", fullName);
                    intent.putExtra("STAFF_ROLE", selectedRole.substring(0, 1).toUpperCase() + selectedRole.substring(1));
                    intent.putExtra("STAFF_MOBILE", mobile);
                    intent.putExtra("STAFF_EMAIL", generatedEmail);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NewStaffActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("NewStaffActivity", "Error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.btnRegister.setEnabled(true);
                binding.btnRegister.setText("Register User");
                Toast.makeText(NewStaffActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
