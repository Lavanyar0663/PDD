package com.simats.frontend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.models.DoctorPatient;

import java.util.List;

public class PharmacistPatientAdapter extends RecyclerView.Adapter<PharmacistPatientAdapter.ViewHolder> {

    private Context context;
    private List<DoctorPatient> patientList;

    public PharmacistPatientAdapter(Context context, List<DoctorPatient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pharmacist_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorPatient patient = patientList.get(position);

        holder.tvPatientName.setText(patient.getName());
        holder.tvAvatarInitials.setText(patient.getInitials());
        holder.tvOpdId.setText(patient.getOpdId());
        holder.tvAgeGender.setText(patient.getAgeGender());
        holder.tvLastVisit.setText(patient.getLastVisit());
        holder.tvPhone.setText(patient.getPhone());

        // Set dynamic colors
        holder.ivPatientAvatar.setBackgroundResource(patient.getAvatarColorResId());
        holder.tvAvatarInitials.setTextColor(ContextCompat.getColor(context, patient.getAvatarTextColorResId()));
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPatientAvatar, ivPhoneIcon, ivArrow;
        TextView tvAvatarInitials, tvPatientName, tvOpdId, tvAgeGender, tvLastVisitLabel, tvLastVisit, tvPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPatientAvatar = itemView.findViewById(R.id.ivPatientAvatar);
            tvAvatarInitials = itemView.findViewById(R.id.tvAvatarInitials);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvOpdId = itemView.findViewById(R.id.tvOpdId);
            tvAgeGender = itemView.findViewById(R.id.tvAgeGender);
            tvLastVisitLabel = itemView.findViewById(R.id.tvLastVisitLabel);
            tvLastVisit = itemView.findViewById(R.id.tvLastVisit);
            ivPhoneIcon = itemView.findViewById(R.id.ivPhoneIcon);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivArrow = itemView.findViewById(R.id.ivArrow);
        }
    }
}
