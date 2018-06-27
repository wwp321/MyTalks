package com.byron.mytalks.Talks;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TalksCollection {
    private final String TAG = "Youku";
    List<String> mImageList = new ArrayList<>();

    public void Get(){
        try {
            Document document = Jsoup.connect("http://www.youku.com").get();
            Elements links = document.select("div.ykRecommend img");
            Log.e(TAG, "Get links: " + links.outerHtml() );

            for (Element image : links) {
                String imageUrl = "http://www.youku.com" + image.attr("alt");
                Log.e(TAG, "Get image: " + imageUrl );
                mImageList.add(imageUrl);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getmImageList() {
        return mImageList;
    }
}
