package com.byron.mytalks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.byron.mytalks.UserInfo.UserInfoAdapter;
import com.byron.mytalks.UserInfo.UserInfoItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {

    private List<UserInfoItem> infoItemList = new ArrayList<>();

    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        initUserInfoList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.userInfo_recyclerview);
        recyclerView.setLayoutManager(layoutManager);

        UserInfoAdapter adapter = new UserInfoAdapter(infoItemList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    void initUserInfoList() {
        UserInfoItem item = new UserInfoItem(R.drawable.user_info_item_mylist, "My List", "No talks");
        infoItemList.add(item);
        item = new UserInfoItem(R.drawable.user_info_item_likes, "Likes", "No talks");
        infoItemList.add(item);
        item = new UserInfoItem(R.drawable.user_info_item_download, "Downloads", "No talks");
        infoItemList.add(item);
        item = new UserInfoItem(R.drawable.user_info_item_history, "History", "No talks");
        infoItemList.add(item);
    }

}
