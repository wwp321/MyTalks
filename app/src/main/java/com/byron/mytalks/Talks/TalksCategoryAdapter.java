package com.byron.mytalks.Talks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byron.mytalks.R;

import java.util.List;

public class TalksCategoryAdapter extends RecyclerView.Adapter<TalksCategoryAdapter.ViewHolder> {
    List<TalksCategoryItem> mCategoryList;
    View mView;

    public TalksCategoryAdapter(List<TalksCategoryItem> mCategoryList) {
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.talks_category_item, parent, false);
        ViewHolder holder = new ViewHolder(mView);
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
