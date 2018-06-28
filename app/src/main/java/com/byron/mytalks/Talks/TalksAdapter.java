package com.byron.mytalks.Talks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.byron.mytalks.PicassoTransformations.RadiusTransformation;
import com.byron.mytalks.R;
import com.byron.mytalks.WebPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TalksAdapter extends RecyclerView.Adapter<TalksAdapter.ViewHolder> {

    private List<TalksItem> talksItemList;
    View mView;
    ViewGroup mParent;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.talks_item, parent, false);
        final ViewHolder holder = new ViewHolder(mView);
        mParent = parent;
        holder.talksThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TalksItem item = talksItemList.get(position);

                String link = item.getLink();

                if(link.contains("javascript")) {
                    Toast.makeText(mView.getContext(), "Don't have a active url", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mView.getContext(), WebPlayerActivity.class);
                    intent.putExtra("videoUrl" , item.getLink());
                    mView.getContext().startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TalksItem talksItem = talksItemList.get(position);

        if(talksItem.getThumbnailId() != 0) {
            Picasso.with(mView.getContext())
                    .load(talksItem.getThumbnailId())
                    .resize(mParent.getWidth(), (int)(mParent.getWidth() * 0.6))
                    .centerCrop()
                    .transform(new RadiusTransformation(30,30))
                    .into(holder.talksThumbnail);
        } else {
            Picasso.with(mView.getContext())
                    .load(talksItem.getThumbnailUrl())
                    .resize(mParent.getWidth(), (int)(mParent.getWidth() * 0.6))
                    .centerCrop()
                    .transform(new RadiusTransformation(30,30))
                    .into(holder.talksThumbnail);
        }

        holder.talksAuthor.setText(talksItem.getAuthor());
        holder.talksTitle.setText(talksItem.getTitle());
        holder.talksDuration.setText(talksItem.getDuration());
    }

    @Override
    public int getItemCount() {
        return talksItemList.size();
    }

    public TalksAdapter(List<TalksItem> talksItemList) {
        this.talksItemList = talksItemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView talksThumbnail;
        TextView talksAuthor;
        TextView talksTitle;
        TextView talksDuration;

        public ViewHolder(View itemView) {
            super(itemView);

            talksThumbnail = itemView.findViewById(R.id.talks_thumbnail);
            talksAuthor = itemView.findViewById(R.id.talks_author);
            talksTitle = itemView.findViewById(R.id.talks_title);
            talksDuration = itemView.findViewById(R.id.talks_duration);
        }
    }
}
