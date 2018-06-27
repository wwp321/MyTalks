package com.byron.mytalks.UserInfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.byron.mytalks.R;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {

    private List<UserInfoItem> userInfoItemList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfoItem item = userInfoItemList.get(position);
        holder.userInfoItemIcon.setImageResource(item.getItemIcon());
        holder.userInfoItemTitle.setText(item.getItemTitle());
        holder.userInfoItemDescription.setText(item.getItemDescription());
    }

    public UserInfoAdapter(List<UserInfoItem> userInfoItemList) {
        this.userInfoItemList = userInfoItemList;

    }

    @Override
    public int getItemCount() {
        return userInfoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userInfoItemIcon;
        TextView userInfoItemTitle;
        TextView userInfoItemDescription;


        public ViewHolder(View itemView) {
            super(itemView);

            userInfoItemIcon = itemView.findViewById(R.id.userInfo_item_icon);
            userInfoItemTitle = itemView.findViewById(R.id.userInfo_item_title);
            userInfoItemDescription = itemView.findViewById(R.id.userInfo_item_desc);
        }
    }
}
