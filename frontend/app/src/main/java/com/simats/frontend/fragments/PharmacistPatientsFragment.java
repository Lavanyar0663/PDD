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
import com.simats.frontend.adapters.PharmacistPatientAdapter;
import com.simats.frontend.models.DoctorPatient;

import java.util.ArrayList;
import java.util.List;

public class PharmacistPatientsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacist_patients, container, false);

        RecyclerView rvPatients = view.findViewById(R.id.rvPatients);
        rvPatients.setLayoutManager(new LinearLayoutManager(getContext()));

        List<DoctorPatient> patients = new ArrayList<>();
        patients.add(new DoctorPatient("Robert M. Henderson", "RH", "#PX-8821", "45 yrs  •  Male", "Oct 24, 2023",
                "+1 234 567 8900", R.drawable.bg_circle_cyan_light, R.color.colorPrimary));
        patients.add(new DoctorPatient("Elena Sofia Rodriguez", "ER", "#PX-9054", "28 yrs  •  Female", "Oct 23, 2023",
                "+1 234 567 8901", R.drawable.bg_circle_cyan_light, R.color.colorPrimary));
        patients.add(new DoctorPatient("James Wilson", "JW", "#PX-7712", "62 yrs  •  Male", "Oct 21, 2023",
                "+1 234 567 8902", R.drawable.bg_circle_cyan_light, R.color.colorPrimary));
        patients.add(new DoctorPatient("Linda Chen", "LC", "#PX-4491", "34 yrs  •  Female", "Oct 19, 2023",
                "+1 234 567 8903", R.drawable.bg_circle_cyan_light, R.color.colorPrimary));
        patients.add(new DoctorPatient("Michael O'Brian", "MO", "#PX-2230", "51 yrs  •  Male", "Oct 18, 2023",
                "+1 234 567 8904", R.drawable.bg_circle_cyan_light, R.color.colorPrimary));

        PharmacistPatientAdapter adapter = new PharmacistPatientAdapter(getContext(), patients);
        rvPatients.setAdapter(adapter);

        return view;
    }
}
