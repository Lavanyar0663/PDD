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
import com.simats.frontend.models.Pharmacist;

import java.util.List;

public class PharmacistAdapter extends RecyclerView.Adapter<PharmacistAdapter.PharmacistViewHolder> {

    private final Context context;
    private final List<Pharmacist> pharmacistList;

    public PharmacistAdapter(Context context, List<Pharmacist> pharmacistList) {
        this.context = context;
        this.pharmacistList = pharmacistList;
    }

    @NonNull
    @Override
    public PharmacistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_pharmacist, parent, false);
        return new PharmacistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacistViewHolder holder, int position) {
        Pharmacist pharmacist = pharmacistList.get(position);

        holder.tvName.setText(pharmacist.getName());
        holder.tvStatusBadge.setText(pharmacist.getStatus().toUpperCase());

        if (pharmacist.getStatus().equalsIgnoreCase("ACTIVE")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_teal);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#00897B"));
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_grey);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#5A6B82"));
        }
    }

    @Override
    public int getItemCount() {
        return pharmacistList.size();
    }

    public static class PharmacistViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatusBadge;

        public PharmacistViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
        }
    }
}
