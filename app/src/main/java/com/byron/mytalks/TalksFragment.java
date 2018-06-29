package com.byron.mytalks;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TalksFragment extends Fragment {
    public static final int TALKS_ITEM_DOWNLOAD_FINISHED = 1;
    public static final int TALKS_ITEM_UPDATE = 2;
    public static final int TALKS_CATEGORY_DOWNLOAD_FINISHED = 3;
    public static final String CATEGORY_CLICK_BROADCAST = "com.byron.mytalks.TALKS_CATEGORY_CLICK";
    private final String TAG = "TalksFragment";

    private List<TalksItem> talksItemList = new ArrayList<>();
    private List<TalksCategoryItem> mTalksCategoryList = new ArrayList<>();
    private View mView;

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TALKS_ITEM_DOWNLOAD_FINISHED:
                    if(mView != null) {
                        Log.d(TAG, "handleMessage: Show talks");
                        RecyclerView recyclerView = mView.findViewById(R.id.talks_recyclerview_lists);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        TalksAdapter adapter = new TalksAdapter(talksItemList);
                        recyclerView.setAdapter(adapter);
                    }
                    break;
                case TALKS_ITEM_UPDATE:
                    RecyclerView recyclerView = mView.findViewById(R.id.talks_recyclerview_lists);
                    TalksAdapter adapter = (TalksAdapter) recyclerView.getAdapter();
                    if(adapter == null){
                        adapter = new TalksAdapter(talksItemList);
                        recyclerView.setAdapter(adapter);
                    }
                    recyclerView.getAdapter().notifyItemRangeInserted(0, talksItemList.size());
                    recyclerView.scrollToPosition(0);
                    break;

                case TALKS_CATEGORY_DOWNLOAD_FINISHED: {
                    RecyclerView talksCategoryView = mView.findViewById(R.id.talks_category_recyclerview);
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                    layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                    talksCategoryView.setLayoutManager(layoutManager1);
                    TalksCategoryAdapter adapter1 = new TalksCategoryAdapter(mTalksCategoryList);
                    talksCategoryView.setAdapter(adapter1);
                    initTalksItemList(mTalksCategoryList.get(0).getmCategorySelector(), TALKS_ITEM_DOWNLOAD_FINISHED);
                }
                    break;
            }
        }
    };

    public TalksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        mView =  inflater.inflate(R.layout.fragment_talks, container, false);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localReceiver = new LocalReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CATEGORY_CLICK_BROADCAST);

        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

        initTalksCategoryList();
        return  mView;
    }

    public void initTalksItemList(final String category, final int type){
        if(type == TALKS_ITEM_UPDATE)
        {
            RecyclerView recyclerView = mView.findViewById(R.id.talks_recyclerview_lists);
            recyclerView.getAdapter().notifyItemRangeRemoved(0, talksItemList.size());
            talksItemList.clear();
        }

        Observable<TalksItem> observable = Observable.create(
                new ObservableOnSubscribe<TalksItem>() {
                    @Override
                    public void subscribe(ObservableEmitter<TalksItem> e) throws Exception {
                        TalksItem item ;
                        try {
                            Document document = Jsoup.connect("http://www.iqiyi.com/news/").get();
                            Element rootNode = document.selectFirst(category);
                            Elements thumbnailLists = rootNode.select("div.site-piclist_pic img");
                            Elements titleLists = rootNode.select(" div.site-piclist_pic a");
                            Elements durationLists = rootNode.select(" span.mod-listTitle_right");
                            Elements subtitleLists = rootNode.select(" a.title_v");
                            Elements hrefLinks = titleLists;

                            int minNum = Math.min(thumbnailLists.size(), titleLists.size());
                            minNum = Math.min(minNum, durationLists.size());
                            minNum = Math.min(minNum, subtitleLists.size());

                            String thumbnailUrl;
                            String title;
                            String durations;
                            String subtitle;
                            String link;
                            for (int index = 0; index < minNum ; index ++) {
                                thumbnailUrl = thumbnailLists.get(index).attr("src");
                                title = titleLists.get(index).attr("title");
                                durations = durationLists.get(index).text();
                                subtitle = subtitleLists.get(index).text();
                                link = hrefLinks.get(index).attr("href");

                                item = new TalksItem(subtitle, title, durations, thumbnailUrl);
                                item.setLink(link);
                                    e.onNext(item);
                            }

                            e.onComplete();
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                    }
                }
        );

        Observer<TalksItem> observer = new Observer<TalksItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TalksItem value) {
                talksItemList.add(value);
                Log.e(TAG, "onNext: " + value.getLink() );
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
                Message message = new Message();
                message.what = type;
                handler.sendMessage(message);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }

    void initTalksCategoryList() {
        Observable<TalksCategoryItem> observable = Observable.create(
                new ObservableOnSubscribe<TalksCategoryItem>() {
                    @Override
                    public void subscribe(ObservableEmitter<TalksCategoryItem> e) throws Exception {
                        Document document = Jsoup.connect("http://www.iqiyi.com/news").get();

                        Elements blockList = document.select("div[data-block-name]");

                        List<Element> validBlocks = new ArrayList<>();

                        for (Element node : blockList) {
                            if(node.select("div.site-piclist_pic img").size() > 0) {
                                validBlocks.add(node);
                            }
                        }

                        for (Element node : validBlocks) {
                            String category = node.attr("data-block-name");
                            String selector = "div#" + node.id();

                            TalksCategoryItem item = new TalksCategoryItem(category, selector);
                            e.onNext(item);
                        }

                        e.onComplete();
                    }
                }
        );

        Observer<TalksCategoryItem> observer = new Observer<TalksCategoryItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TalksCategoryItem value) {
                mTalksCategoryList.add(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Message message = new Message();
                message.what = TALKS_CATEGORY_DOWNLOAD_FINISHED;
                handler.sendMessage(message);
            }
        };

        observable.observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    private class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String selector = intent.getStringExtra("CATEGORY_SELECTOR");
            Log.e(TAG, "onReceive: " + selector );
            initTalksItemList(selector,  TALKS_ITEM_UPDATE);
        }
    }

}
