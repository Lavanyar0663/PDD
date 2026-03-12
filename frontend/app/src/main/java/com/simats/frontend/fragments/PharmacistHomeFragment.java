package com.simats.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.adapters.PharmacistPrescriptionAdapter;
import com.simats.frontend.models.PharmacistPrescription;

import java.util.ArrayList;
import java.util.List;

public class PharmacistHomeFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                        @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_pharmacist_home, container, false);

                RecyclerView rvLatestPrescriptions = view.findViewById(R.id.rvLatestPrescriptions);
                rvLatestPrescriptions.setLayoutManager(new LinearLayoutManager(getContext()));

                com.simats.frontend.network.ApiInterface apiInterface = com.simats.frontend.network.ApiClient
                                .getClient(getContext()).create(com.simats.frontend.network.ApiInterface.class);
                retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call = apiInterface
                                .getPendingPrescriptions();

                call.enqueue(new retrofit2.Callback<java.util.List<com.google.gson.JsonObject>>() {
                        @Override
                        public void onResponse(retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call,
                                        retrofit2.Response<java.util.List<com.google.gson.JsonObject>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                        List<PharmacistPrescription> prescriptions = new ArrayList<>();

                                        for (com.google.gson.JsonObject pJson : response.body()) {
                                                String doctorName = pJson.has("doctor_id")
                                                                ? "Dr. ID: " + pJson.get("doctor_id").getAsString()
                                                                : "Unknown Dr";
                                                String patientInitials = "PT";
                                                String patientName = pJson.has("patient_id")
                                                                ? "Patient #" + pJson.get("patient_id").getAsString()
                                                                : "Unknown Patient";

                                                String status = pJson.has("status")
                                                                ? pJson.get("status").getAsString().toUpperCase()
                                                                : "PENDING";

                                                // Time parsing (very simplified)
                                                String time = "Recent";
                                                if (pJson.has("created_at") && !pJson.get("created_at").isJsonNull()) {
                                                        time = pJson.get("created_at").getAsString().substring(11, 16); // Extract
                                                                                                                        // roughly
                                                                                                                        // HH:mm
                                                }

                                                int statusColor = R.color.colorPrimary;
                                                int badgeBg = R.drawable.bg_badge_light_orange;

                                                String pId = pJson.has("id") ? pJson.get("id").getAsString()
                                                                : "unknown";

                                                prescriptions.add(new PharmacistPrescription(
                                                                pId, patientName, patientInitials, doctorName, status,
                                                                time,
                                                                R.color.colorPrimary, statusColor, badgeBg));
                                        }

                                        PharmacistPrescriptionAdapter adapter = new PharmacistPrescriptionAdapter(
                                                        getContext(), prescriptions);
                                        rvLatestPrescriptions.setAdapter(adapter);
                                } else {
                                        android.widget.Toast
                                                        .makeText(getContext(), "Failed to load pending prescriptions",
                                                                        android.widget.Toast.LENGTH_SHORT)
                                                        .show();
                                }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<java.util.List<com.google.gson.JsonObject>> call,
                                        Throwable t) {
                                android.widget.Toast.makeText(getContext(), "Network error: " + t.getMessage(),
                                                android.widget.Toast.LENGTH_SHORT).show();
                        }
                });

                return view;
        }
}
