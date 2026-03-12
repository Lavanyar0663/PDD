package com.simats.frontend.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.models.DoctorPatient;

import java.util.List;

public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientAdapter.ViewHolder> {

    private final Context context;
    private final List<DoctorPatient> patientList;

    public DoctorPatientAdapter(Context context, List<DoctorPatient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_patient, parent, false);
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

        // Dynamic Avatar Colors
        holder.ivPatientAvatar.setBackgroundResource(patient.getAvatarColorResId());
        holder.tvAvatarInitials.setTextColor(context.getResources().getColor(patient.getAvatarTextColorResId()));

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context,
                    com.simats.frontend.CreatePrescriptionActivity.class);
            context.startActivity(intent);
            // Toast.makeText(context, "Opening " + patient.getName() + "'s File",
            // Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPatientAvatar;
        TextView tvAvatarInitials, tvPatientName, tvOpdId, tvAgeGender, tvLastVisit, tvPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPatientAvatar = itemView.findViewById(R.id.ivPatientAvatar);
            tvAvatarInitials = itemView.findViewById(R.id.tvAvatarInitials);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvOpdId = itemView.findViewById(R.id.tvOpdId);
            tvAgeGender = itemView.findViewById(R.id.tvAgeGender);
            tvLastVisit = itemView.findViewById(R.id.tvLastVisit);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
