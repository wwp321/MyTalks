package com.byron.mytalks.Playlists;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.byron.mytalks.PicassoTransformations.RadiusTransformation;
import com.byron.mytalks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    List<PlayList> playListList;
    View mView;
    ViewGroup mParent;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        ViewHolder holder = new ViewHolder(mView);
        mParent = parent;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList item = playListList.get(position);

//        holder.playlistThumbnail.setImageResource(item.getPlaylistThumbnail());
        Picasso.with(mView.getContext())
                .load(item.getPlaylistThumbnail())
                .resize(400,400)
                .centerCrop()
                .transform(new RadiusTransformation(10,10))
                .into(holder.playlistThumbnail);
        holder.playlistTitle.setText(item.getPlaylistName());
        holder.playlistContains.setText(item.getTalkNumber());

        int seconds = item.getTotalDuration() % 60;
        int minutes = item.getTotalDuration() % 3600 / 60;
        int hours = item.getTotalDuration() / 3600;

        StringBuilder builder = new StringBuilder();
        builder.append(hours);
        builder.append(":");
        builder.append(minutes);
        builder.append(":");
        builder.append(seconds);

        holder.playlistDuration.setText(builder.toString());
    }

    public PlaylistAdapter(List<PlayList> playListList) {
        this.playListList = playListList;
    }

    @Override
    public int getItemCount() {
        return playListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView playlistThumbnail;
        TextView playlistTitle;
        TextView playlistContains;
        TextView playlistDuration;


        public ViewHolder(View itemView) {
            super(itemView);

            playlistThumbnail = itemView.findViewById(R.id.playlist_thumbnail);
            playlistTitle = itemView.findViewById(R.id.playlist_title);
            playlistContains = itemView.findViewById(R.id.playlist_talks_contains);
            playlistDuration = itemView.findViewById(R.id.playlist_talks_durations);

        }
    }
}
