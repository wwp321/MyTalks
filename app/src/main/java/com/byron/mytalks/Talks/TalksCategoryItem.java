package com.byron.mytalks.Talks;

public class TalksCategoryItem {
    private String mCategoryTitle;
    private String mCategorySelector;

    public String getmCategoryTitle() {
        return mCategoryTitle;
    }

    public TalksCategoryItem(String mCategoryTitle, String mCategorySelector) {
        this.mCategoryTitle = mCategoryTitle;
        this.mCategorySelector = mCategorySelector;
    }

    public String getmCategorySelector() {
        return mCategorySelector;
    }
}
