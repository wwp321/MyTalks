package com.byron.mytalks;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.byron.mytalks.Talks.TalksAdapter;
import com.byron.mytalks.Talks.TalksItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_edittext)
    EditText mInputEdit;
    @BindView(R.id.search_mic_btn)
    ImageView mMicBtn;

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_result_recyclerview)
    RecyclerView searchResultView;

    private List<TalksItem> searchTalksItemList = new ArrayList<>();
    private List<TalksItem> cachedTalksList = new ArrayList<>();

    public static final int SEARCH_RESULT_DOWNLOAD_FINISHED = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case SEARCH_RESULT_DOWNLOAD_FINISHED:
                    TalksAdapter adapter = (TalksAdapter) searchResultView.getAdapter();
                    Timber.d("size:" + cachedTalksList.size());
                    if(adapter == null) {
                        cachedTalksList.addAll(searchTalksItemList);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(MyTalksApplication.getContext());
//                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                        adapter = new TalksAdapter(searchTalksItemList);
                        searchResultView.setLayoutManager(layoutManager);
                        searchResultView.setAdapter(adapter);
                    }else {


                        cachedTalksList.addAll(searchTalksItemList);
                        Timber.d("cached talks list size:" + cachedTalksList.size());
                        adapter.notifyItemRangeInserted(0, cachedTalksList.size());
                        searchResultView.scrollToPosition(0);
                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }


//

        mInputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Timber.d("search for " + v.getText().toString());
                    getSearchResult(v.getText().toString());

                    InputMethodManager ime = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(ime.isActive()){
                        ime.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                return false;
            }
        });
    }

    void getSearchResult(final String searchText) {

        if(searchResultView.getAdapter() != null) {
            int size = cachedTalksList.size();
            cachedTalksList.clear();
            searchResultView.getAdapter().notifyItemRangeRemoved(0, size);
        }

        Observable<TalksItem> observable = Observable.create(
                new ObservableOnSubscribe<TalksItem>() {
                    @Override
                    public void subscribe(ObservableEmitter<TalksItem> e) throws Exception {
                        String url = "http://so.iqiyi.com/so/q_" + searchText + "?source=secondInput&sr=1503338792429" ;
                        Document document = Jsoup.connect(url).get();
                        Elements resultList = document.select("ul.mod_result_list li.list_item");
                        Timber.d("result list size:" + resultList.size());
                        for (Element element : resultList) {
                            Element subElement;

                            String thumbnailUrl = "";
                            String title = "";
                            String subTitle = "";
                            String link = "";
                            String durations = "";

                            subElement = element.selectFirst("img");
                            if(subElement != null) {
                                thumbnailUrl = "http:" + subElement.attr("src");
                            } else { continue; }
                            subElement = element.selectFirst(".result_title a");
                            if(subElement != null) {
                                title = subElement.attr("title");
                            }else { continue; }
                            subElement = element.selectFirst(".result_info_txt");
                            if(subElement != null) {
                                subTitle = subElement.text();
                            }else { continue; }

                            subElement = element.selectFirst("a");
                            if(subElement != null) {
                                link = subElement.attr("href");
                            }else { continue; }

                            subElement = element.selectFirst("span.icon-vInfo");
                            if(subElement != null) {
                                durations = subElement.text();
                            }else { continue; }

                            TalksItem item = new TalksItem(subTitle, title, durations, thumbnailUrl);
                            item.setLink(link);

                            Timber.d("send one item");
                            e.onNext(item);
                        }

                        e.onComplete();
                    }
                }
        );


        Observer<TalksItem> observer = new Observer<TalksItem>() {
            @Override
            public void onSubscribe(Disposable d) {
                searchTalksItemList.clear();
            }

            @Override
            public void onNext(TalksItem value) {
                Timber.d("\n thumbnail:" + value.getThumbnailUrl());

                searchTalksItemList.add(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Message message = new Message();
                message.what = SEARCH_RESULT_DOWNLOAD_FINISHED;
                Timber.d("size:" + searchTalksItemList.size());
                handler.sendMessage(message);
            }
        };

        observable.observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

//    @OnEditorAction(R.id.search_edittext)
//    public boolean onEditorAction(TextView v, int actionId ){
//       return false;
//    }

}
