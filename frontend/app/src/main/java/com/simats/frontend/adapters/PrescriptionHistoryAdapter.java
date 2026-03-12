package com.simats.frontend.adapters;

import android.content.Context;
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
import com.simats.frontend.models.PrescriptionHistoryItem;

import java.util.List;

public class PrescriptionHistoryAdapter extends RecyclerView.Adapter<PrescriptionHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<PrescriptionHistoryItem> historyList;

    public PrescriptionHistoryAdapter(Context context, List<PrescriptionHistoryItem> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prescription_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrescriptionHistoryItem item = historyList.get(position);

        holder.tvPatientName.setText(item.getName());
        holder.tvAvatarInitials.setText(item.getInitials());
        holder.tvContact.setText(item.getOpdId() + " • " + item.getPhone());
        holder.tvDiagnosis.setText(item.getDiagnosis());
        holder.tvDrugsList.setText(item.getDrugs());
        holder.tvTime.setText(item.getTime());
        holder.tvStatusBadge.setText(item.getStatus());

        if ("Dispensed".equalsIgnoreCase(item.getStatus())) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_light_green);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#1B5E20"));
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_light_orange);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#E65100"));
        }

        holder.itemView.setOnClickListener(v -> Toast
                .makeText(context, "Opening " + item.getName() + "'s Prescription", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPatientAvatar;
        TextView tvAvatarInitials, tvPatientName, tvStatusBadge, tvContact, tvDiagnosisLabel, tvDiagnosis, tvDrugsList,
                tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPatientAvatar = itemView.findViewById(R.id.ivPatientAvatar);
            tvAvatarInitials = itemView.findViewById(R.id.tvAvatarInitials);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvDiagnosisLabel = itemView.findViewById(R.id.tvDiagnosisLabel);
            tvDiagnosis = itemView.findViewById(R.id.tvDiagnosis);
            tvDrugsList = itemView.findViewById(R.id.tvDrugsList);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
