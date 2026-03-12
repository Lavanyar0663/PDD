package com.simats.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.simats.frontend.databinding.FragmentDoctorDashboardBinding;

public class DoctorDashboardFragment extends Fragment {

        private FragmentDoctorDashboardBinding binding;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                        @Nullable Bundle savedInstanceState) {
                binding = FragmentDoctorDashboardBinding.inflate(inflater, container, false);

                setupRecentActivity();
                setupClickListeners();

                return binding.getRoot();
        }

        private void setupRecentActivity() {
                binding.rvRecentActivity
                                .setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

                java.util.List<com.simats.frontend.models.DashboardActivityItem> items = new java.util.ArrayList<>();

                items.add(new com.simats.frontend.models.DashboardActivityItem(
                                "Jane Doe", "Antibiotics • 10:30 AM", "Pending", "JD"));

                items.add(new com.simats.frontend.models.DashboardActivityItem(
                                "John Smith", "Pain Mgmt • 09:15 AM", "Dispensed", "JS"));

                items.add(new com.simats.frontend.models.DashboardActivityItem(
                                "Michael Brown", "Root Canal Therapy • Yesterday", "Dispensed", "MB"));

                items.add(new com.simats.frontend.models.DashboardActivityItem(
                                "Pharmacy Update",
                                "Amoxicillin 500mg stock is currently low. Please consider alternatives for non-critical cases."));

                com.simats.frontend.adapters.DashboardActivityAdapter adapter = new com.simats.frontend.adapters.DashboardActivityAdapter(
                                getContext(), items);

                binding.rvRecentActivity.setAdapter(adapter);
        }

        private void setupClickListeners() {
                binding.ivDoctorAvatar.setOnClickListener(v -> {
                        android.content.Intent intent = new android.content.Intent(getContext(),
                                        com.simats.frontend.DoctorSettingsActivity.class);
                        startActivity(intent);
                });

                binding.tvViewAll.setOnClickListener(v -> {
                        android.content.Intent intent = new android.content.Intent(getContext(),
                                        com.simats.frontend.PrescriptionHistoryActivity.class);
                        startActivity(intent);
                        // android.widget.Toast
                        // .makeText(getContext(), "Opening Recent Activity...",
                        // android.widget.Toast.LENGTH_SHORT)
                        // .show();
                });
        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                binding = null;
        }
}
