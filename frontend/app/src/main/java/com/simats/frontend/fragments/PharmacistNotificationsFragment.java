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
import com.simats.frontend.adapters.PharmacistNotificationAdapter;
import com.simats.frontend.models.DoctorNotification;

import java.util.ArrayList;
import java.util.List;

public class PharmacistNotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacist_notifications, container, false);

        RecyclerView rvNotifications = view.findViewById(R.id.rvNotifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        List<DoctorNotification> notifications = new ArrayList<>();
        notifications.add(new DoctorNotification("TODAY"));
        notifications.add(new DoctorNotification("Stat Insulin Order", "Bed 402, Ward B requires immediate validation.",
                "Just now", true, android.R.drawable.ic_dialog_alert, R.drawable.bg_badge_light_orange,
                R.color.colorError, R.color.colorTextSecondary));
        notifications.add(new DoctorNotification("Patient: Sarah Jenkins", "Prescribed by Dr. Emily Chen", "4m ago",
                true, android.R.drawable.ic_menu_edit, R.drawable.bg_badge_light_blue, R.color.colorPrimary,
                R.color.colorTextSecondary));
        notifications.add(new DoctorNotification("Access Approved",
                "Your access to the High-Risk Medication Vault has been granted.", "2h ago", false,
                android.R.drawable.ic_secure, R.drawable.bg_badge_grey, R.color.colorSecondary,
                R.color.colorTextSecondary));
        notifications.add(new DoctorNotification("Patient: Robert Miller", "Prescribed by Dr. Michael Ross", "5h ago",
                false, android.R.drawable.ic_menu_edit, R.drawable.bg_badge_grey, R.color.colorSecondary,
                R.color.colorTextSecondary));

        notifications.add(new DoctorNotification("YESTERDAY"));
        notifications.add(new DoctorNotification("Low Stock: Amoxicillin",
                "Main Pharmacy stock is below 20%. Order replenishment soon.", "Yesterday", false,
                android.R.drawable.ic_menu_agenda, R.drawable.bg_badge_grey, R.color.colorSecondary,
                R.color.colorTextSecondary));

        PharmacistNotificationAdapter adapter = new PharmacistNotificationAdapter(getContext(), notifications);
        rvNotifications.setAdapter(adapter);

        return view;
    }
}
