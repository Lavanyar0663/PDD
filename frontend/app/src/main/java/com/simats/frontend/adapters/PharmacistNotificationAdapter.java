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
import com.simats.frontend.models.DoctorNotification;

import java.util.List;

public class PharmacistNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DoctorNotification> notificationList;

    public PharmacistNotificationAdapter(Context context, List<DoctorNotification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public int getItemViewType(int position) {
        return notificationList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DoctorNotification.TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_notification_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_pharmacist_notification, parent, false);
            return new NotificationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DoctorNotification notification = notificationList.get(position);

        if (holder.getItemViewType() == DoctorNotification.TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvHeader.setText(notification.getHeaderTitle());
        } else {
            NotificationViewHolder notifHolder = (NotificationViewHolder) holder;
            notifHolder.tvNotifTitle.setText(notification.getTitle());
            notifHolder.tvNotifDesc.setText(notification.getDescription());
            notifHolder.tvTime.setText(notification.getTime());

            notifHolder.ivNotifIcon.setImageResource(notification.getIconResId());
            notifHolder.ivNotifIcon.setBackgroundResource(notification.getIconBgResId());
            notifHolder.ivNotifIcon.setColorFilter(ContextCompat.getColor(context, notification.getIconTintColor()));

            notifHolder.tvTime.setTextColor(ContextCompat.getColor(context, notification.getTimeTextColor()));

            if (notification.isUnread()) {
                notifHolder.ivUnreadDot.setVisibility(View.VISIBLE);
                notifHolder.ivUnreadDot
                        .setColorFilter(ContextCompat.getColor(context, notification.getIconTintColor()));
            } else {
                notifHolder.ivUnreadDot.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNotifIcon, ivUnreadDot;
        TextView tvNotifTitle, tvNotifDesc, tvTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotifIcon = itemView.findViewById(R.id.ivNotifIcon);
            ivUnreadDot = itemView.findViewById(R.id.ivUnreadDot);
            tvNotifTitle = itemView.findViewById(R.id.tvNotifTitle);
            tvNotifDesc = itemView.findViewById(R.id.tvNotifDesc);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
