package com.byron.mytalks.Talks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.byron.mytalks.MainActivity;
import com.byron.mytalks.PicassoTransformations.RadiusTransformation;
import com.byron.mytalks.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class TalksAdapter extends RecyclerView.Adapter<TalksAdapter.ViewHolder> {

    private List<TalksItem> talksItemList;
    View mView;
    ViewGroup mParent;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.talks_item, parent, false);
        ViewHolder holder = new ViewHolder(mView);
        mParent = parent;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TalksItem talksItem = talksItemList.get(position);
//        holder.talksThumbnail.setImageResource(talksItem.getThumbnailId());

        Picasso.with(mView.getContext())
                .load("http://vthumb.ykimg.com/054101015B2B82848B3255A19E50A9C8")
                .resize(mParent.getWidth(), (int)(mParent.getWidth() * 0.9))
                .centerCrop()
                .transform(new RadiusTransformation(30,30))
                .into(holder.talksThumbnail);
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
