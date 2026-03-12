package com.simats.frontend.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private final Context context;
    private final List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.tvName.setText(doctor.getName());
        holder.tvDepartment.setText(doctor.getDepartment());
        holder.tvStatusBadge.setText(doctor.getStatus().toUpperCase());

        if (doctor.getStatus().equalsIgnoreCase("ACTIVE")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_teal);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#00897B"));
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_grey);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#5A6B82")); // colorTextSecondary roughly
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDepartment, tvStatusBadge;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
        }
    }
}
