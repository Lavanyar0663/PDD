package com.simats.frontend.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.frontend.R;
import com.simats.frontend.models.DashboardActivityItem;

import java.util.List;

public class DashboardActivityAdapter extends RecyclerView.Adapter<DashboardActivityAdapter.ViewHolder> {

    private final Context context;
    private final List<DashboardActivityItem> itemList;

    public DashboardActivityAdapter(Context context, List<DashboardActivityItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recent_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardActivityItem item = itemList.get(position);

        holder.tvTitle.setText(item.getTitle());

        if (item.getType() == DashboardActivityItem.Type.PRESCRIPTION) {
            holder.tvSubtitle.setVisibility(View.VISIBLE);
            holder.tvSubtitle.setText(item.getSubtitle());
            holder.tvDescription.setVisibility(View.GONE);

            holder.tvStatusBadge.setVisibility(View.VISIBLE);
            holder.tvStatusBadge.setText(item.getStatus());

            holder.tvAvatarInitials.setVisibility(View.VISIBLE);
            holder.tvAvatarInitials.setText(item.getInitials());
            holder.ivIcon.setBackgroundResource(R.drawable.bg_circle_grey);
            holder.ivIcon.setImageDrawable(null);

            if ("Dispensed".equalsIgnoreCase(item.getStatus())) {
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_light_green);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#1B5E20"));
            } else {
                // Pending
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_light_orange);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#E65100"));
            }

        } else {
            // Alert / Info
            holder.tvSubtitle.setVisibility(View.GONE);
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(item.getDescription());
            holder.tvStatusBadge.setVisibility(View.GONE);

            holder.tvAvatarInitials.setVisibility(View.GONE);
            holder.ivIcon.setBackgroundResource(0);
            holder.ivIcon.setImageResource(android.R.drawable.ic_dialog_info);
            holder.ivIcon.setColorFilter(Color.parseColor("#1A237E")); // Dark blue info
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvAvatarInitials, tvTitle, tvSubtitle, tvDescription, tvStatusBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvAvatarInitials = itemView.findViewById(R.id.tvAvatarInitials);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
        }
    }
}
