package com.simats.frontend.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.models.AccessRequest;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private final Context context;
    private final List<AccessRequest> requestList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AccessRequest request);
    }

    public RequestAdapter(Context context, List<AccessRequest> requestList, OnItemClickListener listener) {
        this.context = context;
        this.requestList = requestList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_access_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        AccessRequest request = requestList.get(position);

        holder.tvName.setText(request.getName());
        holder.tvRoleBadge.setText(request.getRole().toUpperCase());

        // Change badge color based on role
        if (request.getRole().equalsIgnoreCase("Pharmacist")) {
            holder.tvRoleBadge.setBackgroundResource(R.drawable.bg_badge_teal);
            holder.tvRoleBadge.setTextColor(Color.parseColor("#00897B"));
        } else {
            holder.tvRoleBadge.setBackgroundResource(R.drawable.bg_badge_blue);
            holder.tvRoleBadge.setTextColor(Color.parseColor("#0052CC"));
        }

        holder.tvTime.setText("• " + request.getTimeAgo());

        // Action Buttons
        holder.btnApprove.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context,
                    com.simats.frontend.RequestApprovedActivity.class);
            intent.putExtra("applicant_name", request.getName());
            context.startActivity(intent);
        });

        holder.btnReject.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context,
                    com.simats.frontend.RequestRejectedActivity.class);
            intent.putExtra("applicant_name", request.getName());
            context.startActivity(intent);
        });

        // Click the whole item to view profile
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRoleBadge, tvTime;
        LinearLayout btnReject, btnApprove;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRoleBadge = itemView.findViewById(R.id.tvRoleBadge);
            tvTime = itemView.findViewById(R.id.tvTime);
            btnReject = itemView.findViewById(R.id.btnReject);
            btnApprove = itemView.findViewById(R.id.btnApprove);
        }
    }
}
