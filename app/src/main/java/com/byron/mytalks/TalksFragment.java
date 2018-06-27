package com.byron.mytalks;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byron.mytalks.Talks.TalksAdapter;
import com.byron.mytalks.Talks.TalksCategoryAdapter;
import com.byron.mytalks.Talks.TalksCategoryItem;
import com.byron.mytalks.Talks.TalksItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TalksFragment extends Fragment {
    private final String TAG = "TalksFragment";

    private List<TalksItem> talksItemList = new ArrayList<>();
    private List<TalksCategoryItem> mTalksCategoryList = new ArrayList<>();

    public TalksFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view =  inflater.inflate(R.layout.fragment_talks, container, false);

        initTalksCategoryList();

        RecyclerView talksCategoryView = view.findViewById(R.id.talks_category_recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        talksCategoryView.setLayoutManager(layoutManager1);
        TalksCategoryAdapter adapter1 = new TalksCategoryAdapter(mTalksCategoryList);
        talksCategoryView.setAdapter(adapter1);

        initTalksItemList();

        RecyclerView recyclerView = view.findViewById(R.id.talks_recyclerview_lists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        TalksAdapter adapter = new TalksAdapter(talksItemList);
        recyclerView.setAdapter(adapter);

        return  view;
    }

    void initTalksItemList(){
        for (int i = 0; i < 10; i++) {
            TalksItem item = new TalksItem("Essam Daod",
                    "How we can bring mental health support to refuge",
                    "5:26",
                    R.drawable.dump_thumbnail);

            talksItemList.add(item);
        }
    }

    void initTalksCategoryList() {
        mTalksCategoryList.add(new TalksCategoryItem("Newest"));
        mTalksCategoryList.add(new TalksCategoryItem("Trending"));
        mTalksCategoryList.add(new TalksCategoryItem("Most viewed"));
        mTalksCategoryList.add(new TalksCategoryItem("Hidden gems"));
    }

}
