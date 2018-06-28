package com.byron.mytalks.Talks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byron.mytalks.R;
import com.byron.mytalks.TalksFragment;

import java.util.List;

public class TalksCategoryAdapter extends RecyclerView.Adapter<TalksCategoryAdapter.ViewHolder> {
    List<TalksCategoryItem> mCategoryList;
    View mView;
//    TalksFragment talksFragment;

    public TalksCategoryAdapter(List<TalksCategoryItem> mCategoryList) {
        this.mCategoryList = mCategoryList;
//        talksFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.talks_category_item, parent, false);
        final ViewHolder holder = new ViewHolder(mView);

        holder.mTalksCategoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TalksCategoryItem item = mCategoryList.get(position);

                Intent intent = new Intent(TalksFragment.CATEGORY_CLICK_BROADCAST);
                intent.putExtra("CATEGORY_SELECTOR", item.getmCategorySelector());
                Log.e("xxxx", "onClick: send broadcast" );
                LocalBroadcastManager.getInstance(mView.getContext()).sendBroadcast(intent);
            }
        });

        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TalksCategoryItem item = mCategoryList.get(position);
        holder.mTalksCategoryTitle.setText(item.getmCategoryTitle());
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTalksCategoryTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            mTalksCategoryTitle = itemView.findViewById(R.id.talks_category_title);
        }
    }
}
