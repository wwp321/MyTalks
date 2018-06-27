package com.byron.mytalks.UserInfo;

public class UserInfoItem {
    private int itemIcon;
    private String itemTitle;
    private String itemDescription;

    public UserInfoItem(int itemIcon, String itemTitle, String itemDescription) {
        this.itemIcon = itemIcon;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
