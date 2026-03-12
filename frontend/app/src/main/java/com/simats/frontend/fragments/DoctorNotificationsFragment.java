package com.simats.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.simats.frontend.databinding.FragmentDoctorNotificationsBinding;

public class DoctorNotificationsFragment extends Fragment {

    private FragmentDoctorNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentDoctorNotificationsBinding.inflate(inflater, container, false);

        setupNotificationsList();

        binding.tvClear.setOnClickListener(v -> {
            android.widget.Toast.makeText(getContext(), "Notifications Cleared", android.widget.Toast.LENGTH_SHORT)
                    .show();
        });

        return binding.getRoot();
    }

    private void setupNotificationsList() {
        binding.rvNotifications.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        java.util.List<com.simats.frontend.models.DoctorNotification> list = new java.util.ArrayList<>();

        // TODAY
        list.add(new com.simats.frontend.models.DoctorNotification("TODAY"));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "Prescription Dispensed",
                "Prescription for Sarah Jenkins (OPD #8921) has been fully dispensed by the pharmacy.",
                "2m ago", true,
                android.R.drawable.ic_menu_add, com.simats.frontend.R.drawable.bg_badge_teal,
                android.R.color.holo_green_dark, android.R.color.holo_green_dark));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "Lab Results Available",
                "Blood work results for Michael Ross are now available for review.",
                "1h ago", true,
                android.R.drawable.ic_menu_report_image, com.simats.frontend.R.drawable.bg_badge_blue,
                android.R.color.holo_blue_dark, android.R.color.holo_green_dark));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "Appointment Confirmed",
                "Follow-up appointment for Anita Lee confirmed for tomorrow at 10:00 AM.",
                "4h ago", false,
                android.R.drawable.ic_menu_my_calendar, com.simats.frontend.R.drawable.bg_badge_grey,
                android.R.color.darker_gray, android.R.color.darker_gray));

        // YESTERDAY
        list.add(new com.simats.frontend.models.DoctorNotification("YESTERDAY"));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "Drug Interaction Alert",
                "Potential interaction detected for patient John Doe between Amoxicillin and Warfarin.",
                "Yesterday", false,
                android.R.drawable.ic_dialog_alert, com.simats.frontend.R.drawable.bg_badge_light_orange,
                android.R.color.holo_orange_dark, android.R.color.darker_gray));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "Treatment Plan Updated",
                "Dr. Smith updated the root canal treatment plan for Elena Kravtsov.",
                "Yesterday", false,
                android.R.drawable.ic_menu_edit, com.simats.frontend.R.drawable.bg_badge_grey,
                android.R.color.holo_purple, android.R.color.darker_gray));

        // EARLIER
        list.add(new com.simats.frontend.models.DoctorNotification("EARLIER"));
        list.add(new com.simats.frontend.models.DoctorNotification(
                "System Update",
                "DIAS Rx has been successfully updated to version 2.4.0 with new inventory features.",
                "Oct 20", false,
                android.R.drawable.checkbox_on_background, com.simats.frontend.R.drawable.bg_badge_light_green,
                android.R.color.holo_green_dark, android.R.color.darker_gray));

        com.simats.frontend.adapters.DoctorNotificationAdapter adapter = new com.simats.frontend.adapters.DoctorNotificationAdapter(
                getContext(), list);
        binding.rvNotifications.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
