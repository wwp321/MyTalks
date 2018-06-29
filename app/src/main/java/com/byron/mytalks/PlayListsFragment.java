package com.byron.mytalks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byron.mytalks.Playlists.PlayList;
import com.byron.mytalks.Playlists.PlaylistAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListsFragment extends Fragment {
    private final String TAG = "PlayListsFragment";

    List<PlayList> playListList = new ArrayList<>();

    public PlayListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Timber.d("onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_play_lists, container, false);

        initPlaylists();

        RecyclerView recyclerView = view.findViewById(R.id.playlist_recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        PlaylistAdapter adapter = new PlaylistAdapter(playListList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    void initPlaylists(){
        Random random = new Random();

        for (int i = 0; i < 10; i++) {

            PlayList item = new PlayList("Talks to watch on a sleepy day in",
                    i,
                    random.nextInt(10000),
                    R.drawable.dump_thumbnail);

            playListList.add(item);
        }
    }

}
