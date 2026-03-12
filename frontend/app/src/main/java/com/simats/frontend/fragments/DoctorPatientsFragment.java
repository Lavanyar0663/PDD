package com.simats.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.simats.frontend.databinding.FragmentDoctorPatientsBinding;

public class DoctorPatientsFragment extends Fragment {

        private FragmentDoctorPatientsBinding binding;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                        @Nullable Bundle savedInstanceState) {
                binding = FragmentDoctorPatientsBinding.inflate(inflater, container, false);

                setupPatientsList();

                return binding.getRoot();
        }

        private void setupPatientsList() {
                binding.rvPatients.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

                com.simats.frontend.network.ApiInterface apiInterface = com.simats.frontend.network.ApiClient
                                .getClient(getContext()).create(com.simats.frontend.network.ApiInterface.class);
                retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call = apiInterface.getPatients();

                call.enqueue(new retrofit2.Callback<java.util.List<com.google.gson.JsonObject>>() {
                        @Override
                        public void onResponse(retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call,
                                        retrofit2.Response<java.util.List<com.google.gson.JsonObject>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                        java.util.List<com.simats.frontend.models.DoctorPatient> patients = new java.util.ArrayList<>();

                                        for (com.google.gson.JsonObject patJson : response.body()) {
                                                String name = patJson.has("name") ? patJson.get("name").getAsString()
                                                                : "Unknown";
                                                String opdId = patJson.has("id")
                                                                ? "OPD #" + patJson.get("id").getAsString()
                                                                : "OPD #---";
                                                int age = patJson.has("age") ? patJson.get("age").getAsInt() : 0;
                                                String gender = patJson.has("gender")
                                                                ? patJson.get("gender").getAsString()
                                                                : "U";
                                                String ageGender = age + "y / " + gender;
                                                String phone = patJson.has("contact_number")
                                                                && !patJson.get("contact_number").isJsonNull()
                                                                                ? patJson.get("contact_number")
                                                                                                .getAsString()
                                                                                : "No Contact";

                                                // Default values for missing backend fields in this basic demo
                                                String initials = name.substring(0, Math.min(name.length(), 2))
                                                                .toUpperCase();
                                                String lastVisit = "Recently";

                                                // Alternate colors for UI feel
                                                int avatarBg = com.simats.frontend.R.drawable.bg_circle_cyan_light;
                                                int avatarColor = android.R.color.holo_green_dark;

                                                patients.add(new com.simats.frontend.models.DoctorPatient(
                                                                name, initials, opdId, ageGender, lastVisit, phone,
                                                                avatarBg, avatarColor));
                                        }

                                        com.simats.frontend.adapters.DoctorPatientAdapter adapter = new com.simats.frontend.adapters.DoctorPatientAdapter(
                                                        getContext(), patients);
                                        binding.rvPatients.setAdapter(adapter);
                                } else {
                                        android.widget.Toast.makeText(getContext(), "Failed to fetch patients",
                                                        android.widget.Toast.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call,
                                        Throwable t) {
                                android.widget.Toast.makeText(getContext(), "Network error: " + t.getMessage(),
                                                android.widget.Toast.LENGTH_SHORT).show();
                        }
                });
        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                binding = null;
        }
}
